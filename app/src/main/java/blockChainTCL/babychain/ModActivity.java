package blockChainTCL.babychain;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

import static blockChainTCL.babychain.R.id.modVal;

public class ModActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mod);
    }

    public void mOnClick(View v) {
        Intent intent = null;
        TextView showVal = (TextView)findViewById(R.id.valueTextView);
        EditText updateVal = (EditText)findViewById(R.id.modVal);

        switch (v.getId()) {
            case R.id.backButton:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.inqButton:
                showVal.setText("010-3456-7890");

                //2018.10.14 추가
                RestAPITask t = new RestAPITask();
                try{
                    String val = t.execute().get();
                    showVal.setText(val);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.modButton:
                showVal.setText(updateVal.getText());
                break;
        }
    }
}
