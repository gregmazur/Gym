package home.gym;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

/**
 * Created by greg on 20.06.15.
 */
public class MainActivity extends FragmentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.main_layout);
        Intent intent = getIntent();
        Toast.makeText(this, (int) intent.getFloatExtra("WEIGHT",0),Toast.LENGTH_LONG);
    }
}
