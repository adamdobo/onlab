package hu.adamdobo.onlabproject.drawer;

import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import hu.adamdobo.onlabproject.model.User;

/**
 * Created by Ádám on 3/12/2018.
 */

public class DrawerPresenterImpl implements DrawerPresenter, DrawerListener{

    private DrawerInteractor drawerInteractor;
    private DrawerView drawerView;

    public DrawerPresenterImpl(DrawerView drawerView, DrawerInteractor drawerInteractor) {
        this.drawerInteractor = drawerInteractor;
        this.drawerView = drawerView;
    }

    @Override
    public void navigationItemSelected(MenuItem item, DrawerLayout drawerLayout) {
        drawerInteractor.navigateTo(item, drawerLayout, this);
    }

    @Override
    public void setUserInfo() {
        User user = drawerInteractor.getUserInfo();
        drawerView.showUserInfo(user);
    }

    @Override
    public void logout() {
        drawerInteractor.logout();
        drawerView.navigateToLogin();
    }

    @Override
    public void setCurrentMenuItem(int itemId) {
        drawerInteractor.setCurrentMenuItem(itemId);
    }

    @Override
    public void fragmentReplace(Fragment fragment) {
        drawerView.navigateUsingTo(fragment);
    }
}
