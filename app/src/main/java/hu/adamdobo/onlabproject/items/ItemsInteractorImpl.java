package hu.adamdobo.onlabproject.items;

import android.net.Uri;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import hu.adamdobo.onlabproject.model.Item;

/**
 * Created by Ádám on 3/12/2018.
 */

public class ItemsInteractorImpl implements ItemsInteractor {

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    ItemsPresenter presenter;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    private Uri downloadUrl;
    private String itemID;
    private Item addedItem;
    private Item deletedItem;
    private Item changedItem;


    @Override
    public void subscribeToDatabaseChanges() {
        DatabaseReference itemsRef = db.getReference().child("items");

        itemsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                addedItem = dataSnapshot.getValue(Item.class);
                presenter.onItemAdded();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                changedItem = dataSnapshot.getValue(Item.class);
                presenter.onItemChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                deletedItem = dataSnapshot.getValue(Item.class);
                presenter.onItemDeleted();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public Item getAddedItem() {
        return addedItem;
    }

    @Override
    public Item getDeletedItem() {
        return deletedItem;
    }

    @Override
    public Item getChangedItem() {
        return changedItem;
    }


    @Override
    public void saveItem(Uri uri, Item item) {
        DatabaseReference itemsRef = db.getReference().child("items").push();
        itemID = itemsRef.getKey();
        item.ID = itemID;
        item.imageUrl = uri.toString();
        item.addedBy = FirebaseAuth.getInstance().getCurrentUser().getUid();
        item.highestBidder = "None";
        itemsRef.setValue(item);
    }

    @Override
    public void saveItemWithPicture(Item item, byte[] bytes) {
        StorageReference storageReference = storage.getReference();
        final Item temp = item;
        storageReference.child("images/" + item.name + item.startPrice).putBytes(bytes).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                saveItem(taskSnapshot.getDownloadUrl(), temp);
            }
        });

    }

    @Override
    public void setPresenter(ItemsPresenter presenter) {
        this.presenter = presenter;
    }
}
