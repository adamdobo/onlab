package hu.adamdobo.onlabproject.myitems;

import java.util.List;

import hu.adamdobo.onlabproject.model.Item;

/**
 * Created by Ádám on 4/8/2018.
 */

public interface MyItemsInteractor {

    void subscribeToDatabaseChanges();

    List<Item> getClosedBids();

    List<Item> getOngoingBids();

    void setPresenter(MyItemsPresenter presenter);

    void startDelivery(Item item);
}
