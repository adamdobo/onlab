package hu.adamdobo.onlabproject.mybids;

import java.util.List;

import hu.adamdobo.onlabproject.model.Item;

/**
 * Created by Ádám on 4/6/2018.
 */

public class MyBidsPresenterImpl implements MyBidsPresenter {

    private MyBidsInteractor myBidsInteractor;
    private MyBidsView myBidsView;

    public MyBidsPresenterImpl(MyBidsView myBidsView, MyBidsInteractor myBidsInteractor) {
        this.myBidsInteractor = myBidsInteractor;
        this.myBidsView = myBidsView;
        myBidsInteractor.subscribeForNewBids();
        myBidsInteractor.setPresenter(this);

    }

    @Override
    public void onDestroy() {
        myBidsView = null;
    }

    @Override
    public List<Item> getWonItems() {
        return myBidsInteractor.getWonItems();
    }

    @Override
    public List<Item> getOngoingBids() {
        return myBidsInteractor.getOngoingBids();
    }

    @Override
    public void onWonItemsLoaded() {
        if (myBidsView != null) {
            myBidsView.setWonItemsSection();
        }
    }

    @Override
    public void onOngoingBidsLoaded() {
        if (myBidsView != null) {
            myBidsView.setOngoingBidsSection();
        }
    }

    @Override
    public void onBidIdsLoaded() {
        myBidsInteractor.subscribeToDatabaseChanges();
    }
}
