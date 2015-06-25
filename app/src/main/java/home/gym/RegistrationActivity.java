package home.gym;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;


import home.gym.Fragments.WeightRequest;
import home.gym.db.ProfileDataSource;
import home.gym.entity.Profile;

/**
 * Created by greg on 18.06.15.
 */
public class RegistrationActivity extends FragmentActivity implements WeightRequest.EditNameDialogListener  {
    private EditText name;
    private EditText height;
    private EditText wrist;
    private EditText age;
    private RadioButton male, female;
    private ProfileDataSource dataSource;
    private int buttonId;
    private boolean sex;
    private long userIdDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        name = (EditText) findViewById(R.id.new_name);
        height = (EditText) findViewById(R.id.new_height);
        wrist = (EditText) findViewById(R.id.wrist_girth);
        age = (EditText) findViewById(R.id.new_age);
        male = (RadioButton) findViewById(R.id.maleBtn);
        female = (RadioButton) findViewById(R.id.femaleBtn);
        dataSource = new ProfileDataSource(this);
        dataSource.open();
    }

    public void submit(View view) {
        try {
            if (buttonId > 0) {
                Log.i("INFORMATION", "BUTTON ID CHECK PASSED");
                Profile profile = new Profile(name.getText().toString().toUpperCase(), Integer.parseInt(wrist.getText().toString()),
                        Integer.parseInt(height.getText().toString()), sex, Integer.parseInt(age.getText().toString()));
                Log.i("INFORMATION", "PROFILE CREATED");
                userIdDB = dataSource.createProfile(profile);
                Log.i("INFORMATION", "created in DB");
                if (userIdDB > 0) {
                    Log.i("profile on registration", "in DB " + userIdDB);
                    showWeightRequestDialog();
                    Log.i("INFORMATION", "CALLING REQUEST DIALOG");
                }
            } else {
                Toast.makeText(this, getResources().getString(R.string.unchecked_sex_button_message), Toast.LENGTH_LONG).show();
            }
        } catch (SQLException e) {
            Toast.makeText(this, getResources().getString(R.string.existed_name_message), Toast.LENGTH_LONG).show();
        } catch (NumberFormatException e) {
            Toast.makeText(this, getResources().getString(R.string.empty_field_message), Toast.LENGTH_LONG).show();
        }
    }

    public void radioBtnClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        buttonId = view.getId();
        switch (buttonId) {
            case R.id.maleBtn:
                if (checked)
                    sex = true;
                break;
            case R.id.femaleBtn:
                if (checked)
                    sex = false;
                break;

        }
    }

    @Override
    public void onFinishEditDialog(float input) {
        Intent intent = new Intent(RegistrationActivity.this, MenuActivity.class);
        intent.putExtra(WeightRequest.CURRENT_WEIGHT, input);
        intent.putExtra("ID",(int)userIdDB);
        startActivity(intent);
    }
    private void showWeightRequestDialog() {
        Log.i("INFORMATION", "REQUEST DIALOG METHOD CALLED");
        FragmentManager fm = getSupportFragmentManager();
        Log.i("INFORMATION", "Fragment MAnager CREATED");
        WeightRequest weightRequest = new WeightRequest();
        Log.i("INFORMATION", "WEIGHT REQUEST DIALOG CREATED");
        weightRequest.show(fm, "fragment_edit_name");
        Log.i("INFORMATION", "WEIGHT REQUEST DIALOG SHOWING");
    }
}
