package blockChainTCL.babychain;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void mOnClick(View v) {

        Intent intent = null;
        switch (v.getId()) {
            case R.id.regButton:
                intent = new Intent(this, RegActivity.class);
                break;
            case R.id.inqButton:
                intent = new Intent(this, InqActivity.class);
                break;
            case R.id.modButton:
                intent = new Intent(this, ModActivity.class);
                break;
            case R.id.tempButton:
                intent = new Intent(this, TempActivity.class);
                break;
        }
        if(intent != null) {
            startActivity(intent);
        }
    }
}
