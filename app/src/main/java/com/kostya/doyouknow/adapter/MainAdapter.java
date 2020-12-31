package com.kostya.doyouknow.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kostya.doyouknow.R;
import com.kostya.doyouknow.listeners.OnItemClick;
import com.kostya.doyouknow.model.Post;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<Post> postList;
    private Context mContext;

    private OnItemClick listener;


    public MainAdapter(List<Post> postList,Context context){
        this.postList = postList;
        mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text;
        public ImageView favorite;

        private boolean isFavoriteChecked = false;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.mainText);
            favorite = itemView.findViewById(R.id.favorite);

        }

        void bind(Post currentPost){
            text.setText(currentPost.getText());

            favorite.setOnClickListener(
                    view -> {
                        listener.itemClick(getAdapterPosition(), currentPost);

                        //Если favorite не выбрана,то ставим такую картинку
                        if (!isFavoriteChecked){
                            favorite.setImageDrawable(mContext.getResources().getDrawable(android.R.drawable.btn_star_big_on));
                            isFavoriteChecked = true;
                        } else {
                            //Иначе такую картинку
                            favorite.setImageDrawable(mContext.getResources().getDrawable(android.R.drawable.btn_star_big_off));
                            isFavoriteChecked = false;
                        }
                    }

            );

        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post currentPost = postList.get(position);
        holder.bind(currentPost);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public void setOnItemClickListener(OnItemClick listener){
        this.listener = listener;
    }

    //Принимаемый обновленный лист
    public void updateAdapter(List<Post> updatedList){
        postList.clear();
        postList.addAll(updatedList);
        this.notifyDataSetChanged();
    }

}

//@android:drawable/btn_star_big_off
//@android:drawable/btn_star_big_on