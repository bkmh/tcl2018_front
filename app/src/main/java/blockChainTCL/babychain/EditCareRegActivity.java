package blockChainTCL.babychain;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class EditCareRegActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inq_care_reg);
    }

    public void onBackBtn(View v){

    }

    public void mOnClick(View v) {
        Intent intent = null;
        TextView showVal = (TextView)findViewById(R.id.valueTextView);
        EditText updateVal = (EditText)findViewById(R.id.modVal);
        String result;

        switch (v.getId()) {
            case R.id.backButton:
                intent = new Intent(this, NewMainActivity.class);
                startActivity(intent);
                break;


            case R.id.btn_temp1:       //보호아동 등 등록 수정/삭제
                setContentView(R.layout.edit_care_reg);
                break;





        }
    }
}
