package practica.univalle.basicretrofitadapter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FactorialActivity extends AppCompatActivity {
    private EditText numberEditText;
    private Button calculateButton;
    private TextView resultTextView;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factorial);

        numberEditText = findViewById(R.id.numberEditText);
        calculateButton = findViewById(R.id.calculateButton);
        resultTextView = findViewById(R.id.resultTextView);

        handler = new Handler(Looper.getMainLooper());

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numberStr = numberEditText.getText().toString();
                int number = Integer.parseInt(numberStr);
                FactorialCalculator factorialCalculator = new FactorialCalculator(number);
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                Future<Long> futureResult = executorService.submit(factorialCalculator);
                executorService.shutdown();
                handleResult(futureResult);
            }
        });
    }
    private void handleResult(Future<Long> futureResult) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    long factorialResult = futureResult.get();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            resultTextView.setText("El factorial es: " + factorialResult);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}