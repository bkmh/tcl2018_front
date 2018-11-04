package blockChainTCL.babychain;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import blockChainTCL.babychain.RestApi.RestAPITask;
import blockChainTCL.babychain.Utils.CacheUtils;
import blockChainTCL.babychain.Utils.Constant;
import blockChainTCL.babychain.Utils.FileUtils;

public class TempActivity extends Activity {

    private Activity thisActivity = this;
    private final int GALLERY = 0, CAMERA = 1;
    private final int PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 0;
    private final int PERMISSIONS_REQUEST_CAMERA = 1;

    private String SHA256 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);


        ((Button) findViewById(R.id.button_temp_register)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        // 조회버튼
        ((Button) findViewById(R.id.button_temp_read)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readImage();
            }
        });
    }

    // Text 등록
    private void register() {
        try {
            RestAPITask restAPITask = new RestAPITask();
            String result = restAPITask.execute(Constant.RESISTER, "keyTempActivity", "valueTempActivity").get();

            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Text 읽기
    private void read() {
        try {
            RestAPITask restAPITask = new RestAPITask();
            String result = restAPITask.execute(Constant.READ, "keyTempActivity").get();

            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Text 수정
    private void modify() {
        try {
            RestAPITask restAPITask = new RestAPITask();
            String result = restAPITask.execute(Constant.MODIFY, "keyTempActivity", "valueModified").get();

            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Text 삭제
    private void delete() {
        try {
            RestAPITask restAPITask = new RestAPITask();
            String result = restAPITask.execute(Constant.DELETE, "keyTempActivity").get();

            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Image 등록 START
    private void uploadImage() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent;
                        switch (which) {
                            case 0:
                                // build.gradle > dependencies 내 추가 : implementation 'com.android.support:support-v4:28.0.0'
                                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(thisActivity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                                } else {
                                    choosePhotoFromGallary();
                                }
                                break;
                            case 1:
                                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(thisActivity, new String[]{Manifest.permission.CAMERA}, PERMISSIONS_REQUEST_CAMERA);
                                } else {
                                    takePhotoFromCamera();
                                }
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                choosePhotoFromGallary();
            } else {
                // Permission Denied
                Toast.makeText(this, "Permission denied for read external storage", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        if (requestCode == PERMISSIONS_REQUEST_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePhotoFromCamera();
            } else {
                // Permission Denied
                Toast.makeText(this, "Permission denied for camera", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void choosePhotoFromGallary() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "GET ALBUM"), GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if(data == null) {
            return;
        }

        CacheUtils cache = new CacheUtils(this);
        String filename = null;
        if(requestCode == GALLERY) {
            Uri contentURI = data.getData();
            filename = FileUtils.getRealPathFromDocumentUri(this, contentURI);
//            try {
//                Bitmap image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        } else if (requestCode == CAMERA) {
            try {
                Bitmap image = (Bitmap) data.getExtras().get("data");
                filename = Calendar.getInstance().getTimeInMillis() + ".jpg";

                cache.writeCacheImage(filename, image);

                filename = cache.getPath(filename);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(!filename.isEmpty()) {
            try {
                RestAPITask restAPITask = new RestAPITask();
                String result = restAPITask.execute(Constant.UPLOAD_IMAGE, filename).get();

                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                SHA256 = result;
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    // Image 등록 END

    // Image 조회
    private void readImage() {
        if(!"".equals(SHA256)) {
            try {
                RestAPITask restAPITask = new RestAPITask();
                String result = restAPITask.execute(Constant.READ_IMAGE, SHA256).get();

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
    }
}
