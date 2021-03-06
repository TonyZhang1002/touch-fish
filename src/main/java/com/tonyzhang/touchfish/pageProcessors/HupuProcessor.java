package com.tonyzhang.touchfish.pageProcessors;

import com.tonyzhang.touchfish.entity.BaseEntity;
import com.tonyzhang.touchfish.factories.EntityFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class HupuProcessor implements PageProcessor {
    // Singleton Instance
    private static HupuProcessor instance = new HupuProcessor();
    // Make construction method private
    private HupuProcessor() {}
    // Public method to get the single object
    public static HupuProcessor getInstance() {
        return instance;
    }
    private Site site = Site.me()
            .setDomain("bbs.hupu.com")
            .setSleepTime(100)
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");

    @Override
    public void process(Page page) {
        for (int i = 1; i < 21; i++) {
            // Get three xPaths from the web page
            String titleXPath = "/html/body/div[4]/div[4]/div/div[2]/div[1]/ul/li[" + i + "]/span[1]/a/span";
            String infoXPath = "/html/body/div[4]/div[4]/div/div[2]/div[1]/ul/li[" + i + "]/span[1]/em";
            String linkXPath = "/html/body/div[4]/div[4]/div/div[2]/div[1]/ul/li[" + i + "]/span[1]/a";
            // Get the string and get rid of the html elements
            String fullTitle = page.getHtml().xpath(titleXPath).toString();
            String title = fullTitle == null ? "" : fullTitle.replaceAll("<span[^>]*>", "").replaceAll("</span>", "");
            String fullInfo = page.getHtml().xpath(infoXPath).toString();
            String info = fullInfo == null ? "" : fullInfo.replaceAll("<em[^>]*>", "").replaceAll("</em>", "");
            // Get the link
            String fullLink = page.getHtml().xpath(linkXPath).links().toString();
            String link = fullLink == null ? "" : fullLink.split("#")[0];
            // Create a new entity and put crawled info in
            EntityFactory entityFactory = new EntityFactory();
            BaseEntity hupu = entityFactory.getEntity("hupu");
            hupu.setTitle(title);
            hupu.setInfo(info);
            hupu.setLink(link);
            hupu.setID(i);
            // Feed to pipeline
            page.putField("hupu:" + title, hupu);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
