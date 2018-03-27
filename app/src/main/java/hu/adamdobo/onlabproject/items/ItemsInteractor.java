package hu.adamdobo.onlabproject.items;

import android.net.Uri;

import hu.adamdobo.onlabproject.model.Item;

/**
 * Created by Ádám on 3/12/2018.
 */

public interface ItemsInteractor {

    Item getAddedItem();

    Item getDeletedItem();

    Item getChangedItem();

    void subscribeToDatabaseChanges();

    void saveItem(Uri uri, Item item);

    void saveItemWithPicture(Item item, byte[] bytes);

    void setPresenter(ItemsPresenter presenter);
}
