package com.tron.chou;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class LangCardActivity extends AppCompatActivity {

    int shuiPing;
    int zao;
    int freya = 0;
    static int fuyu = 0;
    MaterialTextView word;
    MaterialTextView meaning;
    MaterialTextView shengyin;
    int ciri;
    int shi = 0;
    static int fang;
    InterstitialAd mInterstitialAd;
    static boolean fou = false;
    SharedPreferences cailiao;
    SharedPreferences.Editor fenxiang;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String xharacter;
    String xinyin;
    String xefinition;
    static ArrayList<String> character = new ArrayList<>();
    ArrayList<String> definition = new ArrayList<>();
    ArrayList<String> pinyin = new ArrayList<>();
    static ArrayList<String> xharac = new ArrayList<>();
    MaterialButton defenle;
    TextToSpeech tts;
    ArrayList<String> zifi;
    ArrayList<String> rifi;
    ArrayList<String> cifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lang_card);

        fuyu = 0;
        character.clear();

        fou = false;

        jeikou.setHuan(false);
        jeikou.setGuo(-1);

        AudioManager audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);

        if (audioManager != null && audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) == 0) {

            int maxVolumme = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            float percent = 0.6f;
            int volume = (int) (maxVolumme * percent);
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);

        }

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mInterstitialAd = new InterstitialAd(LangCardActivity.this);
        mInterstitialAd.setAdUnitId("ca-app-pub-7294079480685910/2509415283");

        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        shuiPing = getIntent().getIntExtra("level", freya);
        zifi = getIntent().getStringArrayListExtra("character");
        rifi = getIntent().getStringArrayListExtra("meaning");
        cifi = getIntent().getStringArrayListExtra("pinyin");

        xharacter = getIntent().getStringExtra("xharacter");
        xinyin = getIntent().getStringExtra("xinyin");
        xefinition = getIntent().getStringExtra("xefinition");

        defenle = findViewById(R.id.defenle);

        if (xharacter != null) {

             xharac.add(xharacter);

            defenle.setVisibility(View.INVISIBLE);

        }

        sharedPreferences = getSharedPreferences("" + shuiPing, 0);
        editor = sharedPreferences.edit();
        editor.apply();

        ciri = sharedPreferences.getInt("zifi", 0);

        fang = ciri;

        cailiao = getSharedPreferences("cailiao", 0);
        fenxiang = cailiao.edit();

        Date time = Calendar.getInstance().getTime();
        String tian = (String) DateFormat.format("EEEE", time);

        shi = cailiao.getInt("guo", 0);

        if (!cailiao.getString("tian", "tian").equals(tian)) {

            fenxiang.putString("tian", tian);
            fenxiang.apply();

            shi = 0;
            fenxiang.putInt("guo", shi);
            fenxiang.apply();

        }

        MaterialButton defenle = findViewById(R.id.defenle);
        defenle.setText(String.valueOf(cailiao.getInt("guo", shi)));

        assert zifi != null;
        assert rifi != null;
        assert cifi != null;

        if (ciri == 0) {

            for (int ru = 0; ru < 8; ru++) {

                zu(ru);

            }

        } else if (ciri == 1) {

            for (int ru = 12; ru < 24; ru++) {

                zu(ru);

            }

        } else if (ciri == 2) {

            for (int ru = 12; ru < 24; ru++) {

                zu(ru);

            }

            for (int ru = 0; ru < 24; ru = ru + 4) {

                zu(ru);

            }

        } else if (ciri == 3) {

            for (int ru = 24; ru < 30; ru++) {

                zu(ru);

            }

            for (int ru = 0; ru < 30; ru = ru + 3) {

                zu(ru);

            }

        } else if (ciri == 4) {

            for (int ru = 0; ru < 30; ru = ru + 3) {

                zu(ru);

            }

            for (int ru = 1; ru < 30; ru = ru + 2) {

                zu(ru);

            }

        } else if (ciri >= 5) {

            for (int ru = 0; ru < zifi.size(); ru++) {

                zu(ru);

            }

        }

        freya = zao;

        hanzi();

    }

    private void hanzi() {

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        final int width = size.x;

        final WebView webView = findViewById(R.id.svg);
        webView.setWebChromeClient(new WebChromeClient());

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new jeikou(this), "Android");
        webView.loadUrl("file:///android_asset/hanzi.html");

        word = findViewById(R.id.word);
        meaning = findViewById(R.id.meaning);
        shengyin = findViewById(R.id.shengyin);

        if (character.size() != 0) {

            word.setText(character.get(0));
            meaning.setText(definition.get(0));
            shengyin.setText(pinyin.get(0));

        }

        MaterialButton niokou = findViewById(R.id.niokou);

        MaterialTextView tiaoyue = findViewById(R.id.tiaoyue);

        if (xharacter != null) {

            word.setText(xharacter);
            meaning.setText(xefinition);
            shengyin.setText(xinyin);

            niokou.setVisibility(View.INVISIBLE);

            tiaoyue.setVisibility(View.INVISIBLE);

        }

        tts = new TextToSpeech(LangCardActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

                if(status == TextToSpeech.SUCCESS){
                    int result=tts.setLanguage(Locale.CHINESE);
                    if(result==TextToSpeech.LANG_MISSING_DATA ||
                            result==TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("error", "This Language is not supported");
                    }else{
                        tts.setLanguage(Locale.CHINESE);
                        shuohua();
                    }
                }
                else
                    Log.e("error", "Initilization Failed!");

            }
        });

        //onStart();

        MaterialButton sheng = findViewById(R.id.materialButton);
        sheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shuohua();

            }
        });

        niokou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fuyu++;

                if (jeikou.zhayi != null) {

                    if (jeikou.zhayi.equals("shushi") && fuyu < character.size()) {

                        defen(fenxiang);

                        word.setText(character.get(fuyu));
                        meaning.setText(definition.get(fuyu));
                        shengyin.setText(pinyin.get(fuyu));
                        shuohua();

                        jeikou.setShushi(null);
                        webView.loadUrl("file:///android_asset/hanzi.html");

                        jeikou.zhayaiyo(null);

                    } else if (fuyu == character.size()) {

                        if (mInterstitialAd.isLoaded()) {

                            mInterstitialAd.show();

                        } else {

                            Log.d("TAG", "The interstitial wasn't loaded yet.");

                        }

                        defen(fenxiang);

                        int zhi = sharedPreferences.getInt("zifi", 0);
                        zhi++;
                        editor.putInt("zifi", zhi);
                        editor.apply();

                        kaishi();

                    }

                } else {

                    fuyu--;

                    Toast.makeText(LangCardActivity.this, "sketch the character by touching on the right side of card please", Toast.LENGTH_SHORT).show();

                }

            }
        });

        tiaoyue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date time = Calendar.getInstance().getTime();
                String tian = (String) DateFormat.format("EEEE", time);

                Log.d("freya", "onCreate: " + tian);

                fuyu++;

                if (fuyu < character.size()) {

                    Log.d("freya", "onClick: " + fuyu);

                    word.setText(character.get(fuyu));
                    meaning.setText(definition.get(fuyu));
                    shengyin.setText(pinyin.get(fuyu));
                    shuohua();

                    jeikou.setShushi(null);
                    webView.loadUrl("file:///android_asset/hanzi.html");

                    jeikou.zhayaiyo(null);

                } else if (fuyu == character.size()) {

                    if (mInterstitialAd.isLoaded()) {

                        mInterstitialAd.show();

                    } else {

                        Log.d("TAG", "The interstitial wasn't loaded yet.");

                    }

                    kaishi();

                }

            }
        });

        final MaterialCardView hanzAr = findViewById(R.id.hanz_ar);
        hanzAr.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getX() > (3 * width) / 5) {

                    final ObjectAnimator oa1 = ObjectAnimator.ofFloat(hanzAr, "scaleX", 1f, 0f);
                    final ObjectAnimator oa2 = ObjectAnimator.ofFloat(hanzAr, "scaleX", 0f, 1f);

                    oa1.setInterpolator(new DecelerateInterpolator());
                    oa2.setInterpolator(new AccelerateDecelerateInterpolator());

                    oa1.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);

                            jeikou.setShushi("shushi");
                            webView.loadUrl("file:///android_asset/hanzi.html");

                            oa2.start();
                        }
                    });
                    oa1.start();

                } else if (event.getX() < (3 * width) / 5) {

                    final ObjectAnimator oa1 = ObjectAnimator.ofFloat(hanzAr, "scaleX", 0f, 1f);
                    final ObjectAnimator oa2 = ObjectAnimator.ofFloat(hanzAr, "scaleX", 1f, 0f);

                    oa1.setInterpolator(new AccelerateDecelerateInterpolator());
                    oa2.setInterpolator(new DecelerateInterpolator());

                    oa2.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);

                            jeikou.setShushi(null);
                            webView.loadUrl("file:///android_asset/hanzi.html");

                            oa1.start();
                        }
                    });
                    oa2.start();

                }

                return false;
            }
        });

    }

    private void defen(SharedPreferences.Editor fenxiang) {

        int zhai = cailiao.getInt("defenle", 0);
        zhai = zhai + 2;

        fenxiang.putInt("defenle", zhai);
        fenxiang.apply();

        shi = shi + 2;

        fenxiang.putInt("guo", shi);
        fenxiang.apply();

        SharedPreferences fu = getSharedPreferences("fu", 0);

        if (shi > fu.getInt("shuo", 0)) {

            SharedPreferences.Editor editor = fu.edit();
            editor.putInt("shuo", shi);
            editor.apply();

            fou = true;

        }

        defenle.setText(String.valueOf(cailiao.getInt("guo", 0)));

    }

    private void zu(int ru) {

        if (xharacter == null) {

            character.add(zifi.get(ru));
            definition.add(rifi.get(ru));
            pinyin.add(cifi.get(ru));

        }

    }

    private void kaishi() {

        Intent buju = new Intent(getApplicationContext(), reqing.class);
        buju.putStringArrayListExtra("zifu", character);
        buju.putStringArrayListExtra("hanyi", definition);
        buju.putStringArrayListExtra("sheng", pinyin);
        buju.putExtra("shuiping", shuiPing);
        buju.putExtra("ciri", ciri);
        startActivity(buju);

        finish();

    }

    private void shuohua() {

        if (shengyin != null)
        tts.speak(shengyin.getText().toString(), TextToSpeech.QUEUE_ADD, null);

    }

    public static ArrayList<String> za() {

        ArrayList<String> shi;

        if (!xharac.isEmpty()) {

            shi = xharac;

        } else {

            shi = character;

        }

        return shi;
    }

    @Override
    protected void onStart() {
        super.onStart();

        shuohua();

    }

    @Override
    protected void onDestroy() {

        if (tts != null) {

            tts.stop();
            tts.shutdown();

        }

        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    finish();
                }
            });
        } else {
            super.onBackPressed();
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }

        xharac.clear();

    }
}

