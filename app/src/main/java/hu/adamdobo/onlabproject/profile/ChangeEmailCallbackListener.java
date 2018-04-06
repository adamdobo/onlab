package hu.adamdobo.onlabproject.profile;

/**
 * Created by Ádám on 4/5/2018.
 */

public interface ChangeEmailCallbackListener {

    void changeEmail(String oldEmail, String newEmail, String password);
}
