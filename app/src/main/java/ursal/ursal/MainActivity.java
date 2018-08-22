package ursal.ursal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends Activity {

    DataBaseHelper db;
    MediaPlayer ursalMP;
    MediaPlayer fascistaMP;
    Button btnUrsal;
    Button buttonFascista;
    Button buttonFascista2;
    Random r = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DataBaseHelper(this);


        ursalMP = MediaPlayer.create(this, R.raw.hino);
        ursalMP.start();

        Cursor cursor = db.getAllData();
        if (cursor != null && (cursor.getCount() > 0)){
            Intent i = new Intent(getBaseContext(), CertificadoActivity.class);
            startActivity(i);
        }
        btnUrsal = findViewById(R.id.button_ursal);
        buttonFascista = findViewById(R.id.button3);
        buttonFascista2 = findViewById(R.id.button2);

        btnUrsal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(i);
            }
        });
        buttonFascista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int a = r.nextInt(4 - 1)+ 1;
                Log.e("debug",""+a);
                switch (a) {
                    case 1:
                        toast("FASCISTA!", R.raw.fascista);
                        break;
                    case 2:
                        toast("PARA FASCISTA!", R.raw.para_fascista);
                        break;
                    case 3:
                        toast("FASCISTA NÉ CARA!", R.raw.fascista_ne_cara);
                        break;
                }
            }
        });
        buttonFascista2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("PORCO CAPITALISTA!", R.raw.porco_capitalista);
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

    protected void onPause() {
        if (ursalMP.isPlaying()) {
            ursalMP.pause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ursalMP.start();
    }

}
