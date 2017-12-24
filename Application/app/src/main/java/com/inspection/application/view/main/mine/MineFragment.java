package com.inspection.application.view.main.mine;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.inspection.application.R;
import com.inspection.application.app.App;
import com.inspection.application.mode.bean.version.NewVersion;
import com.inspection.application.utils.DownloadAppUtils;
import com.inspection.application.utils.PhotoUtils;
import com.inspection.application.view.MvpFragment;
import com.inspection.application.view.login.LoginActivity;
import com.inspection.application.view.main.MainActivity;
import com.inspection.application.view.setting.AboutActivity;
import com.inspection.application.view.setting.feedback.QuestionActivity;
import com.library.utils.GlideUtils;
import com.soundcloud.android.crop.Crop;

import java.io.File;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 个人中心
 * Created by pingan on 2017/12/8.
 */

public class MineFragment extends MvpFragment<MineContract.Presenter> implements View.OnClickListener, MineContract.View {

    private TextView tv_version_no, tv_user_name, tv_customer;
    private ImageView iv_user_photo;
    private File photoFile;
    private static final int ACTION_START_CAMERA = 100;
    private static final int ACTION_START_PHOTO = 101;

    public static MineFragment newInstance() {
        Bundle args = new Bundle();
        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mine, container, false);
        iv_user_photo = rootView.findViewById(R.id.iv_user_photo);
        tv_version_no = rootView.findViewById(R.id.tv_version_no);
        tv_user_name = rootView.findViewById(R.id.tv_user_name);
        tv_customer = rootView.findViewById(R.id.tv_customer);
        iv_user_photo.setOnClickListener(this);
        rootView.findViewById(R.id.tv_exit_app).setOnClickListener(this);
        rootView.findViewById(R.id.ll_1).setOnClickListener(this);
        rootView.findViewById(R.id.ll_2).setOnClickListener(this);
        rootView.findViewById(R.id.ll_3).setOnClickListener(this);
        rootView.findViewById(R.id.ll_4).setOnClickListener(this);
        if (App.getInstance().getCurrentUser() != null) {
            tv_user_name.setText(App.getInstance().getCurrentUser().getRealName());
            tv_customer.setText(App.getInstance().getCurrentUser().getUserRoleNames());
            GlideUtils.ShowCircleImage(getActivity(), App.getInstance().getCurrentUser().getPortraitUrl()
                    , iv_user_photo, R.drawable.icon_monitor);
        }
        return rootView;
    }

    private MaterialDialog exitDialog;

    @Override
    public void onClick(View view) {
        if (getActivity() == null) {
            return;
        }
        switch (view.getId()) {
            case R.id.tv_exit_app:
                if (getActivity() != null) {
                    View exitView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_home_exit, null);
                    exitView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (exitDialog != null) {
                                exitDialog.dismiss();
                            }
                        }
                    });
                    exitView.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (exitDialog != null) {
                                exitDialog.dismiss();
                                if (mPresenter != null) {
                                    mPresenter.exitApp();
                                }
                                startActivity(new Intent(getActivity(), LoginActivity.class));
                                getActivity().finish();
                            }
                        }
                    });
                    exitDialog = new MaterialDialog.Builder(getActivity())
                            .customView(exitView, false)
                            .build();
                    exitDialog.show();
                }
                break;
            case R.id.iv_user_photo:
                new MaterialDialog.Builder(getActivity())
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
                                    Uri uri = getActivity().getApplicationContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
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
            case R.id.ll_1:
                startActivity(new Intent(getActivity(), QuestionActivity.class));
                break;
            case R.id.ll_2:
                mPresenter.cleanCache();
                App.getInstance().showToast("清理完成!");
                break;
            case R.id.ll_3:
                mPresenter.getNewVersion();
                break;
            case R.id.ll_4:
                startActivity(new Intent(getActivity(), AboutActivity.class));
                break;

        }
    }

    public static final int REQUEST_EXTERNAL = 10;//内存卡权限

    @Override
    public void newVersionDialog(final @NonNull NewVersion version) {
        tv_version_no.setText(String.format("V%s", String.valueOf(version.getVersionString())));
        if (getActivity() == null) {
            return;
        }
        if (!EasyPermissions.hasPermissions(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)) {
            new AppSettingsDialog.Builder(getActivity())
                    .setTitle(getString(R.string.request_permissions))
                    .setRationale(getString(R.string.need_save_setting))
                    .setPositiveButton(getString(R.string.sure))
                    .setNegativeButton(getString(R.string.cancel))
                    .setRequestCode(REQUEST_EXTERNAL)
                    .build()
                    .show();
            return;
        }
        new MaterialDialog.Builder(getActivity())
                .content(version.getNote())
                .negativeText("取消")
                .positiveText("确定")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        DownloadAppUtils.DownLoad(getActivity(), version.getUrl(), "点检");
                    }
                })
                .show();
    }

    @Override
    public void currentVersion() {
        App.getInstance().showToast("当前为最新版本");
    }

    @Override
    public void downLoadApp() {

    }

    @Override
    public void uploadUserPhotoSuccess() {
        GlideUtils.ShowCircleImage(getActivity(), App.getInstance().getCurrentUser().getPortraitUrl()
                , iv_user_photo, R.drawable.mine_head_default);
    }

    @Override
    public void uploadUserPhotoFail() {
        App.getInstance().showToast("上传照片失败");
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
    public void setPresenter(MineContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTION_START_CAMERA && resultCode == Activity.RESULT_OK) {
            Uri photoIn = Uri.fromFile(photoFile);
            beginCrop(photoIn);
        }
        if (requestCode == ACTION_START_PHOTO && resultCode == Activity.RESULT_OK) {
            beginCrop(data.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, data);
        }
    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getActivity().getCacheDir(), "user.jpg"));
        Crop.of(source, destination).asSquare().start(getActivity(), this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = Crop.getOutput(result);
            PhotoUtils.cropPhoto(getActivity(), new File(uri.getEncodedPath()), new PhotoUtils.PhotoListener() {
                @Override
                public void onSuccess(File file) {
                    if (mPresenter != null) {
                        mPresenter.uploadUserPhoto(file);
                    }
                }
            });
        } else if (resultCode == Crop.RESULT_ERROR) {
            App.getInstance().showToast(Crop.getError(result).getMessage());
        }
    }
}
