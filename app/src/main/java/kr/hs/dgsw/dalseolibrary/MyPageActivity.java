package kr.hs.dgsw.dalseolibrary;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kr.hs.dgsw.dalseolibrary.Beans.LoanBean;
import kr.hs.dgsw.dalseolibrary.Beans.ResponseBean;
import kr.hs.dgsw.dalseolibrary.Beans.UserBean;
import kr.hs.dgsw.dalseolibrary.Model.ItemClickListener;
import kr.hs.dgsw.dalseolibrary.Model.LoanAdapter;
import kr.hs.dgsw.dalseolibrary.Network.ServiceControl;
import kr.hs.dgsw.dalseolibrary.Network.WebServerService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPageActivity extends AppCompatActivity implements ItemClickListener{

    private LinearLayout loginLayout;
    private EditText editTextId;
    private EditText editTextPassword;
    private Button buttonLogin;
    private Button buttonJoin;

    private LinearLayout joinLayout;
    private EditText editTextNewId;
    private EditText editTextNewPassword;
    private EditText editTextNewName;
    private Button buttonJoinOk;
    private Button buttonJoinCancel;

    private LinearLayout myPageLayout;
    private TextView textViewWelcome;

    private WebServerService service;
    private UserBean currentUser;
    private RecyclerView recyclerViewLoanList;
    private ArrayList<LoanBean> data;
    private LoanAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        service = ServiceControl.getInstance();

        // 로그인 레이아웃
        loginLayout = findViewById(R.id.loginLayout);
        editTextId = findViewById(R.id.editTextId);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonJoin = findViewById(R.id.buttonJoin);

        // 회원가입 레이아웃
        joinLayout = findViewById(R.id.joinLayout);
        editTextNewId = findViewById(R.id.editTextNewId);
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        editTextNewName = findViewById(R.id.editTextNewName);
        buttonJoinOk = findViewById(R.id.buttonJoinOk);
        buttonJoinCancel = findViewById(R.id.buttonJoinCancel);

        // 마이페이지 레이아웃
        myPageLayout = findViewById(R.id.myPageLayout);
        textViewWelcome = findViewById(R.id.textViewWelcome);
        recyclerViewLoanList = findViewById(R.id.recyclerViewLoanList);
        data = new ArrayList<>();
        adapter = new LoanAdapter(data, this);

        // 로그인한 유저가 있다면
        if(UserBean.isExistInstance()){
            loginSuccess(UserBean.getInstance());
        } else {
            layoutVisibleControl(loginLayout);
        }

        //로그인 버튼
        buttonLogin.setOnClickListener(v->{
            String id = editTextId.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if(id.length() == 0){
                Toast.makeText(this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                return;
            } else if(password.length() == 0){
                Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }
            UserBean userBean = new UserBean();
            userBean.setId(id);
            userBean.setPassword(password);

            service.loginUser(userBean).enqueue(new Callback<ResponseBean<UserBean>>() {
                @Override
                public void onResponse(Call<ResponseBean<UserBean>> call, Response<ResponseBean<UserBean>> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(MyPageActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        loginSuccess(response.body().getData());
                    } else {
                        Toast.makeText(MyPageActivity.this, "아이디 또는 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBean<UserBean>> call, Throwable t) {

                }
            });
        });
        // 회원가입 버튼
        buttonJoin.setOnClickListener(v->{
            layoutVisibleControl(joinLayout);
        });

        // 회원가입 완료 버튼
        buttonJoinOk.setOnClickListener(v->{

            String newId = editTextNewId.getText().toString().trim();
            String newPassword = editTextNewPassword.getText().toString().trim();
            String newName = editTextNewName.getText().toString().trim();

            if(newId.length() == 0){
                Toast.makeText(this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                return;
            } else if(newPassword.length() == 0){
                Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                return;
            } else if(newName.length() == 0){
                Toast.makeText(this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            UserBean userBean = new UserBean();
            userBean.setId(newId);
            userBean.setPassword(newPassword);
            userBean.setName(newName);

            service.registerUser(userBean).enqueue(new Callback<ResponseBean<UserBean>>() {
                @Override
                public void onResponse(Call<ResponseBean<UserBean>> call, Response<ResponseBean<UserBean>> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(MyPageActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        registerSuccess();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBean<UserBean>> call, Throwable t) {

                }
            });
        });
        // 회원가입 취소 버튼
        buttonJoinCancel.setOnClickListener(v->{
            layoutVisibleControl(loginLayout);
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

    // 로그인 성공
    private void loginSuccess(UserBean currentUser){
        // 로그인 정보 저장
        this.currentUser = UserBean.setInstance(currentUser);

        layoutVisibleControl(myPageLayout);

        // 마이페이지 세팅
        textViewWelcome.setText(currentUser.getName());

        recyclerViewLoanList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,true));
        recyclerViewLoanList.setAdapter(adapter);
        service.getLoans(currentUser.get_id()).enqueue(new Callback<ResponseBean<List<LoanBean>>>() {
            @Override
            public void onResponse(Call<ResponseBean<List<LoanBean>>> call, Response<ResponseBean<List<LoanBean>>> response) {
                if(response.isSuccessful()){
                    adapter.addLoans(response.body().getData());
                }
            }
            @Override
            public void onFailure(Call<ResponseBean<List<LoanBean>>> call, Throwable t) {

            }
        });
    }

    // 회원가입 성공
    private void registerSuccess(){
        layoutVisibleControl(loginLayout);
    }

    // 인자로 받은 레이아웃을 Visible 함
    private void layoutVisibleControl(LinearLayout layout){
        // 애니메이션 xml 파일을 로드
        Animation animation
                = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate);
        layout.startAnimation(animation);
        //View.invalidate();
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editTextId.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(editTextPassword.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(editTextNewId.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(editTextNewPassword.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(editTextNewName.getWindowToken(), 0);

        loginLayout.setVisibility(View.GONE);
        joinLayout.setVisibility(View.GONE);
        myPageLayout.setVisibility(View.GONE);
        switch (layout.getId()){
            case R.id.loginLayout:
                loginLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.joinLayout:
                joinLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.myPageLayout:
                myPageLayout.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onItemClick(View v, int i) {
        final int index = i;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("대출 연장");
        builder.setMessage("도서 '" + data.get(i).getBook().getBook().getBookName() + "' 의 대출 기간을 7일 연장하시겠습니까?");
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                service.loanExtend(data.get(index).get_id()).enqueue(new Callback<ResponseBean<LoanBean>>() {
                    @Override
                    public void onResponse(Call<ResponseBean<LoanBean>> call, Response<ResponseBean<LoanBean>> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(MyPageActivity.this, "대출기간이 연장되었습니다.", Toast.LENGTH_SHORT).show();
                            Log.d("aaaa", (currentUser == null)+"");
                            service.getLoans(currentUser.get_id()).enqueue(new Callback<ResponseBean<List<LoanBean>>>() {
                                @Override
                                public void onResponse(Call<ResponseBean<List<LoanBean>>> call, Response<ResponseBean<List<LoanBean>>> response) {
                                    if(response.isSuccessful()){
                                        adapter.addLoans(response.body().getData());
                                    }
                                }
                                @Override
                                public void onFailure(Call<ResponseBean<List<LoanBean>>> call, Throwable t) {

                                }
                            });
                            // adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBean<LoanBean>> call, Throwable t) {

                    }
                });
            }
        });
        builder.setNeutralButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
}
