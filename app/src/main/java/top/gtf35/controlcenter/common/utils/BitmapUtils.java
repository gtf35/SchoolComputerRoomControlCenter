package top.gtf35.controlcenter.common.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class BitmapUtils {
    /**通过图片url生成Bitmap对象
     * @param urlpath
     * @return Bitmap
     * 根据图片url获取图片对象
     */
    public static Bitmap getBitMBitmap(String urlpath) throws Exception {
        Bitmap map = null;
        URL url = new URL(urlpath);
        URLConnection conn = url.openConnection();
        conn.connect();
        InputStream in;
        in = conn.getInputStream();
        map = BitmapFactory.decodeStream(in);
        return map;
    }
}
