package com.tonyzhang.touchfish.service;

import com.tonyzhang.touchfish.entity.BaseEntity;
import com.tonyzhang.touchfish.repository.BaseRepository;
import com.tonyzhang.touchfish.factories.RepositoryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Map;

@Repository
public class WebPipeline implements Pipeline {

    private static final Logger logger = LoggerFactory.getLogger(WebPipeline.class);

    private final RepositoryFactory repositoryFactory;

    public WebPipeline(RepositoryFactory repositoryFactory) {
        this.repositoryFactory = repositoryFactory;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            String type = entry.getKey().split(":")[0];
            BaseRepository repositoryInstance = repositoryFactory.getRepository(type);
            BaseEntity entityInstance = (BaseEntity) entry.getValue();

            if (repositoryInstance.findEntityByID(entityInstance.getID()) == null) {
                logger.info("Entity found {}, not in database, entity will be saved, title: {} \t Link: {}",
                        type, entityInstance.getTitle(), entityInstance.getLink());
                repositoryInstance.saveEntity(entityInstance);
            } else if (!repositoryInstance.findEntityByID(entityInstance.getID()).getLink().equals(entityInstance.getLink())) {
                logger.info("Entity found {}, previous info in database, entity will be updated, new title: {} \t Link: {}",
                        type, entityInstance.getTitle(), entityInstance.getLink());
                repositoryInstance.saveEntity(entityInstance);
            } else {
                logger.info("Entity already stored in database or not found in the web page, aborted.");
            }
        }
    }
}
