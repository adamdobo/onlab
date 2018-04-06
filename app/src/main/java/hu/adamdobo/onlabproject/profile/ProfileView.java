package hu.adamdobo.onlabproject.profile;

/**
 * Created by Ádám on 3/20/2018.
 */

public interface ProfileView {

    void showUserInfo();

    void setPasswordChangeError();

    void setPasswordChangeSuccess();

    void setOldPasswordError();

    void setEmailUpdateError();

    void setEmailUpdateSuccess();

    void setPasswordError();
}
