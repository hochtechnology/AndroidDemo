package com.hochtechnology.skeleton.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hochtechnology.skeleton.R;
import com.hochtechnology.skeleton.model.User;

import java.util.List;

public class HeroesAdapter extends RecyclerView.Adapter<HeroesAdapter.UserViewHolder> {

    Context mCtx;
    List<User> UserList;

    public HeroesAdapter(Context mCtx, List<User> UserList) {
        this.mCtx = mCtx;
        this.UserList = UserList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater  .from(mCtx).inflate(R.layout.image_layout, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User User = UserList.get(position);

        Log.d("HASMUKH", "onBindViewHolder: " + User.getDownload_url());
        Glide.with(mCtx)
                .load(User.getDownload_url()).placeholder(R.drawable.loading).error(R.drawable.no_image_availablr)
                .into(holder.imageView);

        holder.author.setText("Author :" + User.getAuthor());
        holder.size.setText("Height : " + User.getHeight() + " Weight : " + User.getHeight());
    }

    @Override
    public int getItemCount() {
        return UserList.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView author, size;

        public UserViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imag_view);
            author = itemView.findViewById(R.id.author);
            size = itemView.findViewById(R.id.size);
        }
    }
}