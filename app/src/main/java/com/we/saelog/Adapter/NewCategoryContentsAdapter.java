package com.we.saelog.Adapter;

import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.we.saelog.R;

import java.util.ArrayList;

public class NewCategoryContentsAdapter extends RecyclerView.Adapter<NewCategoryContentsAdapter.ViewHolder> {
    public ArrayList<String> contentTitles = new ArrayList<>();

    @NonNull
    @Override
    public NewCategoryContentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new_category_content, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NewCategoryContentsAdapter.ViewHolder holder, int position) {
        holder.onBind(contentTitles.get(position));
    }

    public void addItem(){
        contentTitles.add(null);
        notifyItemInserted(contentTitles.size() - 1);
    }

    public void deleteItem(int position){
        contentTitles.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return contentTitles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mContentOrder;
        EditText mcontentTitle;
        Spinner mSpinner;
        ImageButton mBtnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mContentOrder = (TextView) itemView.findViewById(R.id.contentOrder);

            // Title
            mcontentTitle = (EditText) itemView.findViewById(R.id.contentTitle);
            mcontentTitle.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    mcontentTitle.setTypeface(Typeface.DEFAULT_BOLD);
                    contentTitles.set(getAdapterPosition(), charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    mcontentTitle.setTypeface(Typeface.DEFAULT_BOLD);
                }
            });

            // Spinner
            mSpinner = (Spinner) itemView.findViewById(R.id.spinner);
            ArrayAdapter contentTypeAdapter = ArrayAdapter.createFromResource(itemView.getContext(), R.array.content_types, R.layout.item_content_type);
            mSpinner.setAdapter(contentTypeAdapter);

            // Delete
            mBtnDelete = (ImageButton) itemView.findViewById(R.id.btnDelete);
            mBtnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteItem(getAdapterPosition());
                    new OnIconClickListener() {
                        @Override
                        public void onIconClick() {

                        }
                    };
                }
            });
        }

        public void onBind(String item) {
            mContentOrder.setText(String.format("%d. ", getAdapterPosition() + 1));
            mcontentTitle.setText(item);
        }
    }
}
