package com.quaksire.apprepository;

import com.quaksire.apprepository.specification.ISpecification;

import java.util.List;

import dagger.Provides;
import rx.Observable;

/**
 * Created by Julio.
 */
public interface IRepository <T> {

    T add(T item);

    void add(Iterable<T> items);

    T update(T item);

    T remove(T item);

    void remove(ISpecification specification) throws Exception;

    Observable<List<T>> query(ISpecification specification);
}