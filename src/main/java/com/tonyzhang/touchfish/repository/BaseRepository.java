package com.tonyzhang.touchfish.repository;

import com.tonyzhang.touchfish.entity.BaseEntity;

public interface BaseRepository {
    void saveEntity(BaseEntity baseEntity);

    BaseEntity findEntityByLink(String link);

    void updateEntity(BaseEntity baseEntity);

    void deleteEntityByLink(String link);

    void dropAll();
}
