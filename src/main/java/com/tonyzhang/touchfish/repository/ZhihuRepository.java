package com.tonyzhang.touchfish.repository;

import com.tonyzhang.touchfish.entity.BaseEntity;
import com.tonyzhang.touchfish.entity.V2Entity;
import com.tonyzhang.touchfish.entity.ZhihuEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

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
        ZhihuEntity zhihu =  mongoTemplate.findOne(query , ZhihuEntity.class);
        return zhihu;
    }

    @Override
    public void updateEntity(BaseEntity baseEntity) {

    }

    @Override
    public void deleteEntityByLink(String link) {
        Query query=new Query(Criteria.where("link").is(link));
        mongoTemplate.remove(query, ZhihuEntity.class);
    }
}
