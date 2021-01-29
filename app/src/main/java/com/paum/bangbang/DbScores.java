package com.paum.bangbang;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DbScores extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "BangBang.db";
    public static final String SCORES_TABLE_NAME = "scores";
    public static final String SCORES_COLUMN_ID = "id";
    public static final String SCORES_COLUMN_NAME = "name";
    public static final String SCORES_COLUMN_SCORE = "score";
    private SQLiteDatabase db;

    public DbScores(Context context) {
        super(context, DATABASE_NAME, null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS " + SCORES_TABLE_NAME +
                        "(" + SCORES_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + SCORES_COLUMN_NAME + " TEXT," + SCORES_COLUMN_SCORE + " INTEGER)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", SCORES_TABLE_NAME));
        onCreate(db);
    }

    public boolean insertScore(String name, Integer score) {
        if (this.db == null || this.db.isReadOnly()) {
            this.db = this.getWritableDatabase();
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(SCORES_COLUMN_NAME, name);
        contentValues.put(SCORES_COLUMN_SCORE, score);
        long row = db.insert(SCORES_TABLE_NAME, null, contentValues);
        return true;
    }

    @SuppressLint("DefaultLocale")
    public Cursor getData(int id) {
        if (this.db == null) {
            this.db = this.getReadableDatabase();
        }
        return db.rawQuery(String.format("select * from %s where %s=%d", SCORES_TABLE_NAME, SCORES_COLUMN_ID, id), null);
    }

    public int numberOfRows() {
        if (this.db == null) {
            this.db = this.getReadableDatabase();
        }
        return (int) DatabaseUtils.queryNumEntries(db, SCORES_TABLE_NAME);
    }

    public boolean updateScore(Integer id, String name, Integer score) {
        if (this.db == null || this.db.isReadOnly()) {
            this.db = this.getWritableDatabase();
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(SCORES_COLUMN_NAME, name);
        contentValues.put(SCORES_COLUMN_SCORE, score);
        db.update(SCORES_TABLE_NAME, contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public Integer deleteScore(Integer id) {
        if (this.db == null || this.db.isReadOnly()) {
            this.db = this.getWritableDatabase();
        }
        return db.delete(SCORES_TABLE_NAME,
                "id = ? ",
                new String[]{Integer.toString(id)});
    }

    public ArrayList<String> getAllScores() {
        if (this.db == null) {
            this.db = this.getReadableDatabase();
        }
        ArrayList<String> array_list = new ArrayList<>();
        @SuppressLint("Recycle") Cursor res = db.rawQuery(String.format("select * from %s", SCORES_TABLE_NAME), null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            array_list.add(res.getString(res.getColumnIndex(SCORES_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }

    /**
     * @param n defines how many scores are gonna be returned
     * @return n highest scores in all matches
     */
    public String getNBestPlayers(Integer n) {
        if (this.db == null) {
            this.db = this.getReadableDatabase();
        }
        if (!this.db.isReadOnly()) {
            this.db = this.getReadableDatabase();
        }
        StringBuilder scores = new StringBuilder();
        @SuppressLint({"Recycle", "DefaultLocale"}) Cursor res = db.rawQuery(String.format("select * from %s ORDER BY %s DESC LIMIT %d", SCORES_TABLE_NAME, SCORES_COLUMN_SCORE, n), null);
        res.moveToFirst();
        int place = 1;
        while (!res.isAfterLast()) {
            scores.append("Miejsce ").append(Integer.valueOf(place).toString()).append(". Gracz: ").append(res.getString(res.getColumnIndex(SCORES_COLUMN_NAME))).append(" z wynikiem ").append(Integer.valueOf(res.getInt(res.getColumnIndex(SCORES_COLUMN_SCORE))).toString()).append(" punktów. ");
            res.moveToNext();
            ++place;
        }
        return scores.toString();
    }

    public ArrayList<Integer> getNHighestScores(Integer n) {
        if (this.db == null) {
            this.db = this.getReadableDatabase();
        }
        if (!this.db.isReadOnly()) {
            this.db = this.getReadableDatabase();
        }
        ArrayList<Integer> scores = new ArrayList<>();
        @SuppressLint({"Recycle", "DefaultLocale"}) Cursor res = db.rawQuery(String.format("select %s from %s ORDER BY %s DESC LIMIT %d", SCORES_COLUMN_SCORE, SCORES_TABLE_NAME, SCORES_COLUMN_SCORE, n), null);
        res.moveToFirst();
        int place = 1;
        while (!res.isAfterLast()) {
            scores.add(res.getInt(res.getColumnIndex(SCORES_COLUMN_SCORE)));
            res.moveToNext();
            ++place;
        }
        return scores;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        db.close();
    }
}