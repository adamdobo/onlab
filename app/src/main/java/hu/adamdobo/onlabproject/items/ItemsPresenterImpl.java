package hu.adamdobo.onlabproject.items;

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
    public void saveItemWithPicture(Item item, byte[] bytes) {
        itemsInteractor.saveItemWithPicture(item, bytes);
    }

    @Override
    public Item getAddedItem() {
        return itemsInteractor.getAddedItem();
    }

    @Override
    public Item getDeletedItem() {
        return itemsInteractor.getDeletedItem();
    }

    @Override
    public Item getChangedItem() {
        return itemsInteractor.getChangedItem();
    }


    @Override
    public void onItemChanged() {
        itemsView.onItemChanged();
    }

    @Override
    public void onItemAdded() {
        itemsView.onItemAdded();
    }

    @Override
    public void onItemDeleted() {
        itemsView.onItemDeleted();
    }

}
