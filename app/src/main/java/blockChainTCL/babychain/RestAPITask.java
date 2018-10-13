package blockChainTCL.babychain;

import android.os.AsyncTask;

public class RestAPITask extends AsyncTask<Integer, Void, String> {
    @Override
    public String doInBackground(Integer... params) {
        RestApi ra = new RestApi();
        int lat = params[0];
        int lon = params[1];
        return ra.getResult();
    }
}
