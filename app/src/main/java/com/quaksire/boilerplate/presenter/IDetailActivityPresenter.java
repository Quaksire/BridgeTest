package com.quaksire.boilerplate.presenter;

/**
 * Created by Julio.
 */

public interface IDetailActivityPresenter {

    void loadPupilId(int id);

    void deletePupil();

    void savePupil(String name, String country);
}
