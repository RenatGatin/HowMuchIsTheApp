package ca.gatin.howmuchistheapp.controller;

import android.content.Context;

import java.util.List;

import ca.gatin.howmuchistheapp.dataAccess.DBObjectDataSource;
import ca.gatin.howmuchistheapp.model.Account;

/**
 * @author RGatin
 * @since 12-Oct-2015
 */
public class AccountController {
    private DBObjectDataSource<Account> dboUser;
    private List<Account> users;

    public AccountController(Context context){
        dboUser = new DBObjectDataSource<Account>(context);
    }

    public Account getUserByEmail(String email){
        Account filter = new Account();
        filter.setEmail(email);
        users = dboUser.getObjects(filter);
        if(users.size()> 0) {
            return users.get(0);
        }
        else {
            return null;
        }
    }

    public void saveAccount(String firstName, String lastName, String email){
        Account user = new Account();
        user.setEmail(email);
        List<Account> userList = dboUser.getObjects(user);
        if(userList.size() > 0) {
            user = userList.get(0);
        }
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);

        dboUser.insertUpdate(user);
    }

    public void deleteAccountByEmail(String email){
        Account filter = new Account();
        filter.setEmail(email);
        users = dboUser.getObjects(filter);
        if(users.size()> 0){
            Account user = users.get(0);
            dboUser.deleteObject(user);
        }
    }
}