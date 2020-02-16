package kr.hs.dgsw.dalseolibrary.Model;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import kr.hs.dgsw.dalseolibrary.Beans.BookBean;
import kr.hs.dgsw.dalseolibrary.Beans.LibraryBean;
import kr.hs.dgsw.dalseolibrary.Beans.LoanBean;
import kr.hs.dgsw.dalseolibrary.R;

/**
 * Created by 정민규 on 2019-05-30.
 */

public class LoanAdapter extends RecyclerView.Adapter<LoanAdapter.LoanViewHolder> {

    private List<LoanBean> data;
    private ItemClickListener listener;

    public LoanAdapter(ArrayList<LoanBean> data, ItemClickListener listener){
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LoanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_loan , parent, false);
        return new LoanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoanViewHolder holder, int position) {
        LoanBean loan = data.get(position);

        String bookImage = loan.getBook().getBook().getBookImage();
        String bookName = loan.getBook().getBook().getBookName();
        String libraryName = loan.getBook().getLibrary().getLibraryName();
        String loanStart = new SimpleDateFormat("yyyy-MM-dd").format(loan.getLoanStart());
        String loanEnd = new SimpleDateFormat("yyyy-MM-dd").format(loan.getLoanEnd());

        Glide.with(holder.imageViewBookImage)
                .load(bookImage)
                .into(holder.imageViewBookImage);

        if(loan.isReturned()){ // 반납 완료 되었는지
            holder.buttonLoanRenew.setText("반납 완료");
            holder.buttonLoanRenew.setBackgroundResource(R.color.cardview_dark_background);
            holder.buttonLoanRenew.setTextColor(Color.WHITE);
        } else if(loan.isExtended()){
            loanEnd += " (연장)";
            holder.buttonLoanRenew.setText("연장 완료");
            holder.buttonLoanRenew.setBackgroundResource(R.color.colorPrimary);
            holder.buttonLoanRenew.setTextColor(Color.WHITE);
        } else {
            holder.buttonLoanRenew.setText("대출 연장하기");
            holder.buttonLoanRenew.setBackgroundResource(R.color.colorAccent);
            holder.buttonLoanRenew.setTextColor(Color.WHITE);

            final int index = position;
            holder.buttonLoanRenew.setOnClickListener(v->{
                listener.onItemClick(v, index);
            });
        }

        holder.textViewBookName.setText(bookName);
        holder.textViewLibraryName.setText(libraryName);
        holder.textViewLoanStart.setText(loanStart);
        holder.textViewLoanEnd.setText(loanEnd);


    }

    @Override
    public int getItemCount() {
        if(data == null)
            return 0;
        else
            return data.size();
    }

    public void addLoans(Collection<LoanBean> loanBeans){
        this.data.clear();
        this.data.addAll(loanBeans);
        notifyDataSetChanged();
    }

    class LoanViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewBookImage;
        private TextView textViewBookName;
        private TextView textViewLibraryName;
        private TextView textViewLoanStart;
        private TextView textViewLoanEnd;
        private Button buttonLoanRenew;

        public LoanViewHolder(View itemView) {
            super(itemView);
            imageViewBookImage = itemView.findViewById(R.id.imageViewBookImage);
            textViewBookName = itemView.findViewById(R.id.textViewBookName);
            textViewLibraryName = itemView.findViewById(R.id.textViewLibraryName);
            textViewLoanStart = itemView.findViewById(R.id.textViewLoanStart);
            textViewLoanEnd = itemView.findViewById(R.id.textViewLoanEnd);
            buttonLoanRenew = itemView.findViewById(R.id.buttonLoanRenew);
        }
    }
}
