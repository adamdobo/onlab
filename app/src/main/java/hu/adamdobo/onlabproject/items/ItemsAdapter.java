package hu.adamdobo.onlabproject.items;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import hu.adamdobo.onlabproject.R;
import hu.adamdobo.onlabproject.bid.BidFragment;
import hu.adamdobo.onlabproject.drawer.DrawerActivity;
import hu.adamdobo.onlabproject.model.Item;

/**
 * Created by Ádám on 3/13/2018.
 */

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder> {

    private List<Item> itemList;
    private Context context;

    public ItemsAdapter(Context context) {
        this.context = context;
        itemList = new ArrayList<>();
    }

    @Override
    public ItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemlist_layout_v2, parent, false);

        return new ItemsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemsViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.itemName.setText(item.name);
        if(!item.currentBid.equals("None")) {
            holder.currentPrice.setText(item.currentBid);
        }else {
            holder.currentPrice.setText(item.startPrice);
        }
        if (item.imageUrl != null) {
            Glide.with(context)
                    .load(item.imageUrl)
                    .into(holder.itemPhoto);
            holder.itemPhoto.setVisibility(View.VISIBLE);
        } else {
            holder.itemPhoto.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public void addItem(Item addedItem) {
        itemList.add(addedItem);
        notifyItemInserted(itemList.indexOf(addedItem));
    }

    public void changeItem(Item changedItem) {
    }

    public void deleteItem(Item deletedItem) {
        int index = -1;
        for (Item item : itemList) {
            if(deletedItem !=null) {
                if (item.ID.equals(deletedItem.ID)) {
                    index = itemList.indexOf(item);
                }
            }
        }
        if (index != -1) {
            itemList.remove(index);
            notifyItemRemoved(index);
        }
    }

    class ItemsViewHolder extends RecyclerView.ViewHolder {
        TextView currentPrice, itemName;
        ImageView itemPhoto;

        ItemsViewHolder(final View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            itemPhoto = itemView.findViewById(R.id.itemImage);
            currentPrice = itemView.findViewById(R.id.currentPrice);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    String itemID = itemList.get(getAdapterPosition()).ID;
                    bundle.putString("item_id", itemID);
                    BidFragment bidFragment = (BidFragment) BidFragment.newInstance();
                    bidFragment.setArguments(bundle);
                    ((DrawerActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, bidFragment).addToBackStack(null).commit();
                }
            });
        }
    }
}