package hu.adamdobo.onlabproject.bid;

import android.content.Context;
import android.widget.ImageView;

import hu.adamdobo.onlabproject.model.Item;

/**
 * Created by Ádám on 3/17/2018.
 */

public interface BidPresenter {

    void onDestroy();

    void onItemReceived(Item temp);

    void onBidSuccess(int highestBid);

    void onCurrentBidFailure();

    void onStartPriceBidFailure();

    void validateBid(int highestBid, int currentBid, int startPrice);

    void loadItemPhoto(Context context, String itemID, ImageView imageView);

    boolean checkUser();
}
