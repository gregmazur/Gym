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
    public static final String KEY_USER_CURRENT_WEIGHT = "user_current_weight";

    private static final String DATABASE_NAME = "gymbo.db";
    private static final int DATABASE_VERSION = 4;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_PROFILES + "(" + KEY_ID
            + " integer primary key autoincrement, " + KEY_USER_NAME
            + " text not null unique, " + KEY_USER_TYPE + " text, " + KEY_USER_SEX + " integer not null, "
            + KEY_USER_IDEAL_WEIGHT + " real not null, " + KEY_USER_CURRENT_WEIGHT + " real not null, "
            + KEY_CREATED_AT + " text);";

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
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILES);
        onCreate(db);
    }

}
