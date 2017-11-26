package com.quaksire.boilerplate.domain;

import com.quaksire.applog.LogManager;
import com.quaksire.apprepository.IRepository;
import com.quaksire.apprepository.reposotory.AppLocalDataStore;
import com.quaksire.apprepository.specification.ISpecification;
import com.quaksire.model.Pupil;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollection;
import static org.mockito.Mockito.verify;

/**
 * Created by Julio.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(LogManager.class)
public class PupilsUserCaseTest {

    private IRepository<Pupil> mRepository;
    private IPupilsUserCase mUserCase;

    @Before
    public void setUp() {
        PowerMockito.mockStatic(LogManager.class);

        this.mRepository = Mockito.mock(AppLocalDataStore.class);

        this.mUserCase = new PupilsUserCase(this.mRepository);
    }

    @Test
    public void canInstantiate() {
        Assert.assertNotNull(this.mUserCase);
    }

    @Test
    public void canGetAll() {
        this.mUserCase.getAll();
        verify(this.mRepository).query(any(ISpecification.class));
    }

    @Test
    public void canGetById() {
        this.mUserCase.get(1);
        verify(this.mRepository).query(any(ISpecification.class));
    }

    @Test
    public void canCreate() {
        this.mUserCase.create(new Pupil());
        verify(this.mRepository).add(any(Pupil.class));
    }

    @Test
    public void canUpdate() {
        this.mUserCase.update(new Pupil());
        verify(this.mRepository).update(any(Pupil.class));
    }

    @Test
    public void canRemove() {
        this.mUserCase.remove(new Pupil());
        verify(this.mRepository).remove(any(Pupil.class));
    }
}
