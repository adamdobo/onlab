package hu.adamdobo.onlabproject.drawer;

import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import hu.adamdobo.onlabproject.model.User;

/**
 * Created by Ádám on 3/12/2018.
 */

public interface DrawerInteractor {

    void logout();
    User getUserInfo();
    void navigateTo(MenuItem item, DrawerLayout drawerLayout, DrawerListener listener);

    void setCurrentMenuItem(int itemId);

    void subscribeToTopics();
}
