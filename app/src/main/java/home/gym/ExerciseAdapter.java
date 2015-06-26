package home.gym;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import home.gym.entity.Exercise;

/**
 * Created by greg on 25.06.15.
 */
public class ExerciseAdapter extends BaseAdapter implements Filterable {
    static class ExerciseHolder {
        public TextView name;
        public TextView priMuscle;
        public TextView secMuscle;
        public TextView description;
    }


    private List<Exercise> exercises;
    private Context context;
    private ExerciseFilter filter;
    private List<Exercise> originExercises;


    public ExerciseAdapter(Context context, List<Exercise> exercises) {
        this.context = context;
        this.originExercises = exercises;
        this.exercises = exercises;
    }

    @Override
    public int getCount() {
        return exercises.size();
    }

    @Override
    public Object getItem(int position) {
        return exercises.get(position);
    }

    @Override
    public long getItemId(int position) {
        return exercises.get(position).hashCode();
    }
    public void resetData() {
        exercises = originExercises;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ExerciseHolder holder = new ExerciseHolder();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_exercises, parent, false);
            TextView name = (TextView) v.findViewById(R.id.exercise);
            TextView description = (TextView) v.findViewById(R.id.exerciseDescription);
            TextView priMuscle = (TextView) v.findViewById(R.id.exerciseMuscle);
            TextView secMuscle = (TextView) v.findViewById(R.id.exerciseSecMuscle);
            holder.name = name;
            holder.description = description;
            holder.priMuscle = priMuscle;
            holder.secMuscle = secMuscle;
            v.setTag(holder);
        } else {
            holder = (ExerciseHolder) v.getTag();
        }


        Exercise exercise = exercises.get(position);

        holder.name.setText(exercise.getName());
        holder.description.setText(context.getResources().getString(R.string.description) + exercise.getDescription());
        holder.priMuscle.setText(context.getResources().getString(R.string.primary_muscle) + exercise.getPrimaryMuscle());
        holder.secMuscle.setText(context.getResources().getString(R.string.sec_muscle) + exercise.getSecondaryMuscle());


        return v;

    }

    @Override
    public Filter getFilter() {
        if(filter == null){
            filter = new ExerciseFilter();
        }
        return filter;
    }

    private class ExerciseFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint == null || constraint.length() == 0) {
                // No filter implemented we return all the list
                results.values = exercises;
                results.count = exercises.size();
            } else {
                // We perform filtering operation
                List<Exercise> exercisesFiltered = new ArrayList<Exercise>();

                for (Exercise exercise : ExerciseAdapter.this.exercises) {
                    if (exercise.getName().toUpperCase().startsWith(constraint.toString().toUpperCase()) ||
                            exercise.getPrimaryMuscle().toUpperCase().startsWith(constraint.toString().toUpperCase()))
                        exercisesFiltered.add(exercise);
                }

                results.values = exercisesFiltered;
                results.count = exercisesFiltered.size();

            }
            return results;
        }


        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            if (results.count == 0)
                notifyDataSetInvalidated();
            else {
                exercises = (List<Exercise>) results.values;
                notifyDataSetChanged();
            }


        }
    }


}

