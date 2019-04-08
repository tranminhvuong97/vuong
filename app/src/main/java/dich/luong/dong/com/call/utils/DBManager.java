package dich.luong.dong.com.call.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import dich.luong.dong.com.call.model.MessageModel;

public class DBManager extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "SAVE";
    private static final String TABLE_NAME = "SAVE";
    private static final String ID = "id";
    private static final String PHONE = "PHONE";
    private static final String TIME = "TIME";


    private Context context;

    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, 1);
        Log.d("DBManager", "DBManager: ");
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                ID + " integer primary key, " +
                PHONE + " TEXT, " +
                TIME + " TEXT)";
        db.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addSave(MessageModel messageModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PHONE, messageModel.getPhone());
        values.put(TIME, messageModel.getTime());
        db.insert(TABLE_NAME, null, values);
//        db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        db.close();
    }


    public ArrayList<MessageModel> getAllSave() {
        ArrayList<MessageModel> modelSave = new ArrayList<MessageModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                MessageModel model = new MessageModel();
                model.setId(cursor.getInt(0));
                model.setPhone(cursor.getString(1));
                model.setTime(cursor.getString(2));
                modelSave.add(model);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return modelSave;
    }
}
