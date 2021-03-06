package com.ggktech.playstore.playstore.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.ggktech.playstore.playstore.R;
import com.ggktech.playstore.playstore.activities.AddItemActivity;
import com.ggktech.playstore.playstore.models.Item;
import com.ggktech.playstore.playstore.models.ItemSingleton;

import java.util.List;

/**
 * Created by Sourabh.Wasnik on 6/29/2017.
 */

public class ItemListFragment extends Fragment {

    private RecyclerView mItemRecyclerView;
    private ItemAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        mItemRecyclerView = (RecyclerView) view.findViewById(R.id.item_recycler_view);
        mItemRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        ItemSingleton itemSingleton = ItemSingleton.get(getActivity());
        List<Item> items = itemSingleton.getItems();
        if (mAdapter == null) {
            mAdapter = new ItemAdapter(items);
            mItemRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Item mItem;

        private TextView mTitleTextView;
        private TextView mDescriptionTextView;
        private CheckBox mSolvedCheckBox;

        public ItemHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item__item_title_text_view);
            mDescriptionTextView = (TextView) itemView.findViewById(R.id.list_item__item_description_text_view);
            mSolvedCheckBox = (CheckBox) itemView.findViewById(R.id.list_item__item_solved_check_box);
        }

        public void bindCrime(Item item) {
            mItem = item;
            mTitleTextView.setText(mItem.getTitle());
            mDescriptionTextView.setText(mItem.getDescription().toString());
            mSolvedCheckBox.setChecked(mItem.isSolved());
        }

        @Override
        public void onClick(View v) {
            Intent intent = AddItemActivity.newIntent(getActivity(), mItem.getId());
            startActivity(intent);

        }
    }

    private class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {

        private List<Item> mItems;

        public ItemAdapter(List<Item> items) {
            mItems = items;
        }

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item__item, parent, false);
            return new ItemHolder(view);
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, int position) {
            Item item = mItems.get(position);
            holder.bindCrime(item);
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }
    }
}