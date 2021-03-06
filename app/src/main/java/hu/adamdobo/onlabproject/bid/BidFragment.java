package hu.adamdobo.onlabproject.bid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import hu.adamdobo.onlabproject.R;
import hu.adamdobo.onlabproject.model.Item;

/**
 * Created by Ádám on 3/17/2018.
 */

public class BidFragment extends Fragment implements BidView {
    TextView itemName, bidExpiry, startPrice, currentBid, itemDescription;
    EditText bidEditText;
    TextInputLayout bidLayout;
    Button bidButton, closeBidButton, buyoutButton;
    ImageView itemImage;
    String itemID;
    BidPresenter presenter;
    private FrameLayout progressBarHolder;

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_bid, container, false);
        progressBarHolder = contentView.findViewById(R.id.progressBarHolder);
        showProgress();
        itemID = getArguments().getString("item_id");
        presenter = new BidPresenterImpl(this, new BidInteractorImpl(), itemID);
        itemName = contentView.findViewById(R.id.itemName);
        bidExpiry = contentView.findViewById(R.id.bidExpiry);
        startPrice = contentView.findViewById(R.id.startPrice);
        currentBid = contentView.findViewById(R.id.currentBid);
        itemDescription = contentView.findViewById(R.id.itemDescription);
        bidEditText = contentView.findViewById(R.id.bidEditText);
        bidLayout  = contentView.findViewById(R.id.bidLayout);
        bidButton = contentView.findViewById(R.id.bidButton);
        buyoutButton = contentView.findViewById(R.id.buyoutButton);
        closeBidButton = contentView.findViewById(R.id.closeBidButton);
        itemImage = contentView.findViewById(R.id.itemImage);




        bidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentBid.getText().toString().equals(getString(R.string.none))){
                    validateBid(Integer.parseInt(bidEditText.getText().toString()), 0, Integer.parseInt(startPrice.getText().toString()));

                }else {
                    validateBid(Integer.parseInt(bidEditText.getText().toString()), Integer.parseInt(currentBid.getText().toString()), Integer.parseInt(startPrice.getText().toString()));
                }
            }
        });

        buyoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.buyoutItem();
            }
        });

        closeBidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.closeBid();
            }
        });
        return contentView;
    }

    public static Fragment newInstance(){ return new BidFragment();}

    @Override
    public void validateBid(int highestBid, int currentBid, int startPrice) {
        presenter.validateBid(highestBid, currentBid, startPrice);
    }

    @Override
    public void onItemReceived(Item item) {
        setImageView();
        updateUI(item);
    }

    @Override
    public void updateUI(Item item) {
        itemName.setText(item.name);
        startPrice.setText(item.startPrice);
        currentBid.setText(item.currentBid);
        itemDescription.setText(item.description);
        if(!item.currentBid.equals(getString(R.string.none))) {
            bidEditText.setText(item.currentBid);
        }else{
            bidEditText.setText(item.startPrice);
        }
        bidEditText.setSelection(bidEditText.getText().length());
    }

    @Override
    public void setBidSuccess() {
        bidLayout.setError(null);
        Snackbar.make(getView(), R.string.successfull_bid, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void setImageView() {
        presenter.loadItemPhoto(getContext(), itemImage);
    }

    @Override
    public void checkForBidDisable() {
        if(presenter.checkUser()){
            bidButton.setEnabled(false);
            bidButton.setVisibility(View.GONE);
            bidEditText.setEnabled(false);
            buyoutButton.setEnabled(false);
            buyoutButton.setVisibility(View.GONE);
            closeBidButton.setEnabled(true);
            closeBidButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setCurrentBidFailure() {
        bidLayout.setError(getString(R.string.lower_than_highest));
        bidEditText.setText(currentBid.getText());
    }

    @Override
    public void setStartPriceBidFailure() {
        bidLayout.setError(getString(R.string.lower_than_starting));
        bidEditText.setText(startPrice.getText());
    }

    @Override
    public void onBidClosed() {
        getActivity().getSupportFragmentManager().popBackStack();
        Snackbar.make(getActivity().getCurrentFocus(), R.string.bid_closed, Snackbar.LENGTH_LONG).show();

    }

    @Override
    public void onBidChanged(Item item) {
        currentBid.setText(item.currentBid);
    }

    @Override
    public void hideProgress() {
        AlphaAnimation outAnimation = new AlphaAnimation(1f, 0f);
        outAnimation.setDuration(200);
        progressBarHolder.setAnimation(outAnimation);
        progressBarHolder.setVisibility(View.GONE);
        checkForBidDisable();
    }

    @Override
    public void showProgress() {
        AlphaAnimation inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        progressBarHolder.setAnimation(inAnimation);
        progressBarHolder.setVisibility(View.VISIBLE);
    }

}
