package hu.adamdobo.onlabproject.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import hu.adamdobo.onlabproject.R;
import hu.adamdobo.onlabproject.profile.ChangeEmailCallbackListener;

/**
 * Created by Ádám on 4/5/2018.
 */

public class ChangeEmailDialog extends ValidationDialogFragment {

    public static final String TAG = "ChangeEmailDialog";
    private static ChangeEmailCallbackListener listener;
    EditText newEmailEditText, passwordEditText;
    TextInputLayout newEmailLayout, passwordLayout;

    public static ChangeEmailDialog newInstance(ChangeEmailCallbackListener changeEmailCallbackListener) {
        listener = changeEmailCallbackListener;
        return new ChangeEmailDialog();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getContext())
                .setTitle("Change email")
                .setView(getContentView())
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }

    @Override
    public void onStart() {
        super.onStart();
        AlertDialog d = (AlertDialog) getDialog();
        if (d != null) {
            Button positiveButton = (Button) d.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clearErrors();
                    Boolean wantToCloseDialog = false;
                    if (noEmptyFields()) {
                        wantToCloseDialog = true;
                        listener.changeEmail(newEmailEditText.getText().toString(), passwordEditText.getText().toString());
                    }
                    if (wantToCloseDialog) {
                        dismiss();
                    }
                }
            });
        }

    }

    public View getContentView() {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.change_email_dialog, null);
        newEmailEditText = contentView.findViewById(R.id.newEmailEditText);
        passwordEditText = contentView.findViewById(R.id.passwordEditText);
        newEmailLayout = contentView.findViewById(R.id.newEmail);
        passwordLayout = contentView.findViewById(R.id.password);
        return contentView;
    }

    @Override
    protected boolean noEmptyFields() {
        if(TextUtils.isEmpty(newEmailEditText.getText()) || TextUtils.isEmpty(passwordEditText.getText())){
            setEmptyErrors();
            return false;
        }
        return true;
    }

    @Override
    protected void clearErrors() {
        newEmailLayout.setError(null);
        passwordLayout.setError(null);
    }

    @Override
    protected void setEmptyErrors() {
        if(TextUtils.isEmpty(newEmailEditText.getText())){
            newEmailLayout.setError(getString(R.string.fill_out));
        }
        if(TextUtils.isEmpty(passwordEditText.getText())){
            passwordLayout.setError(getString(R.string.fill_out));
        }
    }
}
