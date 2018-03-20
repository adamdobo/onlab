package hu.adamdobo.onlabproject.bid;

import hu.adamdobo.onlabproject.model.Item;

/**
 * Created by Ádám on 3/17/2018.
 */

public interface BidView {

    void validateBid(int highestBid, int currentBid, int startPrice);

    void onItemReceived(Item item);

    void updateUI(Item item);

    void setBidSuccess();

    void checkForBidDisable();

    void setCurrentBidFailure();

    void setStartPriceBidFailure();
}
