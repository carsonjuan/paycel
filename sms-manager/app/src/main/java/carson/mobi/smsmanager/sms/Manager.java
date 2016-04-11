package carson.mobi.smsmanager.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * Created by JuanIgnacio on 10/04/2016.
 */
public class Manager extends BroadcastReceiver {

    final SmsManager smsManager = SmsManager.getDefault();

    public void enviarSms(String numero, String mensaje) {

        smsManager.sendTextMessage(numero, null, mensaje, null, null);
        Log.i("sms.Manager", "SMS enviado a: " + numero + " Contenido: " + mensaje);
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equalsIgnoreCase("android.provider.Telephony.SMS_RECEIVED")) {
            final Bundle bundle = intent.getExtras();

            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (int i = 0; i < pdusObj.length; i++) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    String message = currentMessage.getDisplayMessageBody();

                    Log.i("sms.Manager", "name: " + phoneNumber + "; Message: " + message);

                }
            }

        }
    }
}