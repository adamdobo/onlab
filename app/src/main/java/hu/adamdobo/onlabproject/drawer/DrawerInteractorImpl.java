package hu.adamdobo.onlabproject.drawer;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import hu.adamdobo.onlabproject.R;
import hu.adamdobo.onlabproject.delivery.DeliveryFragment;
import hu.adamdobo.onlabproject.items.ItemsFragment;
import hu.adamdobo.onlabproject.model.User;
import hu.adamdobo.onlabproject.mybids.MyBidsFragment;
import hu.adamdobo.onlabproject.myitems.MyItemsFragment;
import hu.adamdobo.onlabproject.profile.ProfileFragment;

/**
 * Created by Ádám on 3/12/2018.
 */

public class DrawerInteractorImpl implements DrawerInteractor {

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    private int currentMenuItem;


    @Override
    public void logout() {
        auth.signOut();
    }

    @Override
    public User getUserInfo() {
        FirebaseUser firebaseUser = auth.getCurrentUser();
        User returnUser = new User(firebaseUser.getEmail(), null, firebaseUser.getDisplayName());
        return returnUser;
    }

    @Override
    public void navigateTo(MenuItem item, DrawerLayout drawerLayout, DrawerListener listener) {
        if(currentMenuItem == item.getItemId()){
            drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        switch (item.getItemId()){
            case R.id.nav_items:
                currentMenuItem = R.id.nav_items;
                listener.fragmentReplace(ItemsFragment.newInstance());
                break;
            case R.id.nav_profile:
                currentMenuItem = R.id.nav_profile;
                listener.fragmentReplace(ProfileFragment.newInstance());
                break;
            case R.id.nav_mybids:
                currentMenuItem = R.id.nav_mybids;
                listener.fragmentReplace(MyBidsFragment.newInstance());
                break;
            case R.id.nav_myitems:
                currentMenuItem = R.id.nav_myitems;
                listener.fragmentReplace(MyItemsFragment.newInstance());
                break;
            case R.id.nav_deliveries:
                currentMenuItem = R.id.nav_deliveries;
                listener.fragmentReplace(DeliveryFragment.newInstance());
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public void setCurrentMenuItem(int itemId) {
        currentMenuItem = itemId;
    }
}
