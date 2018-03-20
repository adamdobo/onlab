package hu.adamdobo.onlabproject.items;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import hu.adamdobo.onlabproject.model.Item;

/**
 * Created by Ádám on 3/12/2018.
 */

public class ItemsInteractorImpl implements ItemsInteractor {

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    List<Item> items = new ArrayList<>();
    ItemsPresenter presenter;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    private Uri downloadUrl;
    private String itemID;


    @Override
    public void subscribeToDatabaseChanges() {
        DatabaseReference itemsRef = db.getReference().child("items");
        items.clear();

        itemsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                items.clear();
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    Item temp = child.getValue(Item.class);
                    items.add(temp);
                }
                presenter.onItemChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public List<Item> getAllItems() {
        return items;
    }


    @Override
    public void saveItem(Item item) {
        DatabaseReference itemsRef = db.getReference().child("items").push();
        itemID = itemsRef.getKey();
        item.ID = itemID;
        //item.imageUrl = downloadUrl.toString();
        item.addedBy = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        itemsRef.setValue(item);
    }

    @Override
    public void savePictureToFirebase(Uri uri) {
        StorageReference storageReference = storage.getReference();
        storageReference.child(itemID).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                downloadUrl = taskSnapshot.getDownloadUrl();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("ERROR!", e.getMessage());
            }
        });
    }

    @Override
    public void setPresenter(ItemsPresenter presenter) {
        this.presenter = presenter;
    }
}
