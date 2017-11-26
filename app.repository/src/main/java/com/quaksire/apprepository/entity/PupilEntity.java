package com.quaksire.apprepository.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Julio.
 */

@Entity
public class PupilEntity {

    @PrimaryKey
    public int pupilId;

    @ColumnInfo(name = "country")
    public String country;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "image")
    public String image;

    @ColumnInfo(name = "longitude")
    public double longitude;

    @ColumnInfo(name = "latitude")
    public double latitude;

}
