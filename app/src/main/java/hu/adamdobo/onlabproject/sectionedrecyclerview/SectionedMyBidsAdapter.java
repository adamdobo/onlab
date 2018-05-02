package hu.adamdobo.onlabproject.sectionedrecyclerview;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.intrusoft.sectionedrecyclerview.SectionRecyclerViewAdapter;

import java.util.List;

import hu.adamdobo.onlabproject.R;
import hu.adamdobo.onlabproject.bid.BidFragment;
import hu.adamdobo.onlabproject.drawer.DrawerActivity;
import hu.adamdobo.onlabproject.model.Item;
import hu.adamdobo.onlabproject.myitems.OnDeliveryClickedListener;

/**
 * Created by Ádám on 5/1/2018.
 */

public class SectionedMyBidsAdapter extends SectionRecyclerViewAdapter<SectionHeader, Item, SectionedMyBidsAdapter.SectionViewHolder, SectionedMyBidsAdapter.ItemViewHolder> {
    private Context context;
    private OnDeliveryClickedListener listener;

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int flatPosition) {
        super.onBindViewHolder(holder, flatPosition);
    }

    public SectionedMyBidsAdapter(Context context, List<SectionHeader> sectionItemList, OnDeliveryClickedListener listener) {
        super(context, sectionItemList);
        this.context = context;
        this.listener = listener;
    }

    @Override
    public SectionedMyBidsAdapter.SectionViewHolder onCreateSectionViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_mybids_sectionheader, viewGroup, false);
        return new SectionedMyBidsAdapter.SectionViewHolder(view);
    }

    @Override
    public SectionedMyBidsAdapter.ItemViewHolder onCreateChildViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.itemlist_layout, viewGroup, false);
        return new SectionedMyBidsAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindSectionViewHolder(SectionedMyBidsAdapter.SectionViewHolder sectionViewHolder, int i, SectionHeader sectionHeader) {
        sectionViewHolder.sectionHeader.setText(sectionHeader.sectionText);
    }

    @Override
    public void onBindChildViewHolder(SectionedMyBidsAdapter.ItemViewHolder itemViewHolder, int i, int i1, final Item item) {
        itemViewHolder.itemName.setText(item.name);
        itemViewHolder.startPrice.setText(item.startPrice);
        itemViewHolder.currentBid.setText(item.currentBid);
        itemViewHolder.buyoutPrice.setText(item.buyoutPrice);
        if (item.imageUrl != null) {
            Glide.with(context)
                    .load(item.imageUrl)
                    .into(itemViewHolder.itemPhoto);
            itemViewHolder.itemPhoto.setVisibility(View.VISIBLE);
        } else {
            itemViewHolder.itemPhoto.setVisibility(View.INVISIBLE);
        }
        if(item.status.equals("closed")){
            itemViewHolder.adaptiveButton.setEnabled(false);
            itemViewHolder.adaptiveButton.setVisibility(View.GONE);
        }else{
            itemViewHolder.adaptiveButton.setText(R.string.more);
            itemViewHolder.adaptiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    String itemID = item.ID;
                    bundle.putString("item_id", itemID);
                    BidFragment bidFragment = (BidFragment) BidFragment.newInstance();
                    bidFragment.setArguments(bundle);
                    ((DrawerActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, bidFragment).addToBackStack(null).commit();
                }
            });
        }

    }

    class SectionViewHolder extends RecyclerView.ViewHolder {
        TextView sectionHeader;

        SectionViewHolder(View itemView) {
            super(itemView);
            sectionHeader = itemView.findViewById(R.id.sectionHeader);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, buyoutPrice, startPrice, currentBid;
        ImageView itemPhoto;
        Button adaptiveButton;

        ItemViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            itemPhoto = itemView.findViewById(R.id.itemImage);
            buyoutPrice = itemView.findViewById(R.id.buyoutPrice);
            startPrice = itemView.findViewById(R.id.startPrice);
            currentBid = itemView.findViewById(R.id.currentBid);
            adaptiveButton = itemView.findViewById(R.id.adaptiveButton);
        }
    }
}
