package com.mustafa.hotelreservationsystem.dao;

import com.mustafa.hotelreservationsystem.models.Entity;

public interface Crudable {

    void save(Entity e);
    void update(Entity e);
    Entity retrieve(long id);
    Entity delete(long id);
}
