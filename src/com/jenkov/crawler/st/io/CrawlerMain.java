package com.jenkov.crawler.st.io;

import com.jenkov.crawler.util.SameWebsiteOnlyFilter;

/**
 * This class is an example of how to use the Crawler class. You should
 * not expect to use this class as it is. Use the Crawler class directly
 * from your own code.
 */
public class CrawlerMain {

    public static void main(String[] args) {

        /*if(args.length < 1) {
            System.err.println("Provide a URL as argument to the CrawlerMain class.");
            return;
        }*/

        //String url = args[0];
        String url ="http://tutorials.jenkov.com";

        Crawler crawler  = new Crawler();
        crawler.setUrlFilter(new SameWebsiteOnlyFilter(url));
        crawler.setPageProcessor(null); // set an IPageProcessor instance here.
        crawler.addUrl(url);

        crawler.crawl();
    }
}
