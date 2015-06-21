package home.gym.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import home.gym.MainActivity;
import home.gym.R;
import home.gym.db.ProfileDataSource;
import home.gym.entity.Profile;

/**
 * Created by greg on 18.06.15.
 */
public class Login extends Fragment {
    private ListView listView;
    private ArrayAdapter adapter;
    private ProfileDataSource dataSource;
    private List<Profile> users;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.login_layout,container,true);
        dataSource = new ProfileDataSource(getActivity());
        dataSource.open();
        users = dataSource.getAllProfiles();
        listView = (ListView)view.findViewById(R.id.usersContainer);
        adapter = new ArrayAdapter(getActivity(), R.layout.list_items, users);
        listView.setAdapter(adapter);
        listView.setClickable(true);
        listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("OnItemSelected","callled++++++++");
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }

}
