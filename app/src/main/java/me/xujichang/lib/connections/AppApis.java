package me.xujichang.lib.connections;

import me.xujichang.lib.connections.retrofit.TargetUrl;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author xujichang on 2020/5/14.
 */
@TargetUrl("https://wanandroid.com/maven_pom/")
public interface AppApis {
    @GET("search/json")
    Call<ResponseBody> searchRepository(@Query("k") String key);
}
