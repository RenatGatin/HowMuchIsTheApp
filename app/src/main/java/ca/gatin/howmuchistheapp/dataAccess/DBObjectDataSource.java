package ca.gatin.howmuchistheapp.dataAccess;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ca.gatin.howmuchistheapp.model.DBObject;

/**
 * @author RGatin
 * @since 12-Oct-2015
 */
public class DBObjectDataSource<T extends DBObject> {
    // Database fields
    protected SQLiteDatabase database;
    protected MySQLiteHelper dbHelper;

    public DBObjectDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // This function only checks ID
    public synchronized T insertUpdate(T object) {
        try {
            open();
            if (object.getId() > 0) {
                database.update(object.getTableName(), object.getContentValues(), "id =" + object.getId(), null);
            }
            else {
                long insertId = database.insert(object.getTableName(), null,
                        object.getContentValues());
                Cursor cursor = database.query(object.getTableName(),
                        object.getColumnNames(), "id = " + insertId, null,
                        null, null, null);
                cursor.moveToFirst();
                object.fillFromCursor(cursor);
                cursor.close();
            }
            return object;
        }
        finally {
            close();
        }
    }

    public synchronized void deleteObject(T object) {
        long id = object.getId();
        try {
            open();
            database.delete(object.getTableName(), "id = " + id, null);
        }
        finally {
            close();
        }
    }

    public synchronized List<T> getObjects(T object) {
        List<T> objectList = new ArrayList<T>();
        try {
            open();
            Cursor cursor = database.query(object.getTableName(),
                    object.getColumnNames(), object.getWhereFields(), object.getWhereValues(), null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                object.fillFromCursor(cursor);
                try {
                    objectList.add((T) object.clone());
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                cursor.moveToNext();
            }
            // make sure to close the cursor
            cursor.close();
            return objectList;
        }
        finally {
            close();
        }
    }

    private String arrayToString(String[] array){
        if (array != null && array.length > 0) {
            StringBuilder nameBuilder = new StringBuilder();

            for (String n : array) {
                nameBuilder.append("'").append(n.replace("'", "\\'")).append("',");
                // can also do the following
                // nameBuilder.append("'").append(n.replace("'", "''")).append("',");
            }

            nameBuilder.deleteCharAt(nameBuilder.length() - 1);

            return nameBuilder.toString();
        } else {
            return "";
        }
    }
}

