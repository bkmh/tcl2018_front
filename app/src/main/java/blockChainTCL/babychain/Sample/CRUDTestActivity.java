package blockChainTCL.babychain.Sample;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import blockChainTCL.babychain.NewMainActivity;
import blockChainTCL.babychain.R;
import blockChainTCL.babychain.RestApi.RestAPITask;
import blockChainTCL.babychain.Utils.Constant;
import blockChainTCL.babychain.Utils.ImageLoadActivity;

public class CRUDTestActivity extends Activity {

    private Activity thisActivity = this;
    private final int UPLOAD_IMAGE = 1;
    private final int UPLOAD_IMAGE_TO_TEXT = 2;
    private final int READ_IMAGE_TO_TEXT = 3;
    private final int MODIFY_IMAGE_TO_TEXT = 4;
    private final int DELETE_IMAGE_TO_TEXT = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_test);

        // Text 등록
        ((Button) findViewById(R.id.button_temp_register)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    RestAPITask restAPITask = new RestAPITask();
                    String result = restAPITask.execute(Constant.RESISTER, "keyString", "valueString").get();

                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Text 조회
        ((Button) findViewById(R.id.button_temp_read)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    RestAPITask restAPITask = new RestAPITask();
                    String result = restAPITask.execute(Constant.READ, "keyString").get();

                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Text 수정
        ((Button) findViewById(R.id.button_temp_modify)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    RestAPITask restAPITask = new RestAPITask();
                    String result = restAPITask.execute(Constant.MODIFY, "keyString", "valueStringModified").get();

                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Text 삭제
        ((Button) findViewById(R.id.button_temp_delete)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    RestAPITask restAPITask = new RestAPITask();
                    String result = restAPITask.execute(Constant.DELETE, "keyString").get();

                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Image 등록
        ((Button) findViewById(R.id.button_temp_upload_image)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(thisActivity, ImageLoadActivity.class);
                startActivityForResult(intent, UPLOAD_IMAGE);
            }
        });

        // Image 조회
        ((Button) findViewById(R.id.button_temp_read_image)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    RestAPITask restAPITask = new RestAPITask();
                    String result = restAPITask.execute(Constant.READ_IMAGE, "valueString").get();

                    byte[] decodedString = Base64.decode(result, Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                    ImageView imageview = (ImageView) findViewById(R.id.iv_temp);
                    imageview.setImageBitmap(bitmap);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Image To Text 등록
        ((Button) findViewById(R.id.button_temp_upload_itot)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(thisActivity, ImageLoadActivity.class);
                startActivityForResult(intent, UPLOAD_IMAGE_TO_TEXT);
            }
        });

        // Image To Text 조회
        ((Button) findViewById(R.id.button_temp_read_itot)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(thisActivity, ImageLoadActivity.class);
                startActivityForResult(intent, READ_IMAGE_TO_TEXT);
            }
        });

        // Image To Text 수정
        ((Button) findViewById(R.id.button_temp_modify_itot)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(thisActivity, ImageLoadActivity.class);
                startActivityForResult(intent, MODIFY_IMAGE_TO_TEXT);
            }
        });

        // Image To Text 삭제
        ((Button) findViewById(R.id.button_temp_delete_itot)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(thisActivity, ImageLoadActivity.class);
                startActivityForResult(intent, DELETE_IMAGE_TO_TEXT);
            }
        });
    }

    // Image 파일 불러온 후 Multipart 호출
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == this.RESULT_CANCELED) {
            return;
        } else if(resultCode == this.RESULT_OK) {
            if(data == null) {
                return;
            }

            String filename = data.getStringExtra("filename");

            if(requestCode == UPLOAD_IMAGE) { // Image 등록
                if(!filename.isEmpty()) {
                    try {
                        RestAPITask restAPITask = new RestAPITask();
                        String result = restAPITask.execute(Constant.UPLOAD_IMAGE, filename, "valueString").get();

                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else if(requestCode == UPLOAD_IMAGE_TO_TEXT) { // Image To Text 등록
                if(!filename.isEmpty()) {
                    try {
                        RestAPITask restAPITask = new RestAPITask();
                        String result = restAPITask.execute(Constant.UPLOAD_IMAGE_TO_TEXT, filename, "valueString").get();

                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }  else if(requestCode == READ_IMAGE_TO_TEXT) { // Image To Text 조회
                if(!filename.isEmpty()) {
                    try {
                        RestAPITask restAPITask = new RestAPITask();
                        String result = restAPITask.execute(Constant.UPLOAD_IMAGE_TO_TEXT, filename).get();

                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }  else if(requestCode == MODIFY_IMAGE_TO_TEXT) { // Image To Text 수정
                if(!filename.isEmpty()) {
                    try {
                        RestAPITask restAPITask = new RestAPITask();
                        String result = restAPITask.execute(Constant.UPLOAD_IMAGE_TO_TEXT, filename, "valueStringModified").get();

                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }  else if(requestCode == DELETE_IMAGE_TO_TEXT) { // Image To Text 삭제
                if(!filename.isEmpty()) {
                    try {
                        RestAPITask restAPITask = new RestAPITask();
                        String result = restAPITask.execute(Constant.UPLOAD_IMAGE_TO_TEXT, filename).get();

                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void onBtnClick3(View v) {
        //이미선 수정
        Intent intent = new Intent(getApplicationContext(), NewMainActivity.class);
        startActivity(intent);
    }
}
