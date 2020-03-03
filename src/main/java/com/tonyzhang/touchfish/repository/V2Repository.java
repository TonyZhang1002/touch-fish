package com.tonyzhang.touchfish.repository;

import com.tonyzhang.touchfish.entity.BaseEntity;
import com.tonyzhang.touchfish.entity.V2Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class V2Repository implements BaseRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void saveEntity(BaseEntity v2e) {
        mongoTemplate.save(v2e);
    }

    @Override
    public V2Entity findEntityByLink(String link) {
        Query query = new Query(Criteria.where("link").is(link));
        V2Entity v2e = mongoTemplate.findOne(query, V2Entity.class);
        return v2e;
    }

    @Override
    public void updateEntity(BaseEntity baseEntity) {

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
