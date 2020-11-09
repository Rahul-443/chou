package com.tron.chou;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Lvelz extends AppCompatActivity {

    ArrayList<String> character = new ArrayList<>();
    ArrayList<String> definition = new ArrayList<>();
    ArrayList<String> pinyin = new ArrayList<>();
    LinearLayout chowMou;
    String[] deffs;
    ArrayList<String> words = new ArrayList<>();
    MaterialButton cailiao;
    MaterialButton reviewCards;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor ru;
    int ririko;
    int fu = 20;
    int zou = 0;
    int zie = 0;
    boolean connected = false;
    boolean doubleBackToExitPressedOnce = false;
    List<MaterialButton> mou;
    Intent seis;
    int rou;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lvelz);

        chowMou = findViewById(R.id.chow_mou);

        cailiao = findViewById(R.id.cailiao);

        sharedPreferences = getSharedPreferences("cailiao", 0);

        ru = sharedPreferences.edit();
        ru.apply();

        fu = sharedPreferences.getInt("fu", 20);

        ririko = sharedPreferences.getInt("defenle", 0);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {

            Network networkCapabilities = null;
            if (connectivityManager != null) {
                networkCapabilities = connectivityManager.getActiveNetwork();
            }

            NetworkCapabilities activeNetwork = null;
            if (connectivityManager != null) {
                activeNetwork = connectivityManager.getNetworkCapabilities(networkCapabilities);
            }

            if (activeNetwork != null && ((activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)))) {

                connected = true;

            }

        } else {

            if (connectivityManager != null) {

                NetworkInfo xie = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                NetworkInfo fie = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                NetworkInfo vie = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);

                try {

                    if (xie != null && xie.getState() == NetworkInfo.State.CONNECTED ||
                            fie != null && fie.getState() == NetworkInfo.State.CONNECTED ||
                            vie != null && vie.getState() == NetworkInfo.State.CONNECTED) {

                        connected = true;

                    }

                } catch (Exception e) {

                    e.printStackTrace();

                }

            }

        }

        if (!connected) {

            new MaterialAlertDialogBuilder(Lvelz.this)
                    .setMessage("No Network Connection found, please switch on the Internet, if Internet is not available for the time you won't be able to enjoy writing characters, but you can still review learnt characters by clicking the blue button above :)\nHappy Learning !!")
                    .show();

        }

        cailiao.setText(String.valueOf(ririko));

        poof();

        shi shi = new shi() {
            @Override
            public void run() {
                super.run();
            }
        };
        shi.start();

        mou();

        lock();

        reviewCards = findViewById(R.id.materialButton2);
        reviewCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(seis);

            }
        });

        Log.d("freya", "onClick: " + rou);

        final HorizontalScrollView freya = findViewById(R.id.horizontalScrollView);

        new Handler().post(new Runnable() {
            @Override
            public void run() {

                freya.smoothScrollTo((sharedPreferences.getInt("rou", 0) - 1) * 232, 0);

            }
        });

    }

    private void poof() {

        if (Build.VERSION.SDK_INT > 16) {

            LottieAnimationView zaoshang = findViewById(R.id.zaoshang);

            try {

                String naughtyTime = "22:00:00";
                Date naughty = new SimpleDateFormat("HH:mm:ss").parse(naughtyTime);
                Calendar calendar1 = Calendar.getInstance();
                if (naughty != null) {
                    calendar1.setTime(naughty);
                }
                calendar1.add(Calendar.DATE, 1);

                String Sunrise = "04:00:00";
                Date rise = new SimpleDateFormat("HH:mm:ss").parse(Sunrise);
                Calendar calendar2 = Calendar.getInstance();
                if (rise != null) {
                    calendar2.setTime(rise);
                }
                calendar2.add(Calendar.DATE, 1);

                Date time = Calendar.getInstance().getTime();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                String nightTime = simpleDateFormat.format(time);
                Date shijian = new SimpleDateFormat("HH:mm:ss").parse(nightTime);
                Calendar calendar3 = Calendar.getInstance();
                if (shijian != null) {
                    calendar3.setTime(shijian);
                }
                calendar3.add(Calendar.DATE, 1);

                Date x = calendar3.getTime();
                if (x.after(calendar1.getTime()) || x.before(calendar2.getTime())) {

                    zaoshang.setAnimation(R.raw.full_screen_night);
                    Log.d("freya", "onCreateView: " + time);

                } else {

                    zaoshang.setAnimation(R.raw.morning_scenery);

                }

            } catch (ParseException e) {

                e.printStackTrace();

            }

        }

    }

    abstract class shi extends Thread {

        @Override
        public void run() {
            super.run();

            BufferedReader bufferedReader = null;

            try{

                bufferedReader = new BufferedReader(
                        new InputStreamReader(getAssets().open("zidian.txt")));

                String mLine;

                while ((mLine = bufferedReader.readLine()) != null) {

                    words.add(mLine);

                }

                Log.d("zhin", "run: " + words.size());

                for (int zhi = 0; zhi < 9514; zhi++) {

                    deffs = words.toArray(new String[zhi]);

                }

            } catch (IOException e) {

                Log.w("here", "run: " + e );

            } finally {

                if (bufferedReader != null) {

                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        Log.w("here", "run: " + e );
                    }

                }

            }

        }

    }

    public void mou() {

        mou = new ArrayList<>(300);

        for (int i = 0; i < 60; i++) {

            if (i % 2 == 0) {

                for (int j = 0; j < 5; j++) {

                    yanse(j);

                }

            } else {

                for (int j = 5; j > 0; j--) {

                    yanse(j);

                }

            }

        }

        ArrayList<String> luse = new ArrayList<>();
        luse.add("#47f9fe");
        luse.add("#1295fd");
        luse.add("#fd75a5");
        luse.add("#ea1e77");
        luse.add("#f75fe1");
        luse.add("#c425ec");
        luse.add("#b4fa51");
        luse.add("#78cb29");

        for (int yi = 0; yi < 300; yi++) {

            mou.get(yi).setText(String.valueOf(yi + 1));
            mou.get(yi).setTextSize(40);

            if (yi < 8) {

                mou.get(yi).setStrokeColor(ColorStateList.valueOf(Color.parseColor(luse.get(yi))));

            } else {

                mou.get(yi).setStrokeColor(ColorStateList.valueOf(Color.parseColor(luse.get(yi % 8))));

            }

        }

        for (int i = 0; i < 300; i++) {

            final int finalI = i;
            mou.get(finalI).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    zie = finalI;

                      if (finalI <= sharedPreferences.getInt("zou", 0)) {

                          vie(finalI);

                      } else {

                          if (finalI != sharedPreferences.getInt("zou", 0) + 1) {

                              new MaterialAlertDialogBuilder(Lvelz.this)
                                      .setMessage("unlock level " + (finalI) + " to unlock this level please")
                                      .show();

                          } else {

                              new MaterialAlertDialogBuilder(Lvelz.this)
                                      .setMessage("open with " + fu + " coins")
                                      .setPositiveButton("unLock", new DialogInterface.OnClickListener() {
                                          @Override
                                          public void onClick(DialogInterface dialog, int which) {

                                              Log.d("freya", "onClick: " + fu);

                                              if (ririko >= fu) {

                                                  ririko = ririko - fu;
                                                  ru.putInt("defenle", ririko);
                                                  ru.apply();

                                                  fu = fu + 20;

                                                  ru.putInt("fu", fu);
                                                  ru.apply();

                                                  cailiao.setText(String.valueOf(ririko));

                                                  zou = finalI;

                                                  ru.putInt("zou", zou);
                                                  ru.apply();

                                                  lock();

                                                  new MaterialAlertDialogBuilder(Lvelz.this)
                                                          .setMessage("woop! level " + (finalI + 1) + " opened")
                                                          .setPositiveButton("let's go", new DialogInterface.OnClickListener() {
                                                              @Override
                                                              public void onClick(DialogInterface dialog, int which) {

                                                                  vie(finalI);

                                                              }
                                                          })
                                                          .show();

                                              } else {

                                                  Toast.makeText(Lvelz.this, "" + "skore", Toast.LENGTH_SHORT).show();

                                              }

                                          }
                                      })
                                      .show();

                          }

                      }

                }
            });

        }

    }

    private void vie(int finalI) {

        rou = finalI;

        ru.putInt("rou", rou);
        ru.apply();

        character.clear();
        definition.clear();
        pinyin.clear();

        for (int yi = (finalI * 30); yi < ((finalI + 1) * 30); yi++) {

            String[] defs = deffs[yi].split(":");

            character.add(defs[0]);
            definition.add(defs[1]);
            pinyin.add(defs[2]);

        }

        Intent startLangCard = new Intent(getApplicationContext(), LangCardActivity.class);
        startLangCard.putExtra("level", finalI);
        startLangCard.putStringArrayListExtra("character", character);
        startLangCard.putStringArrayListExtra("meaning", definition);
        startLangCard.putStringArrayListExtra("pinyin", pinyin);
        startActivity(startLangCard);

    }

    private void yanse(int j) {

        MaterialButton shi = new MaterialButton(this, null, R.attr.materialButtonOutlinedStyle);
        shi.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.orchid)));
        shi.setBackgroundColor(Color.WHITE);
        shi.setCornerRadius(48);
        shi.setStrokeWidth(8);
        LinearLayout.LayoutParams layoutParamz = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
        layoutParamz.setMargins(20, j*100, 20, 20);
        shi.setLayoutParams(layoutParamz);
        chowMou.addView(shi);
        mou.add(shi);

    }

    private void lock() {

        ArrayList<String> character = new ArrayList<>();
        ArrayList<String> definition = new ArrayList<>();
        ArrayList<String> pinyin = new ArrayList<>();

        for (int cuatros = 0; cuatros < 1 + sharedPreferences.getInt("zou", 0); cuatros++) {

            for (int yi = (cuatros * 30); yi < ((cuatros + 1) * 30); yi++) {

                String[] defs = deffs[yi].split(":");

                character.add(defs[0]);
                definition.add(defs[1]);
                pinyin.add(defs[2]);

            }

        }

        seis = new Intent(this, ShareSocialCards.class);
        seis.putStringArrayListExtra("xharacter", character);
        seis.putStringArrayListExtra("xefinition", definition);
        seis.putStringArrayListExtra("xinyin", pinyin);

    }

    @Override
    public void onStart() {
        super.onStart();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (LangCardActivity.fang == 4) {

                    new MaterialAlertDialogBuilder(Lvelz.this)
                            .setMessage("Wooh! Ultimate practise reached")
                            .setPositiveButton("let's ultimate", new DialogInterface.OnClickListener() {//let's go
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    vie(zie);

                                }
                            })
                            .show();

                }

            }
        }, 1000);

        fu = sharedPreferences.getInt("fu", 20);

        if (sharedPreferences.getInt("fu", 40) == 80) {

            fu = 20;

        }

        ririko = sharedPreferences.getInt("defenle", 0);

        cailiao.setText(String.valueOf(ririko));

        poof();

    }

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {

            super.onBackPressed();
            return;

        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click back button again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                doubleBackToExitPressedOnce = false;

            }
        }, 2000);

    }
}
