package com.ismummy.roitech.task.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.ismummy.roitech.task.models.Task;

import java.util.ArrayList;

/**
 * Created by ISMUMMY on 9/29/2016.
 */

public class DBHandler extends SQLiteOpenHelper {
    private static final String DBNAME = "task";
    private static final int DBVERSION = 1;

    public DBHandler(Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTask(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS 'task'");
        onCreate(db);
    }


    private void createTask(SQLiteDatabase db) {
        String table = "CREATE TABLE IF NOT EXISTS task (" +
                "id int(11) PRIMARY KEY," +
                "title text NOT NULL," +
                "description text  NOT NULL," +
                "date varchar(30)  NOT NULL, " +
                "start timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ) ";
        db.execSQL(table);
    }


    public void addTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", task.getTitle());
        values.put("description", task.getDescription());
        values.put("start", task.getStart());
        values.put("date", task.getDate());
        db.insert("task", null, values);
        db.close();
    }


    public ArrayList<Task> getTask(String date) {
        ArrayList<Task> tasks = new ArrayList<>();

        String selectQuery = "SELECT * FROM task WHERE date = ? ORDER BY start Asc";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{date});
        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                task.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                task.setStart(cursor.getString(cursor.getColumnIndex("start")));
                tasks.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return tasks;
    }
}