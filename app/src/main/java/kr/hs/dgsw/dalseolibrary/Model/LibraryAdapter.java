package kr.hs.dgsw.dalseolibrary.Model;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;

import kr.hs.dgsw.dalseolibrary.Beans.LibraryBean;
import kr.hs.dgsw.dalseolibrary.R;

/**
 * Created by 정민규 on 2019-05-30.
 */

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.LibraryViewHolder> {

    private ArrayList<LibraryBean> data;
    private ItemClickListener listener;

    public LibraryAdapter(ArrayList<LibraryBean> data, ItemClickListener listener){
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LibraryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_library , parent, false);
        return new LibraryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LibraryViewHolder holder, int position) {
        LibraryBean library = data.get(position);

        holder.textViewLibraryName.setText(library.getLibraryName());
        holder.textViewLibraryLocation.setText(library.getLocation());

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

    public void addLibraries(Collection<LibraryBean> libraryBeans){
        this.data.addAll(libraryBeans);
        notifyDataSetChanged();
    }

    class LibraryViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewLibraryName;
        private TextView textViewLibraryLocation;

        public LibraryViewHolder(View itemView) {
            super(itemView);
            textViewLibraryName = itemView.findViewById(R.id.textViewLibraryName);
            textViewLibraryLocation = itemView.findViewById(R.id.textViewLibraryLocation);
        }
    }
}


