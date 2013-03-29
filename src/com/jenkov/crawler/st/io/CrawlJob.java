package com.jenkov.crawler.st.io;

import com.jenkov.crawler.util.UrlNormalizer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 */
public class CrawlJob {

    protected Crawler              crawler        = null;
    protected String               urlToCrawl     = null;
    protected List<IPageProcessor> pageProcessors = new ArrayList<IPageProcessor>();


    public CrawlJob(String urlToCrawl, IPageProcessor pageProcessor) {
        this.urlToCrawl    = urlToCrawl;
        this.crawler       = crawler;
    }

    public void addPageProcessor(IPageProcessor pageProcessor) {
        this.pageProcessors.add(pageProcessor);
    }
    
    public void crawl() throws IOException{

        URL url = new URL(this.urlToCrawl);

        URLConnection urlConnection = null;
        try {
            urlConnection = url.openConnection();

            try (InputStream input = urlConnection.getInputStream()) {

                Document doc      = Jsoup.parse(input, "UTF-8", "");
                for(IPageProcessor pageProcessor : pageProcessors ){
                    pageProcessor.process(this.urlToCrawl, doc);
                }

            } catch (IOException e) {
                throw new RuntimeException("Error connecting to URL", e);
            }
        } catch(IOException e) {
            throw new RuntimeException("Error connecting to URL", e);
        }
    }

}
