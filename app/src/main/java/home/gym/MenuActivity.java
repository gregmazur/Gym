package home.gym;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import home.gym.Fragments.WeightRequest;
import home.gym.db.ProfileDataSource;
import home.gym.entity.Profile;

/**
 * Created by greg on 20.06.15.
 */
public class MenuActivity extends Activity {
    private float currentWeight;
    private int userId;
    private Profile profile;
    private Button customTraining;
    private Button readyTraining;
    private TextView targetWeight;
    private TextView actionToGetTarget;
    private TextView kilosToGetTarget;
    private ProfileDataSource profileDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);
        targetWeight = (TextView) findViewById(R.id.targetWeight);
        actionToGetTarget = (TextView) findViewById(R.id.weightAction);
        kilosToGetTarget = (TextView) findViewById(R.id.weightActionChangeKG);
        customTraining = (Button) findViewById(R.id.customTrainingTV);
        readyTraining = (Button) findViewById(R.id.trainingSolutionsTV);
        Intent intent = getIntent();
        currentWeight = intent.getFloatExtra(WeightRequest.CURRENT_WEIGHT, 0);
        Log.i("INFORMATION", "RECIEVED currentWeight " + currentWeight);
        userId = intent.getIntExtra("ID", 0);
        Log.i("INFORMATION", "RECIEVED ID " + userId);
        profileDataSource = new ProfileDataSource(this);
        profileDataSource.open();
        profile = profileDataSource.getProfileByID(userId);
        showWeightTarget();
        customTraining.setFocusable(true);
        readyTraining.setFocusable(true);
        readyTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });
        customTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO

            }
        });
    }

    private void showWeightTarget() {
        targetWeight.setText(String.valueOf(profile.getIdealWeight()));
        if (gainWeight() > 0) {
            actionToGetTarget.setTextColor(-16711936);
            actionToGetTarget.setText(getResources().getString(R.string.you_should_gain));
            kilosToGetTarget.setText(String.valueOf(gainWeight()));
        } else {
            actionToGetTarget.setTextColor(-65536);
            actionToGetTarget.setText(getResources().getString(R.string.you_should_lose));
            kilosToGetTarget.setText(String.valueOf(Math.abs(gainWeight())));
        }

    }

    private float gainWeight() {
        return profile.getIdealWeight() - currentWeight;
    }


}
