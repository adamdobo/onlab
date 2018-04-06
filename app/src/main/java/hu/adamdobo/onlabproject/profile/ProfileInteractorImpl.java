package hu.adamdobo.onlabproject.profile;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Ádám on 3/20/2018.
 */

public class ProfileInteractorImpl implements ProfileInteractor {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    public String getUserName() {
        return user.getDisplayName();
    }

    @Override
    public String getUserEmail() {
        return user.getEmail();
    }

    @Override
    public void updatePassword(String oldPassword, final String newPassword, final OnPasswordUpdateFinishedListener listener) {
        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), oldPassword);
        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                listener.onPasswordChangeSuccess();
                            }
                            else{
                                listener.onPasswordChangeError();
                            }
                        }
                    });
                }
                else {
                    listener.onOldPasswordFailed();
                }
            }
        });
    }

    @Override
    public void updateEmail(String oldEmail, final String newEmail, String password, final OnEmailUpdateFinishedListener listener) {
        AuthCredential credential = EmailAuthProvider.getCredential(oldEmail, password);
        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    user.updateEmail(newEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                listener.onEmailChangeSuccess();
                            }
                            else {
                                listener.onEmailChangeError();
                            }
                        }
                    });
                }else {
                    listener.onPasswordFailed();
                }
            }
        });
    }

}
