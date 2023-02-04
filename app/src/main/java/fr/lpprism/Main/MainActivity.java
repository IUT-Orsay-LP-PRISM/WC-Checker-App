
package fr.lpprism.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import fr.lpprism.Main.Map.OpenMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        new Handler().postDelayed(() -> {
            final Intent mainIntent = new Intent(MainActivity.this, OpenMap.class);
            startActivity(mainIntent);
            finish();
        }, 4000);

    }

}

