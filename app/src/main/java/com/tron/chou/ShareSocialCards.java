package com.tron.chou;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Arrays;

public class ShareSocialCards extends AppCompatActivity {

    ArrayList<String> xharacter;
    ArrayList<String> xefinition;
    ArrayList<String> xinyin;
    static int weizhi;
    InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_social_cards);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                mInterstitialAd = new InterstitialAd(ShareSocialCards.this);
                mInterstitialAd.setAdUnitId("ca-app-pub-7294079480685910/2509415283");
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });

        xharacter = getIntent().getStringArrayListExtra("xharacter");
        xefinition = getIntent().getStringArrayListExtra("xefinition");
        xinyin = getIntent().getStringArrayListExtra("xinyin");

        final CustomAdapter customAdapter = new CustomAdapter(this, xharacter, xinyin, xefinition);

        final GridView buju = findViewById(R.id.social_share_cards);

        buju.setAdapter(customAdapter);

        buju.setSelection(weizhi);
        buju.requestFocusFromTouch();

        SearchView searchPointBegin = findViewById(R.id.searchPointBegin);
        searchPointBegin.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                buju.requestFocus();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                for (String xef: xefinition) {

                    if (xef.contains(newText)) {

                        buju.setSelection(xefinition.indexOf(xef));

                        Log.d("freya", "onQueryTextChange: " + xefinition.indexOf(xef));

                    }

                }

                return false;
            }
        });

    }

    class CustomAdapter extends ArrayAdapter<String> {

        private final Context context;
        private final ArrayList<String> xharacter;
        private final ArrayList<String> xinyin;
        private final ArrayList<String> xefinition;

        CustomAdapter(@NonNull Context context, ArrayList<String> xharacter, ArrayList<String> xinyin, ArrayList<String> xefiniton) {
            super(context, R.layout.tiny_tiny_share_cards, xharacter);

            this.context = context;
            this.xharacter = xharacter;
            this.xinyin = xinyin;
            this.xefinition = xefiniton;

        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            View xharacView;
            ViewHolder viewHolder;

            if (convertView == null) {

                LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                xharacView = layoutInflater.inflate(R.layout.tiny_tiny_share_cards, null, true);
                viewHolder = new ViewHolder(xharacView);
                xharacView.setTag(viewHolder);

            } else {

                xharacView = convertView;
                viewHolder = (ViewHolder) xharacView.getTag();

            }

            viewHolder.xharacText.setText(xharacter.get(position));
            viewHolder.xefText.setText(xinyin.get(position));
            viewHolder.vengyin.setText(xefinition.get(position));

            xharacView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(ShareSocialCards.this, LangCardActivity.class);
                    intent.putExtra("xharacter", xharacter.get(position));
                    intent.putExtra("xinyin", xinyin.get(position));
                    intent.putExtra("xefinition", xefinition.get(position));
                    startActivity(intent);

                }
            });

            xharacView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    weizhi = position;

                    return false;
                }
            });

            return xharacView;
        }
    }

    private static class ViewHolder {

        private final MaterialTextView xharacText;
        private final MaterialTextView xefText;
        private final MaterialTextView vengyin;

        private ViewHolder (View xharacView) {

            xharacText = xharacView.findViewById(R.id.zimu);
            xefText = xharacView.findViewById(R.id.shengyin);
            vengyin = xharacView.findViewById(R.id.vengyin);

        }

    }

    @Override
    public void onBackPressed() {

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

    }
}
