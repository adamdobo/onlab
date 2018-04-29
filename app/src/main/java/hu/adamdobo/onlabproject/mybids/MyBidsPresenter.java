package hu.adamdobo.onlabproject.mybids;

import java.util.List;

import hu.adamdobo.onlabproject.model.Item;

/**
 * Created by Ádám on 4/6/2018.
 */

public interface MyBidsPresenter {

    void onDestroy();

    List<Item> getWonItems();

    List<Item> getOngoingBids();

    void onWonItemsLoaded();

    void onOngoingBidsLoaded();

    void onBidIdsLoaded();
}
