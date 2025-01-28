package com.mustafa.hotelreservationsystem.services;

import com.mustafa.hotelreservationsystem.dao.FeatureDao;
import com.mustafa.hotelreservationsystem.dao.FeatureDaoImpl;
import com.mustafa.hotelreservationsystem.models.Feature;
import com.mustafa.hotelreservationsystem.models.Reservation;

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
    public void changeFeatureName(long id, String newFeatureName) {
        featureDao.updateSpecifiedFeatureField(id, "featureName", newFeatureName);
    }

    @Override
    public void changePrice(long id, int newPrice) {
        featureDao.updateSpecifiedFeatureField(id, "price", newPrice);
    }

    @Override
    public Feature deleteFeature(long id) {
        Feature deletedFeature = featureDao.delete(id);
        return deletedFeature;
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
    public Feature getFeature(long id) {
        Feature targetFeature = featureDao.retrieve(id);
        if (targetFeature != null){
            return targetFeature;
        }
        else{
            System.out.println("public Feature getFeature(long id) -> returned null");
            return null;
        }
    }
}
