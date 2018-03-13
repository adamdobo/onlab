package hu.adamdobo.onlabproject.dialog;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import hu.adamdobo.onlabproject.R;
import hu.adamdobo.onlabproject.items.SaveItemCallbackListener;
import hu.adamdobo.onlabproject.model.Item;
import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by Ádám on 3/13/2018.
 */

public class NewItemDialog implements DatePickerDialogFragment.OnDateSelectedListener{

    private EditText nameEditText, startPriceEditText, descriptionEditText;
    private TextView expiryTextView;
    private ImageView photoImageView;


    public void show(Context context, final SaveItemCallbackListener listener, FragmentManager fragmentManager){
        final MaterialDialog materialDialog = new MaterialDialog(context);
        materialDialog.setTitle(context.getString(R.string.new_item))
                .setView(getContentView(context, fragmentManager))
                .setPositiveButton(android.R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.saveItem(getItem());
                        materialDialog.dismiss();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        materialDialog.dismiss();
                    }
                })
                .show();
    }

    public View getContentView(final Context context, final FragmentManager fragmentManager) {
        final View contentView = LayoutInflater.from(context).inflate(R.layout.add_item_dialog, null);
        nameEditText = contentView.findViewById(R.id.item_name);
        startPriceEditText = contentView.findViewById(R.id.item_startprice);
        descriptionEditText = contentView.findViewById(R.id.item_description);
        expiryTextView = contentView.findViewById(R.id.item_expiry);
        photoImageView = contentView.findViewById(R.id.item_photo);
        final DatePickerDialogFragment datePickerDialogFragment = new DatePickerDialogFragment();
        datePickerDialogFragment.setOnDateSelectedListener(this);
        expiryTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialogFragment.show(fragmentManager, DatePickerDialogFragment.TAG);
            }
        });

        return contentView;
    }

    public Item getItem() {
        Item item = new Item();
        item.name = nameEditText.getText().toString();
        item.startPrice = startPriceEditText.getText().toString();
        item.currentBid = "None";
        item.description = descriptionEditText.getText().toString();
        item.bidExpiry = expiryTextView.getText().toString();
        return item;
    }

    @Override
    public void onDateSelected(int year, int month, int day) {
        String expiry = year + "-" + (month+1) + "-" + day;
        expiryTextView.setText(expiry);
    }
}
