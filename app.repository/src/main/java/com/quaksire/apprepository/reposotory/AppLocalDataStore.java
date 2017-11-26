package com.quaksire.apprepository.reposotory;

import com.quaksire.applog.LogManager;
import com.quaksire.apprepository.IRepository;
import com.quaksire.apprepository.adapter.PupilAdapter;
import com.quaksire.apprepository.db.AppDatabase;
import com.quaksire.apprepository.entity.PupilEntity;
import com.quaksire.apprepository.specification.ISpecification;
import com.quaksire.model.Pupil;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Julio.
 */

public class AppLocalDataStore implements IRepository<Pupil> {

    private final String TAG = "APP-AppLocalDataStore";

    /**
     * Room database
     */
    private AppDatabase mAppDatabase;

    public AppLocalDataStore(AppDatabase appDatabase) {
        LogManager.i(TAG, "Constructor()");
        this.mAppDatabase = appDatabase;
    }

    @Override
    public Pupil add(Pupil item) {
        LogManager.i(TAG, "add()");
        this.mAppDatabase.pupilDAO().insert(PupilAdapter.toPupilEntity(item));
        return item;
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
        this.mAppDatabase.pupilDAO().update(PupilAdapter.toPupilEntity(item));
        return item;
    }

    @Override
    public Pupil remove(Pupil item) {
        LogManager.i(TAG, "remove()");
        this.mAppDatabase.pupilDAO().delete(PupilAdapter.toPupilEntity(item));
        return item;
    }

    @Override
    public void remove(ISpecification specification) throws Exception {
        LogManager.i(TAG, "remove()");
        this.mAppDatabase.pupilDAO().nukeTable();
    }

    @Override
    public Observable<List<Pupil>> query(final ISpecification specification) {
        LogManager.i(TAG, "query()");
        if(specification.whereClause().equals(ISpecification.ID)) {
            return Observable.defer(
                    new Func0<Observable<List<PupilEntity>>>() {
                        @Override
                        public Observable<List<PupilEntity>> call() {
                            return Observable.just(mAppDatabase.pupilDAO().getById(specification.id()));
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .map(new Func1<List<PupilEntity>, List<Pupil>>() {
                        @Override
                        public List<Pupil> call(List<PupilEntity> pupilEntities) {
                            List<Pupil> pupils = new ArrayList<>();
                            for (PupilEntity entity : pupilEntities) {
                                pupils.add(PupilAdapter.toPupil(entity));
                            }
                            return pupils;
                        }
                    });
        } else {
            return Observable.defer(
                    new Func0<Observable<List<PupilEntity>>>() {
                        @Override
                        public Observable<List<PupilEntity>> call() {
                            return Observable.just(mAppDatabase.pupilDAO().getAll());
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .map(new Func1<List<PupilEntity>, List<Pupil>>() {
                        @Override
                        public List<Pupil> call(List<PupilEntity> pupilEntities) {
                            List<Pupil> pupils = new ArrayList<>();
                            for (PupilEntity entity : pupilEntities) {
                                pupils.add(PupilAdapter.toPupil(entity));
                            }
                            return pupils;
                        }
                    });
        }
    }
}
