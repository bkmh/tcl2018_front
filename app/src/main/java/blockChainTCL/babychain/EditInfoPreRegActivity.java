package blockChainTCL.babychain;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import blockChainTCL.babychain.RestApi.RestAPITask;
import blockChainTCL.babychain.Utils.Constant;
import blockChainTCL.babychain.Utils.ImageLoadActivity;


public class EditInfoPreRegActivity extends Activity {

    private Activity thisActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inq_info_pre);
    }

    public void mOnClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.button:   //이미지 버튼
                intent = new Intent(thisActivity, ImageLoadActivity.class);
                startActivityForResult(intent, 0);
                break;
        }
    }

    // Image 파일 불러온 후 Setting
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            if(data == null) {
                return;
            }

            String filename = data.getStringExtra("filename");

            EditText upfileEdit = (EditText)findViewById(R.id.editText3);
            upfileEdit.setText(filename);

            try {
                RestAPITask restAPITask = new RestAPITask();
//                String result = restAPITask.execute(Constant.READ_FOR_REGISTERED, filename).get();
                String result = restAPITask.execute(Constant.READ_IMAGE_TO_TEXT, filename).get();

                JSONObject jsonObject = new JSONObject(result);

                EditText strcontectnumberEdit = (EditText)findViewById(R.id.editText5);
                strcontectnumberEdit.setText(jsonObject.getString("contactNum"));

                EditText strdetailinfoEdit = (EditText)findViewById(R.id.editText7);
                strdetailinfoEdit.setText(jsonObject.getString("detailContents"));
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
