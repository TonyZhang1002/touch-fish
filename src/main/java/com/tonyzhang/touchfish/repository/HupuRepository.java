package com.tonyzhang.touchfish.repository;

import com.tonyzhang.touchfish.entity.BaseEntity;
import com.tonyzhang.touchfish.entity.HupuEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HupuRepository implements BaseRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void saveEntity(BaseEntity hupu) {
        mongoTemplate.save(hupu);
    }

    @Override
    public HupuEntity findEntityByLink(String link) {
        Query query = new Query(Criteria.where("link").is(link));
        return mongoTemplate.findOne(query, HupuEntity.class);
    }

    @Override
    public BaseEntity findEntityByID(int id) {
        Query query = new Query(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, HupuEntity.class);
    }

    @Override
    public List<? extends BaseEntity> getAllEntities() {
        return mongoTemplate.findAll(HupuEntity.class);
    }

    @Override
    public void deleteEntityByLink(String link) {
        Query query = new Query(Criteria.where("link").is(link));
        mongoTemplate.remove(query, HupuEntity.class);
    }

    @Override
    public void dropAll() {
        mongoTemplate.dropCollection(HupuEntity.class);
    }
}
