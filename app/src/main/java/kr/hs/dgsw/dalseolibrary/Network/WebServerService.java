package kr.hs.dgsw.dalseolibrary.Network;

import java.util.List;

import kr.hs.dgsw.dalseolibrary.Beans.BookBean;
import kr.hs.dgsw.dalseolibrary.Beans.LibraryBean;
import kr.hs.dgsw.dalseolibrary.Beans.LoanBean;
import kr.hs.dgsw.dalseolibrary.Beans.ResponseBean;
import kr.hs.dgsw.dalseolibrary.Beans.UserBean;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by 정민규 on 2019-06-03.
 */

public interface WebServerService {

    // BOOK
    @GET("/book/loadAll")
    Call<ResponseBean<List<BookBean>>> getAllBooks();

    @GET("/book/load/{id}")
    Call<ResponseBean<BookBean>> getBook(@Path("id") String id);

    // LIBRARY
    @GET("/library/loadAll")
    Call<ResponseBean<List<LibraryBean>>> getAllLibraries();

    @GET("/library/load/{id}")
    Call<ResponseBean<LibraryBean>> getLibrary(@Path("id") String id);

    // USER
    @POST("/user/register")
    Call<ResponseBean<UserBean>> registerUser(@Body UserBean user);

    @POST("/user/login")
    Call<ResponseBean<UserBean>> loginUser(@Body UserBean user);

    // LOAN
    @GET("/loan/load/{userId}")
    Call<ResponseBean<List<LoanBean>>> getLoans(@Path("userId") String userId);

    @PUT("/loan/extend/{loanId}")
    Call<ResponseBean<LoanBean>> loanExtend(@Path("loanId") String loanId);


    @PUT("loan/init/5cf63e0f6463933f4461a436")
    Call<String> loanInit();

}
