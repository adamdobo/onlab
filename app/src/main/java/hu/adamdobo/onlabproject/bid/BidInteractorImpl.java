package hu.adamdobo.onlabproject.bid;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import hu.adamdobo.onlabproject.model.Item;

/**
 * Created by Ádám on 3/17/2018.
 */

public class BidInteractorImpl implements BidInteractor {
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    BidPresenter presenter;
    Item bidItem;


    @Override
    public void subscribeToItemChange(String itemID) {
        DatabaseReference itemsRef = db.getReference().child("items");
        itemsRef.child(itemID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bidItem = dataSnapshot.getValue(Item.class);
                presenter.onItemReceived(bidItem);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void setPresenter(BidPresenter presenter){
        this.presenter = presenter;
    }

    @Override
    public void placeBid(String itemID, String highestBid) {
        DatabaseReference itemsRef = db.getReference().child("items");

        itemsRef.child(itemID).child("currentBid").setValue(highestBid);
    }

    @Override
    public String getDownloadUrl() {
        return bidItem.imageUrl;
    }

    @Override
    public boolean checkUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        return bidItem.addedBy.equals(user.getDisplayName());

    }
}
