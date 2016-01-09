package pl.edu.wat.wcy.pz.controller;

import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.net.*;

public class BrowserSearcher {
    public void searchString(String text) {
        try {
            openWebpage(new URL("https://www.youtube.com/results?search_query="+URLEncoder.encode(text, "UTF-8")));
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
