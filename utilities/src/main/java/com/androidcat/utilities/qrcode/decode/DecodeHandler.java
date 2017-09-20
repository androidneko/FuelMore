package com.androidcat.utilities.qrcode.decode;


import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import com.androidcat.utilities.LogUtil;
import com.androidcat.utilities.qrcode.bitmap.PlanarYUVLuminanceSource;
import com.androidcat.utilities.qrcode.ui.CaptureActivity;
import com.androidcat.utilities.qrcode.ui.Ids;

import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;

import java.io.File;
import java.io.FileOutputStream;

/**
 * 解析Handler
 * 
 * @author Hitoha
 * @version 1.00 2015/04/29 新建
 */
final class DecodeHandler extends Handler {

	CaptureActivity activity = null;

	DecodeHandler(CaptureActivity activity) {
		this.activity = activity;
	}

	@Override
	public void handleMessage(Message message) {
		switch (message.what) {
		case Ids.decode:
			decode((byte[]) message.obj, message.arg1, message.arg2);
			break;
		case Ids.quit:
			Looper.myLooper().quit();
			break;
		}
	}

	/**
	 * 二维码解析
	 * 
	 * @param data
	 *            图片数据
	 * @param cameraResolutionX
	 *            原始宽度
	 * @param cameraResolutionY
	 *            原始高度
	 */
	private void decode(byte[] data, int cameraResolutionX, int cameraResolutionY) {
		// 这里需要将获取的data翻转一下，因为相机默认拿的的横屏的数据
		byte[] rotatedData = new byte[data.length];
		for (int y = 0; y < cameraResolutionY; y++) {
			for (int x = 0; x < cameraResolutionX; x++)
				rotatedData[x * cameraResolutionY + cameraResolutionY - y - 1] = data[x + y * cameraResolutionX];
		}

		// Here we are swapping, that's the difference to #11
		int tmp = cameraResolutionX;
		cameraResolutionX = cameraResolutionY;
		cameraResolutionY = tmp;

		// ZBar管理器
		//ZbarManager manager = new ZbarManager();
		// 进行解码
		/*String result = manager.decode(rotatedData, width, height, true,
				activity.getX(), activity.getY(), activity.getCropWidth(),
				activity.getCropHeight());*/

		//////////////////////////////////////////////////

		Image barcode = new Image(cameraResolutionX, cameraResolutionY, "Y800");
		barcode.setData(rotatedData);
		barcode.setCrop(activity.getX(), activity.getY(), activity.getCropWidth() + activity.getX(),
				activity.getCropHeight() + activity.getY());

		int retCode = activity.getmImageScanner().scanImage(barcode);
		String resultStr = null;

		if (retCode != 0) {
			SymbolSet syms = activity.getmImageScanner().getResults();
			for (Symbol sym : syms) {
				resultStr = sym.getData();
			}
		}
		LogUtil.e("retCode","resultStr:"+resultStr);
		/////////////////////////////////////////////////

		if (!TextUtils.isEmpty(resultStr)) {
			// 需要保存扫描的二维码图片
			if (activity.isNeedCapture()) {
				// 生成bitmap
				PlanarYUVLuminanceSource source = new PlanarYUVLuminanceSource(
						rotatedData, cameraResolutionX, cameraResolutionY, activity.getX(),
						activity.getY(), activity.getCropWidth(),
						activity.getCropHeight(), false);
				int[] pixels = source.renderThumbnail();
				int w = source.getThumbnailWidth();
				int h = source.getThumbnailHeight();
				Bitmap bitmap = Bitmap.createBitmap(pixels, 0, w, w, h,
						Bitmap.Config.ARGB_8888);
				try {
					// 保存二维码图片
					String rootPath = Environment.getExternalStorageDirectory()
							.getAbsolutePath() + "/Qrcode/";
					File root = new File(rootPath);
					if (!root.exists()) {
						root.mkdirs();
					}
					File f = new File(rootPath + "Qrcode.jpg");
					if (f.exists()) {
						f.delete();
					}
					f.createNewFile();

					FileOutputStream out = new FileOutputStream(f);
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
					out.flush();
					out.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			// 向Activity发一条消息
			if (null != activity.getHandler()) {
				Message msg = new Message();
				msg.obj = resultStr;
				msg.what = Ids.decode_succeeded;
				activity.getHandler().sendMessage(msg);
			}
		} else {
			if (null != activity.getHandler()) {
				activity.getHandler().sendEmptyMessage(Ids.decode_failed);
			}
		}
	}

}
