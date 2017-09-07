package com.leyifu.weiliaodemo.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.leyifu.weiliaodemo.R;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RxJavaActivity extends AppCompatActivity {

    private static final String TAG = RxJavaActivity.class.getSimpleName();
    private Button btn_iv;
    private ImageView imageView;
    private String Path = "http://img2.imgtn.bdimg.com/it/u=2974104803,1439396293&fm=200&gp=0.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);

        initView();

//        init01();

//        init02();

//        initRange();

        initFilter();

//        initInterval();


    }


    private void initView() {
        btn_iv = ((Button) findViewById(R.id.btn_iv));
        imageView = ((ImageView) findViewById(R.id.imageView));

        btn_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable<byte[]> observable = initOkHttp();
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<byte[]>() {
                            @Override
                            public void onCompleted() {
                                Log.e(TAG, "onCompleted: ");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "onError: =" + e);
                            }

                            @Override
                            public void onNext(byte[] bytes) {
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                imageView.setImageBitmap(bitmap);
                            }
                        });
            }
        });
    }

    private Observable<byte[]> initOkHttp() {
        OkHttpClient client = new OkHttpClient();
        return Observable.create(new Observable.OnSubscribe<byte[]>() {
            @Override
            public void call(Subscriber<? super byte[]> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    Request request = new Request.Builder().url(Path).build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            subscriber.onError(e);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (response.isSuccessful()) {
                                byte[] bytes = response.body().bytes();
                                if (bytes != null) {
                                    subscriber.onNext(bytes);
                                }
                            }
                            subscriber.onCompleted();
                        }
                    });
                }
            }
        });

    }

    /**
     * 定时发送
     */
    private void initInterval() {
        Observable.interval(3, 1, TimeUnit.SECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        Log.e(TAG, "call: " + aLong);
                    }
                });
    }

    /**
     * 过滤器
     * 输入 6，7，8，9
     */
    private void initFilter() {
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9)
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer > 5;
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.e(TAG, "call: " + integer);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG, "call: " + throwable);
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        Log.e(TAG, "onCompleted: ");
                    }
                });
    }

    private void initRange() {
        //输入0-39   适用范围
        Observable.range(0, 40)
                .subscribeOn(Schedulers.io())       //事件产生线程
                .observeOn(AndroidSchedulers.mainThread())  //时间消费线程
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.e(TAG, "onNext: " + integer);
                    }
                });
    }


    /**
     * 使用create方式
     */
    private void init02() {
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    /*onNext 一次执行*/
                    subscriber.onNext("java");
                    subscriber.onNext("android");
                    subscriber.onNext(downloadPython());
                    subscriber.onNext("PHP");
                    subscriber.onCompleted();
                }
            }
        });
//        observable.observeOn(Schedulers.io());
//        observable.subscribeOn(Schedulers.)
        Observer observer = new Observer() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ");
            }

            @Override
            public void onNext(Object o) {
                Log.e(TAG, "onNext: " + o);
            }
        };

        observable.subscribe(observer);
    }

    private String downloadPython() {
        return "python";
    }

    private void init01() {
        Observable.just("nihaoya")
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return s + "ok";
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.e(TAG, "call: " + s);
                    }
                });
    }
}
