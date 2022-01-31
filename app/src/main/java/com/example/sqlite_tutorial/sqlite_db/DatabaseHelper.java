package com.example.sqlite_tutorial.sqlite_db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.sqlite_tutorial.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String FRIENDS_TABLE = "FRIENDS_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_AGE = "AGE";
    public static final String COLUMN_STATUS = "STATUS";


    public DatabaseHelper(@Nullable Context context) {
        super(context, "friends.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // ToDo: Check if exist -> return; else-. create & return
        String createTableStatement = "CREATE TABLE " + FRIENDS_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT, " + COLUMN_AGE + " INT, " + COLUMN_STATUS + " BOOL)";

        sqLiteDatabase.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // called if db version changed
    }

    public boolean addFriend(UserModel userModel){
        SQLiteDatabase db = this.getWritableDatabase();
        // set user`s data
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, userModel.getName());
        contentValues.put(COLUMN_AGE, userModel.getAge());
        contentValues.put(COLUMN_STATUS, userModel.isActive());
        // insert to db
        long insert = db.insert(FRIENDS_TABLE, null, contentValues);
        // close db
        db.close();
        if(insert < 0) return false;
        return true;
    }

    public List<UserModel> getAll(){
        List<UserModel> friendsList = new ArrayList<>();
        String queryTableStatement = "SELECT * FROM " + FRIENDS_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryTableStatement, null);
        if(cursor.moveToFirst()){
            // loop throw the cursor & create new user
            int id, age;
            String name;
            boolean active;
            do{
                id = cursor.getInt(0);
                name = cursor.getString(1);
                age = cursor.getInt(2);
                active = cursor.getInt(3) == 1 ? true: false;
                UserModel userModel = new UserModel(id, name, age, active);
                friendsList.add(userModel);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return friendsList;
    }
    public boolean deleteFriend(UserModel friend){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryTableStatement = "DELETE FROM " + FRIENDS_TABLE + " WHERE " + COLUMN_ID + " = " + friend.getId();
        Cursor cursor = db.rawQuery(queryTableStatement, null);
        if(cursor.moveToFirst()) return true;
        return false;
    }
}
