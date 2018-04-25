package hu.adamdobo.onlabproject.signup;

/**
 * Created by Ádám on 3/11/2018.
 */

public interface SignupView {

    void signUpUser();

    void showProgress();

    void hideProgress();

    void setEmailEmptyError();

    void setPasswordEmptyError();

    void navigateToLogin();

    void setAddressEmptyError();

    void setNickNameEmptyError();

    void setSignupError();

    void setPasswordMismatchError();

    void clearErrors();

    void setPasswordTooShortError();
}
