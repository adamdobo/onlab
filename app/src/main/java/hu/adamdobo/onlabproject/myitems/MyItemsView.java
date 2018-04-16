package hu.adamdobo.onlabproject.myitems;

import hu.adamdobo.onlabproject.model.DeliveryItem;

/**
 * Created by Ádám on 4/8/2018.
 */

public interface MyItemsView {

    void setClosedBidsSection();

    void setOngoingBidsSection();

    void setSections();

    void startDeliveryService(DeliveryItem deliveryItem);
}
