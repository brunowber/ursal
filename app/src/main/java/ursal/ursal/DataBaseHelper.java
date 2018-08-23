package ursal.ursal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Usuario.db";
    public static final String TABLE_NAME = "usuario_table";
    public static final String ID= "ID";
    public static final String NAME= "NAME";
    public static final String GUERRILHEIRO= "GUERRILHEIRO";
    public static final String DATA_ENTROU= "DATA_ENTROU";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ TABLE_NAME  +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME, GUERRILHEIRO, DATA_ENTROU)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name, String guerrilheiro, String dataEntrou) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(GUERRILHEIRO, guerrilheiro);
        contentValues.put(DATA_ENTROU, dataEntrou);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1 )
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM  "+ TABLE_NAME, null);
        return res;
    }

    public Cursor getUserName() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT "+ NAME +" FROM  "+ TABLE_NAME + " WHERE "+ ID +" = (SELECT MAX (" + ID + ") FROM "+ TABLE_NAME+")", null);
        return res;
    }
    public Cursor getUserGuerrilheiro() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT "+ GUERRILHEIRO +" FROM  "+ TABLE_NAME+ " WHERE "+ ID +" = (SELECT MAX (" + ID + ") FROM "+ TABLE_NAME+")", null);
        return res;
    }
    public Cursor getUserData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT "+ DATA_ENTROU +" FROM  "+ TABLE_NAME+ " WHERE "+ ID +" = (SELECT MAX (" + ID + ") FROM "+ TABLE_NAME+")", null);
        return res;
    }
    public void dropData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ TABLE_NAME);
    }
}
