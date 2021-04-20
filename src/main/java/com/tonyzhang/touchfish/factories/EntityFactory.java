package com.tonyzhang.touchfish.factories;

import com.tonyzhang.touchfish.entity.BaseEntity;
import com.tonyzhang.touchfish.entity.HupuEntity;
import com.tonyzhang.touchfish.entity.V2Entity;
import com.tonyzhang.touchfish.entity.ZhihuEntity;

public class EntityFactory {

    public BaseEntity getEntity (String type) {
        if (type == null) {
            return null;
        } else if (type.contains("v2ex")) {
            return new V2Entity();
        } else if (type.contains("zhihu")) {
            return new ZhihuEntity();
        } else if (type.contains("hupu")) {
            return new HupuEntity();
        }
        return null;
    }
}
