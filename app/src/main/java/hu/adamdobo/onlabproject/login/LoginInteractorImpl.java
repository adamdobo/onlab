package hu.adamdobo.onlabproject.login;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Ádám on 3/7/2018.
 */

public class LoginInteractorImpl implements LoginInteractor {
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    public void login(String email, String password, final OnLoginFinishedListener listener, LoginView view) {
        if (TextUtils.isEmpty(email)) {
            listener.onUsernameEmptyError();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            listener.onPasswordEmptyError();
            return;
        }
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) view, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            listener.onLoginError();
                        } else {
                            listener.onSuccess();
                        }
                    }
                });
    }
}
