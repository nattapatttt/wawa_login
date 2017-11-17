package wawa.labwawa;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper
{
    static final String DATABASE_NAME = "MyCompany.db3";
    static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String sql = "CREATE TABLE `Customers` (\n" +
                "\t`Id`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t`Name`\tTEXT NOT NULL,\n" +
                "\t`Surname`\tTEXT,\n" +
                "\t`Telephone`\tTEXT,\n" +
                "\t`Email`\tTEXT,\n" +
                "\t`Username`\tTEXT NOT NULL UNIQUE,\n" +
                "\t`Password`\tTEXT NOT NULL\n" +
                ");";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }

    public long insertCustomer(Customer customer)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Name", customer.Name);
        values.put("Surname", customer.surname);
        values.put("Telephone", customer.Telephone);
        values.put("Email", customer.Email);
        values.put("Username", customer.Username);
        values.put("Password", customer.Password);

        long id = db.insert("Customers", null, values);
        db.close();

        return id;
    }

    public boolean authenUser(String username, String password)
    {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "select count(*) from Customers where Username=? and Password=?";
        String[] selectArgs = { username, password };

        Cursor cursor = db.rawQuery(sql, selectArgs);
        cursor.moveToFirst();
        long count = cursor.getLong(0);
        cursor.close();
        db.close();

        return count > 0;
    }

    public List<Customer> getCustomers()
    {
        String sql = "select * from Customers";
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();

        List<Customer> customers = new ArrayList<>();
        while (!cursor.isAfterLast())
        {
            Customer customer = new Customer();
            customer.Id = cursor.getLong(0);
            customer.Name = cursor.getString(1);
            customer.surname = cursor.getString(2);
            customer.Telephone = cursor.getString(3);
            customer.Email = cursor.getString(4);
            customer.Username = cursor.getString(5);
            customer.Password = cursor.getString(6);
            customers.add(customer);

            cursor.moveToNext();
        }
        cursor.close();
        db.close();

        return  customers;
    }


}


