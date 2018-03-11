package hu.adamdobo.onlabproject.signup;

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
    public void registerUser(String email, String password) {
        if(signupView!=null){
            signupView.showProgress();
        }
        signupInteractor.signup(email, password, this, signupView);
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
    public void onSignupError() {
        if(signupView!=null){
            signupView.hideProgress();
            signupView.setSignupError();
        }
    }

    @Override
    public void onSuccess() {
        if(signupView!=null){
            signupView.hideProgress();
            signupView.navigateToLogin();
        }
    }
}
