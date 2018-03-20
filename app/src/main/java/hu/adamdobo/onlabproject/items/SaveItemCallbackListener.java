package hu.adamdobo.onlabproject.items;

import android.net.Uri;

import hu.adamdobo.onlabproject.model.Item;

/**
 * Created by Ádám on 3/13/2018.
 */

public interface SaveItemCallbackListener {

    void saveItem(Item item);

    void savePictureToFirebase(Uri uri);

}
