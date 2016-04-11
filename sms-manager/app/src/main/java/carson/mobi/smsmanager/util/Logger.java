package carson.mobi.smsmanager.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Observable;

/**
 * Created by JuanIgnacio on 11/04/2016.
 */
public class Logger extends Observable {
    private static Logger instancia = null;
    String log = "";

    protected Logger() {
    }

    public static Logger obtenerInstancia() {
        if (instancia == null) {
            instancia = new Logger();
        }
        return instancia;
    }

    public String obtenerLog() {
        return log;
    }

    public void log(String mensaje) {
        String registro;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        registro = sdf.format(cal.getTime()) + " - " + mensaje + "\n";
        log = log + registro;
        setChanged();
        notifyObservers(registro);
    }


}
