package home.gym;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


import home.gym.db.ExercisesDataSource;
import home.gym.entity.Exercise;

/**
 * Created by greg on 25.06.15.
 */
public class Exercises_activity extends Activity {
    private List<Exercise> exercises;
    private ExercisesDataSource dataSource;
    private ListView listView;
    private ExerciseAdapter adapter;
    private EditText input;
    private String userName;
    private int userId;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercises_layout);
        Intent intent = getIntent();

        userId = intent.getIntExtra("ID", 0);
        userName = intent.getStringExtra("NAME");
        dataSource = new ExercisesDataSource(this);
        dataSource.open();
        exercises = dataSource.getAllExercises();
        listView = (ListView) findViewById(R.id.exercisesList);
        adapter = new ExerciseAdapter(this, exercises);
        View footerView = ((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_layout, null, false);
        listView.addFooterView(footerView);
        button = (Button) findViewById(R.id.button);
        button.setText(getResources().getString(R.string.add_exercise));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Exercise exercise = new Exercise();
                exercise.setName("DEMO");
                exercise.setDescription("sdfsdfsdsdf");
                exercise.setPrimaryMuscle("Primary muscle");
                exercise.setSecondaryMuscle("Secondary muscle");
                dataSource.createExercise(exercise, userName);
            }
        });
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView clickedView = (TextView) view;
            }
        });
        listView.setTextFilterEnabled(true);
        input = (EditText) findViewById(R.id.inputSearch);
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("Text ["+s+"] - Start ["+start+"] - Before ["+before+"] - Count ["+count+"]");
                if (count < before) {
                    // We're deleting char so we need to reset the adapter data
                    adapter.resetData();
                }

                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }


}
