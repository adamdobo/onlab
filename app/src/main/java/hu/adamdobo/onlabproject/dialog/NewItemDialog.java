package hu.adamdobo.onlabproject.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import hu.adamdobo.onlabproject.R;
import hu.adamdobo.onlabproject.items.SaveItemCallbackListener;
import hu.adamdobo.onlabproject.model.Item;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Ádám on 3/13/2018.
 */

public class NewItemDialog extends ValidationDialogFragment {

    private static final int REQUEST_IMAGE_CAPTURE = 100;
    public static final String TAG = "NewItemDialog";
    private EditText nameEditText, startPriceEditText, descriptionEditText, buyoutEditText;
    private TextInputLayout nameLayout, startPriceLayout, buyoutLayout;
    private ImageView photoImageView;
    private String mCurrentPhotoPath;
    private static SaveItemCallbackListener listener;
    private byte[] bytes;

    public static NewItemDialog newInstance(SaveItemCallbackListener saveItemCallbackListener) {
        listener = saveItemCallbackListener;
        NewItemDialog newItemDialog = new NewItemDialog();
        return newItemDialog;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getContext())
                .setTitle("New item")
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
                        if (startPriceIsBiggerThanBuyout()) {
                            setStartPriceBiggerThanBuyoutError();
                        }else {
                            wantToCloseDialog = true;
                            if(bytes != null) {
                                listener.saveItemWithPicture(getItem(), bytes);
                            }
                        }
                    } else {
                        setEmptyErrors();
                    }
                    if (wantToCloseDialog) {
                        dismiss();
                    }
                }
            });
        }
    }

    private boolean startPriceIsBiggerThanBuyout() {
        if (((Integer.parseInt(startPriceEditText.getText().toString()) >= Integer.parseInt(buyoutEditText.getText().toString()))))
        {
            return true;
        }
        return false;
    }

    public View getContentView() {
        final View contentView = LayoutInflater.from(getContext()).inflate(R.layout.add_item_dialog, null);
        nameEditText = contentView.findViewById(R.id.item_name);
        startPriceEditText = contentView.findViewById(R.id.item_startprice);
        descriptionEditText = contentView.findViewById(R.id.item_description);
        buyoutEditText = contentView.findViewById(R.id.item_buyout);
        photoImageView = contentView.findViewById(R.id.item_photo);
        nameLayout = contentView.findViewById(R.id.item_name_layout);
        startPriceLayout = contentView.findViewById(R.id.item_startprice_layout);
        buyoutLayout = contentView.findViewById(R.id.item_buyout_layout);
        photoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestNeededPermission();
            }
        });

        return contentView;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


    private void requestNeededPermission() {
        if (ContextCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    android.Manifest.permission.CAMERA)) {
            }

            ActivityCompat.requestPermissions(getActivity(), new String[]{
                            android.Manifest.permission.CAMERA},
                    REQUEST_IMAGE_CAPTURE);
        } else {
            openCameraIntent();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Snackbar.make(getContentView(), "Permission granted!",
                        Snackbar.LENGTH_SHORT).show();

                openCameraIntent();

            } else {
                Snackbar.make(getContentView(), "Permission not granted!",
                        Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    private void openCameraIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.e("IOException", "Error while creating file.");
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "hu.adamdobo.onlabproject.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            bytes = getByteArray();
        }
    }

    private byte[] getByteArray() {
        int targetW = photoImageView.getWidth();
        int targetH = photoImageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        photoImageView.setImageBitmap(bitmap);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    public Item getItem() {
        Item item = new Item();
        item.name = nameEditText.getText().toString();
        item.startPrice = startPriceEditText.getText().toString();
        item.currentBid = "None";
        item.description = descriptionEditText.getText().toString();
        item.buyoutPrice = buyoutEditText.getText().toString();
        return item;
    }

    private void setStartPriceBiggerThanBuyoutError() {
        buyoutLayout.setError(getString(R.string.buyout_must_be_higher));
    }

    @Override
    protected boolean noEmptyFields() {
        if (TextUtils.isEmpty(nameEditText.getText()) || TextUtils.isEmpty(startPriceEditText.getText()) || TextUtils.isEmpty(buyoutEditText.getText())) {
            return false;
        }
        return true;
    }

    @Override
    protected void clearErrors() {
        nameLayout.setError(null);
        startPriceLayout.setError(null);
        buyoutLayout.setError(null);
    }

    @Override
    protected void setEmptyErrors() {
        if (TextUtils.isEmpty(nameEditText.getText())) {
            nameLayout.setError(getString(R.string.fill_out));
        }
        if (TextUtils.isEmpty(startPriceEditText.getText())) {
            startPriceLayout.setError(getString(R.string.fill_out));
        }
        if (TextUtils.isEmpty(buyoutEditText.getText())) {
            buyoutLayout.setError(getString(R.string.fill_out));
        }
    }
}
