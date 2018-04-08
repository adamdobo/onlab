package hu.adamdobo.onlabproject.mybids;

import java.util.List;

import hu.adamdobo.onlabproject.model.Item;

/**
 * Created by Ádám on 4/6/2018.
 */

public interface MyBidsInteractor {

    void subscribeToDatabaseChanges();

    void setPresenter(MyBidsPresenter presenter);

    List<Item> getWonItems();

    List<Item> getOngoingBids();

    void subscribeForNewBids();
}
