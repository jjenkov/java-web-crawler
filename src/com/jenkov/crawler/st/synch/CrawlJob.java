package com.jenkov.crawler.st.synch;

import com.jenkov.crawler.util.UrlNormalizer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 */
public class CrawlJob {

    protected Crawler crawler    = null;
    protected String  urlToCrawl = null;

    public CrawlJob(String urlToCrawl, Crawler crawler) {
        this.urlToCrawl = urlToCrawl;
        this.crawler    = crawler;
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

                    if(linkUrl.indexOf("../") > -1){
                        System.out.println("*** linkUrl:   " + linkUrl + "   -   " + baseUrl + "(" + normalizedUrl + ")");
                    }

                    //System.out.println(normalizedUrl + " : " + linkUrl);

                    this.crawler.addUrl(normalizedUrl);
                }

            } catch (IOException e) {
                throw new RuntimeException("Error connecting to URL", e);
            }
        } catch(IOException e) {
            throw new RuntimeException("Error connecting to URL", e);
        }
    }

}
