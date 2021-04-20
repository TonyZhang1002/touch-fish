package com.tonyzhang.touchfish.pageProcessors;

import com.tonyzhang.touchfish.entity.BaseEntity;
import com.tonyzhang.touchfish.entity.EntityFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;

import java.util.List;

public class ZhihuProcessor implements PageProcessor {

    private static final Logger logger = LoggerFactory.getLogger(ZhihuProcessor.class);

    // Singleton Instance
    private static ZhihuProcessor instance = new ZhihuProcessor();
    // Make construction method private
    private ZhihuProcessor() {}
    // Public method to get the single object
    public static ZhihuProcessor getInstance() {
        return instance;
    }
    private Site site = Site.me()
            .setDomain("www.zhihu.com")
            .setSleepTime(100)
            .setUserAgent("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:73.0) Gecko/20100101 Firefox/73.0");

    @Override
    public void process(Page page) {
        String origin = page.getHtml().xpath("//*[@id=\"js-initialData\"]").toString().replaceAll("<script[^>]*>", "").replaceAll("</script>", "");
        List<String> hots = new JsonPathSelector("$.initialState.topstory.hotList[*]").selectList(origin);
        int index = 1;
        for (String hot : hots) {
            String title = new JsonPathSelector("$.target.titleArea.text").select(hot);
            String info = new JsonPathSelector("$.target.metricsArea.text").select(hot);
            String link = new JsonPathSelector("$.target.link.url").select(hot);
            logger.info("Got entity in zhihu processor, Title: {} \t Link: {}", title, link);
            EntityFactory entityFactory = new EntityFactory();
            BaseEntity zhihu = entityFactory.getEntity("zhihu");
            zhihu.setTitle(title);
            zhihu.setInfo(info);
            zhihu.setLink(link);
            zhihu.setID(index++);
            // Feed to pipeline
            page.putField("zhihu:" + title, zhihu);
        }

    }

    @Override
    public Site getSite() {
        return site;
    }

    // For test use only
//    public static void main(String[] args) {
//        Spider spider = Spider.create(new ZhihuProcessor());
//        spider.addUrl("http://www.zhihu.com/billboard");
//        spider.thread(5);
//        spider.setExitWhenComplete(true);
//        spider.start();
//    }
}
