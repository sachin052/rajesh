package com.example.trail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import android.util.Log;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    private Apicalls apiCalls;
    private RecyclerView recyclerView;
    private ApiAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        recyclerView = findViewById(R.id.recycle);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public okhttp3.Response intercept(@NotNull Chain chain) throws IOException {
                        Request orignalRequest = chain.request();
                        Request newRequest = orignalRequest.newBuilder()
                                .build();
                        return chain.proceed(newRequest);
                    }
                })
                .addInterceptor(loggingInterceptor)
                .build();


        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://wall.alphacoders.com/api2.0/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        apiCalls = retrofit.create(Apicalls.class);
        viewby();

    }

    private void viewby() {

        Call<Photos> call = apiCalls.getphotos_viewby("9810aea4588d89773551cdd66b618821","by_views",1,1);
        call.enqueue(new Callback<Photos>() {
            @Override
            public void onResponse(Call<Photos> call, Response<Photos> response){
                Toast.makeText(MainActivity.this, "Load...", Toast.LENGTH_SHORT).show();

                //List<viewby> viewbyList=response.body();
                recyclerView = findViewById(R.id.recycle);
                recyclerView.setHasFixedSize(true);
                adapter= new ApiAdapter(getApplicationContext(),response.body().getWallpapers());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<Photos> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }


    }

