package com.tron.chou;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;

import java.util.ArrayList;

public class jeikou {

    private Context context;
    static String shousi = null;
    static String zhayi = null;
    int fuyu = 0;
    static int guo = -1;
    static boolean zuo = false;

    public jeikou(Context context) {

        this.context = context;

    }

    ArrayList<String> zifu = LangCardActivity.za();

    @JavascriptInterface
    public String zi() {

        fuyu = LangCardActivity.fuyu;

        if (zuo) {

            guo++;

        }

        if (guo != -1) {

            fuyu = guo;

        }

        Log.d("guo", "zi: " + guo);

         //if (LangCardActivity.fuyu != 8) {
//
         //    fuyu = LangCardActivity.fuyu;
         //    //here fuyu is changing
//
         //}

        return zifu.get(fuyu);
    }

    public static void setShushi(String shushi) {

        shousi = shushi;
    }

    @JavascriptInterface
    public String getShushi() {

        return shousi;
    }

    @JavascriptInterface
    public static void zhayaiyo(String zhay) {

        zhayi = zhay;
    }

    public static String getZhayi() {

        return zhayi;
    }

    public static void setHuan(boolean hanguo) {

       zuo = hanguo;
    }

    public static void setGuo(int han) {

        guo = han;
    }

}
