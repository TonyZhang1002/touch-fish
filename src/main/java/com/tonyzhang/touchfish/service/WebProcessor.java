package com.tonyzhang.touchfish.service;

import com.tonyzhang.touchfish.entity.V2Entity;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class WebProcessor implements PageProcessor {

    private Site site = Site.me()
            .setDomain("www.v2ex.com")
            .setSleepTime(100)
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");

    @Override
    public void process(Page page) {
        for (int i = 2; i < 30; i++) {
            // Get three xPaths from the web page
            String titleXPath = "//*[@id=\"Main\"]/div[2]/div[" + i + "]/table/tbody/tr/td[3]/span[1]/a";
            String infoXPath = "//*[@id=\"Main\"]/div[2]/div[" + i + "]/table/tbody/tr/td[4]/a";
            String authorXPath = "//*[@id=\"Main\"]/div[2]/div[" + i + "]/table/tbody/tr/td[3]/span[2]/strong[1]/a";
            // Get the string and get rid of the html elements
            String fullTitle = page.getHtml().xpath(titleXPath).toString();
            String title = fullTitle == null ? "" : fullTitle.replaceAll("<a[^>]*>", "").replaceAll("</a>", "");
            String fullInfo = page.getHtml().xpath(infoXPath).toString();
            String info = fullInfo == null ? "" : fullInfo.replaceAll("<a[^>]*>", "").replaceAll("</a>", "");
            String fullAuthor = page.getHtml().xpath(authorXPath).toString();
            String author = fullAuthor == null ? "" : fullAuthor.replaceAll("<a[^>]*>", "").replaceAll("</a>", "");
            // Get the link
            String fullLink = page.getHtml().xpath(titleXPath).links().toString();
            String link = fullLink == null ? "" : fullLink.split("#")[0];
            // Create a new entity and put crawled info in
            V2Entity v2e = new V2Entity();
            v2e.setTitle(title);
            v2e.setInfo(info);
            v2e.setAuthor(author);
            v2e.setLink(link);
            // Feed to pipeline
            page.putField("v2Ex" + title, v2e);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider spider = Spider.create(new WebProcessor());
        spider.addUrl("http://www.v2ex.com/?tab=hot");
        spider.addPipeline(new WebPipeline());
        spider.thread(5);
        spider.setExitWhenComplete(true);
        spider.start();
    }
}
