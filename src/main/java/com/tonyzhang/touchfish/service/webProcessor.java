package com.tonyzhang.touchfish.service;

import com.tonyzhang.touchfish.domain.v2Entity;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

public class webProcessor implements PageProcessor {

    private Site site = Site.me()
            .setDomain("www.v2ex.com")
            .setSleepTime(100)
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");

    public static final String list = "http://www.v2ex.com/\\w+";

    @Override
    public void process(Page page) {
        for (int i = 3; i < 8; i++) {
            String xPath = "//*[@id=\"Main\"]/div[2]/div[" + i +"]/table/tbody/tr/td[3]/span[1]/a";
            String fullContent = page.getHtml().xpath(xPath).toString();
            String title = fullContent.replaceAll("<a[^>]*>", "").replaceAll("</a>", "");
            String link = page.getHtml().xpath(xPath).links().toString();
            v2Entity v2e = new v2Entity();
            v2e.setTitle(title);
            v2e.setInfo(title);
            v2e.setLink(link);
            page.putField("v2Ex" + title, v2e);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider spider = Spider.create(new webProcessor());
        spider.addUrl("http://www.v2ex.com/?tab=tech");
        spider.addPipeline(new webPipeline());
        spider.thread(5);
        spider.setExitWhenComplete(true);
        spider.start();
    }
}
