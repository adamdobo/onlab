package hu.adamdobo.onlabproject.mybids;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hu.adamdobo.onlabproject.model.Item;

/**
 * Created by Ádám on 4/6/2018.
 */

public class MyBidsInteractorImpl implements MyBidsInteractor {
    DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    List<Item> wonItems = new ArrayList<>();
    List<Item> ongoingBids = new ArrayList<>();
    Set<String> myBidIds = new HashSet<>();
    MyBidsPresenter presenter;


    @Override
    public void subscribeToDatabaseChanges() {
        db.child("closedBids").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                wonItems.clear();
                for (DataSnapshot item :
                        dataSnapshot.getChildren()) {
                    Item temp = item.getValue(Item.class);
                    if (temp.highestBidder.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                        wonItems.add(temp);
                    }
                }
                presenter.onWonItemsLoaded();
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
                    if (myBidIds.contains(temp.ID)) {
                        ongoingBids.add(item.getValue(Item.class));
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
    public void setPresenter(MyBidsPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public List<Item> getWonItems() {
        return wonItems;
    }

    @Override
    public List<Item> getOngoingBids() {
        return ongoingBids;
    }

    @Override
    public void subscribeForNewBids() {
        db.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("bidItems").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot id :
                     dataSnapshot.getChildren()) {
                    myBidIds.add(id.getKey());
                }
                presenter.onBidIdsLoaded();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
