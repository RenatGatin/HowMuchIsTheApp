package ca.gatin.howmuchistheapp.model;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RGatin
 * @since 12-Oct-2015
 */
public class Property extends DBObject {
    private String key;
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }



    public Property(String key, String value){
        super("app_properties");
        this.key = key;
        this.value = value;
    }

    public Property(){
        super("app_properties");
        this.key = null;
        this.value = null;
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("key",key);
        contentValues.put("value",value);

        return contentValues;
    }

    @Override
    public String[] getColumnNames() {
        return new String[]{"id","key","value"};
    }

    @Override
    public String getWhereFields() {
        String where = null;
        if (key != null)
            where = (where == null? "" : where + " and " ) + "key = ?";
        if (value != null)
            where = (where == null? "" : where + " and " ) + "value = ?";

        return where;
    }

    @Override
    public String[] getWhereValues() {
        List<String> values = new ArrayList<String>();

        if (key != null)
            values.add(key);
        if (value != null)
            values.add(value);

        return values.size () > 0? (String[])values.toArray() : null;
    }

    @Override
    public void fillFromCursor(Cursor cursor) {
        id = cursor.getLong(0);
        key = cursor.getString(1);
        value = cursor.getString(2);
    }
}
