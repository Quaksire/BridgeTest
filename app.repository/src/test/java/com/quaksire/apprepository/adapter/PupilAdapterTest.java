package com.quaksire.apprepository.adapter;

import com.quaksire.apprepository.entity.PupilEntity;
import com.quaksire.model.Pupil;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Created by Julio.
 */


@RunWith(PowerMockRunner.class)
public class PupilAdapterTest {

    @Test
    public void canAdaptPupilToPupilEntity() {
        Pupil pupil = new Pupil();
        pupil.name = "Name";
        pupil.country = "Country";
        pupil.image = "Image";
        pupil.latitude = 1.0;
        pupil.longitude = 2.0;

        PupilEntity pupilEntity = PupilAdapter.toPupilEntity(pupil);

        Assert.assertNotNull(pupilEntity);
        Assert.assertEquals(pupil.name, pupilEntity.name);
        Assert.assertEquals(pupil.country, pupilEntity.country);
        Assert.assertEquals(pupil.image, pupilEntity.image);
        Assert.assertTrue(pupil.latitude == pupilEntity.latitude);
        Assert.assertTrue(pupil.longitude == pupilEntity.longitude);
    }

    @Test
    public void canAdaptPupilEntityToPupil() {
        PupilEntity pupilEntity = new PupilEntity();
        pupilEntity.name = "Name";
        pupilEntity.country = "Country";
        pupilEntity.image = "Image";
        pupilEntity.latitude = 1.0;
        pupilEntity.longitude = 2.0;

        Pupil pupil = PupilAdapter.toPupil(pupilEntity);

        Assert.assertNotNull(pupil);
        Assert.assertEquals(pupilEntity.name, pupil.name);
        Assert.assertEquals(pupilEntity.country, pupil.country);
        Assert.assertEquals(pupilEntity.image, pupil.image);
        Assert.assertTrue(pupilEntity.latitude == pupil.latitude);
        Assert.assertTrue(pupilEntity.longitude == pupil.longitude);
    }
}
