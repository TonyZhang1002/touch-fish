package com.tonyzhang.touchfish.service;

import com.tonyzhang.touchfish.entity.BaseEntity;
import com.tonyzhang.touchfish.repository.HupuRepository;
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
import java.util.List;


@Service
public class MainService {

    private static final Logger logger = LoggerFactory.getLogger(MainService.class);

    @Autowired
    private WebPipeline wp;

    @Autowired
    private ZhihuRepository zhihuRepository;

    @Autowired
    private V2Repository v2Repository;

    @Autowired
    private HupuRepository hupuRepository;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(cron = "0 */5 * * * *")
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

    @Scheduled(cron = "20 */5 * * * *")
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

    @Scheduled(cron = "40 */5 * * * *")
    public void scheduledHupu() {
        logger.info("----Mission Start for hupu : {}", dateFormat.format(new Date()));
        Spider spider = Spider.create(new HupuProcessor());
        spider.addUrl("http://bbs.hupu.com/all-gambia");
        spider.addPipeline(wp);
        spider.thread(5);
        spider.setExitWhenComplete(true);
        spider.start();
        spider.stop();
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void dropV2() {
        logger.info("Time now : {}, v2ex collection will be dropped.", dateFormat.format(new Date()));
        v2Repository.dropAll();
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void dropZhihu() {
        logger.info("Time now : {}, zhihu collection will be dropped.", dateFormat.format(new Date()));
        zhihuRepository.dropAll();
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void dropHupu() {
        logger.info("Time now : {}, hupu collection will be dropped.", dateFormat.format(new Date()));
        hupuRepository.dropAll();
    }

    public List<? extends BaseEntity> getAllEntities(String website) {
        logger.info("Trying to retrieve entities from {}", website);
        switch (website) {
            case "hupu": return hupuRepository.getAllEntities();
            case "zhihu": return zhihuRepository.getAllEntities();
            case "v2": return v2Repository.getAllEntities();
            default:
                logger.info("Error in retrieving entities, wrong website name {}", website);
                return null;
        }
    }
}
