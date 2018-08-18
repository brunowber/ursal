package ursal.ursal;

import android.app.Activity;
import android.app.RemoteAction;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends Activity {

    MediaPlayer ursalMP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ursalMP = MediaPlayer.create(this, R.raw.hino);
        ursalMP.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ursalMP.stop();
        ursalMP.release();

    }
}
