package hu.adamdobo.onlabproject.items;

import android.net.Uri;

import java.util.List;

import hu.adamdobo.onlabproject.model.Item;

/**
 * Created by Ádám on 3/12/2018.
 */

public interface ItemsPresenter {

    void onDestroy();

    void saveItem(Item item);

    void savePictureToFirebase(Uri uri);

    List<Item> getAllItems();

    void onItemChanged();

}
