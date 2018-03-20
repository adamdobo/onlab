package hu.adamdobo.onlabproject.bid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
    Button bidButton;
    ImageView itemImage;
    String itemID;
    BidPresenter presenter;

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_bid, container, false);
        itemName = contentView.findViewById(R.id.itemName);
        bidExpiry = contentView.findViewById(R.id.bidExpiry);
        startPrice = contentView.findViewById(R.id.startPrice);
        currentBid = contentView.findViewById(R.id.currentBid);
        itemDescription = contentView.findViewById(R.id.itemDescription);
        bidEditText = contentView.findViewById(R.id.bidEditText);
        bidButton = contentView.findViewById(R.id.bidButton);
        itemImage = contentView.findViewById(R.id.itemImage);
        itemID = getArguments().getString("item_id");
        presenter = new BidPresenterImpl(this, new BidInteractorImpl(), itemID);

        bidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentBid.getText().toString().equals("None")){
                    validateBid(Integer.parseInt(bidEditText.getText().toString()), 0, Integer.parseInt(startPrice.getText().toString()));

                }else {
                    validateBid(Integer.parseInt(bidEditText.getText().toString()), Integer.parseInt(currentBid.getText().toString()), Integer.parseInt(startPrice.getText().toString()));
                }
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
        checkForBidDisable();
        updateUI(item);
    }

    @Override
    public void updateUI(Item item) {
        itemName.setText(item.name);
        bidExpiry.setText(item.bidExpiry);
        startPrice.setText(item.startPrice);
        currentBid.setText(item.currentBid);
        itemDescription.setText(item.description);
        if(!item.currentBid.equals("None")) {
            bidEditText.setText(item.currentBid);
        }else{
            bidEditText.setText(item.startPrice);
        }
        bidEditText.setSelection(bidEditText.getText().length());
    }

    @Override
    public void setBidSuccess() {
        Snackbar.make(getView(), "Successful bid!", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void checkForBidDisable() {
        if(presenter.checkUser()){
            bidButton.setEnabled(false);
            bidEditText.setEnabled(false);
        }
    }

    @Override
    public void setCurrentBidFailure() {
        Snackbar.make(getView(), "You cannot place a lower bid, than the current highest bid!", Snackbar.LENGTH_LONG).show();
        bidEditText.setText(currentBid.getText());
    }

    @Override
    public void setStartPriceBidFailure() {
        Snackbar.make(getView(), "You cannot place a lower bid, than the starting price!", Snackbar.LENGTH_LONG).show();
        bidEditText.setText(startPrice.getText());
    }
}
