package projetoursal.recursal;

import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.Calendar;
import java.util.Date;

import model.Usuario;

public class TelaUsuarioActivity extends AppCompatActivity {

    Button btn_enviar;
    EditText nome;
    EditText guerrilheiro;
    Usuario user = new Usuario();
    DataBaseHelper db;

    AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_usuario);

        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        db = new DataBaseHelper(this);
        nome = findViewById(R.id.edt_nome);
        guerrilheiro = findViewById(R.id.edt_guerrilheiro);
        btn_enviar = findViewById(R.id.btn_enviar);

        btn_enviar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                criarUsuario();
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void criarUsuario(){
        String nomeF = nome.getText().toString();
        String guerrilheiroF = guerrilheiro.getText().toString();
        if (nomeF.matches("")) {
            toast("Você não escreveu seu nome!");
            return;
        }
        if (guerrilheiroF.matches("")) {
            toast("Você não escreveu seu codinome de guerrilheiro!");
            return;
        }

        Date date = Calendar.getInstance().getTime();

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

        user.setNome(nomeF);
        user.setGuerrilheiro(guerrilheiroF);
        user.setEntrou(format.format(date));
        boolean isInserted = db.insertData(user.getNome(), user.getGuerrilheiro(), user.getEntrou());
        if(isInserted) {
            Intent i = new Intent(getBaseContext(), CertificadoActivity.class);
            startActivity(i);
        }
        else
            toast("Falha ao gravar no banco");
    }


}
