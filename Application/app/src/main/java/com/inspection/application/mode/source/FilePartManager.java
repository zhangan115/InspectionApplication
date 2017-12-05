package com.inspection.application.mode.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 文件上传
 * Created by pingan on 2017/12/5.
 */

public class FilePartManager {

    public static List<MultipartBody.Part> getPostFileParts(@NonNull String businessType, @NonNull String fileType, @NonNull File file) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("businessType", businessType)
                .addFormDataPart("fileType", fileType);
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        builder.addFormDataPart("file", file.getName(), requestFile);
        return builder.build().parts();
    }

}
