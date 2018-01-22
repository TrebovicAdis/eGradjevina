package hci_gradjevinski.materijali;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Developer on 04.06.2017..
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(hci_gradjevinski.materijali.R.layout.activity_main);

            Intent intent = new Intent(MainActivity.this, NavigationDrawerActivity.class);
            startActivity(intent);
finish();
       }


    }

