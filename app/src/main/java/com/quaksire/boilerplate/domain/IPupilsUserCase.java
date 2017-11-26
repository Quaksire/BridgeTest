package com.quaksire.boilerplate.domain;

import com.quaksire.model.Pupil;

import java.util.List;

import rx.Observable;

/**
 * Created by Julio.
 */

public interface IPupilsUserCase {

    Observable<List<Pupil>> getAll();

    Observable<List<Pupil>> get(int pupilId);

    void create(Pupil pupil);

    void update(Pupil pupil);

    void remove(Pupil pupil);

}
