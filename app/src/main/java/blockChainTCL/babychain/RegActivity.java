package blockChainTCL.babychain;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
                                Toast.makeText(RegActivity.this, "등록을 완료했습니다.", Toast.LENGTH_SHORT).show();
                                finish();
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
