package com.lifestyle.application.exerciseclock.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lifestyle.application.exerciseclock.model.WorkOutPlan;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ppandey on 12/15/2015.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "exerciseclockdb.sqlite";
    public static final String EXERCISE_TABLE_NAME = "workOutHistory";
    public static final String EXERCISE_ID = "id";
    public static final String EXERCISE_SETS = "sets";
    public static final String EXERCISE_DURATION = "woduration";
    public static final String EXERCISE_REST_DURATION = "restduration";


    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "create table workOutHistory " +
                        "(id integer primary key autoincrement, sets text,woduration text,restduration text)"

        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS workOutHistory");
        onCreate(db);
    }

    public boolean addExerciseLog(String sets, String woduration, String restduration) {
        long result = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contantValues = new ContentValues();
        try {
            contantValues.put("sets", sets);
            contantValues.put("woduration", woduration);
            contantValues.put("restduration", restduration);
            result = db.insert("workOutHistory", null, contantValues);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.close();
        }

        return result > 0;
    }

    public ArrayList<WorkOutPlan> getWorkoutHistory() {
        ArrayList<WorkOutPlan> workouts = new ArrayList<WorkOutPlan>();

        try {

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("Select * from workOutHistory", null);

            if (cursor.moveToFirst()) {
                do {
                    WorkOutPlan oWorkout = new WorkOutPlan();

                    oWorkout.setSets(Integer.valueOf(cursor.getString(cursor.getColumnIndex("sets"))));
                    oWorkout.setCode1((cursor.getString(cursor.getColumnIndex("woduration"))));
                    oWorkout.setCode2((cursor.getString(cursor.getColumnIndex("restduration"))));

                    workouts.add(oWorkout);
                } while (cursor.moveToNext());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return workouts;
    }

}
