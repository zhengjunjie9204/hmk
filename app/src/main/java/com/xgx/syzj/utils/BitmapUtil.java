package com.xgx.syzj.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.DisplayMetrics;

import java.io.ByteArrayOutputStream;

/**
 * bitmap帮助类
 */
public class BitmapUtil {

	private final static int DES_WIDTH = 50;
	/**
	 * @param context 上下文
	 * @param rid     缩放的本地资源id
	 * @return
	 */
	public static Bitmap scaleBitmap(Context context,int rid){
		Bitmap bitmap;
		BitmapFactory.Options options = new BitmapFactory.Options();
		//这里为ture，则下面bitmap为空（节省内存），并把bitmap对应资源的宽和高分别保存在options.outWidth和options.outHeight中
		options.inJustDecodeBounds = true;
		options.inPurgeable = true;
		bitmap = BitmapFactory.decodeResource(context.getResources(), rid, options);
		int outWidth = options.outWidth;
		int outHeight = options.outHeight;
		if(outWidth >= DES_WIDTH){
			options.inSampleSize = outWidth/DES_WIDTH;//注意：这里的缩放等级必须是2的倍数（不够会就近取2的倍数值）
		}
		//注意：设置好缩放后，前面的true需要设置为false，不然bitmap一直为空
		options.inJustDecodeBounds = false;
		bitmap = BitmapFactory.decodeResource(context.getResources(), rid, options);
		bitmap.recycle();
		return bitmap;
	}

