package blockChainTCL.babychain;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends Activity {

    private BackPressCloseHandler backPressCloseHandler;
    private static String AGREE_CACHE_NAME = "fileAgree";
    private static String AGREED = "agreed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Cache cache = new Cache(this);

            if(AGREED.equals(cache.readCache(AGREE_CACHE_NAME))) {
                setContentView(R.layout.activity_main);
            } else {
                setContentView(R.layout.activity_terms);
            }
        } catch (IOException e) {
            setContentView(R.layout.activity_terms);
        }

        // 약관 1 스크롤 기능 추가
        TextView textTerms1Boby = (TextView)findViewById(R.id.textTerms1Boby);
        if(textTerms1Boby != null) {
            textTerms1Boby.setMovementMethod(new ScrollingMovementMethod());
        }

        backPressCloseHandler = new BackPressCloseHandler(this);
    }

    public void mOnClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            // 약관 '동의 및 진행' 버튼 클릭 시
            case R.id.agreeButton:
                try {
                    Cache cache = new Cache(this);
                    cache.writeCache(AGREED, AGREE_CACHE_NAME);

                    setContentView(R.layout.activity_main);
                } catch (IOException e) {
                    this.finish();
                }
                break;

            // 메인메뉴 '등록' 버튼 클릭 시
            case R.id.regButton:
                intent = new Intent(this, RegActivity.class);
                break;

            // 메인메뉴 '조회' 버튼 클릭 시
            case R.id.inqButton:
                intent = new Intent(this, InqActivity.class);
                break;

            // 메인메뉴 '변경' 버튼 클릭 시
            case R.id.modButton:
                intent = new Intent(this, ModActivity.class);
                break;

            // 메인메뉴 '임시' 버튼 클릭 시
            case R.id.tempButton:
                intent = new Intent(this, TempActivity.class);
                break;
        }
        if(intent != null) {
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed(); // 뒤로 가기 입력 시 바로 종료
        //backPressCloseHandler.onBackPressed(); // 뒤로 가기 두번 눌러 종료
    }
}
