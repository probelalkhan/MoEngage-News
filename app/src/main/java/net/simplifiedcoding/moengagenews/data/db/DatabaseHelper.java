package net.simplifiedcoding.moengagenews.data.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import net.simplifiedcoding.moengagenews.MyApplication;
import net.simplifiedcoding.moengagenews.data.models.OfflineArticle;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "NewsDatabase";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "articles";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_CONTENT = "content";

    public DatabaseHelper() {
        super(MyApplication.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (\n" +
                "    " + COLUMN_ID + " INTEGER NOT NULL CONSTRAINT articles_pk PRIMARY KEY AUTOINCREMENT,\n" +
                "    " + COLUMN_TITLE + " VARCHAR(200) NOT NULL,\n" +
                "    " + COLUMN_CONTENT + " TEXT NOT NULL\n" +
                ");";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

    public boolean addArticle(String title, String content) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, title);
        contentValues.put(COLUMN_CONTENT, content);
        SQLiteDatabase db = getWritableDatabase();
        return db.insert(TABLE_NAME, null, contentValues) != -1;
    }

    public List<OfflineArticle> getAllArticleTitles() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_ID + ", " + COLUMN_TITLE + " FROM " + TABLE_NAME, null);
        List<OfflineArticle> articles = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                articles.add(
                        new OfflineArticle(
                                cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)),
                                null
                        )
                );
            } while (cursor.moveToNext());
        }
        cursor.close();
        return articles;
    }

    public OfflineArticle getArticle(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE id=?", new String[]{String.valueOf(id)});
        OfflineArticle offlineArticle = null;
        if (cursor.moveToFirst()) {
            offlineArticle = new OfflineArticle(
                    cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_CONTENT))
            );
        }
        cursor.close();
        return offlineArticle;
    }

    public boolean deleteArticle(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)}) == 1;
    }

}
