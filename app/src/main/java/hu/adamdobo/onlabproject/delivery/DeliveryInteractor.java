package hu.adamdobo.onlabproject.delivery;

import java.util.List;

import hu.adamdobo.onlabproject.model.DeliveryItem;

/**
 * Created by Ádám on 4/10/2018.
 */

public interface DeliveryInteractor {

    void subscribeToDatabaseChanges();

    List<DeliveryItem> getDeliveries();

    void setPresenter(DeliveryPresenter presenter);

    void closeDelivery(DeliveryItem deliveryItem);
}
