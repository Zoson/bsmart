package com.koalitech.service.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zoson on 2015/6/17.
 */
public class DatabaseService extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "database";
    private static final int DATABASE_VISION = 1;
    public DatabaseService(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VISION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CreateTable.CHATLOG);
        db.execSQL(CreateTable.USER);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS "+USER_TABLE_NAME);
//        onCreate(db);
//
//        db.execSQL("DROP TABLE IF EXISTS "+BROMESSAGE_TABLE_NAME);
//        onCreate(db);
    }

    public long insert(long uid ,ContentValues contentValues, String table_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        long rowid = -1;
        rowid = db.insert(table_name,null,contentValues);
        db.close();
        return rowid;
    }

    public int delete(String table_name, String[] attr, String[] value) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(table_name,genWhereClause(attr,value),null);
        db.close();
        return rows;
    }
    public int deleteAll(String table_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete(table_name,null,null);
        db.close();
        return i;
    }

    public int update(IDataModel iDataModel,String table_name,String[] attr, String[] value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = iDataModel.getContentValues();
        int i = db.update(table_name, contentValues, genWhereClause(attr, value),null);
        db.close();
        return i;
    }


    public ArrayList<Map<String,String>> query(String table_name, String[] cols,String[] attr, String[] value) {
        String where = genWhereClause(attr,value);
        System.out.println("where is "+where);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(table_name, cols, where, null, null, null, null, null);
        int c = cursor.getCount();
        int cc = cursor.getColumnCount();
        cursor.moveToFirst();
        ArrayList<Map<String,String>> list_map = new ArrayList<Map<String, String>>();
        for (int i = 0;i<cursor.getCount();i++){
            Map<String,String> map = new HashMap<String, String>();
            for (int j=0;j<cursor.getColumnCount();j++){
                String col_name = cursor.getColumnName(j);
                String val = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(j)));
                map.put(col_name,val);
            }
            list_map.add(map);
            cursor.moveToNext();
        }
        db.close();
        return list_map;
    }
    protected String genWhereClause(String []attr,String[]value){
        String where = null;
        if (attr == null){
            return null;
        }
        int len = attr.length;
        where = "";
        for(int i =0;i<len;i++){
            where += attr[i] + "="+value[i] ;
            if (len>1&&i!=(len-1)){
                where += " and ";
            }
        }

        return where;
    }
    public interface IDataModel{
        public ContentValues getContentValues();
    }
}
