package edu.sungshin.mlp_con;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="Alar_manager";
    private static final String TABLE_NAME="Alarm";
    private static final String COLUMN_ALARM_ID="Alarm_Id";
    private static final String COLUMN_ALARM_HOUR="Alarm_Hour";
    private static final String COLUMN_ALARM_MINUTE="Alarm_Minute";
    private static final String COLUMN_ALARM_STATUS="Alarm_Status";
    private static final String COLUMN_ALARM_NAME="Alarm_Name";
    public DataBaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String script="CREATE TABLE "+TABLE_NAME+"("
                +COLUMN_ALARM_ID+" INTEGER PRIMARY KEY,"
                +COLUMN_ALARM_HOUR +" INTEGER,"
                +COLUMN_ALARM_MINUTE +" INTEGER,"
                +COLUMN_ALARM_STATUS+" BOOLEAN,"
                +COLUMN_ALARM_NAME +" STRING"
                +")";
        db.execSQL(script);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public void addAlarm(Alarm alarm){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COLUMN_ALARM_HOUR,alarm.getHour());
        values.put(COLUMN_ALARM_MINUTE,alarm.getMinute());
        values.put(COLUMN_ALARM_STATUS,alarm.getStatus());
        values.put(COLUMN_ALARM_NAME,alarm.getName());
        db.insert(TABLE_NAME,null,values);
        db.close();
    }
    public List<Alarm> getAllAlarms(){
        List<Alarm> alarmList=new ArrayList<>();
        String selectQuery ="SELECT  * FROM "+TABLE_NAME ;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do{
                Alarm alarm=new Alarm();
                alarm.setId(cursor.getInt(0));
                alarm.setHour(cursor.getInt(1));
                alarm.setMinute(cursor.getInt(2));
                alarm.setStatus(cursor.getInt(3)!=0);
                alarm.setName(cursor.getString(4));
                alarmList.add(alarm);
            }while(cursor.moveToNext());
        }
        return alarmList;
    }
    public int updataAlarm(Alarm alarm){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COLUMN_ALARM_HOUR,alarm.getHour());
        values.put(COLUMN_ALARM_MINUTE,alarm.getMinute());
        values.put(COLUMN_ALARM_STATUS,alarm.getStatus());
        values.put(COLUMN_ALARM_NAME,alarm.getName());
        return db.update(TABLE_NAME,values,COLUMN_ALARM_ID+" = ?",new String[]{String.valueOf(alarm.getId())});
    }
}

