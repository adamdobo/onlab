package hu.adamdobo.onlabproject.signup;

import hu.adamdobo.onlabproject.model.User;

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

    void signup(User user, String password, final OnSignupFinishedListener listener, final SignupView signupView);

    void saveUserToFirebase(User user);

    void updateUserProfile(User user);

}
