package com.mustafa.hotelreservationsystem.models;

import java.util.Objects;

public abstract class Entity {
    protected long id;

    public Entity(long id) {
        this.id = id;
    }

    public Entity() {}

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return id == entity.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
