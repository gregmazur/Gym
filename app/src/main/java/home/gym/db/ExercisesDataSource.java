package home.gym.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import home.gym.entity.Exercise;

/**
 * Created by greg on 21.06.15.
 */
public class ExercisesDataSource {
    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {MySQLiteHelper.KEY_ID,
            MySQLiteHelper.KEY_EXERCISE_NAME, MySQLiteHelper.KEY_EXERCISE_PRI_MUSCLE, MySQLiteHelper.KEY_EXERCISE_SEC_MUSCLE,
            MySQLiteHelper.KEY_EXERCISE_DESCRIPTION, MySQLiteHelper.KEY_EXERCISE_PIC1, MySQLiteHelper.KEY_EXERCISE_PIC2,
            MySQLiteHelper.KEY_EXERCISE_PIC3,MySQLiteHelper.KEY_CREATED_AT,MySQLiteHelper.KEY_CREATED_BY,
            MySQLiteHelper.KEY_MODIFIED_AT,MySQLiteHelper.KEY_MODIFIED_BY};

    public ExercisesDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long createExercise(Exercise exercise, String userName) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.KEY_EXERCISE_NAME, exercise.getName());
        values.put(MySQLiteHelper.KEY_EXERCISE_PRI_MUSCLE, exercise.getPrimaryMuscle());
        values.put(MySQLiteHelper.KEY_EXERCISE_SEC_MUSCLE, exercise.getSecondaryMuscle());
        values.put(MySQLiteHelper.KEY_EXERCISE_DESCRIPTION, exercise.getDescription());
//        values.put(MySQLiteHelper.KEY_EXERCISE_PIC1, exercise.getImage1());
//        values.put(MySQLiteHelper.KEY_EXERCISE_PIC2, exercise.getImage2());
//        values.put(MySQLiteHelper.KEY_EXERCISE_PIC3, exercise.getImage3());
        values.put(MySQLiteHelper.KEY_CREATED_AT, MySQLiteHelper.getCurrentTime());
        values.put(MySQLiteHelper.KEY_CREATED_BY, userName);

        return database.insert(MySQLiteHelper.TABLE_EXERCISES, null,
                values);

    }

//    public Profile getProfileByID(int id) {
//        Cursor cursor = database.query(MySQLiteHelper.TABLE_PROFILES,
//                allColumns, MySQLiteHelper.KEY_ID + " = " + id, null,
//                null, null, null);
//        cursor.moveToFirst();
//        Profile profile = cursorToProfile(cursor);
//        cursor.close();
//        return profile;
//    }


    public List<Exercise> getAllExercises() {
        List<Exercise> exercises = new ArrayList<Exercise>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_EXERCISES,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Exercise exercise = cursorToExercise(cursor);
            exercises.add(exercise);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return exercises;
    }
//    public Profile getProfile(String name) {
//        Profile profile = new Profile();
//
//        Cursor cursor = database.query(MySQLiteHelper.TABLE_PROFILES,
//                allColumns, null, null, null, null, null);
//
//        cursor.moveToFirst();
//        profile = cursorToProfile(cursor);
//        // make sure to close the cursor
//        cursor.close();
//        return profile;
//    }


    private Exercise cursorToExercise(Cursor cursor) {
        Exercise exercise = new Exercise();
        exercise.setId(cursor.getInt(0));
        exercise.setName(cursor.getString(1));
        exercise.setPrimaryMuscle(cursor.getString(2));
        exercise.setSecondaryMuscle(cursor.getString(3));
        exercise.setDescription(cursor.getString(4));
//        exercise.setImage1(cursor.getBlob(5));
//        exercise.setImage2(cursor.getBlob(6));
//        exercise.setImage3(cursor.getBlob(7));
        exercise.setCreatedAt(cursor.getString(8));
        exercise.setCreatedBy(cursor.getString(9));
        exercise.setModifiedAt(cursor.getString(10));
        exercise.setModifiedBy(cursor.getString(11));
        return exercise;
    }
}
