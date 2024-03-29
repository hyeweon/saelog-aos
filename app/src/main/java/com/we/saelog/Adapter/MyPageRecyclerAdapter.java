package com.we.saelog.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.we.saelog.CategoryPostFragment;
import com.we.saelog.MainActivity;
import com.we.saelog.R;
import com.we.saelog.room.MyCategory;

import java.util.ArrayList;
import java.util.List;

public class MyPageRecyclerAdapter extends RecyclerView.Adapter<MyPageRecyclerAdapter.ViewHolder> {
    private ArrayList<MyCategory> myCategoryArrayList;

    @NonNull
    @Override
    public MyPageRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_page_category, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPageRecyclerAdapter.ViewHolder holder, int position) {
        holder.onBind(myCategoryArrayList.get(position));
    }

    public void setMyCategoryArrayList(List<MyCategory> list){
        // DB에 저장된 카테고리로 RecyclerView를 구성
        ArrayList<MyCategory> arrayList = new ArrayList<>();
        arrayList.addAll(list);
        myCategoryArrayList = arrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return myCategoryArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        ImageView mThumbnail;
        TextView mTitle;

        int categoryID;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.cardView);
            mThumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            mTitle = (TextView) itemView.findViewById(R.id.categoryTitle);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("category", myCategoryArrayList.get(position));
                        CategoryPostFragment fragment = new CategoryPostFragment();
                        fragment.setArguments(bundle);

                        FragmentTransaction transaction = ((MainActivity)itemView.getContext()).getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                }
            });
        }

        void onBind(MyCategory item){
            mCardView.setCardBackgroundColor(Color.parseColor(item.getTheme()));
            mTitle.setText(item.getTitle());
            Bitmap bitmapThumbnail = StringToBitmap(item.getThumbnail());
            mThumbnail.setImageBitmap(bitmapThumbnail);
        }
    }

    public static Bitmap StringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
