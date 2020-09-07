package com.tonyzhang.touchfish.entity;

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
