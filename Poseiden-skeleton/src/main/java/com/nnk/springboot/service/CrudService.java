package com.nnk.springboot.service;

import java.util.List;

public interface CrudService<T> {

    List<T> getAll();

    T getById(Integer id);

    void save(T t);

    void update(T t);

    void delete(Integer id);
}
