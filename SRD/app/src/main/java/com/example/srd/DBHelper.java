package com.example.srd;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "SRD.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "EMPLOYEE";

    public static final String EMP_ID = "empid";
    public static final String EMP_NAME = "name";
    public static final String EMP_PHONE_NUMBER = "phoneNumber";
    public static final String EMP_PASSWORD = "password";
    public static final String TYPE_OF_DATA = "Type";
    private SQLiteDatabase sqLiteDatabase;

    //query to create a table
//    private static final String CREATE_TABLE= "create table " +TABLE_NAME +"("+EMP_ID+"INTEGER PRIMARY KEY AUTOINCREMENT,"+EMP_NAME+"TEXT NOT NULL,"+EMP_PHONE_NUMBER+"TEXT NOT NULL"
  //          +EMP_PASSWORD+" TEXT NOT NULL,"+TYPE_OF_DATA+"TEXT NOT NULL);";
    private static final String query = "CREATE TABLE " + TABLE_NAME + " ("
            + EMP_ID + " TEXT PRIMARY KEY  , "
            + EMP_NAME + " TEXT NOT NULL,"
            + EMP_PHONE_NUMBER + " TEXT NOT NULL,"
            + EMP_PASSWORD + " TEXT NOT NULL,"
            + TYPE_OF_DATA + " TEXT NOT NULL)";
    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

                db.execSQL(query);
        Log.d("data","database created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        if(oldVersion!=newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }


public void addEmployee(EmployeeModel employeeModel){
        ContentValues contentValues=new ContentValues();
        contentValues.put(DBHelper.EMP_ID,employeeModel.getEmpId());
        contentValues.put(DBHelper.EMP_NAME, employeeModel.getName());
    contentValues.put(DBHelper.EMP_PHONE_NUMBER,employeeModel.getPhoneNumber());

        contentValues.put(DBHelper.EMP_PASSWORD,employeeModel.getPassword());

        contentValues.put(DBHelper.TYPE_OF_DATA,employeeModel.getTypeOfData());
        sqLiteDatabase=this.getWritableDatabase();
        sqLiteDatabase.insert(DBHelper.TABLE_NAME,null,contentValues);
}



}