package hu.adamdobo.onlabproject.profile;

/**
 * Created by Ádám on 4/5/2018.
 */

public interface ChangePasswordCallbackListener {

    void changePassword(String oldPassword, String newPassword);

}
