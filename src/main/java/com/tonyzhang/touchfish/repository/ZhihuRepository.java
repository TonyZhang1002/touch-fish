package com.tonyzhang.touchfish.repository;

import com.tonyzhang.touchfish.entity.BaseEntity;
import com.tonyzhang.touchfish.entity.ZhihuEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ZhihuRepository implements BaseRepository{
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void saveEntity(BaseEntity zhihu) {
        mongoTemplate.save(zhihu);
    }

    @Override
    public ZhihuEntity findEntityByLink(String link) {
        Query query=new Query(Criteria.where("link").is(link));
        return mongoTemplate.findOne(query , ZhihuEntity.class);
    }

    @Override
    public BaseEntity findEntityByID(int id) {
        Query query = new Query(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, ZhihuEntity.class);
    }

    @Override
    public List<? extends BaseEntity> getAllEntities() {
        return mongoTemplate.findAll(ZhihuEntity.class);
    }

    @Override
    public void deleteEntityByLink(String link) {
        Query query=new Query(Criteria.where("link").is(link));
        mongoTemplate.remove(query, ZhihuEntity.class);
    }

    @Override
    public void dropAll() {
        mongoTemplate.dropCollection(ZhihuEntity.class);
    }
}
