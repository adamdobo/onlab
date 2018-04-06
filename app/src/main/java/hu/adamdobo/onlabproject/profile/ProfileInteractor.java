package hu.adamdobo.onlabproject.profile;

/**
 * Created by Ádám on 3/20/2018.
 */

public interface ProfileInteractor {



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

    void updatePassword(String oldPassword, String newPassword, OnPasswordUpdateFinishedListener listener);

    void updateEmail(String oldEmail, String newEmail, String password, OnEmailUpdateFinishedListener listener);


}
