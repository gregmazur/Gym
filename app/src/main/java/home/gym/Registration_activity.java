package home.gym;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import home.gym.db.ProfileDataSource;
import home.gym.entity.Profile;

/**
 * Created by greg on 18.06.15.
 */
public class Registration_activity extends Activity {
    EditText name;
    EditText height;
    EditText wrist;
    EditText age;
    EditText weight;
    RadioGroup sex;
    RadioButton male, female;
    ProfileDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        name = (EditText) findViewById(R.id.new_name);
        height = (EditText) findViewById(R.id.new_height);
        wrist = (EditText) findViewById(R.id.wrist_girth);
        age = (EditText) findViewById(R.id.new_age);
        weight = (EditText) findViewById(R.id.weight_reg);
        sex = (RadioGroup) findViewById(R.id.radioGroup);
        male = (RadioButton) findViewById(R.id.maleBtn);
        female = (RadioButton) findViewById(R.id.femaleBtn);
        dataSource = new ProfileDataSource(this);
        dataSource.open();
    }

    public void submit(View view) {
        try {
            Profile profile = new Profile(name.getText().toString().toUpperCase(), Integer.parseInt(wrist.getText().toString()),
                    Integer.parseInt(height.getText().toString()), true, Integer.parseInt(age.getText().toString()),
                    Float.parseFloat(weight.getText().toString()));
            long userId = dataSource.createProfile(profile);
            if (userId > 0) {
                Log.i("profile on registration", "created");
                Toast.makeText(this, getResources().getString(R.string.congratulations_profile_created), Toast.LENGTH_SHORT).show();
                Log.i("profile on registration", "in DB");
                Intent intent = new Intent(this,MainActivity.class);
                intent.putExtra("id", userId);
                startActivity(intent);
            }
        } catch (SQLiteConstraintException e) {
            Toast.makeText(this, getResources().getString(R.string.existed_name_error), Toast.LENGTH_LONG).show();
        } catch (NumberFormatException e) {
            Toast.makeText(this, getResources().getString(R.string.empty_field_error), Toast.LENGTH_LONG).show();
        }


    }
}
