package hu.adamdobo.onlabproject.model;

/**
 * Created by Ádám on 4/10/2018.
 */

public class DeliveryItem extends Item {
    public double longitude;
    public double latitude;

    public DeliveryItem(Item item) {
        ID = item.ID;
        name = item.name;
        addedBy = item.addedBy;
        startPrice = item.startPrice;
        currentBid = item.currentBid;
        highestBidder = item.highestBidder;
        buyoutPrice = item.buyoutPrice;
        imageUrl = item.imageUrl;
        description = item.description;
        status = item.status;
    }

    public DeliveryItem(){

    }
}
