package blockChainTCL.babychain;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import blockChainTCL.babychain.RestApi.RestAPITask;
import blockChainTCL.babychain.Utils.Constant;

public class InqActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inq);
    }

    public void mOnClick(View v) {

        Intent intent = null;
        TextView showVal = (EditText)findViewById(R.id.editText6);
        String result;

        switch (v.getId()) {
            case R.id.backButton:
                intent = new Intent(this, MainActivity.class);
                break;
            case R.id.inqButton:
                try {
                    EditText keyEdit = (EditText)findViewById(R.id.editText4);
                    String key = keyEdit.getText().toString();

                    if(!key.isEmpty()) {
                        RestAPITask restAPITask = new RestAPITask();
                        result = restAPITask.execute(Constant.READ, key).get();

                        if("".equals(result)) {
                            result = "해당 Key는 존재하지 않습니다.";
                        }
                    } else {
                        result = "값을 입력해주세요.";
                    }

                } catch (Exception e) {
                    result = "조회 실패했습니다. \n" + e.getStackTrace();
                }
                showVal.setText(result);

                break;
        }
        if(intent != null) {
            startActivity(intent);
        }
    }


}
