package com.tonyzhang.touchfish.service;

import com.tonyzhang.touchfish.entity.V2Entity;
import com.tonyzhang.touchfish.repository.V2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Map;

@Repository
public class WebPipeline implements Pipeline {

    @Autowired
    V2Repository v2Repository;

    @Override
    public void process(ResultItems resultItems, Task task) {
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            if (entry.getKey().contains("v2Ex")) {
                V2Entity v2e = (V2Entity) entry.getValue();
                System.out.println("Entity found, title: " + v2e.getTitle() + "\t Link: " + v2e.getLink());
                v2Repository.saveEntity(v2e);
            }
        }
    }
}
