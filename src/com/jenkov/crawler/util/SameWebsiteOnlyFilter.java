package com.jenkov.crawler.util;


/**
 */
public class SameWebsiteOnlyFilter implements IUrlFilter {

    protected String domainUrl = null;

    public SameWebsiteOnlyFilter(String domainUrl) {
        this.domainUrl = domainUrl;
    }

    @Override
    public boolean include(String url) {
        return url.startsWith(this.domainUrl);
    }
}
