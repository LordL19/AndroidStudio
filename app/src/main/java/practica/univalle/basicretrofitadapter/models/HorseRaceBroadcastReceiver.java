package practica.univalle.basicretrofitadapter.models;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class HorseRaceBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String winnerName = intent.getStringExtra("winnerName");
        Toast.makeText(context, "El caballo ganador es: " + winnerName, Toast.LENGTH_LONG).show();
    }
}
