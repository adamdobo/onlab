package hu.adamdobo.onlabproject.login;

/**
 * Created by Ádám on 3/7/2018.
 */

public interface LoginInteractor {

    interface OnLoginFinishedListener {
        void onLoginError();
        void onSuccess();
        void onEmailEmptyError();
        void onPasswordEmptyError();
    }

    void login(String username, String password, final OnLoginFinishedListener listener, final LoginView view);

    boolean isUserLoggedIn();
}
