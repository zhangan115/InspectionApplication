package com.inspection.application.view.setting;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.inspection.application.R;
import com.inspection.application.app.App;
import com.inspection.application.mode.Injection;
import com.inspection.application.mode.bean.version.NewVersion;
import com.inspection.application.utils.PhotoUtils;
import com.inspection.application.view.BaseActivity;
import com.inspection.application.view.setting.feedback.QuestionActivity;
import com.library.utils.GlideUtils;
import com.soundcloud.android.crop.Crop;

import java.io.File;

public class SettingActivity extends BaseActivity implements View.OnClickListener, SettingContract.View {
    private ImageView mUserPhoto;

    SettingContract.Presenter mPresenter;

    private File photoFile;
    private static final int ACTION_START_CAMERA = 100;
    private static final int ACTION_START_PHOTO = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new SettingPresenter(Injection.getIntent().provideApplicationRepository(App.getInstance().getModule()), this);
        setLayoutAndToolbar(R.layout.activity_setting, "设置");
        initView();
    }

    private void initView() {
        TextView mUser = findViewById(R.id.id_setting_user);
        TextView mUserPosition = findViewById(R.id.id_setting_user_position);
        LinearLayout itemLLs = findViewById(R.id.ll_items);
        for (int i = 0; i < itemLLs.getChildCount(); i++) {
            itemLLs.getChildAt(i).setOnClickListener(this);
        }
        mUserPhoto = findViewById(R.id.iv_user_photo);
        mUserPhoto.setOnClickListener(this);
        if (App.getInstance().getCurrentUser() != null) {
            mUser.setText(App.getInstance().getCurrentUser().getRealName());
            mUserPosition.setText(App.getInstance().getCurrentUser().getUserRoleNames());
            GlideUtils.ShowCircleImage(this, App.getInstance().getCurrentUser().getPortraitUrl()
                    , mUserPhoto, R.drawable.mine_head_default);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_setting_about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case R.id.ll_setting_clear:
                mPresenter.cleanCache();
                break;
            case R.id.ll_setting_sugback:
                startActivity(new Intent(this, QuestionActivity.class));
                break;
            case R.id.ll_setting_version:
                mPresenter.getNewVersion();
                break;
            case R.id.iv_user_photo:
                new MaterialDialog.Builder(this)
                        .items(R.array.choose_photo)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                if (position == 0) {
                                    photoFile = new File(App.getInstance().imageCacheFile(), System.currentTimeMillis() + ".jpg");
                                    Intent intent = new Intent();
                                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                                    ContentValues contentValues = new ContentValues(1);
                                    contentValues.put(MediaStore.Images.Media.DATA, photoFile.getAbsolutePath());
                                    Uri uri = getApplicationContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                                    startActivityForResult(intent, ACTION_START_CAMERA);
                                } else {
                                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                    intent.setType("image/*");
                                    startActivityForResult(intent, ACTION_START_PHOTO);
                                }
                            }
                        })
                        .show();
                break;
        }
    }

    @Override
    public void newVersionDialog(@NonNull NewVersion version) {
        new MaterialDialog.Builder(this)
                .content(version.getNote())
                .negativeText("取消")
                .positiveText("确定")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        downLoadApp();
                    }
                }).show();
    }

    @Override
    public void currentVersion() {
        App.getInstance().showToast("当前为最新版本");
    }

    @Override
    public void downLoadApp() {

    }

    @Override
    public void uploadUserPhotoSuccess(String url) {
        App.getInstance().getCurrentUser().setPortraitUrl(url);
        GlideUtils.ShowCircleImage(this, userPhoto.getAbsolutePath(), mUserPhoto, R.drawable.mine_head_default);
    }

    @Override
    public void uploadUserPhotoFail() {
        App.getInstance().showToast("图片上传失败");
    }

    @Override
    public void showUploadProgress() {
        showProgressDialog("上传中...");
    }

    @Override
    public void hideProgress() {
        hideProgressDialog();
    }

    @Override
    public void setPresenter(SettingContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private File userPhoto;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTION_START_CAMERA && resultCode == RESULT_OK) {
            Uri photoIn = Uri.fromFile(photoFile);
            beginCrop(photoIn);
        }
        if (requestCode == ACTION_START_PHOTO && resultCode == RESULT_OK) {
            beginCrop(data.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, data);
        }
    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(this.getCacheDir(), "user.jpg"));
        Crop.of(source, destination).asSquare().start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            Uri uri = Crop.getOutput(result);
            PhotoUtils.cropPhoto(this, new File(uri.getEncodedPath()), new PhotoUtils.PhotoListener() {
                @Override
                public void onSuccess(File file) {
                    if (mPresenter != null) {
                        userPhoto = file;
                        mPresenter.uploadUserPhoto(file);
                    }
                }
            });
        } else if (resultCode == Crop.RESULT_ERROR) {
            App.getInstance().showToast(Crop.getError(result).getMessage());
        }
    }
}
