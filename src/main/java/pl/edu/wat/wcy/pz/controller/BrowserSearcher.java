package pl.edu.wat.wcy.pz.controller;

import pl.edu.wat.wcy.pz.model.dao.WebsiteDao;
import pl.edu.wat.wcy.pz.model.entities.web.Website;
import pl.edu.wat.wcy.pz.view.MainWindow;

import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.*;

public class BrowserSearcher {
    private MainWindow mainWindow;

    public BrowserSearcher(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void searchString(String text, Object selectedWebsite, Object selectedTitleOrArtist) {
        java.util.List<Website> websiteList = mainWindow.websiteDao.findWebsitesWithTitle(selectedWebsite.toString());

        try {
            if(Objects.equals(selectedTitleOrArtist.toString(), "Title")){
                openWebpage(new URL(websiteList.get(0).getUrlTitle()+URLEncoder.encode(text, "UTF-8")));
            }else{
                openWebpage(new URL(websiteList.get(0).getUrlArtist()+URLEncoder.encode(text, "UTF-8")));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void searchTitle(String title, Object selectedWebsite) {
        java.util.List<Website> websiteList = mainWindow.websiteDao.findWebsitesWithTitle(selectedWebsite.toString());

        try {
            openWebpage(new URL(websiteList.get(0).getUrlTitle()+URLEncoder.encode(title, "UTF-8")));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void openUrl(String url){
        try {
            openWebpage(new URL(url));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void openWebpage(URI uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void openWebpage(URL url) {
        try {
            openWebpage(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


}
