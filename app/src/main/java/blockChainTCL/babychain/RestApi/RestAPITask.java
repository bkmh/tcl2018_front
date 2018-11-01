package blockChainTCL.babychain.RestApi;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.RandomAccessFile;
import java.security.MessageDigest;

import static blockChainTCL.babychain.Utils.Constant.BACKEND_URL;
import static blockChainTCL.babychain.Utils.Constant.DELETE;
import static blockChainTCL.babychain.Utils.Constant.MODIFY;
import static blockChainTCL.babychain.Utils.Constant.READ;
import static blockChainTCL.babychain.Utils.Constant.RESISTER;
import static blockChainTCL.babychain.Utils.Constant.UPLOAD;

public class RestAPITask extends AsyncTask<String, String, String> {

    private Context mContext;

    public RestAPITask() {}
    public RestAPITask(Context context) {
        mContext = context;
    }

    @Override
    protected String doInBackground(String... strings) {

        String result;
        JSONArray jsonArray;
        JSONObject jsonObject;

        try {
            String method = strings[0]; // 0 : register, read, modify, delete, upload
            RestApi restApi = new RestApi();

            switch(method) {
                case RESISTER :
                    result = restApi.POST(BACKEND_URL + RESISTER, "key", strings[1], "value", strings[2]);

                    break;
                case READ :
                    result = restApi.GET(BACKEND_URL + strings[1]);

                    jsonArray = new JSONArray(result);
                    jsonObject = jsonArray.getJSONObject(0);
                    result = jsonObject.getString("parsed");

                    break;
                case MODIFY :
                    result = restApi.POST(BACKEND_URL + MODIFY, "key", strings[1], "value", strings[2]);

                    break;
                case DELETE :
                    result = restApi.GET(BACKEND_URL + DELETE + "/" + strings[1]);

                    break;
                case UPLOAD :
                    result =  upload(strings[1]);

                    break;
                default :
                    result = "ERROR : NOT EXIST METHOD!";
                    break;
            }
        } catch(Exception e) {
            result = e.toString();
        }

        return result;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(String... progress) { //4
        super.onProgressUpdate(progress);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }

    private String upload(String filename) {

        String result;

        try {
            String SHA = extractFileHashSHA256(filename);
            File uploadFile = new File(filename);
            String requestURL = BACKEND_URL + UPLOAD;

            Multipart multipart = new Multipart(requestURL, "UTF-8");

            // KEY, VALUE setting
            multipart.addFormField("value", SHA);
            multipart.addFilePart("upfile", uploadFile);

            result = multipart.finish();
        } catch (Exception e) {
            result = e.toString();
        }

        return result;
    }

    private static String extractFileHashSHA256(String filename) throws Exception {

        String SHA;
        int buff = 16384;

        RandomAccessFile file = new RandomAccessFile(filename, "r");

        MessageDigest hashSum = MessageDigest.getInstance("SHA-256");

        byte[] buffer = new byte[buff];
        byte[] partialHash = null;

        long read = 0;

        long offset = file.length();
        int unitsize;
        while(read < offset) {
            unitsize = (int) ((offset -read) >= buff ? buff : (offset - read));
            file.read(buffer, 0 , unitsize);

            read += unitsize;
        }
        file.close();

        partialHash = hashSum.digest();

        StringBuffer sb = new StringBuffer();
        for(int i=0; i<partialHash.length; i++) {
            sb.append(Integer.toString((partialHash[i]&0xff) + 0x100, 16).substring(1));
        }
        SHA = sb.toString();

        return SHA;
    }
}
