package com.place4code.myfeatures;

public class MyURL {

    public static String makeShortUrl(String tempUrl) {

        String finalUrl = "";

        if (tempUrl.startsWith("http://")) {
            finalUrl = tempUrl.substring(7);
        } else if (tempUrl.startsWith("https://")) {
            finalUrl = tempUrl.substring(8);
        }
        if (finalUrl.contains("/")) {
            finalUrl = finalUrl.substring(0, finalUrl.indexOf("/"));
        }

        return finalUrl;
    }
}
