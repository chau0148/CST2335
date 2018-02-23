package lab5;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by amychau on 2/10/2018.
 */

public class ChatDatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "messages.db";
    public static String TABLE_NAME = "ChatMessage";
    public static final String KEY_MESSAGE = "message";
    private static int VERSION_NUM = 4;
    public static final String KEY_ID = "_id";

    private static final String DB_CREATE = "CREATE TABLE " + TABLE_NAME + " ("
                                            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                            + KEY_MESSAGE + " TEXT);";


    public ChatDatabaseHelper(Context ctx){
        super(ctx, DATABASE_NAME, null, VERSION_NUM);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("ChatDatabaseHelper", "Calling onCreate");
        db.execSQL(DB_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldDB, int newDB) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVerstion= " +oldDB+ " newVersion= " +newDB);
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return cursor;
    }

}
