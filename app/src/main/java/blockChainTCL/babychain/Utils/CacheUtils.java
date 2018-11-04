package blockChainTCL.babychain.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CacheUtils {

    private Context context;

    public CacheUtils(Context co){
        context = co;
    }

    private File getCacheDir(Context context) {
//        File cacheDir = null;
//        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//            cacheDir = new File(Environment.getExternalStorageDirectory(), "cachefolder");
//            if(!cacheDir.isDirectory()) {
//                cacheDir.mkdirs();
//            }
//        }
//        if(!cacheDir.isDirectory()) {
//            cacheDir = context.getCacheDir();
//        }
//        return cacheDir;
        return context.getCacheDir();
    }

    public void writeCacheString(String filename, String obj) throws IOException {
        File cacheDir = getCacheDir(context);
        File cacheFile = new File(cacheDir, filename);
        if(!cacheFile.exists()) {
            cacheFile.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(cacheFile);
        fileWriter.write(obj);
        fileWriter.flush();
        fileWriter.close();
    }

    public String readCacheString(String filename) throws IOException {
        File cacheDir = getCacheDir(context);
        File cacheFile = new File(cacheDir, filename);
        if(!cacheFile.exists()) {
            cacheFile.createNewFile();
        }
        FileInputStream inputStream = new FileInputStream(cacheFile);
        Scanner s = new Scanner(inputStream);
        StringBuilder sb= new StringBuilder();
        while(s.hasNext()){
            sb.append(s.nextLine());
        }
        inputStream.close();
        return sb.toString();
    }

    public void writeCacheImage(String filename, Bitmap image) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        File cacheDir = getCacheDir(context);
        File cacheFile = new File(cacheDir, filename);
        if(!cacheFile.exists()) {
            cacheFile.createNewFile();
        }

        FileOutputStream fo = new FileOutputStream(cacheFile);
        fo.write(bytes.toByteArray());
        MediaScannerConnection.scanFile(context,
                new String[]{cacheFile.getPath()},
                new String[]{"image/jpeg"}, null);
        fo.close();
    }

    public String getPath(String fileName) {
        return context.getCacheDir().getPath() + "/" + fileName;
    }
}
