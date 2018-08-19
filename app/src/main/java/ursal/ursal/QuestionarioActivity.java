package ursal.ursal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class QuestionarioActivity extends AppCompatActivity {
    Button btn_questionário;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionario);

        btn_questionário = findViewById(R.id.btn_responder_questionario);

        btn_questionário.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), TelaUsuarioActivity.class);
                startActivity(i);
            }
        });


    }
}
