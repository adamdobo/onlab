package hu.adamdobo.onlabproject.signup;

import hu.adamdobo.onlabproject.model.User;

/**
 * Created by Ádám on 3/11/2018.
 */

public interface SignupInteractor {

    interface OnSignupFinishedListener {
        void onEmailEmptyError();
        void onPasswordEmptyError();
        void onSuccess();
        void onAddressEmptyError();
        void onNickNameEmptyError();

        void onSignupError();

        void onPasswordsMismatchError();

        void onPasswordTooShort();
    }

    void signup(User user, String password, String passwordAgain, final OnSignupFinishedListener listener);

    void saveUserToFirebase(User user);

    void updateUserProfile(User user);

}
