package blockChainTCL.babychain;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MyPageMainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_main);
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

            case R.id.bnt_edit_1:       //정보 사전등록 수정 리스트 조회
                intent = new Intent(this, EditInfoPreRegActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_edit_2:       //실종아동 등 등록 수정
                intent = new Intent(this, EditMissingRegActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_edit_3:       //보호아동 등 등록 수정
                intent = new Intent(this, EditCareRegActivity.class);
                startActivity(intent);
                break;


            case R.id.btn_edit_4:       //일일매칭서비스
                setContentView(R.layout.reg_care);
                break;



        }
    }
}
