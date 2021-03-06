package unknown.hittsss.hitesh1bhutani.worldscountry_flagsquiz;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        getRating();
        loadAd();
    }

    private void loadAd() {
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                finish();
            }
        });
        requestNewInterstitial();
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    private void getRating(){
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if(sharedPreferences.getInt(getResources().getString(R.string.getRating), 0) == 1 || rating(sharedPreferences)){
            final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            alert.setTitle(R.string.rateUs);
            alert.setMessage(R.string.feedBack);
            alert.setCancelable(false);
            alert.setPositiveButton(R.string.rateNow, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    launchMarket();
                }
            });
            alert.setNegativeButton(R.string.rateLater, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            increaseRatingNumber(sharedPreferences);
            alert.show();
        } else {
            increaseRatingNumber(sharedPreferences);
        }
    }

    private void launchMarket() {
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, " unable to find market app", Toast.LENGTH_LONG).show();
        }
    }

    private void shareApp()
    {
        final String appPackageName = getPackageName();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out this cool World's Country - Flags Quiz at: https://play.google.com/store/apps/details?id=" + appPackageName);
        sendIntent.setType("text/plain");
        sendIntent.createChooser(sendIntent, "Choose client");
        startActivity(sendIntent);
    }

    private void increaseRatingNumber(SharedPreferences sharedPreferences){
        final int rating = sharedPreferences.getInt(getResources().getString(R.string.getRating), 0) + 1;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(rating == 7)
            editor.putInt(getResources().getString(R.string.getRating), 2);
        else
            editor.putInt(getResources().getString(R.string.getRating), rating);
        editor.apply();
        editor.commit();
    }

    private boolean rating(SharedPreferences sharedPreferences){
        final int rating = sharedPreferences.getInt(getResources().getString(R.string.getRating), 0);
        return rating == 5;
    }

    private void initialize() {
        final Button flagToCountry = (Button) findViewById(R.id.flagToCountry);
        final Button countryToFlag = (Button) findViewById(R.id.countryToFlag);
        final Button exit = (Button) findViewById(R.id.exit);
        final Button rapidFire = (Button) findViewById(R.id.rapidFire);
        final Button highScore = (Button) findViewById(R.id.highScores);
        highScore.setTypeface(Typeface.createFromAsset(getAssets(), "palatino-linotype.ttf"));
        rapidFire.setTypeface(Typeface.createFromAsset(getAssets(), "palatino-linotype.ttf"));
        exit.setTypeface(Typeface.createFromAsset(getAssets(), "palatino-linotype.ttf"));
        countryToFlag.setTypeface(Typeface.createFromAsset(getAssets(), "palatino-linotype.ttf"));
        flagToCountry.setTypeface(Typeface.createFromAsset(getAssets(), "palatino-linotype.ttf"));
        flagToCountry.setOnClickListener(this);
        countryToFlag.setOnClickListener(this);
        rapidFire.setOnClickListener(this);
        exit.setOnClickListener(this);
        highScore.setOnClickListener(this);
        final ImageView share = (ImageView) findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareApp();
            }
        });
        final ImageView rate = (ImageView) findViewById(R.id.rating);
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchMarket();
            }
        });
        final ImageView settings = (ImageView) findViewById(R.id.changeSettings);
        settings.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                showSettings();
            }
            
            private void showSettings(){
                final AlertDialog.Builder alertSettings = new AlertDialog.Builder(MainActivity.this);
                final LinearLayout fullLayout = new LinearLayout(MainActivity.this);
                final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                fullLayout.setLayoutParams(lp);
                 
                final LinearLayout soundLayout = new LinearLayout(MainActivity.this);
                soundLayout.setOrientation(LinearLayout.Vertical);
                soundLayout.setLayoutParams(lp);
                final RelativeLayout soundTextLayout = new RelativeLayout(MainActivity.this);
                final RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                soundTextLayout.setLayoutParams(rlp);
                final TextView sountText = new TextView(MainActivity.this);
                soundText.setText(R.string.sound);
                RelativeLayout.LayoutParams soundTextLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                soundTextLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                soundText.setLayoutParams(soundTextLayoutParams);
                soundTextLayout.addView(soundText);
                soundLayout.addView(soundTextLayout);
                final RelativeLayout radioButtonLayout = new RelativeLayout(MainActivity.this);
                radioButtonLayout.setLayoutParams(rlp);
                final RadioGroup rg = new RadioGroup(this); 
                rg.setLayoutParams(soundTextLayoutParams);
                rg.setOrientation(RadioGroup.HORIZONTAL);
                final RadioButton volume1 = new RadioButton(MainActivity.this);
                lang1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_volume_up_indicator, 0);
                final RadioButton volume2 = new RadioButton(MainActivity.this);
                lang2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_volume_off_indicator, 0);
                rg.addView(volume1);
                rg.addView(volume2);
                radioButtonLayout.addView(rg);
                soundLayout.addView(radioButtonLayout);
                fullLayout.addView(soundLayout);
                
                soundText.setText(R.string.language);
                fullLayout.addView(soundTextLayout);
                final RelativeLayout radioButtonLangLayout = new RelativeLayout(MainActivity.this);
                radioButtonLangLayout.setLayoutParams(rlp);
                final RadioGroup rg1 = new RadioGroup(this); 
                rg1.setLayoutParams(soundTextLayoutParams);
                rg1.setOrientation(RadioGroup.HORIZONTAL);
                final RadioButton lang1 = new RadioButton(MainActivity.this);
                lang1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_volume_up_indicator, 0);
                final RadioButton lang2 = new RadioButton(MainActivity.this);
                lang2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_volume_off_indicator, 0);
                rg1.addView(lang1);
                rg1.addView(lang2);
                radioButtonLangLayout.addView(rg);
                fullLayout.addView(radioButtonLangLayout);
                
                alertSettings.setPositiveButton(getResources().getString(R.string.save), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        
                    }
                });
                alertSettings.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        dialog.cancel();
                    }
                });
                alertSettings.setCancelable(false);
