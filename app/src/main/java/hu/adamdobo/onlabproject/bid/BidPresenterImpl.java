package hu.adamdobo.onlabproject.bid;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import hu.adamdobo.onlabproject.model.Item;

/**
 * Created by Ádám on 3/17/2018.
 */

public class BidPresenterImpl implements BidPresenter {
    private BidView bidView;
    private BidInteractor bidInteractor;
    private String itemID;

    public BidPresenterImpl(BidView bidView, BidInteractor bidInteractor, String itemID) {
        this.bidView = bidView;
        this.bidInteractor = bidInteractor;
        bidInteractor.setPresenter(this);
        bidInteractor.subscribeToItemChange(itemID);
        this.itemID = itemID;
    }


    @Override
    public void onDestroy() {
        this.bidView = null;
    }

    @Override
    public void onItemReceived(Item temp) {
        if (bidView != null) {
            bidView.onItemReceived(temp);
        }
    }

    @Override
    public void onBidSuccess(int highestBid) {
        if (bidView != null) {
            bidInteractor.placeBid(itemID, highestBid + "");
            bidView.setBidSuccess();
        }
    }

    @Override
    public void onCurrentBidFailure() {
        if (bidView != null) {
            bidView.setCurrentBidFailure();
        }
    }

    @Override
    public void onStartPriceBidFailure() {
        if (bidView != null) {
            bidView.setStartPriceBidFailure();
        }
    }

    @Override
    public void validateBid(int highestBid, int currentBid, int startPrice) {
        if (currentBid == 0) {
            if (highestBid < startPrice) {
                onStartPriceBidFailure();
                return;
            }
        } else {
            if (highestBid <= currentBid) {
                onCurrentBidFailure();
                return;
            }
        }
        onBidSuccess(highestBid);
    }

    @Override
    public void loadItemPhoto(Context context, ImageView imageView) {
        Glide.with(context)
                .load(bidInteractor.getDownloadUrl())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        if (bidView != null) {
                            bidView.hideProgress();
                        }
                        return false;
                    }
                })
                .into(imageView);
    }

    @Override
    public boolean checkUser() {
        return bidInteractor.checkUser();
    }

    @Override
    public void closeBid() {
        bidInteractor.closeBid();
    }

    @Override
    public void onBidClosed() {
        if (bidView != null) {
            bidView.onBidClosed();
        }
    }

    @Override
    public void onBidChanged(Item item) {
        if (bidView != null) {
            bidView.onBidChanged(item);
        }
    }

    @Override
    public void buyoutItem() {
        bidInteractor.buyoutItem();
    }

}
