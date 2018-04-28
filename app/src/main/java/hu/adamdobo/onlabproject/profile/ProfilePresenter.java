package hu.adamdobo.onlabproject.profile;

/**
 * Created by Ádám on 3/20/2018.
 */

public interface ProfilePresenter {

    void onDestroy();

    void changePassword(String oldPassword, String newPassword);

    void changeEmail(String newEmail, String password);

    void onUserInfoReady();
}
