package kr.hs.dgsw.dalseolibrary;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import kr.hs.dgsw.dalseolibrary.Beans.UserBean;
import kr.hs.dgsw.dalseolibrary.Network.ServiceControl;
import kr.hs.dgsw.dalseolibrary.Network.WebServerService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView textView1Rank;
    private TextView textView2Rank;
    private TextView textView3Rank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        WebServerService service = ServiceControl.getInstance();
        findViewById(R.id.button).setOnClickListener(v -> {
            service.loanInit().enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        });


        textView1Rank = findViewById(R.id.textView1Rank);
        textView2Rank = findViewById(R.id.textView2Rank);
        textView3Rank = findViewById(R.id.textView3Rank);

        Task jsoupAsyncTask = new Task();
        jsoupAsyncTask.execute();

    }

    // 순위 받아오기
    class Task extends AsyncTask<Void, Void, List<String>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<String> doInBackground(Void... voids) {
            List<String> result = new ArrayList<>();

            try {
                Document doc = Jsoup.connect("http://search.dalseolib.kr/index.php?mod=wdDataSearch&act=loanBestList").get();
                Elements contents = doc.select(".best_big .title > a");

                result.add(contents.get(0).text());
                result.add(contents.get(1).text());
                result.add(contents.get(2).text());
            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "대출 순위를 불러오지 못했습니다.", Toast.LENGTH_SHORT).show();
            }

            return result;
        }

        @Override
        protected void onPostExecute(List<String> ranks) {
            textView1Rank.setText(ranks.get(0));
            textView2Rank.setText(ranks.get(1));
            textView3Rank.setText(ranks.get(2));
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_mypage) {
            Intent i = new Intent(MainActivity.this, MyPageActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_library) {
            Intent i = new Intent(MainActivity.this, LibraryActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_book) {
            Intent i = new Intent(MainActivity.this, BookActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
