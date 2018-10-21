package blockChainTCL.babychain;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import blockChainTCL.babychain.RestApi.RestAPITask;
import blockChainTCL.babychain.Utils.Constant;

public class TempActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        Button buttonRequestJSON = (Button)findViewById(R.id.button_main_requestjson);
        TextView textviewJSONText = (TextView)findViewById(R.id.textview_main_jsontext);
        textviewJSONText.setMovementMethod(new ScrollingMovementMethod());

        buttonRequestJSON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RestAPITask restAPITask = new RestAPITask();
                String result = null;
                try {
                    result = restAPITask.execute(Constant.READ, "keyBBB").get();
                    //result = restAPITask.execute(Constant.RESISTER, "keyBBB", "valueBBB").get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            }
        });
    }
}
