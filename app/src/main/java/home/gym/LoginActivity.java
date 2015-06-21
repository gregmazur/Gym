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
    public Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        listView = (ListView) findViewById(R.id.usersContainer);
        dataSource = new ProfileDataSource(this);
        dataSource.open();
        users = dataSource.getAllProfiles();
        adapter = new ArrayAdapter(this, R.layout.list_items, users);
        listView.setAdapter(adapter);
        listView.setClickable(true);
        listView.setFocusable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                showEditDialog();

                startActivity(intent);
            }
        });
    }


    public void registration(View view) {
        Intent intent = new Intent(this, Registration_activity.class);
        startActivity(intent);
    }

    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        WeightRequest weightRequest = new WeightRequest();
        weightRequest.show(fm, "fragment_edit_name");
    }

    @Override
    public void onFinishEditDialog(Float input) {
        intent.putExtra("WEIGHT", input);
    }
}
