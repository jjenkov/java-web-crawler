package com.jenkov.crawler.mt.io;

import com.jenkov.crawler.util.IUrlFilter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Jakob Jenkov and Emmanuel John
 */
public class CrawlerMT {

    protected IUrlFilter urlFilter     = null;
    
    protected Set<String>  crawledUrls = new HashSet<String>();
    private ExecutorService crawlService;
    protected final LinkedBlockingQueue<String> linksQueue = new LinkedBlockingQueue<>();
    protected CyclicBarrier barrier = new CyclicBarrier(2);
    
    
    public CrawlerMT(IUrlFilter urlFilter) {
        this.urlFilter = urlFilter;
    }


    public void addUrl(String url) {
        linksQueue.add(url);
    }

    public void crawl() {
        
        long startTime = System.currentTimeMillis();
        
        //create thread pool
        crawlService = Executors.newCachedThreadPool();
        int count=0;
        
        while(!linksQueue.isEmpty()) {
            String nextUrl=null;
            try {
                nextUrl = linksQueue.take();
            } catch (InterruptedException ex) {
                Logger.getLogger(CrawlerMT.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if(nextUrl==null)
                System.out.println("queue is null here");

            if (!shouldCrawlUrl(nextUrl)) continue; // skip this URL.


            this.crawledUrls.add(nextUrl);

            try {
                System.out.println(nextUrl);
                CrawlJobMT crawlJob = new CrawlJobMT(nextUrl, this);
                crawlService.submit(crawlJob);
                //neads further refactoring
                //this serves only for testing
                synchronized(this){
                    count++;
                     
                }
                if(linksQueue.isEmpty()){
                      barrier.await();
                }
                
            } catch (InterruptedException | BrokenBarrierException e) {
                System.out.println("Error crawling URL: " + nextUrl);
            }
            
                

        }
        
        //shut down when the last url have been added to the pool
        crawlService.shutdown();
        
        try {
            //wait until all threads have ended
            crawlService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException ex) {
            Logger.getLogger(CrawlerMT.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        System.out.println("URL's crawled: " + count + " in " + totalTime + " ms (avg: " + totalTime / count + ")");

    }

    private boolean shouldCrawlUrl(String nextUrl) {
        if(this.urlFilter != null && !this.urlFilter.include(nextUrl)){
            return false;
        }
        if(this.crawledUrls.contains(nextUrl)) { return false; }
        if(nextUrl.startsWith("javascript:"))  { return false; }
        if(nextUrl.startsWith("#"))            { return false; }
        if(nextUrl.endsWith(".swf"))           { return false; }
        if(nextUrl.endsWith(".pdf"))           { return false; }
        if(nextUrl.endsWith(".png"))           { return false; }
        if(nextUrl.endsWith(".gif"))           { return false; }
        if(nextUrl.endsWith(".jpg"))           { return false; }
        if(nextUrl.endsWith(".jpeg"))          { return false; }

        return true;
    }


}
