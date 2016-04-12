package be.thomasmore.brrr.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import be.thomasmore.brrr.data.model.Beacon;

/**
 * Created by Gotze on 2016/4/4.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Brrr2.db";
    public static final String FAVORITES_TABLE_NAME = "favorites";
    public static final String FAVORITES_COLUMN_ID = "id";
    public static final String FAVORITES_COLUMN_UUID = "uuid";
    public static final String FAVORITES_COLUMN_MAJOR = "major";
    public static final String FAVORITES_COLUMN_MINOR = "minor";
    public static final String FAVORITES_COLUMN_PICTUREURL = "pictureUrl";
    public static final String FAVORITES_COLUMN_TITLE = "title";
    public static final String FAVORITES_COLUMN_MAINTEXT = "mainText";
    public static final String FAVORITES_COLUMN_TIME = "time";

    private HashMap hp;

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS favorites " +
                        "(id integer primary key, uuid text, major text, minor text, pictureUrl text, title text, mainText text, time text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS favorites");
        onCreate(db);
    }

    public boolean insert(Beacon beacon)
    {
        return insert(beacon.getIds().getUuid(), beacon.getIds().getMajor(), beacon.getIds().getMinor(), beacon.getPictureUrl(), beacon.getTitle(), beacon.getMainText(), beacon.getShowTime());
    }

    public boolean insert (String uuid, String major, String minor, String pictureUrl, String title, String mainText,String time)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("uuid", uuid);
        contentValues.put("major", major);
        contentValues.put("minor", minor);
        contentValues.put("pictureUrl", pictureUrl);
        contentValues.put("title", title);
        contentValues.put("mainText", mainText);
        contentValues.put("time", time);
        db.insert("favorites", null, contentValues);
        return true;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from favorites where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, FAVORITES_TABLE_NAME);
        return numRows;
    }

    public boolean update (Integer id, String uuid, String major, String minor, String pictureUrl, String title, String mainText,String time)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("uuid", uuid);
        contentValues.put("major", major);
        contentValues.put("minor", minor);
        contentValues.put("pictureUrl", pictureUrl);
        contentValues.put("title", title);
        contentValues.put("mainText", mainText);
        contentValues.put("time", time);
        db.update("favorites", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer delete (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("favorites",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public Integer delete (Beacon beacon)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("favorites",
                "uuid = ?  and major = ? and minor = ?",
                new String[] { beacon.getIds().getUuid(), beacon.getIds().getMajor(),beacon.getIds().getMinor() });
    }

    public ArrayList<String> getAll()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from favorites", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(FAVORITES_COLUMN_UUID)));
            res.moveToNext();
        }
        return array_list;
    }

    public boolean get(Beacon beacon)
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from favorites where uuid = ?  and major = ? and minor = ?",  new String[] { beacon.getIds().getUuid(), beacon.getIds().getMajor(),beacon.getIds().getMinor() } );
        return res.getCount() >0 ? true: false;

    }
}
