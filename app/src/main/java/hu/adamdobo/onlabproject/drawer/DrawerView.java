package hu.adamdobo.onlabproject.drawer;

import android.support.v4.app.Fragment;

import hu.adamdobo.onlabproject.model.User;

/**
 * Created by Ádám on 3/12/2018.
 */

public interface DrawerView {

    void navigateUsingTo(Fragment fragment);
    void navigateToLogin();
    void showUserInfo(User userInfo);
    void setActiveMenuItem(int itemPlace);

    void stopDeliveryService();
}
