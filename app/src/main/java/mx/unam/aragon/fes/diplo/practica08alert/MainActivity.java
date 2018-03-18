package mx.unam.aragon.fes.diplo.practica08alert;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String[] datos = {"Toast", "Toast Personalizado", "SnackBar",
            "Alert Dialog", "Alert Dialog List", "Notification", "Big Text Notification",
            "Inbox Style Notification", "Picture Notification",
            "Progress Dialog", "Progress Dialog Notification", "Spinner"};

    final CharSequence[] colors={"Rojo","Negro","Azul","Naranja"};
    ArrayList<Integer> slist=new ArrayList();
    boolean icount[]=new boolean[colors.length];
    String msg ="";

    ListView listView;
    TextView texto;

    ProgressDialog progressDialog;
    private int progressStatus=0;
    private Handler handler=new Handler();

    public static final String NOTIFICACION= "NOTIFICACION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView=(ListView)findViewById(R.id.listView);
        texto=(TextView)findViewById(R.id.textView);

        listViewWork();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void listViewWork(){
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,datos);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Toast.makeText(getApplicationContext(), "Ejemplo de Toast", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        LayoutInflater inflater=getLayoutInflater();
                        View layout=  inflater.inflate(R.layout.toast_layout,
                                (ViewGroup)findViewById(R.id.toastLinearLayout));

                        TextView tv = new TextView(getApplicationContext());

                        tv.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        tv.setTextColor(Color.WHITE);
                        tv.setTextSize(30);
                        tv.setPadding(10,10,10,10);
                        tv.setText("Ejemplo de Toast");
                        Toast toast = new Toast(getApplicationContext());
                        toast.setGravity(Gravity.CENTER_VERTICAL,0, 0);
                        toast.setDuration(Toast.LENGTH_LONG);
                            //toast.setView(layout);
                        toast.setView(tv);
                        toast.show();
                      /*  Toast toast=new Toast(getApplicationContext());
                        toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(layout);
                        toast.show();*/
                        break;
                    case 2:
                        Snackbar.make(view, "Ejemplo de Snack Bar", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        break;
                    case 3:
                        showAlertDialog();
                        break;
                    case 4:
                        showAlertDialogList();
                        break;
                    case 5:
                        showNotification();
                        break;
                    case 6:
                        showNotificationBigText();
                        break;
                    case 7:
                        showNotificationInbox();
                        break;
                    case 8:
                        showNotificationPicture();
                        break;
                    case 9:
                        showProgressDialog();
                        break;
                    case 10:
                        showNotificationProgressDialog();
                        break;
                    case 11:
                        showSpinner();
                        break;

                }
            }
        });
    }

    public void showAlertDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("Ejemplo de Alert Dialog");
        dialog.setMessage("¿Deseas Salir?");
        dialog.setIcon(android.R.drawable.ic_dialog_alert);
        dialog.setCancelable(false);
        dialog.setNegativeButton("NO QUIERO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        dialog.setPositiveButton("SI QUIERO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface,int i) {
                        finish();
                    }
                });
        dialog.show();
    }

    public void showAlertDialogList(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("Eligir Colores")
                .setMultiChoiceItems(colors, icount,
                        new DialogInterface.OnMultiChoiceClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                        if(isChecked){
                                            slist.add(which);
                                        }else if(slist.contains(which)){
                                            slist.remove(Integer.valueOf(which));
                                        }
                                    }
                                })
                .setCancelable(false)
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                msg = "";
                                for (int i = 0; i < slist.size(); i++){
                                    msg = msg + "\n" + (i + 1) + " : " + colors[slist.get(i)];
                                }
                                Toast.makeText(getApplicationContext(), "Total " + slist.size()+
                                                "Items Seleccionados" + "\n" + msg, Toast.LENGTH_SHORT).show();
                            }
                        })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getApplicationContext(), "Nada Seleccionado",Toast.LENGTH_SHORT).show();
                            }
                        })
                .show();
    }

    public void showNotification(){
        int icono = R.drawable.ic_notification;
        CharSequence titulo = "Notificacion de Practica";
        CharSequence titulobar = "Barra de Notificacion";
        CharSequence texto = "Ejemplo de lanzamiento de notificacion Android";
        String txtoNotifica = "Saludos desde \n" + getResources().getString(R.string.app_name);
        Intent i = new Intent(getApplicationContext(), SecondActivity.class);
        i.putExtra(NOTIFICACION, txtoNotifica);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(),0, i, 0);
        Notification notification = new Notification.Builder(getApplicationContext())
                .setTicker(titulo)
                .setContentTitle(titulobar)
                .setContentText(texto)
                .setSmallIcon(icono)
                .setAutoCancel(true)
                .setContentIntent(pi)
                .build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notification.defaults |= Notification.DEFAULT_LIGHTS;
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(0, notification);
    }

    public void showNotificationBigText(){

    }

    public void showNotificationInbox(){

    }

    public void showNotificationPicture(){

    }

    public void showProgressDialog(){

    }

    public void showNotificationProgressDialog(){

    }

    public void showSpinner(){

    }


  /*  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
