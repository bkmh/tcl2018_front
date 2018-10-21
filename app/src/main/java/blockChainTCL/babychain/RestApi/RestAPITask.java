package blockChainTCL.babychain.RestApi;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import static blockChainTCL.babychain.Utils.Constant.BACKEND_URL;
import static blockChainTCL.babychain.Utils.Constant.READ;
import static blockChainTCL.babychain.Utils.Constant.RESISTER;

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
                    result = restApi.POST(BACKEND_URL + "register", "key", strings[1], "value", strings[2]);

                    break;
                case READ :
                    result = restApi.GET(BACKEND_URL + strings[1]);

                    jsonArray = new JSONArray(result);
                    jsonObject = jsonArray.getJSONObject(0);
                    result = jsonObject.getString("parsed");

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
}
