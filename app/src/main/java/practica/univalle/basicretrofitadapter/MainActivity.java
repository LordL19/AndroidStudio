package practica.univalle.basicretrofitadapter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goThreatHandler(View v) {
        startActivity(new Intent(MainActivity.this,SimpleThreadExampleActivity.class));
    }

    public void goHandlerExample(View v) {
        startActivity(new Intent(MainActivity.this, HandlerExampleActivity.class));
    }

    public void goFactorialActivity(View v) {
        startActivity(new Intent(MainActivity.this, FactorialActivity.class));
    }

    public void goHorseRaceActivity(View v) {
        startActivity(new Intent(MainActivity.this, HorseRaceActivity.class));
    }
}
