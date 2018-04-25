package hu.adamdobo.onlabproject.profile;

/**
 * Created by Ádám on 3/20/2018.
 */

public class ProfilePresenterImpl implements ProfilePresenter, ProfileInteractor.OnPasswordUpdateFinishedListener, ProfileInteractor.OnEmailUpdateFinishedListener {


    ProfileView profileView;
    ProfileInteractor profileInteractor;

    public ProfilePresenterImpl(ProfileView profileView, ProfileInteractor profileInteractor) {
        this.profileView = profileView;
        this.profileInteractor = profileInteractor;
        profileInteractor.setPresenter(this);
        profileInteractor.getUserAddressFromDatabase();
    }


    @Override
    public void onDestroy() {
        profileView = null;
    }

    @Override
    public void onPasswordChangeError() {
        if (profileView != null) {
            profileView.setPasswordChangeError();
        }
    }

    @Override
    public void onPasswordChangeSuccess() {
        if (profileView != null) {
            profileView.setPasswordChangeSuccess();
        }
    }

    @Override
    public void onOldPasswordFailed() {
        if (profileView != null) {
            profileView.setOldPasswordError();
        }
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        profileInteractor.updatePassword(oldPassword, newPassword, this);
    }

    @Override
    public void changeEmail(String oldEmail, String newEmail, String password) {
        profileInteractor.updateEmail(oldEmail, newEmail, password, this);
    }


    @Override
    public void onUserInfoReady() {
        if(profileView!=null){
            profileView.showUserInfo(profileInteractor.getUserName(), profileInteractor.getUserEmail(), profileInteractor.getUserAddress());
        }
    }

    @Override
    public void onEmailChangeError() {
        if (profileView != null) {
            profileView.setEmailUpdateError();
        }
    }

    @Override
    public void onEmailChangeSuccess() {
        if (profileView != null) {
            profileView.setEmailUpdateSuccess();
        }
    }

    @Override
    public void onPasswordFailed() {
        if (profileView != null) {
            profileView.setPasswordError();
        }
    }
}
