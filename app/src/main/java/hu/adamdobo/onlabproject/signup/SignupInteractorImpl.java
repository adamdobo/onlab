package hu.adamdobo.onlabproject.signup;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Ádám on 3/11/2018.
 */

public class SignupInteractorImpl implements SignupInteractor {
    FirebaseAuth auth = FirebaseAuth.getInstance();


    @Override
    public void signup(String email, String password, final OnSignupFinishedListener listener, SignupView signupView) {
        if (TextUtils.isEmpty(email)) {
            listener.onEmailEmptyError();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            listener.onPasswordEmptyError();
            return;
        }
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    listener.onSignupError();
                } else {
                    listener.onSuccess();
                }
            }
        });
    }
}
