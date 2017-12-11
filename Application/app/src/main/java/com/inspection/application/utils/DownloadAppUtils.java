package com.inspection.application.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;


import com.inspection.application.app.App;

import java.io.File;

/**
 * 下载app
 * Created by zhangan on 2017/12/11.
 */

public class DownloadAppUtils {

    @Nullable
    public static Long DownLoad(@NonNull Context context, @NonNull String apkUrl, @NonNull String fileName) {
        Long downLoadId = null;
        DownloadManager.Request request;
        request = new DownloadManager.Request(Uri.parse(apkUrl));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName + ".apk");
        request.setTitle("下载更新中");
        request.setDescription("下载完成后点击安装");
        request.setMimeType("application/vnd.android.package-archive");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        if (downloadManager != null) {
            downLoadId = downloadManager.enqueue(request);
        }
        return downLoadId;
    }

    public static void installApk(Context mContext, String saveFileName) {
        File apkFile = new File(saveFileName);
        if (!apkFile.exists()) {
            return;
        }
        Intent i = new Intent();
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setAction(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            i.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(mContext, mContext.getApplicationContext().getPackageName() + ".fileprovider", apkFile);
            i.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setDataAndType(Uri.parse("file://" + apkFile.toString()), "application/vnd.android.package-archive");
        }
        try {
            mContext.startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
            App.getInstance().showToast("App已经下载完毕,请在下载目录下安装");
        }
    }
}
