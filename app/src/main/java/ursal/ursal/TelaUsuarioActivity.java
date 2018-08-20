package ursal.ursal;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import model.Usuario;

public class TelaUsuarioActivity extends AppCompatActivity {

    Button btn_enviar;
    EditText nome;
    EditText guerrilheiro;
    Usuario user = new Usuario();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_usuario);

        nome = findViewById(R.id.edt_nome);
        guerrilheiro = findViewById(R.id.edt_guerrilheiro);
        btn_enviar = findViewById(R.id.btn_enviar);

        btn_enviar.setOnClickListener(new View.OnClickListener() {
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
        user.setNome(nomeF);
        user.setGuerrilheiro(guerrilheiroF);
        user.setEntrou(Calendar.getInstance().getTime());
        Intent i = new Intent(getBaseContext(), CertificadoActivity.class);
        startActivity(i);
    }


}
