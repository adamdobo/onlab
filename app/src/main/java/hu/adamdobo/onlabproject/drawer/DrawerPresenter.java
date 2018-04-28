package hu.adamdobo.onlabproject.drawer;

import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

/**
 * Created by Ádám on 3/12/2018.
 */

public interface DrawerPresenter {

    void navigationItemSelected(MenuItem item, DrawerLayout drawerLayout);
    void setUserInfo();
    void logout();

    void setCurrentMenuItem(int itemId);
}
