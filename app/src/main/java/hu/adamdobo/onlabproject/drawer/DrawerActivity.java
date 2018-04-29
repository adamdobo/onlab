package hu.adamdobo.onlabproject.drawer;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import hu.adamdobo.onlabproject.R;
import hu.adamdobo.onlabproject.items.ItemsFragment;
import hu.adamdobo.onlabproject.fbcloudmessaging.MyNotificationManager;
import hu.adamdobo.onlabproject.login.LoginActivity;
import hu.adamdobo.onlabproject.model.User;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DrawerView {

    private DrawerPresenter presenter;
    private TextView emailTextView, nameTextView;
    DrawerLayout drawer;
    NavigationView navigationView;


    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            MyNotificationManager.getInstance(this).createMainNotificationChannel();
        }
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        presenter = new DrawerPresenterImpl(this, new DrawerInteractorImpl());
        setDefaultFragment();
        setNavigationHeader();
        presenter.setUserInfo();
    }

    private void setDefaultFragment() {
        navigationView.getMenu().getItem(0).setChecked(true);
        presenter.setCurrentMenuItem(navigationView.getMenu().getItem(0).getItemId());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame,ItemsFragment.newInstance()).commit();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            int count = getSupportFragmentManager().getBackStackEntryCount();
            if(count==0){
                super.onBackPressed();
            }else {
                getSupportFragmentManager().popBackStack();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            presenter.logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        presenter.navigationItemSelected(item, drawer);
        return true;
    }



    @Override
    public void navigateToLogin() {
        Intent login = new Intent(this, LoginActivity.class);
        login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(login);
        finish();
    }

    @Override
    public void showUserInfo(User userInfo) {
        emailTextView.setText(userInfo.email);
        nameTextView.setText(userInfo.nickname);
    }

    @Override
    public void setActiveMenuItem(int itemPlace) {
        navigationView.getMenu().getItem(itemPlace).setChecked(true);
    }

    @Override
    public void navigateUsingTo(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment).addToBackStack(null).commit();
    }

    public void setNavigationHeader() {
        View hView = navigationView.getHeaderView(0);
        nameTextView = hView.findViewById(R.id.nameTextView);
        emailTextView = hView.findViewById(R.id.emailTextView);
    }
}
