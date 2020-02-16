package kr.hs.dgsw.dalseolibrary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import kr.hs.dgsw.dalseolibrary.Beans.BookBean;
import kr.hs.dgsw.dalseolibrary.Beans.LibraryBean;
import kr.hs.dgsw.dalseolibrary.Beans.ResponseBean;
import kr.hs.dgsw.dalseolibrary.Model.ItemClickListener;
import kr.hs.dgsw.dalseolibrary.Model.LibraryAdapter;
import kr.hs.dgsw.dalseolibrary.Network.ServiceControl;
import kr.hs.dgsw.dalseolibrary.Network.WebServerService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookInfoActivity extends AppCompatActivity implements ItemClickListener{

    private TextView textViewBookNameInfo;
    private ImageView imageViewBookImageInfo;
    private TextView textViewBookInfo;
    private TextView textViewBookPublisherInfo;
    private TextView textViewBookAuthorInfo;

    private RecyclerView recyclerHaveLibraryList;
    private ArrayList<LibraryBean> data;
    private LibraryAdapter adapter;

    private WebServerService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);

        // 애니메이션 xml 파일을 로드
        Animation animation
                = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate);
        findViewById(R.id.card1).startAnimation(animation);
        findViewById(R.id.card2).startAnimation(animation);
        findViewById(R.id.card3).startAnimation(animation);
        findViewById(R.id.card4).startAnimation(animation);
        findViewById(R.id.card5).startAnimation(animation);

        // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textViewBookNameInfo = findViewById(R.id.textViewBookNameInfo);
        imageViewBookImageInfo = findViewById(R.id.imageViewBookImageInfo);
        textViewBookInfo = findViewById(R.id.textViewBookInfo);
        textViewBookAuthorInfo = findViewById(R.id.textViewBookAuthorInfo);
        textViewBookPublisherInfo = findViewById(R.id.textViewBookPublisherInfo);
        recyclerHaveLibraryList = findViewById(R.id.recyclerViewHaveLibraryList);

        // 데이터 세팅
        String bookId = getIntent().getStringExtra("id");
        service = ServiceControl.getInstance();
        service.getBook(bookId).enqueue(new Callback<ResponseBean<BookBean>>() {
            @Override
            public void onResponse(Call<ResponseBean<BookBean>> call, Response<ResponseBean<BookBean>> response) {
                if (response.isSuccessful()) {
                    BookBean book = response.body().getData();
                    textViewBookNameInfo.setText(book.getBookName());
                    Glide.with(imageViewBookImageInfo)
                            .load(book.getBookImage())
                            .into(imageViewBookImageInfo);
                    textViewBookInfo.setText(book.getDescription());
                    textViewBookAuthorInfo.setText(book.getAuthor());
                    textViewBookPublisherInfo.setText(book.getPublisher());
                }
            }

            @Override
            public void onFailure(Call<ResponseBean<BookBean>> call, Throwable t) {

            }
        });

        data = new ArrayList<>();
        adapter = new LibraryAdapter(data, this);

        recyclerHaveLibraryList.setLayoutManager(new LinearLayoutManager(this));
        recyclerHaveLibraryList.setAdapter(adapter);
        service.getBook(bookId).enqueue(new Callback<ResponseBean<BookBean>>() {
            @Override
            public void onResponse(Call<ResponseBean<BookBean>> call, Response<ResponseBean<BookBean>> response) {
                if(response.isSuccessful()){
                    adapter.addLibraries(response.body().getData().getLibraries());
                }
            }

            @Override
            public void onFailure(Call<ResponseBean<BookBean>> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: { //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(View v, int i) {
        Intent intent = new Intent(this, LibraryInfoActivity.class);
        intent.putExtra("id", data.get(i).get_id());
        startActivity(intent);
    }
}
