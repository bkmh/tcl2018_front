package blockChainTCL.babychain;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import blockChainTCL.babychain.RestApi.RestAPITask;
import blockChainTCL.babychain.Utils.Constant;

public class ModActivity extends Activity {

    private String key = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mod);
    }

    public void mOnClick(View v) {
        Intent intent = null;
        TextView showVal = (TextView)findViewById(R.id.valueTextView);
        EditText updateVal = (EditText)findViewById(R.id.modVal);
        String result;

        switch (v.getId()) {
            case R.id.backButton:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.inqButton:

                try {
                    EditText keyEdit = (EditText)findViewById(R.id.inputKey);
                    key = keyEdit.getText().toString();

                    if(!key.isEmpty()) {
                        RestAPITask restAPITask = new RestAPITask();
                        result = restAPITask.execute(Constant.READ, key).get();

                        if("".equals(result)) {
                            result = "해당 Key는 존재하지 않습니다.";
                            key = null;
                        }
                    } else {
                        result = "값을 입력해주세요.";
                        key = null;
                    }
                } catch (Exception e) {
                    result = "조회 실패했습니다. \n" + e.getStackTrace();
                    key = null;
                }

                showVal.setText(result);

                break;

            case R.id.modButton:
                String value = updateVal.getText().toString();

                if(key != null && !key.isEmpty() && !value.isEmpty()) {
                    RestAPITask restAPITask = new RestAPITask();
                    restAPITask.execute(Constant.MODIFY, key, value);

                    Toast.makeText(ModActivity.this, "[" + key + ":" + value + "]수정을 완료했습니다.", Toast.LENGTH_SHORT).show();
//                    showVal.setText(value);
                } else {
                    Toast.makeText(ModActivity.this, "값을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}
