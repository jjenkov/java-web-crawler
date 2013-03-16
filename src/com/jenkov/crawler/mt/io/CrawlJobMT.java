package com.jenkov.crawler.mt.io;

import com.jenkov.crawler.util.UrlNormalizer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Jakob Jenkov and Emmanuel John
 */
public class CrawlJobMT implements Runnable {

    protected CrawlerMT crawler    = null;
    protected String  urlToCrawl = null;
    
    public CrawlJobMT(String urlToCrawl, CrawlerMT crawler) {
        this.urlToCrawl = urlToCrawl;
        this.crawler    = crawler;
    }
    @Override
    public void run(){
        try{
            crawl();
        }catch(Exception ex){
            
        }
    }
    public void crawl() throws IOException{

        URL url = new URL(this.urlToCrawl);

        URLConnection urlConnection = null;
        try {
            urlConnection = url.openConnection();

            try (InputStream input = urlConnection.getInputStream()) {

                Document doc      = Jsoup.parse(input, "UTF-8", "");
                Elements elements = doc.select("a");

                String baseUrl = url.toExternalForm();
                for(Element element : elements){
                    String linkUrl       = element.attr("href");
                    String normalizedUrl = UrlNormalizer.normalize(linkUrl, baseUrl);
                    crawler.linksQueue.add(normalizedUrl);
                    
                    System.out.println(" - "+normalizedUrl);
                    
                }

            } catch (IOException e) {
                throw new RuntimeException("Error connecting to URL", e);
            } 
        } catch(IOException e) {
            throw new RuntimeException("Error connecting to URL", e);
        }
    }

}
