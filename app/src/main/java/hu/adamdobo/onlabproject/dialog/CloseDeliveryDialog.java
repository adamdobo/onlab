package hu.adamdobo.onlabproject.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;

import hu.adamdobo.onlabproject.delivery.DeliveryDoneListener;
import hu.adamdobo.onlabproject.model.DeliveryItem;

/**
 * Created by Ádám on 5/1/2018.
 */

public class CloseDeliveryDialog extends AppCompatDialogFragment {

    public static final String TAG = "CloseDeliveryDialog";
    private static DeliveryDoneListener listener;
    private static DeliveryItem deliveryItem;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getContext())
                .setTitle("New item")
                .setMessage("Do you want to mark this delivery as done? (Only do this if you got the item delivered to you!")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.closeDelivery(deliveryItem);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }

    public static CloseDeliveryDialog newInstance(DeliveryDoneListener deliveryDoneListener, DeliveryItem item) {
        listener = deliveryDoneListener;
        CloseDeliveryDialog closeDeliveryDialog = new CloseDeliveryDialog();
        deliveryItem = item;
        return closeDeliveryDialog;


    }
}
