package com.mustafa.hotelreservationsystem.services;

import com.mustafa.hotelreservationsystem.models.Feature;

import java.util.List;

public interface FeatureService {
    void createFeature(Feature f);
    void changeFeatureName(long id, String newFeatureName);
    void changePrice(long id, int price);
    Feature deleteFeature(long id);
    List<Feature> getAllFeatures();
    Feature getFeature(long id);


}
