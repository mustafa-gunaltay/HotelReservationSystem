package com.mustafa.hotelreservationsystem.dao;

import com.mustafa.hotelreservationsystem.models.Entity;

public interface Crudable {

    void save(Entity e) throws Exception;
    void update(Entity e) throws Exception;
    Entity retrieve(long id) throws Exception;
    Entity delete(long id) throws Exception;
}
