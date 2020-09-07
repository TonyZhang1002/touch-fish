package com.tonyzhang.touchfish.repository;

import com.tonyzhang.touchfish.entity.BaseEntity;
import com.tonyzhang.touchfish.entity.V2Entity;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class V2Repository implements BaseRepository {
    private final MongoTemplate mongoTemplate;

    public V2Repository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void saveEntity(BaseEntity v2e) {
        mongoTemplate.save(v2e);
    }

    @Override
    public V2Entity findEntityByLink(String link) {
        Query query = new Query(Criteria.where("link").is(link));
        return mongoTemplate.findOne(query, V2Entity.class);
    }

    @Override
    public V2Entity findEntityByID(int id) {
        Query query = new Query(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, V2Entity.class);
    }

    @Override
    public List<? extends BaseEntity> getAllEntities() {
        return mongoTemplate.findAll(V2Entity.class);
    }

    @Override
    public void deleteEntityByLink(String link) {
        Query query = new Query(Criteria.where("link").is(link));
        mongoTemplate.remove(query, V2Entity.class);
    }

    @Override
    public void dropAll() {
        mongoTemplate.dropCollection(V2Entity.class);
    }

}
