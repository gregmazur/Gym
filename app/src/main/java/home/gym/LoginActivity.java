package home.gym;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by greg on 18.06.15.
 */
public class LoginActivity extends Activity implements AbsListView.OnScrollListener {

    private static final int MAX_ROWS = 50;
    private int lastTopValue = 0;

    private List<String> modelList = new ArrayList<>();
    private ListView listView;
    private ImageView backgroundImage;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        listView = (ListView) findViewById(R.id.list);

        for (int i = 0; i < 4; i++) {
            modelList.add("List item " + i);
        }

        adapter = new ArrayAdapter(this, R.layout.users_list, modelList);
        listView.setAdapter(adapter);

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
}
