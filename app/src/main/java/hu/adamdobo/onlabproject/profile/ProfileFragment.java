package hu.adamdobo.onlabproject.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import hu.adamdobo.onlabproject.R;
import hu.adamdobo.onlabproject.dialog.ChangeEmailDialog;
import hu.adamdobo.onlabproject.dialog.ChangePasswordDialog;

/**
 * Created by Ádám on 3/17/2018.
 */

public class ProfileFragment extends Fragment implements ProfileView, ChangeEmailCallbackListener, ChangePasswordCallbackListener {
    TextView userName, userEmail, userAddress;
    ProfilePresenter presenter;
    Toolbar toolbar;
    Button changePW, changeEmail;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_profile, container, false);
        userEmail = contentView.findViewById(R.id.userEmail);
        userName = contentView.findViewById(R.id.userName);
        userAddress = contentView.findViewById(R.id.userAddress);
        toolbar = contentView.findViewById(R.id.toolbar);
        presenter = new ProfilePresenterImpl(this, new ProfileInteractorImpl());
        changeEmail = contentView.findViewById(R.id.changeEmailButton);
        changePW = contentView.findViewById(R.id.changePasswordButton);
        changeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeEmailDialog changeEmailDialog = ChangeEmailDialog.newInstance(ProfileFragment.this);
                changeEmailDialog.show(getActivity().getSupportFragmentManager(), ChangeEmailDialog.TAG);
            }
        });

        changePW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePasswordDialog changePasswordDialog = ChangePasswordDialog.newInstance(ProfileFragment.this);
                changePasswordDialog.show(getActivity().getSupportFragmentManager(), ChangePasswordDialog.TAG);
            }
        });

        return contentView;
    }

    public static Fragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void showUserInfo(String username, String email, String address) {
        toolbar.setTitle(username);
        userName.setText(username);
        userEmail.setText(email);
        userAddress.setText(address);
    }

    @Override
    public void setPasswordChangeError() {
        Snackbar.make(getView(), R.string.something_went_wrong, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void setPasswordChangeSuccess() {
        Snackbar.make(getView(), R.string.password_changed, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void setOldPasswordError() {
        Snackbar.make(getView(), R.string.wrong_old_password, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void setEmailUpdateError() {
        Snackbar.make(getView(), R.string.email_update_failed, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void setEmailUpdateSuccess() {
        Snackbar.make(getView(), R.string.email_updated, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void setPasswordError() {
        Snackbar.make(getView(), R.string.wrong_password, Snackbar.LENGTH_LONG).show();
    }


    @Override
    public void changePassword(String oldPassword, String newPassword) {
        presenter.changePassword(oldPassword, newPassword);
    }

    @Override
    public void changeEmail(String oldEmail, String newEmail, String password) {
        presenter.changeEmail(oldEmail, newEmail, password);
    }
}
