package hu.adamdobo.onlabproject.items;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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


    @Override
    public void subscribeToDatabaseChanges() {
        DatabaseReference itemsRef = db.getReference().child("items");

        itemsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                items.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Item temp = item.getValue(Item.class);
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
        item.ID = itemsRef.getKey();
        item.addedBy = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        itemsRef.setValue(item);
    }

    @Override
    public void setPresenter(ItemsPresenter presenter) {
        this.presenter = presenter;
    }
}
