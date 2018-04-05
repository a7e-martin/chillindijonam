package org.diiage.martin.chillindijon.utils;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String CHILLINDIJON_DB = "chillindijon.db";
    public static final int VERSION = 1;

    public DatabaseHelper(Context context){
        super(context, CHILLINDIJON_DB, null, VERSION);
    }
    //Appelé une seule fois dans le cycle de vie de l'app, lors du premier appel à getReadableDatabase() ou getWritableDatabase()
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE bookmarks(id INTEGER PRIMARY KEY AUTOINCREMENT, poi_id VARCHAR(36), note INTEGER, created DATETIME)");
    }

    /*Méthode appelée lorsqu'il est nécessaire de mettre à jour la structure de la base de données.
    Le but de cette méthode est de :
    - Modifier la structure (ajout de table, ajout de champ, suppression de table ou de champ, ...)

    */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
