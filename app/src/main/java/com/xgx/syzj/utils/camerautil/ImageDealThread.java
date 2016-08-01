//package com.xgx.syzj.utils.camerautil;
//
//import android.os.Handler;
//import android.os.Looper;
//import android.os.Message;
//
///**
// * 处理图片线程
// * @author Administrator
// *
// */
//public class ImageDealThread extends Thread{
//
//	public static final int THEARD_START = 1;//线程开始执行
//	public static final int THEARD_END = 2;//线程执行完毕
//	private String imagePath = "";
//	private Handler handle;
//	private int compress = 100;//压缩比例
//
//	/**
//	 * 获取图片处理线程
//	 * @param handler
//	 * @param compress 100为不压缩，70 为压缩百分之30
//	 * @return
//	 */
//	public static ImageDealThread getImageDealThread(Handler handler,int compress){
//		ImageDealThread mImageDealTheard = new ImageDealThread();
//		mImageDealTheard.compress = compress;
//    	mImageDealTheard.setPriority(Thread.NORM_PRIORITY - 1);
//    	mImageDealTheard.setHandle(handler);
//    	return mImageDealTheard;
//	}
//
//	@Override
//	public void run() {
//		super.run();
//
//		Looper.prepare();
//		Message msg1 = new Message();
//		msg1.what = THEARD_START;
//		getHandle().sendMessage(msg1);
//
//		if(compress != 100){
//			CameraUtil.compress(imagePath, compress);
//		}
//
//		Message msg2 = new Message();
//		msg2.obj = imagePath;
//		msg2.what = THEARD_END;
//		getHandle().sendMessage(msg2);
//
//		Looper.loop();
//	}
//
//
//
//	public Handler getHandle() {
//		return handle;
//	}
//	public void setHandle(Handler handle) {
//		this.handle = handle;
//	}
//	public String getImagePath() {
//		return imagePath;
//	}
//
//	public void setImagePath(String imagePath) {
//		this.imagePath = imagePath;
//	}
//
//}
