package com.jenkov.crawler.st.io;

import com.jenkov.crawler.util.SameWebsiteOnlyFilter;

/**
 */
public class CrawlerMain {

    public static void main(String[] args) {

        /*if(args.length < 1) {
            System.err.println("Provide a URL as argument to the CrawlerMain class.");
            return;
        }*/

        //String url = args[0];
        String url ="http://tutorials.jenkov.com";
        Crawler crawler  = new Crawler(new SameWebsiteOnlyFilter(url));
        crawler.addUrl(url);
        crawler.crawl();
    }
}
