package com.jenkov.crawler.mt.io;

import org.jsoup.nodes.Document;

/**
 * An interface for components that want to process the HTML page after it
 * has been downloaded and parsed.
 */
public interface IPageProcessor {

    public void process(Document doc);

}
