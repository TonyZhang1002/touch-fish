package com.tonyzhang.touchfish.service;

import com.tonyzhang.touchfish.entity.V2Entity;
import com.tonyzhang.touchfish.repository.V2Repository;
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

    @Override
    public void process(ResultItems resultItems, Task task) {
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            if (entry.getKey().contains("v2Ex")) {
                V2Entity v2e = (V2Entity) entry.getValue();
                if (v2Repository.findEntityByLink(v2e.getLink()) == null && !v2e.getLink().equals("")) {
                    logger.info("Entity found, not in database, entity will be saved, title: {} \t Link: {}", v2e.getTitle(), v2e.getLink());
                    v2Repository.saveEntity(v2e);
                } else {
                    logger.info("Entity already stored in database or not found in the web page, aborted.");
                }
            }
        }
    }
}
