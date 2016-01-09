package pl.edu.wat.wcy.pz.controller;

import pl.edu.wat.wcy.pz.model.dao.WebsiteDao;
import pl.edu.wat.wcy.pz.model.entities.web.Website;

import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.*;

public class BrowserSearcher {
    private WebsiteDao websiteDao = new WebsiteDao();

    public void searchString(String text, Object selectedItem) {
        java.util.List<Website> websiteList = websiteDao.findWebsitesWithTitle(selectedItem.toString());

        try {
            openWebpage(new URL(websiteList.get(0).getUrlTitle()+URLEncoder.encode(text, "UTF-8")));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
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
