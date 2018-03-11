package hu.adamdobo.onlabproject.signup;

/**
 * Created by Ádám on 3/11/2018.
 */

public interface SignupPresenter {

    void registerUser(String email, String password);

    void onDestroy();
}
