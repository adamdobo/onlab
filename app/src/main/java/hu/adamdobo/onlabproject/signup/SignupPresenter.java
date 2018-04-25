package hu.adamdobo.onlabproject.signup;

import hu.adamdobo.onlabproject.model.User;

/**
 * Created by Ádám on 3/11/2018.
 */

public interface SignupPresenter {

    void registerUser(User user, String password, String passwordAgain);

    void onDestroy();
}
