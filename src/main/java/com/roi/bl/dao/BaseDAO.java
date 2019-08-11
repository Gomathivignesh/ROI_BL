package com.roi.bl.dao;


import com.roi.bl.model.BaseEntity;

public interface BaseDAO<T extends BaseEntity> {

       T getById(T t, Long id);

       Long create(T obj);

      void update(T obj);

      void delete(T obj);





}
