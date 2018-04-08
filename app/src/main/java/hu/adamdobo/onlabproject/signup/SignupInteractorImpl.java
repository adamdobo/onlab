package hu.adamdobo.onlabproject.signup;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import hu.adamdobo.onlabproject.model.User;

/**
 * Created by Ádám on 3/11/2018.
 */

public class SignupInteractorImpl implements SignupInteractor {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseDatabase db = FirebaseDatabase.getInstance();


    @Override
    public void signup(final User user, String password, final OnSignupFinishedListener listener, SignupView signupView) {
        if (TextUtils.isEmpty(user.email)) {
            listener.onEmailEmptyError();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            listener.onPasswordEmptyError();
            return;
        }

        auth.createUserWithEmailAndPassword(user.email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    listener.onSignupError();
                } else {
                    saveUserToFirebase(user);
                    updateUserProfile(user);
                    auth.signOut();
                    listener.onSuccess();
                }
            }
        });
    }

    @Override
    public void saveUserToFirebase(User user) {
        DatabaseReference usersRef = db.getReference().child("users");
        Map<String, User> users = new HashMap<>();
        users.clear();
        users.put(auth.getCurrentUser().getUid(), user);
        usersRef.setValue(users);
    }

    @Override
    public void updateUserProfile(User user) {
        FirebaseUser firebaseUser = auth.getCurrentUser();
        UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                .setDisplayName(user.nickname)
                .build();
        firebaseUser.updateProfile(profileChangeRequest);
    }
}
