package com.quaksire.model;

import com.google.gson.annotations.Expose;

/**
 * Created by Julio.
 */

public class Pupil {

    @Expose
    public int pupilId;

    @Expose
    public String country;

    @Expose
    public String name;

    @Expose
    public String image;

    @Expose
    public double longitude;

    @Expose
    public double latitude;
}
