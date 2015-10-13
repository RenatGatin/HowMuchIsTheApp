package ca.gatin.howmuchistheapp.model;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author RGatin
 * @since 12-Oct-2015
 */
public class Account extends DBObject {

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public Account() {
        super("account");
    }

    public Account(String firstName, String lastName, String email, String password) {
        super("account");
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("first_name",firstName);
        contentValues.put("last_name",lastName);
        contentValues.put("email",email);


        return contentValues;
    }

    @Override
    public String[] getColumnNames() {
        return new String[]{"id","first_name","last_name","email"};
    }

    @Override
    public String getWhereFields() {
        String where = null;
        if (firstName != null)
            where = (where == null? "" : where + " and " ) + "first_name = ?";
        if (lastName != null)
            where = (where == null? "" : where + " and " ) + "last_name = ?";
        if (email != null)
            where = (where == null? "" : where + " and " ) + "email = ?";

        return where;
    }

    @Override
    public String[] getWhereValues() {
        List<String> values = new ArrayList<String>();

        if (firstName != null)
            values.add(firstName);
        if (lastName != null)
            values.add(lastName);
        if (email != null)
            values.add(email);

        String []array = null;
        if( values != null) {
            array = new String[values.size()];
            values.toArray(array);
        }

        return array;
    }

    @Override
    public void fillFromCursor(Cursor cursor) {
        id = cursor.getLong(0);
        firstName = cursor.getString(1);
        lastName = cursor.getString(2);
        email = cursor.getString(3);
    }
}
