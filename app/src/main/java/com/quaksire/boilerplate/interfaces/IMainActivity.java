package com.quaksire.boilerplate.interfaces;

import com.quaksire.model.Pupil;

import java.util.List;

/**
 * Created by Julio.
 */

public interface IMainActivity {

    void displayItems(List<Pupil> pupilList);

    void displayError(String message);
}
