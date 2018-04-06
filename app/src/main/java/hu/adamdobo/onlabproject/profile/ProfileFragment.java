package hu.adamdobo.onlabproject.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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
    TextView userName, userEmail;
    Button changePasswordButton, changeEmailButton;
    ProfilePresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_profile, container, false);

        userEmail = contentView.findViewById(R.id.userEmail);
        userName = contentView.findViewById(R.id.userName);
        changePasswordButton = contentView.findViewById(R.id.changePWButton);
        changeEmailButton = contentView.findViewById(R.id.changeMailButton);
        presenter = new ProfilePresenterImpl(this, new ProfileInteractorImpl());
        showUserInfo();
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ChangePasswordDialog dialog = ChangePasswordDialog.newInstance(ProfileFragment.this);
               dialog.show(getActivity().getSupportFragmentManager(), ChangePasswordDialog.TAG);
            }
        });

        changeEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeEmailDialog dialog = ChangeEmailDialog.newInstance(ProfileFragment.this);
                dialog.show(getActivity().getSupportFragmentManager(), ChangeEmailDialog.TAG);
            }
        });
        return contentView;
    }

    public static Fragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void showUserInfo() {
        userEmail.setText(presenter.getUserEmail());
        userName.setText(presenter.getUserName());
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
