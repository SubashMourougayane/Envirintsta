package bluepanther.envirinsta.Services;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Created by SUBASH.M on 11/18/2016.
 */

public class BootReciever extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("Service in BootReciever");//eeee
        context.startService(new Intent(context,MyService.class));
    }
}
