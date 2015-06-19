package home.gym.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import home.gym.entity.BodyType;
import home.gym.entity.Profile;

public class DBAdapter {

    public static final boolean DEBUG = true;

    //Logcat TAG
    public static final String LOG_TAG = "DBAdapter";

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


    //Database Name
    public static final String DATABASE_NAME = "DB_Gym4";

    //Database Version
    public static final int DATABASE_VERSION = 4;// started at 1

    //Table names
    public static final String USER_TABLE = "tbl_profile";
    public static final String PERFORMANCE_TABLE = "tbl_performance";


    private static final String[] ALL_TABLES = {USER_TABLE};
    private static final String USER_CREATE = "create table tbl_profile ( _id integer primary key autoincrement,"
            + " user_name text not null);";

    private static DataBaseHelper DBHelper = null;

    protected DBAdapter() {

    }

    public static void init(Context context) {
        if (DBHelper == null) {
            if (DEBUG)
                Log.i("DBAdapter", context.toString());
            DBHelper = new DataBaseHelper(context);
        }
    }

    private static class DataBaseHelper extends SQLiteOpenHelper {
        public DataBaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            if (DEBUG)
                Log.i(LOG_TAG, "new create");
            try {
                db.execSQL(USER_CREATE);


            } catch (Exception exception) {
                if (DEBUG)
                    Log.i(LOG_TAG, "Exception onCreate() exception");
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (DEBUG)
                Log.w(LOG_TAG, "Upgrading database from version" + oldVersion
                        + "to" + newVersion + "...");

            for (String table : ALL_TABLES) {
                db.execSQL("DROP TABLE IF EXISTS " + table);
            }
            onCreate(db);
        }

    } // Inner class closed


    /**
     * ** Open database for insert,update,delete in syncronized manner ***
     */
    private static synchronized SQLiteDatabase open() throws SQLException {
        return DBHelper.getWritableDatabase();
    }


    /**
     * ******** Escape string for single quotes (Insert,Update) *******
     */
    private static String sqlEscapeString(String aString) {
        String aReturn = "";

        if (null != aString) {
            //aReturn = aString.replace(", );
            aReturn = DatabaseUtils.sqlEscapeString(aString);
            // Remove the enclosing single quotes ...
            aReturn = aReturn.substring(1, aReturn.length() - 1);
        }

        return aReturn;
    }


    private static String getCurrentTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    /**
     * Profile data functions
     */

    public static void addUserData(Profile profile) {

        // Open database for Read / Write

        final SQLiteDatabase db = open();

       try {
           String name = profile.getName();
           String type = profile.getBodyType().toString();
           int sex = profile.isManInt();
           String idealWeight = Float.toString(profile.getIdealWeight());
           String currentWeight = Float.toString(profile.getCurrentWeight());
           ContentValues cVal = new ContentValues();
           cVal.put(KEY_USER_NAME, name);
//           cVal.put(KEY_USER_TYPE, type);
//           cVal.put(KEY_USER_SEX, sex);
//           cVal.put(KEY_USER_IDEAL_WEIGHT, idealWeight);
//           cVal.put(KEY_USER_CURRENT_WEIGHT, currentWeight);
//           cVal.put(KEY_CREATED_AT, getCurrentTime());
           // Insert user values in database

           if (db.insert(USER_TABLE, null, cVal) <= 0) {
               throw new SQLException("Failed to insert row into " + USER_TABLE);
           }

           Log.i("addUserData", "worked");
       }finally {
           db.close(); // Closing database connection
       }
    }


//    // Updating single profile
//    public static int updateUserData(Profile profile) {
//
//        final SQLiteDatabase db = open();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_USER_NAME, profile.getName());
//        values.put(KEY_USER_EMAIL, profile.getEmail());
//
//        // updating row
//        return db.update(USER_TABLE, values, KEY_ID + " = ?",
//                new String[]{String.valueOf(profile.getID())});
//    }

    // Getting single contact
    public static Profile getProfile(String name) {
        final SQLiteDatabase db = open();
try {
    // Open database for Read / Write

    Cursor res =  db.rawQuery( "select * from tbl_profile where "+KEY_USER_NAME+" = ?", new String[] { name } );
    Profile profile = new Profile();
    profile.setName(res.getString(1));
//    Cursor cursor = db.query(USER_TABLE, new String[]{KEY_ID,
//                    KEY_USER_NAME, KEY_USER_TYPE, KEY_USER_SEX, KEY_USER_IDEAL_WEIGHT,
//                    KEY_USER_CURRENT_WEIGHT}, KEY_ID + "=?",
//            new String[]{String.valueOf(id)}, null, null, null, null);
//Log.i("cursor","initialized");
//
//    if (cursor != null)
//        cursor.moveToFirst();
//
////    Profile profile = new Profile(cursor.getString(1), BodyType.valueOf(cursor.getString(2)),
////            Boolean.getBoolean(cursor.getString(3)),Float.parseFloat(cursor.getString(4)),
////            Float.parseFloat(cursor.getString(5)));
//    Profile profile = new Profile();
//    profile.setId(Integer.parseInt(cursor.getString(0)));
//    profile.setName(cursor.getString(1));
//    profile.setBodyType(BodyType.ECTOMORPH);
//    profile.setIsMan(true);
//    profile.setIdealWeight(Float.parseFloat(cursor.getString(4)));
//    profile.setCurrentWeight(Float.parseFloat(cursor.getString(5)));
//    profile.setCreatedAt(cursor.getString(6));
    Log.i("DBAdapter","profile received from db");

    // return user data
    return profile;
}finally {
    db.close();
}

    }

//    // Getting All User data
//    public static List<UserData> getAllUserData() {
//
//        List<UserData> contactList = new ArrayList<UserData>();
//        // Select All Query
//        String selectQuery = "SELECT  * FROM " + USER_TABLE;
//
//
//        // Open database for Read / Write
//        final SQLiteDatabase db = open();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                UserData data = new UserData();
//                data.setID(Integer.parseInt(cursor.getString(0)));
//                data.setName(cursor.getString(1));
//                data.setEmail(cursor.getString(2));
//
//                // Adding contact to list
//                contactList.add(data);
//            } while (cursor.moveToNext());
//        }
//
//        // return user list
//        return contactList;
//    }
//
//
//    // Deleting single contact
//    public static void deleteUserData(UserData data) {
//        final SQLiteDatabase db = open();
//        db.delete(USER_TABLE, KEY_ID + " = ?",
//                new String[]{String.valueOf(data.getID())});
//        db.close();
//    }
//
//    // Getting dataCount

    public static int getUserDataCount() {

        final SQLiteDatabase db = open();

        String countQuery = "SELECT  * FROM " + USER_TABLE;
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}

