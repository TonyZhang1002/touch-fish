package com.tonyzhang.touchfish.service;

import com.tonyzhang.touchfish.domain.v2Entity;
import org.springframework.stereotype.Repository;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Map;

@Repository
public class webPipeline implements Pipeline {

    @Override
    public void process(ResultItems resultItems, Task task) {
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            if (entry.getKey().contains("v2Ex")) {
                v2Entity v2e = (v2Entity) entry.getValue();
                System.out.println("Entity found, title: " + v2e.getTitle() + "\t Link: " + v2e.getLink());
            }

        }
    }
}
