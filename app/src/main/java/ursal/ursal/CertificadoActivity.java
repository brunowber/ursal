package ursal.ursal;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class CertificadoActivity extends AppCompatActivity {

    Image image;
    Canvas canvas;
    Button btn_gerar_certificado;
    Uri imageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificado);

        ActivityCompat.requestPermissions(CertificadoActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);


        btn_gerar_certificado = findViewById(R.id.btn_gerar_certificado);

        btn_gerar_certificado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shareWhatsApp();
            }
        });
    }
    public void toast(String msg){
        Context context = getApplicationContext();
        CharSequence text = msg;
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void shareWhatsApp()
    {
        Intent whatsappIntent = new Intent();
        whatsappIntent.setAction(Intent.ACTION_SEND);
        whatsappIntent.setPackage("com.whatsapp");

        Uri bmpUri = FileProvider.getUriForFile(CertificadoActivity.this, BuildConfig.APPLICATION_ID + ".provider", createImageFile());

        whatsappIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
        whatsappIntent.setType("image/*");
        whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        whatsappIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            toast("Whatsapp não está instalado.");
        }
    }

    public File createImageFile(){
        Bitmap bm = BitmapFactory.decodeResource( getResources(), R.drawable.certificado_final);
        File path = Environment.getExternalStorageDirectory();
        File dir = new File(path+"/save/");
        dir.mkdir();

        Bitmap mutableBitmap = bm.copy(Bitmap.Config.ARGB_8888, true);

        canvas = new Canvas(mutableBitmap);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(45);
        paint.setTypeface(Typeface.create("Arial", Typeface.ITALIC));
        canvas.drawText("Bruno Weber", 500, 465, paint);
        canvas.drawText("Comunista do Mal", 240, 540, paint);

        File file =  new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "certificado.png");
        OutputStream out;
        try{
            out = new FileOutputStream(file);
            mutableBitmap.compress(Bitmap.CompressFormat.PNG,100,out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}