package home.gym.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_PROFILES = "profiles";
    public static final String TABLE_EXERCISES = "exercises";

    //Base Fields
    public static final String KEY_ID = "_id";
    public static final String KEY_CREATED_AT = "created_at";
    public static final String KEY_CREATED_BY = "created_by";
    public static final String KEY_MODIFIED_AT = "modified_at";
    public static final String KEY_MODIFIED_BY = "modified_by";

    // Profile Table Fields

    public static final String KEY_USER_NAME = "user_name";
    public static final String KEY_USER_SEX = "user_sex";
    public static final String KEY_USER_TYPE = "user_type";
    public static final String KEY_USER_IDEAL_WEIGHT = "user_ideal_weight";

    //Exercise Table Fields

    public static final String KEY_EXERCISE_NAME = "exercise_name";
    public static final String KEY_EXERCISE_PRI_MUSCLE = "exercise_pri_muscle";
    public static final String KEY_EXERCISE_SEC_MUSCLE = "exercise_sec_muscle";
    public static final String KEY_EXERCISE_DESCRIPTION = "exercise_description";
    public static final String KEY_EXERCISE_PIC1 = "exercise_pic1";
    public static final String KEY_EXERCISE_PIC2 = "exercise_pic2";
    public static final String KEY_EXERCISE_PIC3 = "exercise_pic3";

    //Performance Table Fields
    public static final String KEY_USER_CURRENT_WEIGHT = "user_current_weight";

    private static final String DATABASE_NAME = "test.db";
    private static final int DATABASE_VERSION =2;

    // Database creation sql statement
    private static final String CREATE_PROFILES =
            "create table "
            + TABLE_PROFILES + "(" + KEY_ID
            + " integer primary key autoincrement, " + KEY_USER_NAME
            + " text not null unique, " + KEY_USER_TYPE + " text, " + KEY_USER_SEX + " integer not null, "
            + KEY_USER_IDEAL_WEIGHT + " real not null, " + KEY_CREATED_AT + " text);";
    private static final String CREATE_EXERCISES =  "create table "
            + TABLE_EXERCISES + "(" + KEY_ID
            + " integer primary key autoincrement, " + KEY_EXERCISE_NAME
            + " text not null unique, " + KEY_EXERCISE_PRI_MUSCLE + " text not null, " + KEY_EXERCISE_SEC_MUSCLE + " text, "
            + KEY_EXERCISE_DESCRIPTION + " text, " + KEY_EXERCISE_PIC1 + " blob, " + KEY_EXERCISE_PIC2 + " blob, "
            + KEY_EXERCISE_PIC3 + " blob, " + KEY_CREATED_AT + " text," + KEY_CREATED_BY + " text, " + KEY_MODIFIED_AT + " text,"
            + KEY_MODIFIED_BY + " text);";


    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static String getCurrentTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_PROFILES);
        database.execSQL(CREATE_EXERCISES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISES);
        onCreate(db);
    }

}