//                 final LinearLayout langLayout = new LinearLayout(MainActivtiy.this);
//                 langLayout.setOrientation(LinearLayout.Vertical);
//                 langLayout.setLayoutParams(lp);
//                 final RelativeLayout languageTextLayout = new RelativeLayout(MainActivity.this);
//                 languageTextLayout.setLayoutParams(rlp);
            }
        });
        final ImageView sound = (ImageView) findViewById(R.id.volume);
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        drawableChange(sound, sharedPreferences.getBoolean(getResources().getString(R.string.isVolumeEnabled), true));
        sound.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if(sharedPreferences.getBoolean(getResources().getString(R.string.isVolumeEnabled), true)){
                    editor.putBoolean(getResources().getString(R.string.isVolumeEnabled), false);
                    drawableChange(sound, false);
                }
                else {
                    editor.putBoolean(getResources().getString(R.string.isVolumeEnabled), true);
                    drawableChange(sound, true);
                }
                editor.apply();
            }
        });

    }

    private void drawableChange(ImageView sound, boolean indicator) {
        if(indicator)
            sound.setImageResource(R.drawable.ic_volume_up_indicator);
        else
            sound.setImageResource(R.drawable.ic_volume_off_indicator);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        final Intent newIntent;
        final Bundle bundle;
        switch (view.getId()){
            case R.id.flagToCountry:
                newIntent = new Intent(getApplicationContext(), ChooseOcean.class);
                bundle = new Bundle();
                bundle.putInt(getResources().getString(R.string.quizType),1);
                newIntent.putExtras(bundle);
                startActivity(newIntent);
                break;
            case R.id.countryToFlag:
                newIntent = new Intent(getApplicationContext(), ChooseOcean.class);
                bundle = new Bundle();
                bundle.putInt(getResources().getString(R.string.quizType),2);
                newIntent.putExtras(bundle);
                startActivity(newIntent);
                break;
            case R.id.rapidFire:
                newIntent = new Intent(getApplicationContext(), RapidFire.class);
                startActivity(newIntent);
                break;
            case R.id.highScores:
                newIntent = new Intent(getApplicationContext(), HighScore.class);
                startActivity(newIntent);
                break;
            case R.id.exit:
                if(mInterstitialAd!=null && mInterstitialAd.isLoaded()) mInterstitialAd.show();
                else finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(mInterstitialAd!=null && mInterstitialAd.isLoaded()) mInterstitialAd.show();
        else finish();
    }
}
