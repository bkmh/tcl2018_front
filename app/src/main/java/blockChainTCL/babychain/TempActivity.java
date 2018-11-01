package blockChainTCL.babychain;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import blockChainTCL.babychain.RestApi.RestAPITask;
import blockChainTCL.babychain.Utils.Cache;
import blockChainTCL.babychain.Utils.Constant;

public class TempActivity extends Activity {

    private int GALLERY = 1, CAMERA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        // 등록버튼
        ((Button) findViewById(R.id.button_temp_register)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA);
            }
        });

        // 조회버튼
        ((Button) findViewById(R.id.button_temp_read)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    RestAPITask restAPITask = new RestAPITask();
                    String result = restAPITask.execute(Constant.READ, "keyBBB").get();

                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // 수정버튼
        ((Button) findViewById(R.id.button_temp_modify)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    RestAPITask restAPITask = new RestAPITask();
                    String result = restAPITask.execute(Constant.MODIFY, "keyBBB", "valueBBBmodifed").get();

                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // 삭제버튼
        ((Button) findViewById(R.id.button_temp_delete)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    RestAPITask restAPITask = new RestAPITask();
                    String result = restAPITask.execute(Constant.DELETE, "keyBBB").get();

                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }

        String result = "";
        try {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

            File file = new File(this.getCacheDir(), "test.jpg");
            file.createNewFile();

            FileOutputStream fo = new FileOutputStream(file);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{file.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();

            Cache cache = new Cache(this);

            RestAPITask restAPITask = new RestAPITask();
            result = restAPITask.execute(Constant.UPLOAD, cache.getPath("test.jpg")).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();

    }
}
