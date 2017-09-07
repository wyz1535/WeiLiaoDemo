package com.leyifu.weiliaodemo.interf;

import com.leyifu.weiliaodemo.bean.BookDetailBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by hahaha on 2017/9/7 0007.
 */

public interface Presenter {

    @GET("book/search")
    Call<BookDetailBean> getBookDetail(@Query("q") String name,
                                       @Query("tag") String tag,
                                       @Query("start") int start,
                                       @Query("count") int count);


//    @Query(GET请求):用于在url后拼接上参数
//    @QueryMap(GET请求):当然如果入参比较多，就可以把它们都放在Map中
//    @Path(GET请求): 用于替换url中某个字段

    @GET("book/search")
    Observable<BookDetailBean> getBookDetailRxjava(@Query("q") String name, @Query("tag") String tag, @Query("start") int start, @Query("count") int count);

}
