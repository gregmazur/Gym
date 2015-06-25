package home.gym.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import home.gym.entity.BodyType;
import home.gym.entity.Profile;

public class ProfileDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {MySQLiteHelper.KEY_ID,
            MySQLiteHelper.KEY_USER_NAME, MySQLiteHelper.KEY_USER_TYPE, MySQLiteHelper.KEY_USER_SEX,
            MySQLiteHelper.KEY_USER_IDEAL_WEIGHT, MySQLiteHelper.KEY_CREATED_AT};

    public ProfileDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long createProfile(Profile profile) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.KEY_USER_NAME, profile.getName());
        values.put(MySQLiteHelper.KEY_USER_TYPE, profile.getBodyType().toString());
        values.put(MySQLiteHelper.KEY_USER_SEX, profile.isManInt());
        values.put(MySQLiteHelper.KEY_USER_IDEAL_WEIGHT, profile.getIdealWeight());
        values.put(MySQLiteHelper.KEY_CREATED_AT, MySQLiteHelper.getCurrentTime());

        return database.insert(MySQLiteHelper.TABLE_PROFILES, null,
                values);

    }

    public Profile getProfileByID(int id) {
        Cursor cursor = database.query(MySQLiteHelper.TABLE_PROFILES,
                allColumns, MySQLiteHelper.KEY_ID + " = " + id, null,
                null, null, null);
        cursor.moveToFirst();
        Profile profile = cursorToProfile(cursor);
        cursor.close();
        return profile;
    }


    public List<Profile> getAllProfiles() {
        List<Profile> profiles = new ArrayList<Profile>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_PROFILES,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Profile profile = cursorToProfile(cursor);
            profiles.add(profile);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return profiles;
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


    private Profile cursorToProfile(Cursor cursor) {
        Profile profile = new Profile();
        profile.setId(cursor.getInt(0));
        profile.setName(cursor.getString(1));
        profile.setBodyType(BodyType.valueOf(cursor.getString(2)));
        profile.setManFromInt(cursor.getInt(3));
        profile.setIdealWeight(cursor.getFloat(4));
        profile.setCreatedAt(cursor.getString(5));
        return profile;
    }
}

