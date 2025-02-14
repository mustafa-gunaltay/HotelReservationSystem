package com.mustafa.hotelreservationsystem.ui.utils;


@FunctionalInterface
public interface SceneInitializer<T> {
    void initialize(T controller);
}

