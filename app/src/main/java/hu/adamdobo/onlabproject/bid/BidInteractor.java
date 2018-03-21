package hu.adamdobo.onlabproject.bid;

import com.google.firebase.storage.StorageReference;

/**
 * Created by Ádám on 3/17/2018.
 */

public interface BidInteractor {
    void subscribeToItemChange(String itemID);

    void setPresenter(BidPresenter presenter);

    void placeBid(String itemID, String highestBid);

    StorageReference getPhotoReference(String itemID);

    boolean checkUser();
}
