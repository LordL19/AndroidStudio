package practica.univalle.basicretrofitadapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import practica.univalle.basicretrofitadapter.models.Horse;
import practica.univalle.basicretrofitadapter.models.HorseRaceBroadcastReceiver;
import practica.univalle.basicretrofitadapter.models.OnHorseWinListener;

public class HorseRaceActivity extends AppCompatActivity {

    private final int NUM_CABALLOS = 10;

    private static final String[] HORSE_NAMES = {
            "(1)Invencible",
            "(2)Corsel de Shrek",
            "(3)Rocinante",
            "(4)Dominante",
            "(5)Valentin",
            "(6)Hidalgo",
            "(7)Perseo",
            "(8)Epiro",
            "(9)Catafracto",
            "(10)Raiven"
    };


    private Horse[] horses = new Horse[NUM_CABALLOS];
    public volatile  Horse winner = null;
    private HorseRaceBroadcastReceiver horseRaceBroadcastReceiver = new HorseRaceBroadcastReceiver();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horse_race);

        LinearLayout layout = findViewById(R.id.horsesLayout);
        for (int i = 0; i < NUM_CABALLOS; i++) {
            View horseView = getLayoutInflater().inflate(R.layout.horse_layout, layout, false);
            horses[i] = new Horse(horseView, "horse" + (i + 1), HORSE_NAMES[i], this);
            layout.addView(horseView);
        }

        Button startRaceButton = findViewById(R.id.startRaceButton);
        startRaceButton.setOnClickListener(v -> startRace());

        Button stopRaceButton = findViewById(R.id.stopRaceButton);
        stopRaceButton.setOnClickListener(v -> stopRace());

        Button stopHorseButton = findViewById(R.id.stopHorseButton);
        stopHorseButton.setOnClickListener(v -> stopSpecificHorse());

        LocalBroadcastManager.getInstance(this).registerReceiver(horseRaceBroadcastReceiver, new IntentFilter("HORSE_RACE_FINISHED"));
    }

    private void stopSpecificHorse() {
        EditText horseNumberInput = findViewById(R.id.horseNumberInput);
        String horseNumberText = horseNumberInput.getText().toString();
        if (!horseNumberText.isEmpty()) {
            int horseNumber = Integer.parseInt(horseNumberText) - 1;
            if (horseNumber >= 0 && horseNumber < NUM_CABALLOS) {
                horses[horseNumber].stop();
            } else {
                // Mostrar un mensaje de error si el número de caballo no es válido
                Toast.makeText(this, "Número de caballo no válido", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Mostrar un mensaje de error si el EditText está vacío
            Toast.makeText(this, "Por favor, ingrese un número de caballo", Toast.LENGTH_SHORT).show();
        }

    }

    private void stopRace() {
        for (Horse horse : horses) {
            horse.stop();
        }
    }

    private void startRace() {
        for (Horse horse : horses) {
            horse.reset();
            new Thread(horse).start();
        }
    }
}





