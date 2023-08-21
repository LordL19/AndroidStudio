package practica.univalle.basicretrofitadapter.models;

import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.Locale;
import java.util.concurrent.CountDownLatch;

import practica.univalle.basicretrofitadapter.HorseRaceActivity;
import practica.univalle.basicretrofitadapter.R;

public class Horse implements Runnable {
    private final ImageView horseImage;
    public FrameLayout horsesLayout;
    private final TextView horseProgress;
    private static final int MAX_DISTANCE = 1000;
    private int horseImageId;
    private String imageName;
    private String name;
    private final TextView horseName;



    private boolean isRunning=true;
    private boolean finished = false;
    private HorseRaceActivity horseRaceActivity;



    public Horse(View view, String s, String name, HorseRaceActivity horseRaceActivity) {
        horseImage = view.findViewById(R.id.horseImage);
        horsesLayout = view.findViewById(R.id.racetrackLayout);
        horseProgress=view.findViewById(R.id.horseProgress);
        horseName = view.findViewById(R.id.horseName);
        this.imageName= s;
        this.horseImageId = view.getResources().getIdentifier(imageName, "drawable", view.getContext().getPackageName());
        horseImage.setImageResource(horseImageId);
        this.name = name;
        horseName.setText(name);
        this.horseRaceActivity = horseRaceActivity;

    }


    public boolean isFinished(){
        return getProgressPercentage() >=100;
    }
    public String getName(){
        return name;
    }

    public void stop() {
        isRunning=false;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public float getProgressPercentage(){
        FrameLayout.LayoutParams params = ( FrameLayout.LayoutParams) horseImage.getLayoutParams();
        float progress= (float) params.leftMargin / (horsesLayout.getWidth() - horseImage.getWidth());
        return progress * 100;
    }
    public void reset(){
        isRunning=true;
        horseImage.post(() -> {
            FrameLayout.LayoutParams params = ( FrameLayout.LayoutParams) horseImage.getLayoutParams();
            params.leftMargin = 0;
            horseImage.setLayoutParams(params);
        });
    }

    @Override
    public void run() {
        int distance = 0;
        while (distance < MAX_DISTANCE && isRunning) {
            try {
                Thread.sleep((long) (Math.random() * 200)); // Simula el tiempo que tarda el caballo en avanzar
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            distance += (int) (Math.random() * 10); // Simula la distancia que avanza el caballo
            int finalDistance = Math.min(distance, MAX_DISTANCE);
            float progress = (float) finalDistance / MAX_DISTANCE;
            horseImage.post(() -> {
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) horseImage.getLayoutParams();
                params.leftMargin = (int) (progress * (horsesLayout.getWidth() - horseImage.getWidth()));
                horseImage.setLayoutParams(params);
                horseProgress.setText(String.format(Locale.getDefault(), "%.0f%%", progress * 100));
            }); // Actualiza la posición de la imagen y el porcentaje de progreso

            if (distance >= MAX_DISTANCE) {
                setFinished(true);
                stop();
                if (horseRaceActivity.winner == null) {
                    horseRaceActivity.winner = this;
                    horseRaceActivity.stopAllHorsesExceptWinner(this);
                    Intent intent = new Intent("HORSE_RACE_FINISHED");
                    intent.putExtra("winnerName", getName());
                    LocalBroadcastManager.getInstance(horseRaceActivity).sendBroadcast(intent);
                }
            }
        }


    }
    private void setFinished(boolean finishedb) {
        this.finished = finished;
    }
}