package com.jenkov.crawler.util;

/**
 *  Capable of normalizing a relative URL using on a base URL.
 *
 */
public class UrlNormalizer {

    public static String normalize(String targetUrl, String baseUrl) {
        if(targetUrl.startsWith("http://"))                   return targetUrl;
        if(targetUrl.startsWith("https://"))                  return targetUrl;
        if(targetUrl.toLowerCase().startsWith("javascript:")) return targetUrl;

        //System.out.println("*** Normalizing " + targetUrl + " using base url " + baseUrl);

        StringBuilder builder = new StringBuilder();

        if(!baseUrl.startsWith("http://")) {
            builder.append("http://");
        }

        //if is relative to root of server.
        if(targetUrl.startsWith("/")) {

            int endOfDomain = baseUrl.indexOf("/", 7);
            if(endOfDomain == -1){
                builder.append(baseUrl);
            } else {
                builder.append(baseUrl.substring(0, endOfDomain));
            }
            builder.append(targetUrl);

        } else {
            //find last directory of base url
            int lastDirSeparatorIndex = baseUrl.lastIndexOf("/");
            if(lastDirSeparatorIndex > 6){
                builder.append(baseUrl.substring(0, lastDirSeparatorIndex));
            } else {
                builder.append(baseUrl);
            }
            builder.append('/');
            builder.append(targetUrl);
        }

        // delete any fragment identifiers
        int fragmentIndex = builder.indexOf("#");
        if(fragmentIndex > -1) {
            builder.delete(fragmentIndex, builder.length());
        }

        // delete any internal dir up ../ if any are inside the URL, and not just in the start.
        int indexOfDirUp = builder.indexOf("../");
        while(indexOfDirUp > -1){
            int indexOfLastDirBeforeDirUp = builder.lastIndexOf("/", indexOfDirUp - 2);
            if(indexOfLastDirBeforeDirUp > -1 ) {
                builder.delete(indexOfLastDirBeforeDirUp +1, indexOfDirUp + 3);
            }
            indexOfDirUp = builder.indexOf("../");
        }

        return builder.toString();

    }
}
