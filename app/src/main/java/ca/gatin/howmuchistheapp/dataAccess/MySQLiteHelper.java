package ca.gatin.howmuchistheapp.dataAccess;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author RGatin
 * @since 12-Oct-2015
 *
 *
 * TODO: Complete implementaion of this class
 *
 */
public class MySQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "todoapi.db";
    private static final int DATABASE_VERSION = 3;


    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
