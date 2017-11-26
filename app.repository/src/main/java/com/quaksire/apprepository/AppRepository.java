package com.quaksire.apprepository;

import com.quaksire.applog.LogManager;
import com.quaksire.apprepository.specification.ISpecification;
import com.quaksire.model.Pupil;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Julio.
 */

public class AppRepository implements IRepository<Pupil> {

    private final String TAG = "APP-AppRepository";

    /**
     * Local repository, stored in SQL
     */
    private IRepository<Pupil> mAppLocalDataStore;

    /**
     * Network repository
     */
    private IRepository<Pupil> mAppRemoteDataStore;

    @Inject
    public AppRepository(
            @Named("AppLocalDataStore") IRepository<Pupil> mAppLocalDataStore,
            @Named("AppRemoteDataStore") IRepository<Pupil> mAppRemoteDataStore) {
        LogManager.i(TAG, "Constructor()");
        this.mAppLocalDataStore = mAppLocalDataStore;
        this.mAppRemoteDataStore = mAppRemoteDataStore;
    }


    @Override
    public Pupil add(final Pupil item) {
        LogManager.i(TAG, "add()");
        this.mAppLocalDataStore.add(item);
        return item;
    }

    @Override
    public void add(Iterable<Pupil> items) {
        LogManager.i(TAG, "add()");
        this.mAppLocalDataStore.add(items);
    }

    @Override
    public Pupil update(final Pupil item) {
        LogManager.i(TAG, "update");

        Observable.just(this.mAppLocalDataStore)
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<IRepository<Pupil>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(IRepository<Pupil> pupilIRepository) {
                        pupilIRepository.update(item);
                    }
                });

        Observable.just(this.mAppRemoteDataStore.update(item))
                .subscribeOn(Schedulers.io());

        return item;
    }

    @Override
    public Pupil remove(final Pupil item) {
        LogManager.i(TAG, "remove()");

        Observable.just(this.mAppLocalDataStore)
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<IRepository<Pupil>>() {
                    @Override
                    public void onCompleted() {
                        LogManager.d(TAG, "onCompleted()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogManager.e(TAG, e);
                    }

                    @Override
                    public void onNext(IRepository<Pupil> pupilIRepository) {
                        LogManager.d(TAG, "remove() - Local - PupilId: " + item.pupilId );
                        pupilIRepository.remove(item);
                    }
                });

        Observable.just(this.mAppRemoteDataStore.remove(item))
                .subscribeOn(Schedulers.io());

        return item;
    }

    @Override
    public void remove(ISpecification specification) throws Exception {
        LogManager.i(TAG, "remove()");
        this.mAppLocalDataStore.remove(specification);
        this.mAppRemoteDataStore.remove(specification);
    }

    @Override
    public Observable<List<Pupil>> query(ISpecification specification) {
        LogManager.i(TAG, "query()");

        if (specification.whereClause().equals(ISpecification.ID)) {
            LogManager.d(TAG, "query() - Getting Pupil by Id: " + specification.id());
            return this.mAppLocalDataStore.query(specification);
        } else {
            LogManager.d(TAG, "query() - Getting all pupils");
            this.mAppRemoteDataStore.query(specification)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe(new Subscriber<List<Pupil>>() {
                        @Override
                        public void onNext(List<Pupil> pupils) {
                            LogManager.d(TAG, "query() - OnNext() - Update local values");
                            //Remove all pupils
                            try {
                                LogManager.d(TAG, "query() - OnNext() - Delete all pupils");
                                mAppLocalDataStore.remove(new ISpecification() {
                                    @Override
                                    public String whereClause() {
                                        return ISpecification.ALL;
                                    }

                                    @Override
                                    public int id() {
                                        return 0;
                                    }
                                });
                            } catch (Exception e) {
                                LogManager.e(TAG, e);
                            }

                            LogManager.d(TAG, "query() - OnNext() - Adding " + pupils.size() + " pupils");
                            mAppLocalDataStore.add(pupils);
                        }

                        @Override
                        public void onCompleted() {
                            LogManager.d(TAG, "query() - OnCompleted()");
                        }

                        @Override
                        public void onError(Throwable e) {
                            LogManager.d(TAG, "query() - OnError()");
                            LogManager.e(TAG, e);
                        }
                    });

            return Observable.concat(
                    this.mAppLocalDataStore.query(specification),
                    this.mAppRemoteDataStore.query(specification))
                    .filter(new Func1<List<Pupil>, Boolean>() {
                        @Override
                        public Boolean call(List<Pupil> pupils) {
                            //Avoid return empty list
                            return pupils != null && !pupils.isEmpty();
                        }
                    })
                    .first();
        }
    }

}
