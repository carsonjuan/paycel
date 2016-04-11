package carson.mobi.smsmanager.sms;

import android.telephony.SmsManager;

/**
 * Created by JuanIgnacio on 10/04/2016.
 */
public class Manager {
    public static String enviarSms(String numero, String mensaje){

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(numero, null, mensaje, null, null);
        return "enviarSms("+numero+", "+mensaje+")";
    }
}
