package hu.adamdobo.onlabproject.myitems;

import java.util.List;

import hu.adamdobo.onlabproject.model.Item;

/**
 * Created by Ádám on 4/8/2018.
 */

public class MyItemsPresenterImpl implements MyItemsPresenter {
    private MyItemsView myItemsView;
    private MyItemsInteractor myItemsInteractor;

    public MyItemsPresenterImpl(MyItemsView myItemsView, MyItemsInteractor myItemsInteractor) {
        this.myItemsView = myItemsView;
        this.myItemsInteractor = myItemsInteractor;
        myItemsInteractor.setPresenter(this);
        myItemsInteractor.subscribeToDatabaseChanges();
    }

    @Override
    public void onDestroy() {
        myItemsView = null;
    }

    @Override
    public List<Item> getClosedBids() {
        return myItemsInteractor.getClosedBids();
    }

    @Override
    public List<Item> getOngoingBids() {
        return myItemsInteractor.getOngoingBids();
    }

    @Override
    public void onClosedBidsLoaded() {
        if (myItemsView != null) {
            myItemsView.setClosedBidsSection();
        }
    }

    @Override
    public void onOngoingBidsLoaded() {
        if (myItemsView != null) {
            myItemsView.setOngoingBidsSection();
        }

    }
}
