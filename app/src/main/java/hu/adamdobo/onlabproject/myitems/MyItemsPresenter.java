package hu.adamdobo.onlabproject.myitems;

import java.util.List;

import hu.adamdobo.onlabproject.model.Item;

/**
 * Created by Ádám on 4/8/2018.
 */

public interface MyItemsPresenter {

    void onDestroy();

    List<Item> getClosedBids();

    List<Item> getOngoingBids();

    void onClosedBidsLoaded();

    void onOngoingBidsLoaded();
}
