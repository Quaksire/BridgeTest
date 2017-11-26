package com.quaksire.apprepository.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.quaksire.apprepository.entity.PupilEntity;

import java.util.List;

/**
 * Created by Julio.
 */

@Dao
public interface PupilDAO {

    @Query("SELECT * FROM pupilentity")
    List<PupilEntity> getAll();

    @Query("SELECT * FROM pupilentity WHERE pupilId = :pupilId")
    List<PupilEntity> getById(int pupilId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(PupilEntity pupilEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PupilEntity... pupilEntities);

    @Update
    int update(PupilEntity pupilEntity);

    @Delete
    int delete(PupilEntity pupilEntity);

    @Query("DELETE FROM pupilentity")
    void nukeTable();
}
