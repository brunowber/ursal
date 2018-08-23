package projetoursal.recursal;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class LoginActivity extends AppCompatActivity {

    Button questionario;
    Button fascista;
    MediaPlayer fascistaMP;

    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        questionario = findViewById(R.id.btn_questionario);
        fascista = findViewById(R.id.btn_fascista);


        questionario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), QuestionarioActivity.class);
                startActivity(i);
            }
        });

        fascista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("FASCISTA NÉ CARA", R.raw.fascista_ne_cara);
            }
        });
    }

    public void toast(String msg, int som){
        fascistaMP = MediaPlayer.create(this, som);
        fascistaMP.start();
        Context context = getApplicationContext();
        CharSequence text = msg;
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        fascistaMP.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer fascistaMP) {
                fascistaMP.release();
            }
        });
    }

    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
    }
}
