package ursal.ursal;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Button questionario;
    Button fascista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
                toast("FASCISTA");
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

    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
    }
}
