package hu.adamdobo.onlabproject.login;

/**
 * Created by Ádám on 3/7/2018.
 */

public class LoginPresenterImpl implements LoginPresenter, LoginInteractor.OnLoginFinishedListener {

    private LoginView loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenterImpl(LoginView loginView, LoginInteractor loginInteractor) {
        this.loginView = loginView;
        this.loginInteractor = loginInteractor;
    }

    @Override
    public void validateCredentials(String username, String password) {
        if (loginView != null) {
            loginView.showProgress();
        }
        loginInteractor.login(username, password, this, loginView);
    }

    @Override
    public void checkIfUserIsLoggedIn() {
        if(loginInteractor.isUserLoggedIn()){
            loginView.navigateToHome();
        }
    }

    @Override
    public void onDestroy() {
        loginView = null;
    }

    @Override
    public void onLoginError() {
        if (loginView != null) {
            loginView.hideProgress();
            loginView.setLoginError();
        }
    }

    @Override
    public void onSuccess() {
        if (loginView != null) {
            loginView.hideProgress();
            loginView.navigateToHome();
        }

    }

    @Override
    public void onEmailEmptyError() {
        if (loginView != null) {
            loginView.hideProgress();
            loginView.setEmailEmptyError();
        }
    }

    @Override
    public void onPasswordEmptyError() {
        if (loginView != null) {
            loginView.hideProgress();
            loginView.setPasswordEmptyError();
        }
    }
}
