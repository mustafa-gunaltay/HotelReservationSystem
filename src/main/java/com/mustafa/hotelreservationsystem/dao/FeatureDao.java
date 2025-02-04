package com.mustafa.hotelreservationsystem.dao;

import com.mustafa.hotelreservationsystem.exceptions.db.ZeroRowsAffectedOrReturnedException;
import com.mustafa.hotelreservationsystem.models.Entity;
import com.mustafa.hotelreservationsystem.models.Feature;

import java.util.List;

public interface FeatureDao extends Crudable{
    @Override
    void save(Entity e);
    @Override
    void update(Entity e) throws ZeroRowsAffectedOrReturnedException;
    @Override
    Feature retrieve(long id) throws ZeroRowsAffectedOrReturnedException;
    @Override
    Feature delete(long id) throws ZeroRowsAffectedOrReturnedException;

    List<Feature> retrieveAllFeatures();
    void updateSpecifiedFeatureField(long id, String fieldName, Object fieldValue) throws ZeroRowsAffectedOrReturnedException;
}
