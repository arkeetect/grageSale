package com.muhammadyaseen.classifiedapp;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;



public class PostAdapter extends FirebaseRecyclerAdapter<UploadPost,PostAdapter.myviewholder> {
    public PostAdapter(@NonNull FirebaseRecyclerOptions<UploadPost> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull UploadPost model) {
        holder.tittle_img_post.setText(model.getTitle());
        holder.city_post_sale_item.setText(model.getCity());
        holder.country_post_sale_item.setText(model.getCountry());
        holder.button.setText(model.getNumber());
        holder.price_post_item.setText(model.getPrice());
       // Glide.with(holder.img_post_items.getContext())

         //       .load().into(holder.img_post_items);
        Glide.with(holder.img_post_items).load("gs://garagesale-99c1c.appspot.com/image/*1609412191384.jpg").into(holder.img_post_items);

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.garage_home_sale_items,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{
        ImageView img_post_items;
        TextView tittle_img_post,price_post_item,city_post_sale_item,country_post_sale_item;
        Button button;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            img_post_items=(itemView).findViewById(R.id.img_post_items);
            tittle_img_post=(itemView).findViewById(R.id.tittle_img_post);
            price_post_item=(itemView).findViewById(R.id.price_post_item);
            city_post_sale_item=(itemView).findViewById(R.id.city_post_sale_item);
            country_post_sale_item=(itemView).findViewById(R.id.country_post_sale_item);
            button=(itemView).findViewById(R.id.button);

        }
    }


}
