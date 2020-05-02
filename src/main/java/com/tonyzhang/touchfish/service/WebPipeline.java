package com.tonyzhang.touchfish.service;

import com.tonyzhang.touchfish.entity.HupuEntity;
import com.tonyzhang.touchfish.entity.V2Entity;
import com.tonyzhang.touchfish.entity.ZhihuEntity;
import com.tonyzhang.touchfish.repository.HupuRepository;
import com.tonyzhang.touchfish.repository.V2Repository;
import com.tonyzhang.touchfish.repository.ZhihuRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Map;

@Repository
public class WebPipeline implements Pipeline {

    private static final Logger logger = LoggerFactory.getLogger(WebPipeline.class);

    @Autowired
    V2Repository v2Repository;

    @Autowired
    ZhihuRepository zhihuRepository;

    @Autowired
    HupuRepository hupuRepository;

    @Override
    public void process(ResultItems resultItems, Task task) {
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            if (entry.getKey().contains("v2Ex")) {
                V2Entity v2e = (V2Entity) entry.getValue();
                if (v2Repository.findEntityByID(v2e.getID()) == null) {
                    logger.info("Entity found (v2ex), not in database, entity will be saved, title: {} \t Link: {}", v2e.getTitle(), v2e.getLink());
                    v2Repository.saveEntity(v2e);
                } else if (!v2Repository.findEntityByID(v2e.getID()).getLink().equals(v2e.getLink())) {
                    logger.info("Entity found (v2ex), previous info in database, entity will be updated, new title: {} \t Link: {}", v2e.getTitle(), v2e.getLink());
                    v2Repository.saveEntity(v2e);
                } else {
                    logger.info("Entity already stored in database or not found in the web page, aborted.");
                }
            } else if (entry.getKey().contains("zhihu")) {
                ZhihuEntity zhihu = (ZhihuEntity) entry.getValue();
                if (zhihuRepository.findEntityByID(zhihu.getID()) == null) {
                    logger.info("Entity found (zhihu), not in database, entity will be saved, title: {} \t Link: {}", zhihu.getTitle(), zhihu.getLink());
                    zhihuRepository.saveEntity(zhihu);
                } else if (!zhihuRepository.findEntityByID(zhihu.getID()).getLink().equals(zhihu.getLink())) {
                    logger.info("Entity found (zhihu), previous info in database, entity will be updated, new title: {} \t Link: {}", zhihu.getTitle(), zhihu.getLink());
                    v2Repository.saveEntity(zhihu);
                } else {
                    logger.info("Entity already stored in database or not found in the web page, aborted.");
                }
            } else if (entry.getKey().contains("hupu")) {
                HupuEntity hupu = (HupuEntity) entry.getValue();
                if (hupuRepository.findEntityByID(hupu.getID()) == null) {
                    logger.info("Entity found (hupu), not in database, entity will be saved, title: {} \t Link: {}", hupu.getTitle(), hupu.getLink());
                    hupuRepository.saveEntity(hupu);
                } else if (!hupuRepository.findEntityByID(hupu.getID()).getLink().equals(hupu.getLink())) {
                    logger.info("Entity found (hupu), previous info in database, entity will be updated, new title: {} \t Link: {}", hupu.getTitle(), hupu.getLink());
                    v2Repository.saveEntity(hupu);
                }  else {
                    logger.info("Entity already stored in database or not found in the web page, aborted.");
                }
            }
        }
    }
}
