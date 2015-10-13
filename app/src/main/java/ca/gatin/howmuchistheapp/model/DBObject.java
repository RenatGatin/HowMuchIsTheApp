package ca.gatin.howmuchistheapp.model;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * @author RGatin
 * @since 12-Oct-2015
 */
public abstract class DBObject implements Cloneable{
    protected long id;
    protected String tableName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public DBObject(String tableName){
        this.id = 0;
        this.tableName = tableName;
    }

    public abstract ContentValues getContentValues();
    public abstract String[] getColumnNames();
    public abstract String getWhereFields();
    public abstract String[] getWhereValues();
    public abstract void fillFromCursor(Cursor cursor);

    public Object clone()throws CloneNotSupportedException{
        return super.clone();
    }
}
