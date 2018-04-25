package hu.adamdobo.onlabproject.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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

public class NewItemDialog extends AppCompatDialogFragment{

    private static final int REQUEST_IMAGE_CAPTURE = 100;
    public static final String TAG = "NewItemDialog";
    private EditText nameEditText, startPriceEditText, descriptionEditText, buyoutEditText;
    private ImageView photoImageView;
    private String mCurrentPhotoPath;
    private static SaveItemCallbackListener listener;
    private byte[] bytes;

    public static NewItemDialog newInstance(SaveItemCallbackListener saveItemCallbackListener){
        listener = saveItemCallbackListener;
        NewItemDialog newItemDialog = new NewItemDialog();
        return newItemDialog;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getContext())
                .setTitle("New item")
                .setView(getContentView())
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.saveItemWithPicture(getItem(), bytes);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }


    public View getContentView() {
        final View contentView = LayoutInflater.from(getContext()).inflate(R.layout.add_item_dialog, null);
        nameEditText = contentView.findViewById(R.id.item_name);
        startPriceEditText = contentView.findViewById(R.id.item_startprice);
        descriptionEditText = contentView.findViewById(R.id.item_description);
        buyoutEditText = contentView.findViewById(R.id.item_buyout);
        photoImageView = contentView.findViewById(R.id.item_photo);
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
        Double scaleFactor = Math.min(photoW/targetW*1.3, photoH/targetH*1.3);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor.intValue();
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
}
