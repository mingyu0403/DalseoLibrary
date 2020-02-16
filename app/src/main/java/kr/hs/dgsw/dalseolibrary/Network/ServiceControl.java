package kr.hs.dgsw.dalseolibrary.Network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 정민규 on 2019-06-03.
 */

public class ServiceControl {

    private final static String BASE_URL = "http://dalseolibrary.kro.kr:3000/";

    private static WebServerService instance;

    public static WebServerService getInstance() {
        if (instance != null)
            return instance;
        Retrofit builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        instance = builder.create(WebServerService.class);
        return instance;
    }
}
