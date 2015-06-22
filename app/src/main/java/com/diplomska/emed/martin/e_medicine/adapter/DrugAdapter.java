package com.diplomska.emed.martin.e_medicine.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.diplomska.emed.martin.e_medicine.dao.DBHelper;
import com.diplomska.emed.martin.e_medicine.models.Drug;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin on 18-Jun-15.
 */
public class DrugAdapter {

    private DBHelper helper;
    private SQLiteDatabase db;

    private String[] columns = {DBHelper.COLUMN_DRUG_ID, DBHelper.COLUMN_CODE, DBHelper.COLUMN_LATIN_NAME, DBHelper.COLUMN_GENERIC_NAME};

    public DrugAdapter(Context ctx) {
        helper = new DBHelper(ctx);
    }

    public void open() {
        db = helper.getWritableDatabase();
    }

    public void close() {
        db.close();
        helper.close();
    }

    //CRUD operations
    public boolean insert(Drug drug) {
        if (drug.getId() != null) {
            return update(drug);
        }

        ContentValues cv = new ContentValues();
        if (drug.getId() != null) {
            cv.put(DBHelper.COLUMN_DRUG_ID, drug.getId());
        }
        cv.put(DBHelper.COLUMN_CODE, drug.getCode());
        cv.put(DBHelper.COLUMN_LATIN_NAME, drug.getLatin_name());
        cv.put(DBHelper.COLUMN_GENERIC_NAME, drug.getGeneric_name());

        long rowID = db.insert(DBHelper.TABLE_DRUGS, null, cv);//vtoriot argument se koristi za da se postavi NULL na nekoja kolona ako ja ima takva

        if (rowID > 0) {
           drug.setId(rowID);
            return true;
        } else {
            return false;
        }
    }

    public boolean update(Drug drug) {
        ContentValues cv = new ContentValues();
        if (drug.getId() != null) {
            cv.put(DBHelper.COLUMN_DRUG_ID, drug.getId());
        }
        cv.put(DBHelper.COLUMN_CODE, drug.getCode());
        cv.put(DBHelper.COLUMN_LATIN_NAME, drug.getLatin_name());
        cv.put(DBHelper.COLUMN_GENERIC_NAME, drug.getGeneric_name());

        return db.update(DBHelper.TABLE_DRUGS, cv, DBHelper.COLUMN_DRUG_ID + "=" + drug.getId(), null) > 0;
    }

    public boolean delete(long rowID) {
        return db.delete(DBHelper.TABLE_DRUGS, DBHelper.COLUMN_DRUG_ID + "=" + rowID, null) > 0;
    }

    public void deleteByGenName(String generic) {
        db.execSQL("delete from " + DBHelper.TABLE_DRUGS + " where generic_name=\"" + generic + "\"");
    }

    public List<Drug> getAllItems() {
        List<Drug> drugs = new ArrayList<Drug>();
       // Drug d = new Drug();

        Cursor c = db.rawQuery("select * from " + DBHelper.TABLE_DRUGS, null);
        if (c.moveToFirst()) {
            while(!c.isAfterLast()){
                Drug d = new Drug();
                d.setId(c.getLong(c.getColumnIndex(DBHelper.COLUMN_DRUG_ID)));
                d.setCode(c.getString(c.getColumnIndex(DBHelper.COLUMN_CODE)));
                d.setLatin_name(c.getString(c.getColumnIndex(DBHelper.COLUMN_LATIN_NAME)));
                d.setGeneric_name(c.getString(c.getColumnIndex(DBHelper.COLUMN_GENERIC_NAME)));
                drugs.add(d);
                c.moveToNext();
            }
        }
        c.close();
        return drugs;
    }

    public Drug getDrug(long id) {
        Drug d = new Drug();
        Cursor c = db.query(DBHelper.TABLE_DRUGS, columns, DBHelper.COLUMN_DRUG_ID + "=" + id, null, null, null, null);
        try {
            if (c.moveToFirst()) {
                d.setId(c.getLong(c.getColumnIndex(DBHelper.COLUMN_DRUG_ID)));
                d.setCode(c.getString(c.getColumnIndex(DBHelper.COLUMN_CODE)));
                d.setLatin_name(c.getString(c.getColumnIndex(DBHelper.COLUMN_LATIN_NAME)));
                d.setGeneric_name(c.getString(c.getColumnIndex(DBHelper.COLUMN_GENERIC_NAME)));

                return d;
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            c.close();
        }
    }

    //zemanje po ime, kade sovpagjanjeto na imeto treba da bide parcijalno!
    public List<Drug> getDrugByGenName(String generic) {
        List<Drug> result = new ArrayList<Drug>();
        Drug d = new Drug();

        Cursor c = db.rawQuery("select " + DBHelper.COLUMN_CODE + " " + DBHelper.COLUMN_GENERIC_NAME + " " + DBHelper.COLUMN_LATIN_NAME +
                " from " + DBHelper.TABLE_DRUGS + " where " + DBHelper.COLUMN_GENERIC_NAME + " like '%" + generic + "%' or "
                + DBHelper.COLUMN_LATIN_NAME + " like '%" + generic + "%'", null);

        if (c.moveToFirst()) {
            do {
                d.setCode(c.getString(c.getColumnIndex(DBHelper.COLUMN_CODE)));
                d.setGeneric_name(c.getString(c.getColumnIndex(DBHelper.COLUMN_GENERIC_NAME)));
                result.add(d);
            } while (c.moveToNext());
        }

        c.close();
        return result;
    }


}
