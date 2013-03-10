package com.jenkov.crawler.st.synch;

import com.jenkov.crawler.util.SameWebsiteOnlyFilter;

/**
 */
public class CrawlerMain {

    public static void main(String[] args) {

        if(args.length < 1) {
            System.err.println("Provide a URL as argument to the CrawlerMain class.");
            return;
        }

        String url = args[0];

        Crawler crawler  = new Crawler(new SameWebsiteOnlyFilter("http://tutorials.jenkov.com"));
        crawler.addUrl(url);
        crawler.crawl();
    }
}
