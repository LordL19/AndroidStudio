package practica.univalle.basicretrofitadapter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

public class SimpleThreadExampleActivity extends AppCompatActivity {
    private TextView counterTextView;
    private int counterValue = 0;

    // Handler para actualizar la UI desde el hilo de fondo
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_thread_example);
        counterTextView = findViewById(R.id.textView);
        // Iniciar el contador en un hilo separado
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (counterValue < 100) {
                    counterValue++;
                    //counterTextView.setText("Contador: " + counterValue);
                    // Actualizar la interfaz de usuario desde el hilo principal usando el Handler
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            counterTextView.setText("Contador: " + counterValue);
                        }
                    });

                    try {
                        // Hacer que el hilo duerma durante un segundo
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}