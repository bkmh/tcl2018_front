package blockChainTCL.babychain;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import blockChainTCL.babychain.RestApi.RestAPITask;
import blockChainTCL.babychain.Utils.Constant;
import blockChainTCL.babychain.Utils.ImageLoadActivity;


public class RegInfoPreActivity extends Activity {

    private Activity thisActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_info_pre);

        final EditText strdetailinfoEdit = (EditText)findViewById(R.id.editText7);
        strdetailinfoEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                strdetailinfoEdit.setHint("");
            }
        });
    }

    public void mOnClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.button:   //이미지 버튼
                intent = new Intent(thisActivity, ImageLoadActivity.class);
                startActivityForResult(intent, 0);
                break;

            case R.id.button2:  //등록 버튼
                EditText upfileEdit = (EditText)findViewById(R.id.editText3);
                String upfile = upfileEdit.getText().toString();

                EditText strcontectnumberEdit = (EditText)findViewById(R.id.editText5);
                String strcontectnumber = strcontectnumberEdit.getText().toString();

                EditText strdetailinfoEdit = (EditText)findViewById(R.id.editText7);
                String strdetailinfo = strdetailinfoEdit.getText().toString();

                try {
                    RestAPITask restAPITask = new RestAPITask();
                    String result = restAPITask.execute(Constant.UPLOAD_FOR_REGISTERED, upfile, strcontectnumber, strdetailinfo).get();

                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
        }
    }
}
