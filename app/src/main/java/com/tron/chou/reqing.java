package com.tron.chou;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class reqing extends AppCompatActivity {

    int shuiping;
    int ciri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reqing);

        ArrayList<String> zifu = getIntent().getStringArrayListExtra("zifu");
        ArrayList<String> hanyi = getIntent().getStringArrayListExtra("hanyi");
        ArrayList<String> yuyin = getIntent().getStringArrayListExtra("sheng");
        shuiping = getIntent().getIntExtra("shuiping", 0);
        ciri = getIntent().getIntExtra("ciri", 0);

        int xang = getSharedPreferences("cailiao", 0).getInt("guo", 0);
        MaterialTextView materialTextView = findViewById(R.id.materialTextView);
        materialTextView.setText(String.valueOf(xang));

        int fu = getSharedPreferences("fu", 0).getInt("shuo", 0);
        MaterialTextView grace = findViewById(R.id.grace);
        grace.setText(String.valueOf(fu));

        if (LangCardActivity.fou) {

            MaterialTextView xin = findViewById(R.id.xin);
            xin.setText(R.string.new_high_score);

        }

        LinearLayout buju = findViewById(R.id.buju);
        buju.setPadding(30, 30, 30, 30);

        MaterialCardView.LayoutParams params = new MaterialCardView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        params.setMargins(36, 18, 36, 18);

        FrameLayout.LayoutParams paramz = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramz.setMargins(32, 16, 32, 32);

        if (zifu != null) {

            for (int shu = 0; shu < zifu.size(); shu++) {

                LinearLayout chenzen = new LinearLayout(this);
                chenzen.setOrientation(LinearLayout.VERTICAL);

                MaterialCardView ka = new MaterialCardView(this);
                ka.setLayoutParams(params);

                MaterialTextView wenben = new MaterialTextView(this);
                wenben.setTextSize(18);
                wenben.setText(zifu.get(shu));
                chenzen.addView(wenben);
                wenben.setPadding(20, 20, 20, 20);

                MaterialTextView wenbo = new MaterialTextView(this);
                wenbo.setTextSize(18);
                if (hanyi != null) {
                    wenbo.setText(hanyi.get(shu));
                }
                chenzen.addView(wenbo);
                wenbo.setPadding(20, 20, 20, 20);

                MaterialTextView wenvi = new MaterialTextView(this);
                wenvi.setTextSize(18);
                if (yuyin != null) {
                    wenvi.setText(yuyin.get(shu));
                }
                chenzen.addView(wenvi);
                wenvi.setPadding(20, 20, 20, 20);

                jeikou.setHuan(true);

                WebView wang = new WebView(this);
                wang.setLayoutParams(paramz);

                WebSettings webSettings = wang.getSettings();
                webSettings.setJavaScriptEnabled(true);

                wang.addJavascriptInterface(new jeikou(getApplicationContext()), "Android");
                wang.loadUrl("file:///android_asset/hanzi.html");

                chenzen.addView(wang);

                ka.addView(chenzen);

                buju.addView(ka);

            }

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();

    }

}
