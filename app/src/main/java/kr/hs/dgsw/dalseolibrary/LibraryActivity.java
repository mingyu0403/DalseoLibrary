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

import java.util.ArrayList;
import java.util.List;

import kr.hs.dgsw.dalseolibrary.Beans.LibraryBean;
import kr.hs.dgsw.dalseolibrary.Beans.ResponseBean;
import kr.hs.dgsw.dalseolibrary.Model.ItemClickListener;
import kr.hs.dgsw.dalseolibrary.Model.LibraryAdapter;
import kr.hs.dgsw.dalseolibrary.Network.ServiceControl;
import kr.hs.dgsw.dalseolibrary.Network.WebServerService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LibraryActivity extends AppCompatActivity implements ItemClickListener{

    private RecyclerView recyclerViewLibraryList;
    private ArrayList<LibraryBean> data;
    private LibraryAdapter adapter;
    private WebServerService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        service = ServiceControl.getInstance();
        recyclerViewLibraryList = findViewById(R.id.recyclerViewLibraryList);

        // 애니메이션 xml 파일을 로드
        Animation animation
                = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate);
        recyclerViewLibraryList.startAnimation(animation);
        //View.invalidate();

        data = new ArrayList<>();
        adapter = new LibraryAdapter(data, this);

        recyclerViewLibraryList.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewLibraryList.setAdapter(adapter);
        service.getAllLibraries().enqueue(new Callback<ResponseBean<List<LibraryBean>>>() {
            @Override
            public void onResponse(Call<ResponseBean<List<LibraryBean>>> call, Response<ResponseBean<List<LibraryBean>>> response) {
                if (response.isSuccessful()) {
                    adapter.addLibraries(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResponseBean<List<LibraryBean>>> call, Throwable t) {

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
