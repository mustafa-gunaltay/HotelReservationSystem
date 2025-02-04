package com.mustafa.hotelreservationsystem.services;

import com.mustafa.hotelreservationsystem.dao.FeatureDao;
import com.mustafa.hotelreservationsystem.dao.FeatureDaoImpl;
import com.mustafa.hotelreservationsystem.exceptions.db.ZeroRowsAffectedOrReturnedException;
import com.mustafa.hotelreservationsystem.exceptions.general.EntityNotFoundByIdException;
import com.mustafa.hotelreservationsystem.models.Feature;

import java.util.List;

public class FeatureServiceImpl implements FeatureService{

    private FeatureDao featureDao;

    public FeatureServiceImpl() {
        this.featureDao = new FeatureDaoImpl();
    }

    @Override
    public void createFeature(Feature f) {
        featureDao.save(f);
    }

    @Override
    public void changeFeatureName(long id, String newFeatureName) throws EntityNotFoundByIdException{
        try {
            featureDao.updateSpecifiedFeatureField(id, "featureName", newFeatureName);
        } catch (ZeroRowsAffectedOrReturnedException e) {
            throw new EntityNotFoundByIdException("Feature not found by id", e);
        }
    }

    @Override
    public void changePrice(long id, int newPrice) throws EntityNotFoundByIdException{
        try {
            featureDao.updateSpecifiedFeatureField(id, "price", newPrice);
        } catch (ZeroRowsAffectedOrReturnedException e) {
            throw new EntityNotFoundByIdException("Feature not found by id", e);
        }
    }

    @Override
    public Feature deleteFeature(long id) throws EntityNotFoundByIdException {

        try{
            Feature deletedFeature = featureDao.delete(id);
            return deletedFeature;

        } catch (ZeroRowsAffectedOrReturnedException e) {
            throw new EntityNotFoundByIdException("Feature not found by id", e);
        }

    }


    @Override
    public List<Feature> getAllFeatures() {
        List<Feature> allFeatures = featureDao.retrieveAllFeatures();
        if ( ! allFeatures.isEmpty() ){
            return allFeatures;
        }
        else {
            System.out.println("public List<Feature> getAllFeatures() -> returned empty list");
            return allFeatures;
        }
    }

    @Override
    public Feature getFeature(long id) throws EntityNotFoundByIdException {

        try{
            Feature targetFeature = featureDao.retrieve(id);
            return targetFeature;
        }
        catch (ZeroRowsAffectedOrReturnedException e) {
            throw new EntityNotFoundByIdException("Feature not found by id", e);
        }


    }
}
