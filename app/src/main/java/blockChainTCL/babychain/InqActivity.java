package blockChainTCL.babychain;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class InqActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inq);
    }

    public void mOnClick(View v) {

        Intent intent = null;
        TextView showVal = (EditText)findViewById(R.id.editText6);

        switch (v.getId()) {
            case R.id.backButton:
                intent = new Intent(this, MainActivity.class);
                break;
            case R.id.inqButton:
                showVal.setText("조회되었습니다.");
                break;
        }
        if(intent != null) {
            startActivity(intent);
        }
    }


}
