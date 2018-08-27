package projetoursal.recursal;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class CertificadoActivity extends AppCompatActivity {
    Image image;
    Canvas canvas;
    Button btn_gerar_certificado_whatsapp;
    Button btn_gerar_certificado_facebook;
    Button btn_gerar_certificado_twitter;
    Button btn_delete;
    ImageView certificado;
    Button btn_voltar;
    DataBaseHelper db;
    MediaPlayer ursalMP;
    AdView mAdView;
    float xPos;
    float yPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificado);

        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-1328632532738670/5911855123");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        ursalMP = MediaPlayer.create(this, R.raw.hino);
        ursalMP.start();

        db = new DataBaseHelper(this);
        ActivityCompat.requestPermissions(CertificadoActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        createImageFile();

        btn_gerar_certificado_whatsapp = findViewById(R.id.btn_gerar_certificado_whatsapp);
        btn_gerar_certificado_facebook = findViewById(R.id.btn_gerar_certificado_facebook);
        btn_gerar_certificado_twitter = findViewById(R.id.btn_gerar_certificado_twitter);

        btn_delete = findViewById(R.id.btn_delete);

        certificado = findViewById(R.id.imageView);
        certificado.setImageBitmap(createBitmap());

        btn_voltar = findViewById(R.id.btn_voltar_usuario);

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.dropData();
                Intent inicio = new Intent(getBaseContext(), MainActivity.class);
                startActivity(inicio);
            }
        });

        btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent usuario = new Intent(getBaseContext(), TelaUsuarioActivity.class);
                startActivity(usuario);
            }
        });

        btn_gerar_certificado_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri bmpUri = FileProvider.getUriForFile(CertificadoActivity.this, BuildConfig.APPLICATION_ID + ".provider", createImageFile());
                String pacote = "com.whatsapp";
                String app = "Whatsapp";
                startIntent(pacote, app, bmpUri);
            }
        });

        btn_gerar_certificado_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri bmpUri = FileProvider.getUriForFile(CertificadoActivity.this, BuildConfig.APPLICATION_ID + ".provider", createImageFile());
                String pacote = "com.facebook.orca";
                String app = "Facebook";
                startIntent(pacote, app, bmpUri);
            }
        });

        btn_gerar_certificado_twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri bmpUri = FileProvider.getUriForFile(CertificadoActivity.this, BuildConfig.APPLICATION_ID + ".provider", createImageFile());
                String pacote = "com.twitter.android";
                String app = "Twitter";
                startIntent(pacote, app, bmpUri);
            }
        });
    }

    public Bitmap createBitmap(){
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.certificado_final);
        Bitmap mutableBitmap = bm.copy(Bitmap.Config.ARGB_8888, true);

        canvas = new Canvas(mutableBitmap);

        int width = canvas.getWidth();
        int height = canvas.getHeight();

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(40);
        Typeface tf =Typeface.createFromAsset(getAssets(),"fonts/Deutsch.ttf");
        paint.setTypeface(Typeface.create(tf, Typeface.ITALIC));

        Cursor name = db.getUserName();
        Cursor guerrilheiro = db.getUserGuerrilheiro();
        Cursor data = db.getUserData();

        String bufferN = getData(name);
        String bufferG = getData(guerrilheiro);
        String bufferD = getData(data);

        xPos = (float)((width / 2) - bufferN.length()*6);
        yPos = (float)((height / 2.2)) ;
        canvas.drawText(bufferN, xPos, yPos, paint);

        xPos = (float)((width / 3) - bufferG.length()*6);
        yPos = (float)((height / 1.85)) ;
        canvas.drawText(bufferG, xPos, yPos, paint);

        xPos = (float)(width / 8.4);
        yPos = (float)((height / 1.08)) ;
        canvas.drawText(bufferD, xPos, yPos, paint);

        return mutableBitmap;
    }

    public File createImageFile() {
        Bitmap mutableBitmap = createBitmap();
        File path = Environment.getExternalStorageDirectory();
        File dir = new File(path + "/save/");
        dir.mkdir();
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "certificado.png");
        OutputStream out;
        try {
            out = new FileOutputStream(file);
            mutableBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public String getData(Cursor data){
        StringBuffer buffer = new StringBuffer();
        while (data.moveToNext()) {
            buffer.append(data.getString(0));
        }
        return buffer.toString();
    }

    public void startIntent(String pacote, String app, Uri bmpUri){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setPackage(pacote);

        intent.putExtra(Intent.EXTRA_STREAM, bmpUri);
        intent.setType("image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            startActivity(intent);
        } catch (android.content.ActivityNotFoundException ex) {
            toast(app+ " não está instalado.");
        }
    }

    public void toast(String msg) {
        Context context = getApplicationContext();
        CharSequence text = msg;
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
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
