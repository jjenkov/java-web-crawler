package com.jenkov.crawler.util;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 */
public class UrlNormalizerTest {

    @Test
    public void testNormalize() {
        String normalizedUrl = UrlNormalizer.normalize("java/interfaces.html", "http://tutorials.jenkov.com");
        assertEquals("http://tutorials.jenkov.com/java/interfaces.html", normalizedUrl);

        normalizedUrl = UrlNormalizer.normalize("java/interfaces.html", "tutorials.jenkov.com");
        assertEquals("http://tutorials.jenkov.com/java/interfaces.html", normalizedUrl);

        normalizedUrl = UrlNormalizer.normalize("java/interfaces.html", "http://tutorials.jenkov.com/");
        assertEquals("http://tutorials.jenkov.com/java/interfaces.html", normalizedUrl);

        normalizedUrl = UrlNormalizer.normalize("java/interfaces.html", "http://tutorials.jenkov.com/texts/index.html");
        assertEquals("http://tutorials.jenkov.com/texts/java/interfaces.html", normalizedUrl);


        normalizedUrl = UrlNormalizer.normalize("/java/interfaces.html", "http://tutorials.jenkov.com/java");
        assertEquals("http://tutorials.jenkov.com/java/interfaces.html", normalizedUrl);

        normalizedUrl = UrlNormalizer.normalize("/java/interfaces.html", "http://tutorials.jenkov.com/java/");
        assertEquals("http://tutorials.jenkov.com/java/interfaces.html", normalizedUrl);

        normalizedUrl = UrlNormalizer.normalize("/java/interfaces.html", "http://tutorials.jenkov.com/java/index.html");
        assertEquals("http://tutorials.jenkov.com/java/interfaces.html", normalizedUrl);


        // urls with ../  in.
        normalizedUrl = UrlNormalizer.normalize("../java/interfaces.html", "http://tutorials.jenkov.com/html4/index.html");
        assertEquals("http://tutorials.jenkov.com/java/interfaces.html", normalizedUrl);

        normalizedUrl = UrlNormalizer.normalize("../../../java/interfaces.html", "http://tutorials.jenkov.com/html4/level1/level2/index.html");
        assertEquals("http://tutorials.jenkov.com/java/interfaces.html", normalizedUrl);


        normalizedUrl = UrlNormalizer.normalize("/flex-tetris/more/../../index.html", "http://tutorials.jenkov.com/html4/level1/level2/index.html");
        assertEquals("http://tutorials.jenkov.com/index.html", normalizedUrl);



    }


}
