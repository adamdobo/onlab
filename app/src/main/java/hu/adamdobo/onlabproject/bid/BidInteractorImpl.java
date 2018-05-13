package hu.adamdobo.onlabproject.bid;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import hu.adamdobo.onlabproject.model.Item;

/**
 * Created by Ádám on 3/17/2018.
 */

public class BidInteractorImpl implements BidInteractor {
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    BidPresenter presenter;
    Item bidItem;


    @Override
    public void subscribeToItemChange(final String itemID) {
        DatabaseReference itemsRef = db.getReference().child("items");
        itemsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.exists() && itemID.equals(dataSnapshot.getKey())) {
                    bidItem = dataSnapshot.getValue(Item.class);
                    presenter.onItemReceived(bidItem);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                presenter.onBidChanged(dataSnapshot.getValue(Item.class));
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                presenter.onBidClosed();
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
    public void setPresenter(BidPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void placeBid(String itemID, final String highestBid) {
        DatabaseReference itemsRef = db.getReference().child("items");

        final DatabaseReference usersRef = db.getReference().child("users");
        itemsRef.child(itemID).child("highestBidder").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
        itemsRef.child(itemID).child("currentBid").setValue(highestBid).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    usersRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("bidItems").child(bidItem.ID).setValue(highestBid);
                }
            }
        });
    }

    @Override
    public String getDownloadUrl() {
        return bidItem.imageUrl;
    }

    @Override
    public boolean checkUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        return bidItem.addedBy.equals(user.getUid());

    }

    @Override
    public void closeBid() {
        DatabaseReference closedBidsRef = db.getReference().child("closedBids").push();
        DatabaseReference itemsRef = db.getReference().child("items");
        itemsRef.child(bidItem.ID).removeValue();
        bidItem.ID = closedBidsRef.getKey();
        bidItem.status = "closed";
        closedBidsRef.setValue(bidItem);
    }

    @Override
    public void buyoutItem() {
        final DatabaseReference closedBidsRef = db.getReference().child("closedBids").push();
        final DatabaseReference itemsRef = db.getReference().child("items");
        itemsRef.child(bidItem.ID).child("currentBid").setValue(bidItem.buyoutPrice).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                itemsRef.child(bidItem.ID).removeValue();
                bidItem.ID = closedBidsRef.getKey();
                bidItem.status = "closed";
                bidItem.highestBidder = FirebaseAuth.getInstance().getCurrentUser().getUid();
                bidItem.currentBid=bidItem.buyoutPrice;
                closedBidsRef.setValue(bidItem);
            }
        });
    }
}
