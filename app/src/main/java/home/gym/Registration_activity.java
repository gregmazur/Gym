package home.gym;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;


import home.gym.db.ProfileDataSource;
import home.gym.entity.Profile;

/**
 * Created by greg on 18.06.15.
 */
public class Registration_activity extends Activity  {
    private EditText name;
    private EditText height;
    private EditText wrist;
    private EditText age;
    private EditText weight;
    private RadioButton male, female;
    private ProfileDataSource dataSource;
    private int buttonId;
    private boolean sex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        name = (EditText) findViewById(R.id.new_name);
        height = (EditText) findViewById(R.id.new_height);
        wrist = (EditText) findViewById(R.id.wrist_girth);
        age = (EditText) findViewById(R.id.new_age);
        weight = (EditText) findViewById(R.id.weight_reg);
        male = (RadioButton) findViewById(R.id.maleBtn);
        female = (RadioButton) findViewById(R.id.femaleBtn);
        dataSource = new ProfileDataSource(this);
        dataSource.open();
    }

    public void submit(View view) {
        try {
            if (buttonId > 0) {
                Profile profile = new Profile(name.getText().toString().toUpperCase(), Integer.parseInt(wrist.getText().toString()),
                        Integer.parseInt(height.getText().toString()), sex, Integer.parseInt(age.getText().toString()),
                        Float.parseFloat(weight.getText().toString()));
                long userId = dataSource.createProfile(profile);
                if (userId > 0) {
                    Log.i("profile on registration", "created");
                    Toast.makeText(this, getResources().getString(R.string.congratulations_profile_created), Toast.LENGTH_SHORT).show();
                    Log.i("profile on registration", "in DB");
                    Intent intent = new Intent(this, MenuActivity.class);
                    intent.putExtra("id", userId);
                    startActivity(intent);
                }
            } else {
                Toast.makeText(this, getResources().getString(R.string.unchecked_sex_button_message), Toast.LENGTH_LONG).show();
            }
        } catch (SQLiteConstraintException e) {
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
}
