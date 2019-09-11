package com.khayyamapp.juveiran.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import com.khayyamapp.juveiran.data_model.Article;

import java.util.ArrayList;

public class ArticleDatabaseOpenHelper extends SQLiteOpenHelper {

    //SLQLite variables
    public static final String DB_NAME = "post.db";
    public static final String TABLE_NAME = "articles";


    //SQLite columns
    public static final String COL_ID = "id";
    public static final String COL_TITLE = "title";
    public static final String COL_IMAGE = "image";
    public static final String COL_VIEWS = "views";
    public static final String COL_DATE = "date";
    public static final String COL_ISVISITED = "is_visited";
    public static final String COL_ISFAVORITED = "is_favorited";
    public static final String COL_CATEGORY = "category";

    //sql codes
    private static final String SQL_COMMMAND = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( " +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
            COL_TITLE + " TEXT, " +
            COL_IMAGE + " TEXT, " +
            COL_VIEWS + " INTEGER , " +
            COL_DATE + " TEXT, " +
            COL_ISVISITED + " INTEGER DEFAULT 0 ," +
            COL_ISFAVORITED + " INTEGER DEFAULT 0 ," +
            COL_CATEGORY + " TEXT );";


    public ArticleDatabaseOpenHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_COMMMAND);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE  IF EXISTS " + TABLE_NAME);
    }

    public boolean addPost(Article article) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ID, article.getId());
        contentValues.put(COL_TITLE, article.getTitle());
        contentValues.put(COL_IMAGE, article.getImage());
        contentValues.put(COL_VIEWS, article.getViews());
        contentValues.put(COL_DATE, article.getDate());
        contentValues.put(COL_CATEGORY, article.getCategory());
        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();
        return result > 0;
    }

    public void addPosts(ArrayList<Article> articleArrayList, String category) {
        for (int i = 0; i < articleArrayList.size(); i++) {
            if (!checkPostExists(articleArrayList.get(i).getId())) {
                Article article = articleArrayList.get(i);
                article.setCategory(category);
                boolean result = addPost(article);
                Log.i("db_inserting", "addPosts: " + result);
            }
        }
        Log.i("db_test", "added ");
    }

    public ArrayList<Article> getAllPosts(String category) {
        ArrayList<Article> articles;
        articles = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " where " + COL_CATEGORY + "= '" + category + "'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Article article = new Article();
                article.setId(cursor.getInt(cursor.getColumnIndex(COL_ID)));
                article.setTitle(cursor.getString(cursor.getColumnIndex(COL_TITLE)));
                article.setImage(cursor.getString(cursor.getColumnIndex(COL_IMAGE)));
                article.setViews(cursor.getInt(cursor.getColumnIndex(COL_VIEWS)));
                article.setDate(cursor.getString(cursor.getColumnIndex(COL_DATE)));
                article.setCategory(cursor.getString(cursor.getColumnIndex(COL_CATEGORY)));
                article.setIsViewed(cursor.getInt(cursor.getColumnIndex(COL_ISVISITED)));
                article.setIsFavorite(cursor.getInt(cursor.getColumnIndex(COL_ISFAVORITED)));
                articles.add(article);
            }
        }
        cursor.close();
        sqLiteDatabase.close();
        return articles;
    }

    public boolean setArticleIsVisited(int id, int isVisited) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ISVISITED, isVisited);
        int queryResult = sqLiteDatabase.update(TABLE_NAME, contentValues, COL_ID + " =? ", new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
        return queryResult > 0;
    }

    public boolean setArticleIsFavorited(int id, int isFavorited) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ISFAVORITED, isFavorited);

        int queryResult = sqLiteDatabase.update(TABLE_NAME, contentValues, COL_ID + " = ? ", new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
        return queryResult > 0;
    }

    public boolean getArticleIsFavorited(String id) {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from articles where id = " + id, null);
        cursor.moveToFirst();
        int isFavorite = cursor.getInt(cursor.getColumnIndex(COL_ISFAVORITED));
        return isFavorite == 1;
    }

    public boolean checkPostExists(int id) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME
                + " WHERE " +
                COL_ID +
                " = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{String.valueOf(id)});
        return cursor.moveToFirst();
    }

}
