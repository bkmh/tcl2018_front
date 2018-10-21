package blockChainTCL.babychain.Utils;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Cache {

    private Context context;

    public Cache(Context co){
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

    public void writeCache(String obj, String fileName) throws IOException {
        File cacheDir = getCacheDir(context);
        File cacheFile = new File(cacheDir, fileName);
        if(!cacheFile.exists()) {
            cacheFile.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(cacheFile);
        fileWriter.write(obj);
        fileWriter.flush();
        fileWriter.close();
    }

    public String readCache(String fileName) throws IOException {
        File cacheDir = getCacheDir(context);
        File cacheFile = new File(cacheDir, fileName);
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
}
