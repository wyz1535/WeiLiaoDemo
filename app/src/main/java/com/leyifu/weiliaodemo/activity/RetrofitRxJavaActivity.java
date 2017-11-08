package com.leyifu.weiliaodemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.leyifu.weiliaodemo.R;
import com.leyifu.weiliaodemo.bean.BookDetailBean;
import com.leyifu.weiliaodemo.interf.Presenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class RetrofitRxJavaActivity extends AppCompatActivity{

    @BindView(R.id.btn_test_01)
    Button btnTest01;
    @BindView(R.id.btn_test_02)
    Button btnTest02;
    @BindView(R.id.btn_test_03)
    Button btnTest03;
    @BindView(R.id.btn_test_04)
    Button btnTest04;
    @BindView(R.id.btn_test_05)
    Button btnTest05;
    @BindView(R.id.btn_test_06)
    Button btnTest06;
    private TextView textview;
    public static final String TAG = "RetrofitRxJavaActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_rx_java);
        ButterKnife.bind(this);

        textview = ((TextView) findViewById(R.id.textview));

        initRetrofit();

//        initRxjava();

    }

    private void initRxjava() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.douban.com/v2/")
                //接口返回的数据不是我们需要的实体类，我们需要调用addConverterFactory方法进行转换。由于返回的数据为json类型，所以在这个方法中传入Gson转换工厂
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                //支持rxjava
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        Presenter presenter = retrofit.create(Presenter.class);
        Observable<BookDetailBean> observable = presenter.getBookDetailRxjava("金瓶梅", null, 0, 1);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BookDetailBean>() {

                    @Override
                    public void call(BookDetailBean bookDetailBean) {
                        Log.e(TAG, "call: " + bookDetailBean);
                    }
                });
    }

    private void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.douban.com/v2/")
                //接口返回的数据不是我们需要的实体类，我们需要调用addConverterFactory方法进行转换。由于返回的数据为json类型，所以在这个方法中传入Gson转换工厂
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();
        Presenter presenter = retrofit.create(Presenter.class);
        Call<BookDetailBean> call = presenter.getBookDetail("金瓶梅", null, 0, 1);

        call.enqueue(new Callback<BookDetailBean>() {
            @Override
            public void onResponse(Call<BookDetailBean> call, Response<BookDetailBean> response) {
                BookDetailBean body = response.body();
                textview.setText(body + "");
            }

            @Override
            public void onFailure(Call<BookDetailBean> call, Throwable t) {

            }
        });

    }

}
