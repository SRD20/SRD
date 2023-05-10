package com.example.srd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "SRD.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "EMPLOYEE";

    public static final String EMP_ID = "empid";
    public static final String EMP_NAME = "name";
    public static final String EMP_PHONE_NUMBER = "phoneNumber";
    public static final String EMP_PASSWORD = "password";
    public static final String TYPE_OF_DATA = "Type";
    private static DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Context mCtx;
    //private Cursor csr;
    //query to create a table
//    private static final String CREATE_TABLE= "create table " +TABLE_NAME +"("+EMP_ID+"INTEGER PRIMARY KEY AUTOINCREMENT,"+EMP_NAME+"TEXT NOT NULL,"+EMP_PHONE_NUMBER+"TEXT NOT NULL"
    //          +EMP_PASSWORD+" TEXT NOT NULL,"+TYPE_OF_DATA+"TEXT NOT NULL);";
    private static final String query = "CREATE TABLE " + TABLE_NAME + " ("
            + EMP_ID + " TEXT PRIMARY KEY  , "
            + EMP_NAME + " TEXT NOT NULL,"
            + EMP_PHONE_NUMBER + " TEXT NOT NULL,"
            + EMP_PASSWORD + " TEXT NOT NULL)";

    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mCtx = context;
    }

    public static DBHelper getDBInstance(Context ctx){
        if(dbHelper == null)
            dbHelper = new DBHelper(ctx);
        return dbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

        db.execSQL(query);
        Log.d("data", "database created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

    public boolean addEmployee(EmployeeModel employeeModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.EMP_ID, employeeModel.getEmpId());
        contentValues.put(DBHelper.EMP_NAME, employeeModel.getName());
        contentValues.put(DBHelper.EMP_PHONE_NUMBER, employeeModel.getPhoneNumber());
        contentValues.put(DBHelper.EMP_PASSWORD, employeeModel.getPassword());
        sqLiteDatabase = this.getWritableDatabase();
        long res = sqLiteDatabase.insert(DBHelper.TABLE_NAME, null, contentValues);
        return res > 0;
    }

    public List<EmployeeModel> getUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor csr = db.rawQuery("Select " + EMP_ID + ", " + EMP_NAME + " from " + TABLE_NAME, null);
        List<EmployeeModel> list = new ArrayList<>();
        csr.moveToFirst();
        while (csr.moveToNext()) {
            EmployeeModel emp = new EmployeeModel(csr.getString(0), csr.getString(1), null, null);
            list.add(emp);
        }
        //db.close();
        return list;
    }

    public Cursor getCursor(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor csr = db.rawQuery("Select " + EMP_ID + ", " + EMP_NAME + " from " + TABLE_NAME, null);
        return csr;
    }

    public long getRowCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        //db.close();
        return count;
    }

    public void updateUser(String empid,String name,String phno){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(EMP_NAME,name);
        cv.put(EMP_PHONE_NUMBER,phno);
        db.update(TABLE_NAME,cv,EMP_ID+" =? ",new String[]{empid});
    }

    public int deleteUser(String empid){
        SQLiteDatabase db = this.getWritableDatabase();
        int res = db.delete(TABLE_NAME,EMP_ID+" = '"+empid+"'",null);
        //db.close();
        return res;
    }
}