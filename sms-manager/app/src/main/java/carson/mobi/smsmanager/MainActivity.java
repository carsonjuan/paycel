package carson.mobi.smsmanager;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import carson.mobi.smsmanager.sms.Manager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;

    TextView textLog;
    EditText editIpAddress;
    EditText editPort;

    Button buttonIniciar;
    Button buttonParar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textLog = (TextView)findViewById(R.id.text_log);
        textLog.setMovementMethod(new ScrollingMovementMethod());

        editIpAddress = (EditText) findViewById(R.id.edit_ip_address);
        editPort = (EditText) findViewById(R.id.edit_port);

        buttonIniciar = (Button) findViewById(R.id.button_iniciar);
        buttonParar = (Button) findViewById(R.id.button_parar);

        buttonIniciar.setOnClickListener(this);
        buttonParar.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        pedirPermisos();

        switch(view.getId()){
            case R.id.button_iniciar:
                log("Server iniciado");
                //log(Manager.enviarSms("1168808752", "Prueba"));
                break;

            case R.id.button_parar:
                log("Server detenido");
                break;
        }
    }

    private void log(String mensaje){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        System.out.println( sdf.format(cal.getTime()) );

        textLog.append(sdf.format(cal.getTime()) + " - " + mensaje + "\n");
    }

    private void pedirPermisos(){
        final List<String> permissionsList = new ArrayList<String>();
        if (checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(Manifest.permission.SEND_SMS);
        }
        if (checkSelfPermission(Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(Manifest.permission.RECEIVE_SMS);
        }

        if (permissionsList.size() > 0) {
            requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
        }
    }
}
