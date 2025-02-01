package com.mustafa.hotelreservationsystem.dao;

import com.mustafa.hotelreservationsystem.exceptions.db.DuplicateEntryException;
import com.mustafa.hotelreservationsystem.models.Entity;
import com.mustafa.hotelreservationsystem.models.Feature;

import java.util.List;

public interface FeatureDao extends Crudable{
    @Override
    void save(Entity e);
    @Override
    void update(Entity e);
    @Override
    Feature retrieve(long id);
    @Override
    Feature delete(long id);

    List<Feature> retrieveAllFeatures();
    void updateSpecifiedFeatureField(long id, String fieldName, Object fieldValue);
}
