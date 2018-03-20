package hu.adamdobo.onlabproject.items;

import android.net.Uri;

import java.util.List;

import hu.adamdobo.onlabproject.model.Item;

/**
 * Created by Ádám on 3/12/2018.
 */

public class ItemsPresenterImpl implements ItemsPresenter{
    private ItemsInteractor itemsInteractor;
    private ItemsView itemsView;

    public ItemsPresenterImpl(ItemsView itemsView, ItemsInteractor itemsInteractor) {
        this.itemsInteractor = itemsInteractor;
        this.itemsView = itemsView;
        itemsInteractor.setPresenter(this);
        itemsInteractor.subscribeToDatabaseChanges();
    }


    @Override
    public void onDestroy() {
        itemsView = null;
    }

    @Override
    public void saveItem(Item item) {
        itemsInteractor.saveItem(item);
    }

    @Override
    public void savePictureToFirebase(Uri uri) {
        itemsInteractor.savePictureToFirebase(uri);
    }

    @Override
    public List<Item> getAllItems() {
        return itemsInteractor.getAllItems();
    }

    @Override
    public void onItemChanged() {
        itemsView.onItemChanged();
    }

}
