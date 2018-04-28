package hu.adamdobo.onlabproject.profile;

/**
 * Created by Ádám on 3/20/2018.
 */

public interface ProfileInteractor {


    void getUserAddressFromDatabase();

    String getUserAddress();

    interface OnPasswordUpdateFinishedListener{
        void onPasswordChangeError();

        void onPasswordChangeSuccess();

        void onOldPasswordFailed();
    }

    interface OnEmailUpdateFinishedListener {
        void onEmailChangeError();

        void onEmailChangeSuccess();

        void onPasswordFailed();
    }

    String getUserName();

    String getUserEmail();

    void setPresenter(ProfilePresenter presenter);

    void updatePassword(String oldPassword, String newPassword, OnPasswordUpdateFinishedListener listener);

    void updateEmail(String newEmail, String password, OnEmailUpdateFinishedListener listener);


}
