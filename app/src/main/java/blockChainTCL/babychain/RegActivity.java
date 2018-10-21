package blockChainTCL.babychain;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import blockChainTCL.babychain.RestApi.RestAPITask;
import blockChainTCL.babychain.Utils.Constant;

public class RegActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
    }


    public void mOnClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btnBack:
                intent = new Intent(this, MainActivity.class);
                break;
            case R.id.btnReg:
                //intent = new Intent(this, InqActivity.class);

                new AlertDialog.Builder(this)
                        .setTitle("확인")
                        .setMessage("등록하시겠습니까?")
                        .setIcon(android.R.drawable.ic_menu_save)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // 확인시 처리 로직
                                EditText keyEdit = (EditText)findViewById(R.id.editText);
                                String key = keyEdit.getText().toString();

                                EditText valueEdit = (EditText)findViewById(R.id.editText2);
                                String value = valueEdit.getText().toString();

                                if(!key.isEmpty() && !value.isEmpty()) {
                                    RestAPITask restAPITask = new RestAPITask();
                                    restAPITask.execute(Constant.RESISTER, key, value);

                                    Toast.makeText(RegActivity.this, "[" + key + ":" + value + "]등록을 완료했습니다.", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(RegActivity.this, "값을 입력해주세요.", Toast.LENGTH_SHORT).show();
                                }
                            }})
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // 취소시 처리 로직
                                Toast.makeText(RegActivity.this, "취소하였습니다.", Toast.LENGTH_SHORT).show();
                            }})
                        .show();
                break;
        }
        if(intent != null) {
            startActivity(intent);
        }
    }

}
