package com.quaksire.apprepository.reposotory;

import com.quaksire.applog.LogManager;
import com.quaksire.appnet.service.PupilService;
import com.quaksire.apprepository.IRepository;
import com.quaksire.apprepository.specification.ISpecification;
import com.quaksire.model.Pupil;
import com.quaksire.model.Pupils;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Julio.
 */

public class AppRemoteDataStore implements IRepository<Pupil> {

    private final String TAG = "APP-AppRemoteDataStore";

    /**
     * Retrofit Service
     */
    private final PupilService mService;

    @Inject
    public AppRemoteDataStore(Retrofit retrofit) {
        LogManager.i(TAG, "Constructor()");
        this.mService = retrofit.create(PupilService.class);
    }

    @Override
    public Pupil add(Pupil item) {
        LogManager.i(TAG, "add()");
        this.mService.create(item)
                .subscribeOn(Schedulers.io());
        return  item;
    }

    @Override
    public void add(Iterable<Pupil> items) {
        LogManager.i(TAG, "add()");
        for (Pupil pupil : items) {
            add(pupil);
        }
    }

    @Override
    public Pupil update(Pupil item) {
        LogManager.i(TAG, "update()");
        this.mService.update(item)
                .subscribeOn(Schedulers.io());
        return item;
    }

    @Override
    public Pupil remove(Pupil item) {
        LogManager.i(TAG, "remove()");
        this.mService.delete(item.pupilId)
                .subscribeOn(Schedulers.io());
        return item;
    }

    @Override
    public void remove(ISpecification specification) throws Exception {
        LogManager.i(TAG, "remove()");
        throw new Exception("Remove from RemoteData has not been implemented");
    }

    @Override
    public Observable<List<Pupil>> query(ISpecification specification) {
        LogManager.i(TAG, "query()");
        //For a demo purpose, we are going to work only with the page 1
        return this.mService.getAllPupils(1).map(new Func1<Pupils, List<Pupil>>() {
            @Override
            public List<Pupil> call(Pupils pupils) {
                return pupils.items;
            }
        });
    }
}
