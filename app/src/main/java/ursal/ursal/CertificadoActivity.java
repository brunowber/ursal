package ursal.ursal;

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
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

public class CertificadoActivity extends AppCompatActivity {

    Image image;
    Canvas canvas;
    Button btn_gerar_certificado_whatsapp;
    Button btn_gerar_certificado_facebook;
    Button btn_gerar_certificado_twitter;
    ShareDialog shareDialog;
    ImageView certificado;
    Button btn_voltar;
    CallbackManager callbackManager;
    LoginManager manager;
    DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificado);

        db = new DataBaseHelper(this);
        ActivityCompat.requestPermissions(CertificadoActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        createImageFile();


        btn_gerar_certificado_whatsapp = findViewById(R.id.btn_gerar_certificado_whatsapp);
        btn_gerar_certificado_facebook = findViewById(R.id.btn_gerar_certificado_facebook);
        btn_gerar_certificado_twitter = findViewById(R.id.btn_gerar_certificado_twitter);

        certificado = findViewById(R.id.imageView);
        certificado.setImageBitmap(createBitmap());

        btn_voltar = findViewById(R.id.btn_voltar_usuario);

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
                shareWhatsApp(bmpUri);
            }
        });

        btn_gerar_certificado_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri bmpUri = FileProvider.getUriForFile(CertificadoActivity.this, BuildConfig.APPLICATION_ID + ".provider", createImageFile());
                //shareFacebook(bmpUri);
            }
        });

        btn_gerar_certificado_twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri bmpUri = FileProvider.getUriForFile(CertificadoActivity.this, BuildConfig.APPLICATION_ID + ".provider", createImageFile());
                shareTwitter(bmpUri);
            }
        });
    }

    public void shareWhatsApp(Uri bmpUri) {
        String pacote = "com.whatsapp";
        String app = "Whatsapp";
        startIntent(pacote, app, bmpUri );
    }

    public void shareTwitter(Uri bmpUri) {
        String pacote = "com.twitter.android";
        String app = "Twitter";
        startIntent(pacote, app, bmpUri );
    }

    public void shareFacebook(final Uri bmpUri) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        manager = LoginManager.getInstance();

        List<String> permissionsNeeds = Arrays.asList("publish_actions");
        manager.logInWithPublishPermissions(this, permissionsNeeds);
        manager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                SharePhoto photo = new SharePhoto.Builder()
                        .setImageUrl(bmpUri)
                        .setCaption("Caption is Important")
                        .build();
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();

                shareDialog.show(content);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    public Bitmap createBitmap(){
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.certificado_final);
        Bitmap mutableBitmap = bm.copy(Bitmap.Config.ARGB_8888, true);

        canvas = new Canvas(mutableBitmap);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(55);
        Typeface tf =Typeface.createFromAsset(getAssets(),"fonts/Deutsch.ttf");
        paint.setTypeface(Typeface.create(tf, Typeface.ITALIC));

        Cursor name = db.getUserName();
        Cursor guerrilheiro = db.getUserGuerrilheiro();
        Cursor data = db.getUserData();

        StringBuffer bufferN = getData(name);
        StringBuffer bufferG = getData(guerrilheiro);
        StringBuffer bufferD = getData(data);

        double xPos = (canvas.getWidth() / 3.1);
        double yPos = ((canvas.getHeight() / 2.2)) ;

        Log.e("debug", ""+xPos);
        Log.e("debug", ""+yPos);

        canvas.drawText("" + bufferN, (float) xPos, (float) yPos, paint);

        xPos = (canvas.getWidth() / 5.8);
        yPos = ((canvas.getHeight() / 1.85)) ;
        canvas.drawText("" + bufferG,(float) xPos, (float) yPos, paint);

        xPos = (canvas.getWidth() / 8);
        yPos = ((canvas.getHeight() / 1.1)) ;
        canvas.drawText("" + bufferD, (float) xPos, (float) yPos, paint);

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

    public StringBuffer getData(Cursor data){
        StringBuffer buffer = new StringBuffer();
        while (data.moveToNext()) {
            buffer.append(data.getString(0));
        }
        return buffer;
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
}
