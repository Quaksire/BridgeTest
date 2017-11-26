package com.quaksire.apprepository.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.quaksire.apprepository.dao.PupilDAO;
import com.quaksire.apprepository.entity.PupilEntity;

/**
 * Created by Julio.
 */

@Database(entities = {PupilEntity.class}, version = 1, exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {

    /**
     * Operations with Pupil Table
     * @return PupilDAO
     */
    public abstract PupilDAO pupilDAO();
}
