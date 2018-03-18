package mx.unam.aragon.fes.diplo.practica08alert;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_second);

        Intent i = getIntent();
        TextView tv = new TextView(this);
        tv.setTextColor(Color.BLUE);
        tv.setTextSize(18);
        tv.setText(i.getStringExtra(MainActivity.NOTIFICACION));
        setContentView(tv);
    }
}
