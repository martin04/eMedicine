package com.diplomska.emed.martin.e_medicine.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Martin on 18-Jun-15.
 */
public class DBHelper extends SQLiteOpenHelper {

    //database name and version
    public static final String DATABASE_NAME = "emedicine";
    public static final int DATABASE_VERSION = 4;

    //table Drug
    public static final String TABLE_DRUGS = "drugs";
    public static final String COLUMN_DRUG_ID = "_id";
    public static final String COLUMN_CODE = "drug_code";
    public static final String COLUMN_LATIN_NAME = "latin_name";
    public static final String COLUMN_GENERIC_NAME = "generic_name";

    //table Contraindication
    public static final String TABLE_CONTRAINDICATIONS = "contraindications";
    public static final String COLUMN_CONTRA_ID = "_id";
    public static final String COLUMN_DRUG_CODE = "drug_code";
    public static final String COLUMN_CONTRAINDICATION = "contraindication";

    //table Advise
    public static final String TABLE_ADVISES = "advises";
    public static final String COLUMN_ADVISE_ID = "_id";
    public static final String COLUMN_D_CODE = "drug_code";
    public static final String COLUMN_ADVISE = "advise";


    //creating table DRUGS
    static final String createDrugs = String.format("create table %s(%s integer primary key autoincrement, " +
                    "%s text not null unique, %s text not null, %s text not null);", TABLE_DRUGS, COLUMN_DRUG_ID,
            COLUMN_CODE, COLUMN_LATIN_NAME, COLUMN_GENERIC_NAME);

    //creatig table CONTRAIND.
    static final String createContra = String.format("create table %s(%s integer primary key autoincrement, " +
                    "%s text not null, %s text not null, foreign key(%s) references %s(%s));", TABLE_CONTRAINDICATIONS, COLUMN_CONTRA_ID,
            COLUMN_DRUG_CODE, COLUMN_CONTRAINDICATION, COLUMN_DRUG_CODE, TABLE_DRUGS, COLUMN_CODE);

    //creating table ADVISES
    static final String createAdvise = String.format("create table %s(%s integer primary key autoincrement, " +
                    "%s text not null, %s text not null, foreign key(%s) references %s(%s));", TABLE_ADVISES, COLUMN_ADVISE_ID,
            COLUMN_D_CODE, COLUMN_ADVISE, COLUMN_DRUG_CODE, TABLE_DRUGS, COLUMN_CODE);

    public DBHelper(Context ctx) {
        super(ctx, String.format(DATABASE_NAME), null, DATABASE_VERSION);
    }

    //potreben metod koj se povikuva po otvoranje na bazata i se koristi za podesuvanje na osnovni raboti na bazata
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            //za da ovozmozime poddrska za nadvoresni klucevi
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createDrugs);
        db.execSQL(createContra);
        db.execSQL(createAdvise);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", TABLE_DRUGS));
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", TABLE_CONTRAINDICATIONS));
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", TABLE_ADVISES));
        onCreate(db);
    }
}
