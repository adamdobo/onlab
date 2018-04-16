package hu.adamdobo.onlabproject.delivery;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import hu.adamdobo.onlabproject.model.DeliveryItem;

/**
 * Created by Ádám on 4/10/2018.
 */

public class DeliveryInteractorImpl implements DeliveryInteractor {
    DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    List<DeliveryItem> deliveryItems = new ArrayList<>();
    private DeliveryPresenter presenter;

    @Override
    public void subscribeToDatabaseChanges() {
        db.child("deliveryItems").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                deliveryItems.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()
                        ) {
                    DeliveryItem deliveryItem = item.getValue(DeliveryItem.class);
                    if(deliveryItem.highestBidder.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        deliveryItems.add(deliveryItem);
                    }
                }
                presenter.onDeliveryItemsLoaded();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public List<DeliveryItem> getDeliveries() {
        return deliveryItems;
    }

    @Override
    public void setPresenter(DeliveryPresenter presenter) {
        this.presenter = presenter;
    }
}