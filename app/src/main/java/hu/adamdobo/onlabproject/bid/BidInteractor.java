package hu.adamdobo.onlabproject.bid;

/**
 * Created by Ádám on 3/17/2018.
 */

public interface BidInteractor {
    void subscribeToItemChange(String itemID);

    void setPresenter(BidPresenter presenter);

    void placeBid(String itemID, String highestBid);

    String getDownloadUrl();

    boolean checkUser();

    void closeBid();
}
