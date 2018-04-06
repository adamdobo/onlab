package hu.adamdobo.onlabproject.dialog;

import android.support.v7.app.AppCompatDialogFragment;

/**
 * Created by Ádám on 4/5/2018.
 */

public abstract class ChangeCredentialsDialog extends AppCompatDialogFragment {
    protected abstract boolean noEmptyFields();
    protected abstract void clearErrors();
    protected abstract void setEmptyErrors();
}
