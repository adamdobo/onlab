package hu.adamdobo.onlabproject.drawer;

import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import hu.adamdobo.onlabproject.model.User;

/**
 * Created by Ádám on 3/12/2018.
 */

public class DrawerInteractorImpl implements DrawerInteractor {

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseDatabase db = FirebaseDatabase.getInstance();


    @Override
    public void logout() {
        auth.signOut();
    }

    @Override
    public User getUserInfo() {
        FirebaseUser firebaseUser = auth.getCurrentUser();
        User returnUser = new User(null, firebaseUser.getEmail(), null, firebaseUser.getDisplayName());
        return returnUser;
    }

    @Override
    public void navigateTo(MenuItem item, DrawerLayout drawerLayout, DrawerListener listener) {

    }
}