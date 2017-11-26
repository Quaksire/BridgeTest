package com.quaksire.boilerplate.domain;

import com.quaksire.applog.LogManager;
import com.quaksire.apprepository.IRepository;
import com.quaksire.apprepository.specification.ISpecification;
import com.quaksire.model.Pupil;

import java.util.List;

import javax.inject.Named;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

/**
 * Created by Julio.
 */

public class PupilsUserCase implements IPupilsUserCase {

    private final String TAG = "APP-PupilsUserCase";

    /**
     * Data repository
     */
    private IRepository<Pupil> mRepository;

    public PupilsUserCase(@Named("AppDataStore") IRepository<Pupil> repository) {
        LogManager.i(TAG, "Constructor()");
        this.mRepository = repository;
    }

    @Override
    public Observable<List<Pupil>> getAll() {
        LogManager.i(TAG, "getAll()");
        return this.mRepository.query(new ISpecification() {
            @Override
            public String whereClause() {
                return ISpecification.ALL;
            }

            @Override
            public int id() {
                return 0;
            }
        });
    }

    @Override
    public Observable<List<Pupil>> get(final int pupilId) {
        LogManager.i(TAG, "get()");
        return this.mRepository.query(new ISpecification() {
            @Override
            public String whereClause() {
                return ISpecification.ID;
            }

            @Override
            public int id() {
                return pupilId;
            }
        });
    }

    @Override
    public void create(Pupil pupil) {
        LogManager.i(TAG, "create()");
        this.mRepository.add(pupil);
    }

    @Override
    public void update(final Pupil pupil) {
        LogManager.i(TAG, "update()");
        this.mRepository.update(pupil);
    }

    @Override
    public void remove(Pupil pupil) {
        LogManager.i(TAG, "remove()");
        this.mRepository.remove(pupil);
    }
}
