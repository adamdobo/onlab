package hu.adamdobo.onlabproject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import hu.adamdobo.onlabproject.login.LoginInteractor;
import hu.adamdobo.onlabproject.login.LoginPresenterImpl;
import hu.adamdobo.onlabproject.login.LoginView;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Ádám on 5/7/2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginUnitTest {
    @Mock
    LoginView loginView;

    @Mock
    LoginInteractor loginInteractor;

    LoginPresenterImpl presenter;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        presenter = new LoginPresenterImpl(loginView, loginInteractor);
    }

    @Test
    public void testNavigateToHomeIfUserLoggedIn(){
        when(loginInteractor.isUserLoggedIn()).thenReturn(true);
        presenter.checkIfUserIsLoggedIn();
        verify(loginInteractor).isUserLoggedIn();
        verify(loginView).navigateToHome();
    }

    @Test
    public void testNotNavigateToHome(){
        when(loginInteractor.isUserLoggedIn()).thenReturn(false);
        presenter.checkIfUserIsLoggedIn();
        verify(loginInteractor).isUserLoggedIn();
        verify(loginView, never()).navigateToHome();
    }

    @Test
    public void testValidateCredentials(){
        presenter.validateCredentials("somebody", "somebody");
        verify(loginInteractor).login("somebody", "somebody", presenter);
    }

    @Test
    public void testLoginError(){
        presenter.onLoginError();
        verify(loginView).hideProgress();
        verify(loginView).setLoginError();
    }

    @Test
    public void testLoginSuccess(){
        presenter.onSuccess();
        verify(loginView).hideProgress();
        verify(loginView).navigateToHome();
    }

    @Test
    public void testEmailEmptyError(){
        presenter.onEmailEmptyError();
        verify(loginView).hideProgress();
        verify(loginView).setEmailEmptyError();
    }

    @Test
    public void testPasswordEmptyError(){
        presenter.onPasswordEmptyError();
        verify(loginView).hideProgress();
        verify(loginView).setPasswordEmptyError();
    }

    @Test
    public void testOnDestroy(){
        presenter.onDestroy();
        presenter.onSuccess();
        verify(loginView, never()).hideProgress();
        verify(loginView, never()).navigateToHome();
    }
}
