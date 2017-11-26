package com.quaksire.apprepository;

import android.arch.persistence.room.Room;
import android.content.Context;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.quaksire.apprepository.dao.PupilDAO;
import com.quaksire.apprepository.db.AppDatabase;
import com.quaksire.apprepository.entity.PupilEntity;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;


/**
 * Created by Julio.
 */

@RunWith(AndroidJUnit4.class)
public class DbDaoTest {
    private PupilDAO mUserDao;
    private AppDatabase mDb;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mUserDao = mDb.pupilDAO();
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void createPupilAndReadInList() throws Exception {
        PupilEntity pupil = new PupilEntity();
        pupil.pupilId = 1;
        pupil.name = "Name";
        pupil.country = "UK";
        pupil.image = "";
        pupil.latitude = 0L;
        pupil.longitude = 0L;

        mUserDao.insert(pupil);
        List<PupilEntity> pupils = mUserDao.getAll();
        Assert.assertEquals(1, pupils.size());
    }

    @Test
    public void createPupilAndDelete() throws Exception {
        PupilEntity pupil = new PupilEntity();
        pupil.pupilId = 1;
        pupil.name = "Name";
        pupil.country = "UK";
        pupil.image = "";
        pupil.latitude = 0L;
        pupil.longitude = 0L;

        mUserDao.insert(pupil);
        List<PupilEntity> pupils = mUserDao.getAll();
        Assert.assertEquals(1, pupils.size());

        mUserDao.delete(pupil);

        pupils = mUserDao.getAll();
        Assert.assertEquals(0, pupils.size());
    }
}
