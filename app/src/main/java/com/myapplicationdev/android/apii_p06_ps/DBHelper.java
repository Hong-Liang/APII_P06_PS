package com.myapplicationdev.android.apii_p06_ps;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "P06.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_TASK = "tasks";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TASK = "task";
    private static final String COLUMN_DESC = "dec";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createNoteTableSql = "CREATE TABLE " + TABLE_TASK + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TASK + " TEXT,"
                + COLUMN_DESC + " TEXT ) ";
        db.execSQL(createNoteTableSql);
        Log.i("info", "created tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE " + TABLE_TASK + " ADD COLUMN module_name TEXT ");
    }


    public long insertTask(String task, String desc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK, task);
        values.put(COLUMN_DESC, desc);
        long result = db.insert(TABLE_TASK, null, values);
        db.close();
        if (result == -1){
            Log.d("DBHelper", "Insert failed");
        }
        else {
            Log.d("SQL Insert", "ID:" + result); //id returned, shouldn’t be -1
        }
        return result;
    }

    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> notes = new ArrayList<Task>();

        String selectQuery = "SELECT " + COLUMN_ID + ", "
                + COLUMN_TASK + ", "
                + COLUMN_DESC
                + " FROM " + TABLE_TASK;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String taskContent = cursor.getString(1);
                String descContent = cursor.getString(2);
                Task note = new Task(id, taskContent, descContent);
                notes.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return notes;
    }

    public int updateNote(Task data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK, data.getTask());
        values.put(COLUMN_DESC, data.getDesc());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TABLE_TASK, values, condition, args);
        db.close();
        return result;
    }

    public int deleteNote(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_TASK, condition, args);
        db.close();
        return result;
    }
}

