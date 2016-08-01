//package com.xgx.syzj.utils.camerautil;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.database.Cursor;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.provider.MediaStore;
//import android.provider.MediaStore.MediaColumns;
//import android.support.v4.app.Fragment;
//import android.widget.Toast;
//
//import com.xgx.syzj.app.AppConfig;
//import com.xgx.syzj.utils.DebugLog;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.IOException;
//import java.io.RandomAccessFile;
//import java.util.UUID;
//
//
//public class CameraUtil {
//    private static final String TAG = "CameraUtil";
//    public static final int CAMERA = 10001;
//    public static final int PHONTO = 10002;
//    public static final int CUT_PHOTO = 10003;
//    public static final int CUT_PIC = 10004;
//    private Activity activity;
//    private Fragment fragment;
//    private boolean isCut = false;
//    private boolean isFreeCut = false;
//    private int outputWidth = 0;
//    private int outputHeight = 0;
//
//    public CameraUtil(Activity actvity) {
//        this.activity = actvity;
//    }
//
//    public CameraUtil(Fragment frgment) {
//        this.fragment = frgment;
//    }
//
//
//    public boolean isCut() {
//        return isCut;
//    }
//
//    /**
//     * 设置是否剪辑和是否自由剪辑
//     *
//     * @param isCut     是否剪辑
//     * @param isFreeCut 是否自由剪辑(不限定宽高)
//     */
//    public void setCut(boolean isCut, boolean isFreeCut) {
//        this.isCut = isCut;
//        this.isFreeCut = isFreeCut;
//    }
//
//    /**
//     * 设置剪切图片的大小
//     *
//     * @param targetWidth
//     */
//    public void setTargetWidth(int targetWidth) {
//        this.outputWidth = targetWidth;
//        this.outputHeight = targetWidth;
//    }
//
//    /**
//     * 打开摄像头
//     */
//    public void openCamera() {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // 下面这句指定调用相机拍照后的照片存储的路径
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, getUri());
//        startActivityForResult(intent, CAMERA);
//
//    }
//
//    private void startActivityForResult(Intent intent, int requestCode) {
//        if (activity != null) {
//            activity.startActivityForResult(intent, requestCode);
//        } else {
//            fragment.startActivityForResult(intent, requestCode);
//        }
//    }
//
//    /**
//     * 打开相册
//     */
//    public void openAlbum() {
//        if (isCut) {
//            getCutPic();
//        } else {
//            getPic();
//        }
//    }
//
//    String imagePath;
//
//    private Uri getUri() {
//
//        //兼容性控制
////        File file ;
////        if (Build.VERSION.SDK_INT > 8) {
////            file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
////        } else {
////            file = new File(Environment.getExternalStorageDirectory(), AppConfig.IMAGE_PATH);
////        }
//        File file = new File(AppConfig.IMAGE_PATH);
//        if (!file.exists()) { // 创建目录
//            file.mkdirs();
//        }
//
//
//        String name = UUID.randomUUID() + ".png";
//        File file1 = new File(file, name);
//        imagePath = file1.getAbsolutePath();
//        Uri uri = Uri.fromFile(file1);
//        return uri;
//    }
//
//    private void getCutPic() {
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.putExtra("crop", "true"); // 说明要裁剪
//        if (!isFreeCut) {
//            intent.putExtra("aspectX", 1);
//            intent.putExtra("aspectY", 1);
//
//            if (outputWidth != 0) {
//                intent.putExtra("outputX", outputWidth);
//                intent.putExtra("outputY", outputHeight);
//            }
//        }
//
//
//        intent.setType("image/*"); // 说明你想获得图片
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, getUri());
//        startActivityForResult(intent, CUT_PHOTO);
//    }
//
//    private void getPic() {
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.putExtra("return-data", true);
//        intent.setType("image/*"); // 说明你想获得图片
//        startActivityForResult(intent, PHONTO);
//    }
//
//    public void startPhotoZoom(Uri uri) {
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setDataAndType(uri, "image/*");
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, getUri());
//        intent.putExtra("crop", "true"); // 说明要裁剪
//        if (!isFreeCut) {
//            intent.putExtra("aspectX", 1); // 宽度
//            intent.putExtra("aspectY", 1); // 高度
//            if (outputWidth != 0) {
//                intent.putExtra("outputX", outputWidth);
//                intent.putExtra("outputY", outputHeight);
//            }
//        }
////        intent.putExtra("return-data", false);
//        startActivityForResult(intent, CUT_PIC);
//    }
//
//    /*
//     * 剪切图片
//     */
//    public void crop(Activity activity, Uri uri) {
//        // 裁剪图片意图
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setDataAndType(uri, "image/*");
//        intent.putExtra("crop", "true");
//        // 裁剪框的比例，1：1
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
//        // 裁剪后输出图片的尺寸大小
////		intent.putExtra("outputX", 250);
////		intent.putExtra("outputY", 250);
//
//        intent.putExtra("outputFormat", "PNG");// 图片格式
//        intent.putExtra("noFaceDetection", true);// 取消人脸识别
//        intent.putExtra("return-data", true);
//        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
//        if (intent.resolveActivity(activity.getPackageManager()) != null) {
//            activity.startActivityForResult(intent, CUT_PHOTO);
//        } else {
//            Toast.makeText(activity, "无法剪切图片", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    /**
//     * 做剪切处理
//     *
//     * @param requestCode
//     * @param resultCode
//     * @param data
//     * @return
//     */
//    public void getImgPath(int requestCode, int resultCode, Intent data, ImageDealThread theard) {
////		DLog.d(TAG, "requestCode:"+requestCode);
////		DLog.d(TAG, "resultCode:"+resultCode);
//        if (resultCode != Activity.RESULT_OK) {
//            return;
//        }
//        if (requestCode == CAMERA && isCut) {
//            File file = new File(imagePath);
//            startPhotoZoom(Uri.fromFile(file));
//        } else if (requestCode == CAMERA) {
//            theard.setImagePath(imagePath);
//            theard.start();
//        }
//
//        if (requestCode == CUT_PHOTO) {
//            theard.setImagePath(imagePath);
//            theard.start();
//
//        }
//        if (requestCode == PHONTO && data != null) {
//            Uri uri = data.getData();
//            if (uri == null)
//                return;
//            String[] proj = {MediaColumns.DATA};
//            Cursor cursor = null;
//            if (activity != null) {
//                cursor = activity.managedQuery(uri, proj, null, null, null);
//            } else {
//                cursor = fragment.getActivity().managedQuery(uri, proj, null, null, null);
//            }
//            int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
//            cursor.moveToFirst();
//            String imagePath = cursor.getString(column_index);
//            theard.setImagePath(imagePath);
//            theard.start();
//        }
//        if (requestCode == CUT_PIC) {
//            theard.setImagePath(imagePath);
//            theard.start();
//        }
//    }
//
//    /**
//     * 压缩图片
//     *
//     * @param imagePath  图片地址
//     * @param persentage 压缩系数 example：30 是压缩率，表示压缩70%; 如果不压缩是100，表示压缩率为0
//     */
//    public static String compress(String imagePath, int persentage) {
//        File file = new File(imagePath);
//        try {
//            // decode image size
//            DebugLog.e("file path:" + file.getAbsolutePath());
//            BitmapFactory.Options o = new BitmapFactory.Options();
//            o.inJustDecodeBounds = true;
////			Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file), null, o);
//            Bitmap bitmap = BitmapFactory.decodeFile(imagePath, o);
//            o.inJustDecodeBounds = false;
//
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.PNG, persentage, baos);
//            //循环判断如果压缩后图片是否大于100kb,大于继续压缩
//            int options = 100;
//            while (baos.toByteArray().length / 1024 > 100) {
//                //重置baos即清空baos
//                baos.reset();
//                //这里压缩options%，把压缩后的数据存放到baos中
//                bitmap.compress(Bitmap.CompressFormat.PNG, options, baos);
//                //每次都减少10
//                options -= 10;
//            }
//            ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
//            bitmap = BitmapFactory.decodeStream(isBm, null, null);
//            return storeImageToFile(bitmap);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    /**
//     * 将bitmap装换成图片
//     *
//     * @param bitmap
//     * @return
//     */
//    private static String storeImageToFile(Bitmap bitmap) {
//        if (bitmap == null) {
//            return null;
//        }
//        File file1 = new File(AppConfig.IMAGE_PATH);
//
//        if (!file1.exists()) { // 创建目录
//            file1.mkdirs();
//        }
//
//        String name = UUID.randomUUID() + ".png";
//        File file = new File(file1, name);
//
//        RandomAccessFile accessFile = null;
//
//        ByteArrayOutputStream steam = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, steam);
//        byte[] buffer = steam.toByteArray();
//
//        try {
//            accessFile = new RandomAccessFile(file, "rw");
//            accessFile.write(buffer);
//        } catch (Exception e) {
//            return null;
//        }
//
//        try {
//            steam.close();
//            accessFile.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return file.getAbsolutePath();
//    }
//
//    /**
//     * 半角转换为全角
//     *
//     * @param input
//     * @return
//     */
//    public static String ToDBC(String input) {
//        char[] c = input.toCharArray();
//        for (int i = 0; i < c.length; i++) {
//            if (c[i] == 12288) {
//                c[i] = (char) 32;
//                continue;
//            }
//            if (c[i] > 65280 && c[i] < 65375)
//                c[i] = (char) (c[i] - 65248);
//        }
//        return new String(c);
//    }
//}
