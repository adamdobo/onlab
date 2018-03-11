package hu.adamdobo.onlabproject.login;

/**
 * Created by Ádám on 3/7/2018.
 */

public interface LoginPresenter {

    void validateCredentials(String username, String password);

    void checkIfUserIsLoggedIn();

    void onDestroy();
}
