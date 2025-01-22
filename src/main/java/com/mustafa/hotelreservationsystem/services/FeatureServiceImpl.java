package com.mustafa.hotelreservationsystem.services;

import com.mustafa.hotelreservationsystem.dao.FeatureDao;
import com.mustafa.hotelreservationsystem.dao.FeatureDaoImpl;
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
    public void changeFeatureName(long id, String newFeatureName) {
        featureDao.updateSpecifiedFeatureField(id, "featureName", newFeatureName);
    }

    @Override
    public void changePrice(long id, int newPrice) {
        featureDao.updateSpecifiedFeatureField(id, "price", newPrice);
    }

    @Override
    public Feature deleteFeature(long id) {
        return featureDao.delete(id);
    }


    @Override
    public List<Feature> getAllFeatures() {
        List<Feature> allFeatures = featureDao.retrieveAllFeatures();
        return  allFeatures;
    }
}
