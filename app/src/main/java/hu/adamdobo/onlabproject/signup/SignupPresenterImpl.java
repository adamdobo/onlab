package hu.adamdobo.onlabproject.signup;

import hu.adamdobo.onlabproject.model.User;

/**
 * Created by Ádám on 3/11/2018.
 */

public class SignupPresenterImpl implements SignupPresenter, SignupInteractor.OnSignupFinishedListener {

    private SignupInteractor signupInteractor;
    private SignupView signupView;

    public SignupPresenterImpl(SignupView signupView, SignupInteractor signupInteractor) {
        this.signupInteractor = signupInteractor;
        this.signupView = signupView;
    }

    @Override
    public void registerUser(User user, String password, String passwordAgain) {
        if(signupView!=null){
            signupView.showProgress();
        }
        signupInteractor.signup(user, password, passwordAgain, this);
    }

    @Override
    public void onDestroy() {
        signupView = null;
    }

    @Override
    public void onEmailEmptyError() {
        if(signupView!=null){
            signupView.hideProgress();
            signupView.setEmailEmptyError();
        }
    }

    @Override
    public void onPasswordEmptyError() {
        if(signupView!=null){
            signupView.hideProgress();
            signupView.setPasswordEmptyError();
        }
    }

    @Override
    public void onSuccess() {
        if(signupView!=null){
            signupView.hideProgress();
            signupView.navigateToLogin();
        }
    }

    @Override
    public void onAddressEmptyError() {
        if(signupView!=null){
            signupView.hideProgress();
            signupView.setAddressEmptyError();
        }
    }

    @Override
    public void onNickNameEmptyError() {
        if(signupView!=null){
            signupView.hideProgress();
            signupView.setNickNameEmptyError();
        }
    }

    @Override
    public void onSignupError() {
        if(signupView!=null){
            signupView.hideProgress();
            signupView.setSignupError();
        }
    }

    @Override
    public void onPasswordsMismatchError() {
        if(signupView!=null){
            signupView.hideProgress();
            signupView.setPasswordMismatchError();
        }
    }

    @Override
    public void onPasswordTooShort() {
        if(signupView!=null){
            signupView.hideProgress();
            signupView.setPasswordTooShortError();
        }
    }
}
