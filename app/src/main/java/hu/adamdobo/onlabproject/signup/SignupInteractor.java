package hu.adamdobo.onlabproject.signup;

/**
 * Created by Ádám on 3/11/2018.
 */

public interface SignupInteractor {

    interface OnSignupFinishedListener {
        void onEmailEmptyError();
        void onPasswordEmptyError();
        void onSignupError();
        void onSuccess();
    }

    void signup(String email, String password, final OnSignupFinishedListener listener, final SignupView signupView);
}
