package com.xgx.syzj.utils.photos;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.misc.Utils;
import com.xgx.syzj.R;

import java.io.File;

public final class SelectPhotoManager {

    private static final int REQUEST_CODE_CAMERA = 1;
    private static final int REQUEST_CODE_ALBUM = 2;
    private static final int REQUEST_CODE_CROP = 3;
    public static final int ACTION_TAKE_PHOTO = 0;
    public static final int ACTION_ALBUM = 1;
    public static final int ACTION_CANCEL = 2;
    private static SelectPhotoManager sInstance;

    private static String TEMP_PATH_NAME = "xgx-tmp-photo";

    public interface SelectClickHandler {
        /**
         * @param action can be one of {{@link #ACTION_TAKE_PHOTO} {@link #ACTION_ALBUM} {@link #ACTION_CANCEL}}
         */
        public void onClick(int action);
    }

    private PhotoReadyHandler mPhotoReadyHandler;
    private File mTempDir;
    private File mTempFile;
    private Activity mActivity;
    private CropOption mCropOption;

    private SelectPhotoManager() {
    }

    public static SelectPhotoManager getInstance() {
        if (sInstance == null) {
            sInstance = new SelectPhotoManager();
        }
        return sInstance;
    }

    public void setCropOption(CropOption option) {
        mCropOption = option;
    }

    public void setPhotoReadyHandler(PhotoReadyHandler handler) {
        mPhotoReadyHandler = handler;
    }

    public void start(Activity activity) {
        start(activity, null);
    }

    public void start(final Activity activity, final SelectClickHandler handler) {

        File path = Utils.getDiskCacheDir(activity, TEMP_PATH_NAME);
        if (!path.exists() && !path.mkdirs()) {
            Toast.makeText(activity, R.string.cube_photo_can_not_use_camera, Toast.LENGTH_SHORT).show();
            return;
        }

        mTempDir = path;
        mActivity = activity;
        mTempFile = new File(mTempDir.getAbsolutePath(), Long.toString(System.nanoTime()) + ".jpg");

        DialogInterface.OnClickListener clickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                final int action = i;
                if (handler != null) {
                    handler.onClick(action);
                }
                switch (action) {
                    case ACTION_TAKE_PHOTO:
                        PhotoUtils.toCamera(activity, mTempFile, REQUEST_CODE_CAMERA);
                        break;
                    case ACTION_ALBUM:
                        PhotoUtils.toAlbum(activity, REQUEST_CODE_ALBUM);
                        break;
                    case ACTION_CANCEL:
                        // do nothing
                        break;
                }
                dialog.dismiss();
            }
        };

        final Dialog dialog = new Dialog(activity, R.style.picDialog);
        dialog.setContentView(R.layout.layout_pic_choose);
        dialog.findViewById(R.id.btn_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                PhotoUtils.toCamera(activity, mTempFile, REQUEST_CODE_CAMERA);
            }
        });
        dialog.findViewById(R.id.btn_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                PhotoUtils.toAlbum(activity, REQUEST_CODE_ALBUM);
            }
        });
        dialog.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount = 0.6f;
        Window window = dialog.getWindow();
        window.setAttributes(lp);
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
        window.setWindowAnimations(R.style.picDialog); // 添加动画
        dialog.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        String imgPath = null;
        if (requestCode == REQUEST_CODE_CAMERA) {
            imgPath = mTempFile.getPath();
            if (!afterPhotoTaken(imgPath)) {
                sendMessage(PhotoReadyHandler.FROM_CAMERA, imgPath);
            }
        } else if (requestCode == REQUEST_CODE_ALBUM && data != null) {
            Uri imgUri = data.getData();
            imgPath = PhotoUtils.uriToPath(mActivity, imgUri);
            if (!afterPhotoTaken(imgPath)) {
                sendMessage(PhotoReadyHandler.FROM_ALBUM, imgPath);
            }
        } else if (requestCode == REQUEST_CODE_CROP) {
            imgPath = mTempFile.getPath();
            sendMessage(PhotoReadyHandler.FROM_CROP, imgPath);
        }
    }

    private void sendMessage(int from, String imgPath) {

        if (mPhotoReadyHandler != null) {
            mPhotoReadyHandler.onPhotoReady(from, imgPath);
        }

    }

    private boolean afterPhotoTaken(String imgPath) {
        if (TextUtils.isEmpty(imgPath)) {
            throw new RuntimeException();
        }
        if (mCropOption == null) {
            return false;
        }
        File f = new File(imgPath);
        mTempFile = new File(mTempDir.getAbsolutePath(), Long.toString(System.nanoTime()) + "_cropped.jpg");
        PhotoUtils.toCrop(mActivity, f, mTempFile, mCropOption, REQUEST_CODE_CROP);
        return true;
    }
}
