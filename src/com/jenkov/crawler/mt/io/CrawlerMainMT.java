package com.jenkov.crawler.mt.io;

import com.jenkov.crawler.util.SameWebsiteOnlyFilter;

/**
 * @author Jakob Jenkov and Emmanuel John
 */
public class CrawlerMainMT {

    public static void main(String[] args) {

        /*if(args.length < 1) {
            System.err.println("Provide a URL as argument to the CrawlerMainMT class.");
            return;
        }*/

        //String url = args[0];
        String url = "http://tutorials.jenkov.com";
        CrawlerMT crawler  = new CrawlerMT(new SameWebsiteOnlyFilter(url));
        crawler.addUrl(url);
        crawler.crawl();
    }
}