	/**
	 * @param context 上下文
	 * @param rid     缩放的本地资源id
	 * @return
	 */
	public static Bitmap scaleBitmap(Context context,int rid, int dip){
		Bitmap bitmap;
		BitmapFactory.Options options = new BitmapFactory.Options();
		//这里为ture，则下面bitmap为空（节省内存），并把bitmap对应资源的宽和高分别保存在options.outWidth和options.outHeight中
		options.inJustDecodeBounds = true;
		bitmap = BitmapFactory.decodeResource(context.getResources(), rid, options);
		int outWidth = options.outWidth;
		int outHeight = options.outHeight;
        //获取屏幕的宽高
		DisplayMetrics dm = new DisplayMetrics();
		((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;    //得到宽度
		int height = dm.heightPixels;  //得到高度

		if(dip>0&&outWidth >= dip){
			options.inSampleSize = outWidth/dip;//注意：这里的缩放等级必须是2的倍数（不够会就近取2的倍数值）
		}else {
			options.inSampleSize = outWidth/width;//注意：这里的缩放等级必须是2的倍数（不够会就近取2的倍数值）
		}
		//注意：设置好缩放后，前面的true需要设置为false，不然bitmap一直为空
		options.inJustDecodeBounds = false;
		bitmap = BitmapFactory.decodeResource(context.getResources(), rid, options);

		return bitmap;
	}
	/**
	 * @param context   上下文
	 * @param imageUri  图片路径
	 * @return
	 */
	public static Bitmap scaleBitmap(Context context,String imageUri){
		Bitmap bitmap;
		BitmapFactory.Options options = new BitmapFactory.Options();
		//这里为ture，则下面bitmap为空（节省内存），并把bitmap对应资源的宽和高分别保存在options.outWidth和options.outHeight中
		options.inJustDecodeBounds = true;
		bitmap = BitmapFactory.decodeFile(imageUri, options);
		int outWidth = options.outWidth;
		int outHeight = options.outHeight;
		if(outWidth >= DES_WIDTH){
			options.inSampleSize = outWidth/DES_WIDTH;//注意：这里的缩放等级必须是2的倍数（不够会就近取2的倍数值）
		}
		//注意：设置好缩放后，前面的true需要设置为false，不然bitmap一直为空
		options.inJustDecodeBounds = false;
		bitmap = BitmapFactory.decodeFile(imageUri, options);

		return bitmap;
	}
	/**
	 * @param context   上下文
	 * @param imageUri  图片路径
	 * @return
	 */
	public static Bitmap scaleBitmapDefault(Context context,String imageUri){
		Bitmap bitmap;
		BitmapFactory.Options options = new BitmapFactory.Options();
		//这里为ture，则下面bitmap为空（节省内存），并把bitmap对应资源的宽和高分别保存在options.outWidth和options.outHeight中
//		options.inJustDecodeBounds = true;
//		bitmap = BitmapFactory.decodeFile(imageUri, options);
//		int outWidth = options.outWidth;
//		int outHeight = options.outHeight;
//		if(outWidth >= DES_WIDTH){
//			options.inSampleSize = outWidth/DES_WIDTH;//注意：这里的缩放等级必须是2的倍数（不够会就近取2的倍数值）
//		}
		//注意：设置好缩放后，前面的true需要设置为false，不然bitmap一直为空
//		options.inJustDecodeBounds = false;
		bitmap = BitmapFactory.decodeFile(imageUri, options);

		return bitmap;
	}


	/**
	 * @param context   上下文
	 * @param imageUri  图片路径
	 * @return
	 */
	public static Bitmap scaleBitmap(Context context,String imageUri,int dip){
		Bitmap bitmap;
		BitmapFactory.Options options = new BitmapFactory.Options();
		//这里为ture，则下面bitmap为空（节省内存），并把bitmap对应资源的宽和高分别保存在options.outWidth和options.outHeight中
		options.inJustDecodeBounds = true;
		bitmap = BitmapFactory.decodeFile(imageUri, options);
		int outWidth = options.outWidth;
		int outHeight = options.outHeight;

		//获取屏幕的宽高
		DisplayMetrics dm = new DisplayMetrics();
		((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;    //得到宽度
		int height = dm.heightPixels;  //得到高度

		if(dip>0&&outWidth >= dip){
			options.inSampleSize = outWidth/dip;//注意：这里的缩放等级必须是2的倍数（不够会就近取2的倍数值）
		}else {
			options.inSampleSize = outWidth/width;//注意：这里的缩放等级必须是2的倍数（不够会就近取2的倍数值）
		}
		//注意：设置好缩放后，前面的true需要设置为false，不然bitmap一直为空
		options.inJustDecodeBounds = false;
		bitmap = BitmapFactory.decodeFile(imageUri, options);

		return bitmap;
	}


	/**
	 * 
	 * @param imageFilePath
	 *            图片文件的路径
	 * @param imageUpperLimitPix
	 *            返回的bitmap宽或高的最高像素
	 * @return
	 */
	public static Bitmap decodeFile(String imageFilePath, int imageUpperLimitPix) {
		try {
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(imageFilePath, o);
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (true) {
				if (width_tmp / 2 < imageUpperLimitPix && height_tmp / 2 < imageUpperLimitPix)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			return BitmapFactory.decodeFile(imageFilePath, o2);
		} catch (Exception e) {
		}
		return null;
	}
	
	/**
	 * 获取bitmap宽高像素值
	 * @param path
	 * @return
	 */
	public static int[] getBitmapPxSize(String path){
		BitmapFactory.Options opts = new BitmapFactory.Options();  
        opts.inJustDecodeBounds = true;  
        BitmapFactory.decodeFile(path,opts);  
        opts.inSampleSize = 1;    
        opts.inJustDecodeBounds = false;  
        Bitmap mBitmap =BitmapFactory.decodeFile(path, opts);  
        int width=opts.outWidth;  
        int height=opts.outHeight; 
        return new int[]{width,height};
	}
	/**
	 * @param path
	 * @param targetWidth
	 * @return
	 */
	public static int[] getFullScreenBitmapSize(String path,int targetWidth){
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;  
	    Bitmap bitmap = BitmapFactory.decodeFile(path, options);
	    int tempheight = options.outHeight;
	    int tempWidth = options.outWidth;
	    
//		DLog.d("-------", "__width:" + tempWidth);
//		DLog.d("-------", "__height:" + tempheight);
		double d = tempWidth / (targetWidth * 1.0);
//		if(width < Configure.screenWidth){
		tempWidth = targetWidth;
//		}
		tempheight = (int) (tempheight / d);
//		DLog.d("-------", "d:" + d);
//		DLog.d("-------", "width:" + tempWidth);
//		DLog.d("-------", "height:" + tempheight);
		return new int[]{tempWidth,tempheight};
	}

	/**
	 * Drawable转bitmap
	 * 
	 * @param d
	 * @return
	 */
	public static Bitmap drawable2bitmap(Drawable d) {

		return d != null ? ((BitmapDrawable) d).getBitmap() : null;
	}

	public static Bitmap layerdrawable2bitmap(Drawable drawable) {
		Bitmap bitmap_util;
		int w = drawable.getIntrinsicWidth();
		int h = drawable.getIntrinsicHeight();
		Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888 : Config.RGB_565;
		bitmap_util = Bitmap.createBitmap(w, h, config);
		// 注意，下面三行代码要用到，否在在View或者surfaceview里的canvas.drawBitmap会看不到图
		Canvas canvas = new Canvas(bitmap_util);
		drawable.setBounds(0, 0, w, h);
		drawable.draw(canvas);
		return bitmap_util;
	}

	/**
	 * bitmap转drawable
	 * 
	 * @param b
	 * @return
	 */
	public static Drawable bitmap2drawable(Bitmap b) {
		return b != null ? (Drawable) new BitmapDrawable(b) : null;
	}

	/**
	 * 创建带倒影的Bitmap
	 * 
	 * @param originalImage
	 *            原bitmap
	 * @param instanceColor
	 *            原图与倒影之间的颜色值，传null时默认为白色
	 * @return bitmap
	 * 
	 * @author leeib
	 */
	public static Bitmap createReflectedBitmap(Bitmap originalImage, Integer instanceColor) {
		final int reflectionGap = 4;

		int width = originalImage.getWidth();
		int height = originalImage.getHeight();

		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);

		Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0, height / 2, width, height / 2, matrix, false);

		Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height + height / 5), Config.ARGB_8888);

		Canvas canvas = new Canvas(bitmapWithReflection);

		canvas.drawBitmap(originalImage, 0, 0, null);

		Paint deafaultPaint = new Paint();
		if (instanceColor != null) {
			deafaultPaint.setColor(instanceColor);
		} else {
			// 绘制的正方形为白色
			deafaultPaint.setColor(Color.WHITE);
		}

		canvas.drawRect(0, height, width, height + reflectionGap, deafaultPaint);

		canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0, originalImage.getHeight(), 0, bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff, 0x00ffffff,
				TileMode.CLAMP);

		paint.setShader(shader);

		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));

		canvas.drawRect(0, height, width, bitmapWithReflection.getHeight() + reflectionGap, paint);
		return bitmapWithReflection;
	}

	/**
	 * 创建带圆角的Bitmap
	 * 
	 * @param bitmap
	 *            要转换的Bitmap
	 * @param pixels
	 *            圆角的大小
	 * @return
	 */
	public static Bitmap createRoundCornerBitmap(Bitmap bitmap, int pixels) {

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);

		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;

		final Paint paint = new Paint();

		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

		final RectF rectF = new RectF(rect);

		final float roundPx = pixels;

		paint.setAntiAlias(true);

		canvas.drawARGB(0, 0, 0, 0);

		paint.setColor(color);

		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));

		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;

	}

	/**
	 * 图片与边框组合
	 * 
	 * @param bm
	 *            原图片
	 * @param res
	 *            边框资源，size为8，分别对应左上、左、左下、下、右下、右、右上、上，其中四角传0表示忽略对应的边框绘制
	 * @return
	 */
	public static Bitmap createFrameBitmap(Bitmap bm, int[] res, Context context) {
		Bitmap bmp = decodeBitmap(res[1], context);
		Bitmap bmp1 = decodeBitmap(res[3], context);
		// 边框的宽高
		final int smallW = bmp.getWidth();
		final int smallH = bmp1.getHeight();

		// 原图片的宽高
		final int bigW = bm.getWidth();
		final int bigH = bm.getHeight();

		int wCount = (int) Math.ceil(bigW * 1.0 / smallW);
		int hCount = (int) Math.ceil(bigH * 1.0 / smallH);

		// 组合后图片的宽高
		// int newW = (wCount + 2) * smallW;
		// int newH = (hCount + 2) * smallH;
		int newW = bigW + 2;
		int newH = bigH + 2;

		// 重新定义大小
		Bitmap newBitmap = Bitmap.createBitmap(newW, newH, Config.ARGB_8888);
		Canvas canvas = new Canvas(newBitmap);
		Paint p = new Paint();
		p.setColor(Color.TRANSPARENT);
		canvas.drawRect(new Rect(0, 0, newW, newH), p);

		Rect rect = new Rect(smallW, smallH, newW - smallW, newH - smallH);
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		canvas.drawRect(rect, paint);

		// 绘原图
		canvas.drawBitmap(bm, (newW - bigW - 2 * smallW) / 2 + smallW, (newH - bigH - 2 * smallH) / 2 + smallH, null);
		// 绘边框
		// 绘四个角
		int startW = newW - smallW;
		int startH = newH - smallH;
		if (res[0] != 0) {
			Bitmap leftTopBm = decodeBitmap(res[0], context); // 左上角
			canvas.drawBitmap(leftTopBm, 0, 0, null);

			leftTopBm.recycle();
			leftTopBm = null;

		}

		if (res[2] != 0) {
			Bitmap leftBottomBm = decodeBitmap(res[2], context); // 左下角
			canvas.drawBitmap(leftBottomBm, 0, startH, null);
			leftBottomBm.recycle();
			leftBottomBm = null;
		}

		if (res[4] != 0) {
			Bitmap rightBottomBm = decodeBitmap(res[4], context); // 右下角
			canvas.drawBitmap(rightBottomBm, startW, startH, null);
			rightBottomBm.recycle();
			rightBottomBm = null;
		}

		if (res[6] != 0) {
			Bitmap rightTopBm = decodeBitmap(res[6], context); // 右上角
			canvas.drawBitmap(rightTopBm, startW, 0, null);
			rightTopBm.recycle();
			rightTopBm = null;
		}

		// 绘左右边框
		Bitmap leftBm = decodeBitmap(res[1], context);
		Bitmap rightBm = decodeBitmap(res[5], context);
		for (int i = 0, length = hCount; i < length; i++) {
			int h = smallH * (i + 1);
			canvas.drawBitmap(leftBm, 0, h, null);
			canvas.drawBitmap(rightBm, startW, h, null);
		}

		leftBm.recycle();
		leftBm = null;
		rightBm.recycle();
		rightBm = null;

		// 绘上下边框
		Bitmap bottomBm = decodeBitmap(res[3], context);
		Bitmap topBm = decodeBitmap(res[7], context);
		for (int i = 0, length = wCount; i < length; i++) {
			int w = smallW * (i + 1);
			canvas.drawBitmap(bottomBm, w, startH, null);
			canvas.drawBitmap(topBm, w, 0, null);
		}

		bottomBm.recycle();
		bottomBm = null;
		topBm.recycle();
		topBm = null;

		canvas.save(Canvas.ALL_SAVE_FLAG);
		canvas.restore();

		return newBitmap;
	}

	public static Bitmap decodeBitmap(int res, Context context) {
		return BitmapFactory.decodeResource(context.getResources(), res);
	}

	/**
	 * 截取图片的中间的200X200的区域
	 * 
	 * @param bm
	 * @return
	 */
	public static Bitmap cropCenter(Bitmap bm, int width, int height) {
		if (bm == null) {
			return bm;
		}
		int startWidth = (bm.getWidth() - width) / 2;
		int startHeight = ((bm.getHeight() - height) / 2);
		Rect src = new Rect(startWidth, startHeight, startWidth + width, startHeight + height);
		return cropBitmap(bm, src);
	}

	/**
	 * 剪切图片
	 * 
	 * @param bmp
	 *            被剪切的图片
	 * @param src
	 *            剪切的位置
	 * @return 剪切后的图片
	 */
	public static Bitmap cropBitmap(Bitmap bmp, Rect src) {
		int width = src.width();
		int height = src.height();
		Rect des = new Rect(0, 0, width, height);
		Bitmap croppedImage = Bitmap.createBitmap(width, height, Config.RGB_565);
		Canvas canvas = new Canvas(croppedImage);
		canvas.drawBitmap(bmp, src, des, null);
		return croppedImage;
	}

	/**
	 * 调整Bitmap的大小,对变形没有保证
	 * 
	 * @param bm
	 * @param newHeight
	 * @param newWidth
	 * @param context
	 * @return
	 */
	public static Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth, Context context) {

		int width = bm.getWidth();
		int height = bm.getHeight();

		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;

		// create a matrix for the manipulation
		Matrix matrix = new Matrix();

		// resize the bit map
		matrix.postScale(scaleWidth, scaleHeight);

		// recreate the new Bitmap
		Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);

		return resizedBitmap;
	}

	/**
	 * 图片转成string
	 *
	 * @param bitmap
	 * @return
	 */
	public static String convertIconToString(Bitmap bitmap)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		byte[] appicon = baos.toByteArray();// 转为byte数组
		return Base64.encodeToString(appicon, Base64.DEFAULT);

	}

	/**
	 * string转成bitmap
	 *
	 * @param st
	 */
	public static Bitmap convertStringToIcon(String st)
	{
		// OutputStream out;
		Bitmap bitmap = null;
		try
		{
			// out = new FileOutputStream("/sdcard/aa.jpg");
			byte[] bitmapArray;
			bitmapArray = Base64.decode(st, Base64.DEFAULT);
			bitmap =
					BitmapFactory.decodeByteArray(bitmapArray, 0,
							bitmapArray.length);
			// bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
			return bitmap;
		}
		catch (Exception e)
		{
			return null;
		}
	}
}
