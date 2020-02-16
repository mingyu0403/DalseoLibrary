package kr.hs.dgsw.dalseolibrary.Model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import kr.hs.dgsw.dalseolibrary.Beans.BookBean;
import kr.hs.dgsw.dalseolibrary.R;

/**
 * Created by 정민규 on 2019-05-30.
 */

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<BookBean> data;
    private ItemClickListener listener;

    public BookAdapter(ArrayList<BookBean> data, ItemClickListener listener){
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_book , parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        BookBean book = data.get(position);

        Glide.with(holder.imageViewBookImage)
                .load(book.getBookImage())
                .into(holder.imageViewBookImage);

        holder.textViewBookName.setText(book.getBookName());
        holder.textViewBookAuthor.setText(book.getAuthor());
        holder.textViewBooPublisher.setText(book.getPublisher());

        final int index = position;
        holder.itemView.setOnClickListener(v->{
            listener.onItemClick(v, index);
        });
    }

    @Override
    public int getItemCount() {
        if(data == null)
            return 0;
        else
            return data.size();
    }

    public void addBooks(Collection<BookBean> bookBeans){
        this.data.addAll(bookBeans);
        notifyDataSetChanged();
    }

    class BookViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewBookImage;
        private TextView textViewBookName;
        private TextView textViewBookAuthor;
        private TextView textViewBooPublisher;

        public BookViewHolder(View itemView) {
            super(itemView);
            imageViewBookImage = itemView.findViewById(R.id.imageViewBookImage);
            textViewBookName = itemView.findViewById(R.id.textViewBookName);
            textViewBookAuthor = itemView.findViewById(R.id.textViewBookAuthor);
            textViewBooPublisher = itemView.findViewById(R.id.textViewBooPublisher);
        }
    }
}
