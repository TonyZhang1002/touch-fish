package com.tonyzhang.touchfish.service;

import com.tonyzhang.touchfish.repository.V2Repository;
import com.tonyzhang.touchfish.repository.ZhihuRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;

import java.text.SimpleDateFormat;
import java.util.Date;


@Service
public class MainService {

    private static final Logger logger = LoggerFactory.getLogger(MainService.class);

    @Autowired
    private WebPipeline wp;

    @Autowired
    private ZhihuRepository zr;

    @Autowired
    private V2Repository vr;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(cron = "0 0/1 * * * *")
    public void scheduled() {
        logger.info("----Mission Start for v2ex : {}", dateFormat.format(new Date()));
        Spider spider = Spider.create(new V2Processor());
        spider.addUrl("http://www.v2ex.com/?tab=hot");
        spider.addPipeline(wp);
        spider.thread(5);
        spider.setExitWhenComplete(true);
        spider.start();
        spider.stop();
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void scheduledZhihu() {
        logger.info("----Mission Start for zhihu : {}", dateFormat.format(new Date()));
        Spider spider = Spider.create(new ZhihuProcessor());
        spider.addUrl("http://www.zhihu.com/billboard");
        spider.addPipeline(wp);
        spider.thread(5);
        spider.setExitWhenComplete(true);
        spider.start();
        spider.stop();
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void dropV2() {
        logger.info("Time now : {}, v2ex collection will be dropped.", dateFormat.format(new Date()));
        vr.dropAll();
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void dropZhihu() {
        logger.info("Time now : {}, zhihu collection will be dropped.", dateFormat.format(new Date()));
        zr.dropAll();
    }
}
