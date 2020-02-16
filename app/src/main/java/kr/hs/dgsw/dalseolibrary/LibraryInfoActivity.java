package kr.hs.dgsw.dalseolibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.IOException;

import kr.hs.dgsw.dalseolibrary.Beans.LibraryBean;
import kr.hs.dgsw.dalseolibrary.Beans.ResponseBean;
import kr.hs.dgsw.dalseolibrary.Network.ServiceControl;
import kr.hs.dgsw.dalseolibrary.Network.WebServerService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LibraryInfoActivity extends AppCompatActivity {

    private TextView textViewLibraryNameInfo;
    private ImageView imageViewLibraryImageInfo;
    private TextView textViewLocationInfo;
    private TextView textViewClosedDayInfo;

    private WebServerService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_info);

        // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textViewLibraryNameInfo = findViewById(R.id.textViewLibraryNameInfo);
        imageViewLibraryImageInfo = findViewById(R.id.imageViewLibraryImageInfo);
        textViewLocationInfo = findViewById(R.id.textViewLocationInfo);
        textViewClosedDayInfo = findViewById(R.id.textViewClosedDayInfo);

        // 애니메이션 xml 파일을 로드
        Animation animation
                = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate);
        findViewById(R.id.card1).startAnimation(animation);
        findViewById(R.id.card2).startAnimation(animation);
        findViewById(R.id.card3).startAnimation(animation);


        // 데이터 세팅
        String libraryId = getIntent().getStringExtra("id");
        service = ServiceControl.getInstance();
        service.getLibrary(libraryId).enqueue(new Callback<ResponseBean<LibraryBean>>() {
            @Override
            public void onResponse(Call<ResponseBean<LibraryBean>> call, Response<ResponseBean<LibraryBean>> response) {
                if (response.isSuccessful()) {
                    LibraryBean lib = response.body().getData();
                    textViewLibraryNameInfo.setText(lib.getLibraryName());
                    Glide.with(imageViewLibraryImageInfo)
                            .load(lib.getLibraryImage())
                            .into(imageViewLibraryImageInfo);
                    textViewLocationInfo.setText(lib.getLocation());
                    textViewClosedDayInfo.setText(lib.getClosedDay());
                }
            }

            @Override
            public void onFailure(Call<ResponseBean<LibraryBean>> call, Throwable t) {

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


}
