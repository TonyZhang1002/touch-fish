package com.tonyzhang.touchfish.factories;

import com.tonyzhang.touchfish.repository.BaseRepository;
import com.tonyzhang.touchfish.repository.HupuRepository;
import com.tonyzhang.touchfish.repository.V2Repository;
import com.tonyzhang.touchfish.repository.ZhihuRepository;
import org.springframework.stereotype.Repository;

@Repository
public class RepositoryFactory {

    private final V2Repository v2Repository;

    private final ZhihuRepository zhihuRepository;

    private final HupuRepository hupuRepository;

    public RepositoryFactory(V2Repository v2Repository, ZhihuRepository zhihuRepository, HupuRepository hupuRepository) {
        this.v2Repository = v2Repository;
        this.zhihuRepository = zhihuRepository;
        this.hupuRepository = hupuRepository;
    }

    public BaseRepository getRepository (String type) {
        if(type == null){
            return null;
        } else if (type.contains("v2ex")) {
            return v2Repository;
        } else if (type.contains("zhihu")) {
            return zhihuRepository;
        } else if (type.contains("hupu")) {
            return hupuRepository;
        }
        return null;
    }
}
