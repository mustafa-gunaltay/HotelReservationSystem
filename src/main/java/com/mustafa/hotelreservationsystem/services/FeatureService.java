package com.mustafa.hotelreservationsystem.services;

import com.mustafa.hotelreservationsystem.exceptions.general.EntityNotFoundByIdException;
import com.mustafa.hotelreservationsystem.models.Feature;

import java.util.List;

public interface FeatureService {
    void createFeature(Feature f);
    void changeFeatureName(long id, String newFeatureName) throws EntityNotFoundByIdException;
    void changePrice(long id, int price) throws EntityNotFoundByIdException;
    Feature deleteFeature(long id) throws EntityNotFoundByIdException;
    List<Feature> getAllFeatures();
    Feature getFeature(long id) throws EntityNotFoundByIdException;


}
