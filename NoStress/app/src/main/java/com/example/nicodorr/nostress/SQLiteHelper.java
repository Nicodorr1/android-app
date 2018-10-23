package com.example.nicodorr.nostress;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

/**
 * Created by nicodorr on 12/10/2018.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void queryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public void insertData(String name, String price, byte[] image){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "insert into food values (null, ?, ?, ?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, name);
        statement.bindString(2, price);
        statement.bindBlob(3, image);

        statement.executeInsert();
    }

    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
        //String ret = "select * from food";
    }

    public void updateData(String name, String price){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "update food set name = ?, price = ?, image = ? where id = 1";
        SQLiteStatement statement = database.compileStatement(sql);

        statement.bindString(1, name);
        statement.bindString(2, price);
       // statement.bindBlob(3, image);
        //statement.bindDouble(4, (double)id);

        statement.execute();
        database.close();
    }

    public void updatePic(byte[] image){
        SQLiteDatabase db = getWritableDatabase();
        String yesql = "update food set image = ? where id = 1";
        SQLiteStatement stmt = db.compileStatement(yesql);

        stmt.bindBlob(1, image);
        stmt.execute();
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
