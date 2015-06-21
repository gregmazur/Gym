package home.gym;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;


import java.util.List;

import home.gym.db.ProfileDataSource;
import home.gym.entity.Profile;


/**
 * Created by greg on 18.06.15.
 */
public class LoginActivity extends Activity implements AbsListView.OnScrollListener
         {

    private int lastTopValue = 0;

    private List<Profile> users;
    private ListView listView;
    private ImageView backgroundImage;
    private ArrayAdapter adapter;
    private ProfileDataSource dataSource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        listView = (ListView) findViewById(R.id.list);
        dataSource = new ProfileDataSource(this);
        dataSource.open();
        users = dataSource.getAllProfiles();


        adapter = new ArrayAdapter(this, R.layout.users_list, users);
        listView.setAdapter(adapter);
//        listView.setOnClickListener(new ListView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//
//                startActivity(intent);
//            }
//        });

        // inflate custom header_login and attach it to the list
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.header_login, listView, false);
        listView.addHeaderView(header, null, false);
        ViewGroup footer = (ViewGroup) inflater.inflate(R.layout.footer_login,listView,false);
        listView.addFooterView(footer,null,true);

        // we take the background image and button reference from the header_login
        backgroundImage = (ImageView) header.findViewById(R.id.listHeaderImage);
        listView.setOnScrollListener(this);

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        Rect rect = new Rect();
        backgroundImage.getLocalVisibleRect(rect);
        if (lastTopValue != rect.top) {
            lastTopValue = rect.top;
            backgroundImage.setY((float) (rect.top / 2.0));
        }
    }
    public void registration(View view){
        Intent intent = new Intent(this, Registration_activity.class);
        startActivity(intent);
    }



//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//Intent intent = new Intent(this,MainActivity.class);
//        startActivity(intent);
//    }
}
