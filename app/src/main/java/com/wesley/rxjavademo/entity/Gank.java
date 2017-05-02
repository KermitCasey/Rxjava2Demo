package com.wesley.rxjavademo.entity;

import com.wesley.rxjavacore.entity.BaseListEntity;
import com.wesley.rxjavacore.entity.HttpResult;
import com.wesley.rxjavacore.helper.RxSchedulers;
import com.wesley.rxjavademo.api.GankApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;

/**
 * Created by Weiss on 2017/1/20.
 */

public class Gank extends BaseListEntity {

    public Gank() {

    }

    public String _id;

    public String createdAt;

    public String desc;

    public String publishedAt;

    public String source;

    public String type;

    public String url;

    public String imageUrl;

    public boolean used;

    public String who;

    public List<String> images;

    @Override
    public Observable<HttpResult<List<Gank>>> getPage(int page) {
        return GankApi.get().getGankData(param.get("gank"), page)
                .zipWith(GankApi.get().getGankData("福利", page),
                        new BiFunction<HttpResult<List<Gank>>, HttpResult<List<Gank>>, HttpResult<List<Gank>>>() {
                            @Override
                            public HttpResult<List<Gank>> apply(@NonNull HttpResult<List<Gank>> listHttpResult, @NonNull HttpResult<List<Gank>> listHttpResult2) throws Exception {
                                HttpResult zipItems = new HttpResult();
                                Gank zipItem;
                                List<Gank> zipResults = new ArrayList<Gank>();

                                for (int i = 0; i < listHttpResult2.results.size(); i++) {
                                    zipItem = new Gank();
                                    Gank item = listHttpResult2.results.get(i);
                                    Gank gankInfo = listHttpResult.results.get(i);
                                    if (null == gankInfo.images) {
                                        zipItem.imageUrl = item.url;
                                    } else {
                                        zipItem.imageUrl = gankInfo.images.get(0);
                                    }

                                    zipItem.url = gankInfo.url;
                                    zipItem.desc = gankInfo.desc;
                                    zipItem.who = gankInfo.who;
                                    zipResults.add(zipItem);
                                }
                                zipItems.results = zipResults;
                                return zipItems;
                            }
                        }

                ).compose(RxSchedulers.<HttpResult<List<Gank>>>IOmain());
    }

}
