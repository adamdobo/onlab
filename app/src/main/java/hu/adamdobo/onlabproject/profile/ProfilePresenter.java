package hu.adamdobo.onlabproject.profile;

/**
 * Created by Ádám on 3/20/2018.
 */

public interface ProfilePresenter {

    void onDestroy();

    String getUserName();

    String getUserEmail();

    void changePassword(String oldPassword, String newPassword);

    void changeEmail(String oldEmail, String newEmail, String password);
}
