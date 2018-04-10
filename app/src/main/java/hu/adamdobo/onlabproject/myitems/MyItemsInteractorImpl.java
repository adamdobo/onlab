package hu.adamdobo.onlabproject.myitems;

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
 * Created by Ádám on 4/8/2018.
 */

public class MyItemsInteractorImpl implements MyItemsInteractor {
    DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    MyItemsPresenter presenter;
    List<Item> closedBids = new ArrayList<>();
    List<Item> ongoingBids = new ArrayList<>();
    @Override
    public void subscribeToDatabaseChanges() {
        db.child("closedBids").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                closedBids.clear();
                for (DataSnapshot item :
                        dataSnapshot.getChildren()) {
                    Item temp = item.getValue(Item.class);
                    if (temp.addedBy.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                        closedBids.add(temp);
                    }
                }
                presenter.onClosedBidsLoaded();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        db.child("items").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ongoingBids.clear();
                for (DataSnapshot item :
                        dataSnapshot.getChildren()) {
                    Item temp = item.getValue(Item.class);
                    if (temp.addedBy.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                        ongoingBids.add(temp);
                    }
                }
                presenter.onOngoingBidsLoaded();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public List<Item> getClosedBids() {
        return closedBids;
    }

    @Override
    public List<Item> getOngoingBids() {
        return ongoingBids;
    }

    @Override
    public void setPresenter(MyItemsPresenter presenter) {
        this.presenter = presenter;
    }
}
