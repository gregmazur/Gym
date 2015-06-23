package home.gym;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import home.gym.Fragments.WeightRequest;

/**
 * Created by greg on 20.06.15.
 */
public class MenuActivity extends Activity {
private TextView customTraining;
    private TextView readyTraining;
    String userName = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);
        customTraining = (TextView) findViewById(R.id.customTrainingTV);
        readyTraining = (TextView) findViewById(R.id.trainingSolutionsTV);
        customTraining.setFocusable(true);
        readyTraining.setFocusable(true);
        readyTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                Log.i("RECIEVED MESSAGE", intent.getStringExtra(WeightRequest.CURRENT_WEIGHT));

            }
        });
        customTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = getIntent().getExtras();


                if (extras != null) {
                    userName = extras.getString(WeightRequest.CURRENT_WEIGHT);
                    Log.i("RECIEVED MESSAGE", extras.getString(WeightRequest.CURRENT_WEIGHT));
                }else {
                    Log.i("ERROR","EXTRAS IS NULL");
                }

            }
        });
    }


}
