package hu.adamdobo.onlabproject.sectionedrecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.intrusoft.sectionedrecyclerview.SectionRecyclerViewAdapter;

import java.util.List;

import hu.adamdobo.onlabproject.R;
import hu.adamdobo.onlabproject.model.Item;

/**
 * Created by Ádám on 4/7/2018.
 */

public class SectionedRecyclerViewAdapter extends SectionRecyclerViewAdapter<SectionHeader, Item, SectionedRecyclerViewAdapter.SectionViewHolder, SectionedRecyclerViewAdapter.ItemViewHolder> {

    private Context context;

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int flatPosition) {
        super.onBindViewHolder(holder, flatPosition);
    }

    public SectionedRecyclerViewAdapter(Context context, List<SectionHeader> sectionItemList) {
        super(context, sectionItemList);
        this.context = context;
    }

    @Override
    public SectionViewHolder onCreateSectionViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_mybids_sectionheader, viewGroup, false);
        return new SectionViewHolder(view);
    }

    @Override
    public ItemViewHolder onCreateChildViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.itemlist_layout, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindSectionViewHolder(SectionViewHolder sectionViewHolder, int i, SectionHeader sectionHeader) {
        sectionViewHolder.sectionHeader.setText(sectionHeader.sectionText);
    }

    @Override
    public void onBindChildViewHolder(ItemViewHolder itemViewHolder, int i, int i1, Item item) {
        itemViewHolder.itemName.setText(item.name);
        itemViewHolder.startPrice.setText(item.startPrice);
        itemViewHolder.expirationDate.setText(item.bidExpiry);
        itemViewHolder.currentBid.setText(item.currentBid);
        if(item.imageUrl != null) {
            Glide.with(context)
                    .load(item.imageUrl)
                    .into(itemViewHolder.itemPhoto);
            itemViewHolder.itemPhoto.setVisibility(View.VISIBLE);
        }else {
            itemViewHolder.itemPhoto.setVisibility(View.INVISIBLE);
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
        TextView itemName, expirationDate, startPrice, currentBid;
        ImageView itemPhoto;

        ItemViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            itemPhoto = itemView.findViewById(R.id.itemImage);
            expirationDate = itemView.findViewById(R.id.expiry);
            startPrice = itemView.findViewById(R.id.startPrice);
            currentBid = itemView.findViewById(R.id.currentBid);
        }
    }
}
