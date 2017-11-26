package com.quaksire.apprepository.adapter;

import com.quaksire.apprepository.entity.PupilEntity;
import com.quaksire.model.Pupil;

/**
 * Created by Julio.
 */

public class PupilAdapter {

    public static PupilEntity toPupilEntity(Pupil pupil) {
        PupilEntity pupilEntity = new PupilEntity();

        pupilEntity.pupilId = pupil.pupilId;
        pupilEntity.country = pupil.country;
        pupilEntity.image = pupil.image;
        pupilEntity.latitude = pupil.latitude;
        pupilEntity.longitude = pupil.longitude;
        pupilEntity.name = pupil.name;


        return pupilEntity;
    }

    public static Pupil toPupil(PupilEntity pupilEntity) {

        Pupil pupil = new Pupil();

        pupil.pupilId = pupilEntity.pupilId;
        pupil.country = pupilEntity.country;
        pupil.image = pupilEntity.image;
        pupil.latitude = pupilEntity.latitude;
        pupil.longitude = pupilEntity.longitude;
        pupil.name = pupilEntity.name;

        return pupil;
    }
}
