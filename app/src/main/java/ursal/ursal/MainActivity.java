package ursal.ursal;

import android.app.Activity;
import android.app.RemoteAction;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    MediaPlayer ursalMP;
    Button btnUrsal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ursalMP = MediaPlayer.create(this, R.raw.hino);
        ursalMP.start();

        btnUrsal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), Main2Activity.class);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        ursalMP.stop();
        ursalMP.release();

    }
}
