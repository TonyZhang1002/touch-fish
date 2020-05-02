package com.tonyzhang.touchfish.repository;

import com.tonyzhang.touchfish.entity.BaseEntity;

import java.util.List;

public interface BaseRepository {
    void saveEntity(BaseEntity baseEntity);

    BaseEntity findEntityByLink(String link);

    BaseEntity findEntityByID(int id);

    List<? extends BaseEntity> getAllEntities();

    void deleteEntityByLink(String link);

    void dropAll();
}
