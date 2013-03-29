package com.jenkov.crawler.st.io;

import org.jsoup.nodes.Document;

/**
 */
public interface IPageProcessor {
    public void process(String url, Document doc);
}
