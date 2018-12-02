package blockChainTCL.babychain.Utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.widget.Toast;

import java.io.IOException;

public class ImageLoadActivity extends Activity {

    private Activity thisActivity = this;
    private final int GALLERY = 0, CAMERA = 1;
    private final int PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 0;
    private final int PERMISSIONS_REQUEST_CAMERA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);

        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };

        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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

        pictureDialog.setOnKeyListener(
                new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        if(keyCode == KeyEvent.KEYCODE_BACK) {
                            setResult(RESULT_CANCELED);
                            finish();
                        }

                        return false;
                    }
                });

        pictureDialog.setCancelable(false);

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
                finish();
            }
            return;
        }

        if (requestCode == PERMISSIONS_REQUEST_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePhotoFromCamera();
            } else {
                // Permission Denied
                Toast.makeText(this, "Permission denied for camera", Toast.LENGTH_SHORT).show();
                finish();
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
            filename = PathUtil.getRealPath(getApplicationContext(), contentURI);

//            try {
//                Bitmap image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        } else if (requestCode == CAMERA) {
            try {
                Bitmap image = (Bitmap) data.getExtras().get("data");
//                filename = Calendar.getInstance().getTimeInMillis() + ".jpg";
                filename = "temp.jpg";

                cache.writeCacheImage(filename, image);

                filename = cache.getPath(filename);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Intent result = new Intent();
        result.putExtra("filename",filename);
        setResult(RESULT_OK,result);
        finish();

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,0);
    }
}
