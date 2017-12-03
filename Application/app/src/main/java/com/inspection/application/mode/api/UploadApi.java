package com.inspection.application.mode.api;

import com.inspection.application.mode.bean.Bean;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * 提交文件api
 * Created by pingan on 2017/12/2.
 */

public interface UploadApi {

    /**
     * 文件上传
     *
     * @param partList 参数
     * @return 订阅
     */
    @POST("base/file/upload.json")
    @Multipart
    Observable<Bean<List<String>>> postImageFile(@Part List<MultipartBody.Part> partList);
}
