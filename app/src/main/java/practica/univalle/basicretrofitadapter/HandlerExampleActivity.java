package practica.univalle.basicretrofitadapter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

public class HandlerExampleActivity extends AppCompatActivity {
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_example);
        handler = new Handler(Looper.getMainLooper());
        startProcessWithHandler();
    }
    private void startProcessWithHandler() {
        runnable = new Runnable() {
            @Override
            public void run() {
                showToast("Trabajo en segundo plano completado.");
                handler.postDelayed(this, 5000);
            }
        };
        handler.post(runnable);
    }
    private void stopProcessWithHandler() {
        handler.removeCallbacks(runnable);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopProcessWithHandler();
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}