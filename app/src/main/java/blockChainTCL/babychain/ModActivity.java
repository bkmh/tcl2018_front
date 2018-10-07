package blockChainTCL.babychain;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ModActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mod);
    }

    public void mOnClick(View v) {

        Intent intent = null;
        switch (v.getId()) {
            case R.id.backButton:
                intent = new Intent(this, MainActivity.class);
                break;
        }
        if(intent != null) {
            startActivity(intent);
        }
    }
}
