package home.gym;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ListView;


import java.util.List;

import home.gym.Fragments.WeightRequest;
import home.gym.db.ProfileDataSource;
import home.gym.entity.Profile;


/**
 * Created by greg on 18.06.15.
 */
public class LoginActivity extends FragmentActivity implements WeightRequest.EditNameDialogListener {


    private List<Profile> users;
    private ListView listView;
    private ArrayAdapter adapter;
    private ProfileDataSource dataSource;
    private int profileId;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        listView = (ListView) findViewById(R.id.usersContainer);
        dataSource = new ProfileDataSource(this);
        dataSource.open();
        users = dataSource.getAllProfiles();
        adapter = new ArrayAdapter(this, R.layout.list_items, R.id.item, users);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Profile profile =(Profile)adapter.getItem(position);
                profileId = profile.getId();
                showWeightRequestDialog();
            }
        });
    }


    public void registration(View view) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);

    }

    private void showWeightRequestDialog() {
        FragmentManager fm = getSupportFragmentManager();
        WeightRequest weightRequest = new WeightRequest();
        weightRequest.show(fm, "fragment_edit_name");
    }

    @Override
    public void onFinishEditDialog(float input) {
        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
        intent.putExtra(WeightRequest.CURRENT_WEIGHT, input);
        intent.putExtra("ID",profileId);
        startActivity(intent);
    }
}
