package hu.adamdobo.onlabproject.login;

/**
 * Created by Ádám on 3/7/2018.
 */

public interface LoginView {

    void loginUser();

    void showProgress();

    void hideProgress();

    void setLoginError();

    void navigateToHome();

    void navigateToSignUp();

    void setPasswordEmptyError();

    void setEmailEmptyError();
}
