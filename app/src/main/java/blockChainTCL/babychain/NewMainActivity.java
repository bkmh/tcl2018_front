package blockChainTCL.babychain;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class NewMainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_v1);
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
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.regButton:        //정보사전등록
                intent = new Intent(this, RegInfoPreActivity.class);
                startActivity(intent);
                break;

            case R.id.regButton2:       //긴급실종신고
                setContentView(R.layout.reg_emr_ms);
                break;

            case R.id.modButton2:       //등록
                intent = new Intent(this, RegMainActivity.class);
                startActivity(intent);
                break;

            case R.id.inqButton:       //실종아동 등 조회
                setContentView(R.layout.inq_missing);
                break;

            case R.id.inqButton2:       //보호아동 등 조회
                setContentView(R.layout.inq_care);
                break;

            case R.id.modButton:       //경찰청 DB 조회
                intent = new Intent(this, CopDBInqActivity.class);
                startActivity(intent);
                break;

            case R.id.modButton3:       //MY PAGE
                intent = new Intent(this, MyPageMainActivity.class);
                startActivity(intent);
                break;
        }
    }

}
