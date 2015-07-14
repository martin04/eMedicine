package com.diplomska.emed.martin.e_medicine.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.diplomska.emed.martin.e_medicine.dao.DBHelper;
import com.diplomska.emed.martin.e_medicine.models.Contraindication;
import com.diplomska.emed.martin.e_medicine.models.Contraindication;

/**
 * Created by Martin on 18-Jun-15.
 */
public class ContraindicationAdapter {

    private DBHelper helper;
    private SQLiteDatabase db;

    private String[] columns = {DBHelper.COLUMN_CONTRA_ID, DBHelper.COLUMN_DRUG_CODE, DBHelper.COLUMN_CONTRAINDICATION};

    public ContraindicationAdapter(Context ctx) {
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
    public boolean insert(Contraindication contra) {
        if (contra.getId() != null) {
            return update(contra);
        }

        ContentValues cv = new ContentValues();
        if (contra.getId() != null) {
            cv.put(DBHelper.COLUMN_CONTRA_ID, contra.getId());
        }
        cv.put(DBHelper.COLUMN_DRUG_CODE, contra.getDrug().getCode());
        cv.put(DBHelper.COLUMN_CONTRAINDICATION, contra.getContraindication());

        long rowID = db.insert(DBHelper.TABLE_CONTRAINDICATIONS, null, cv);//vtoriot argument se koristi za da se postavi NULL na nekoja kolona ako ja ima takva

        if (rowID > 0) {
            contra.setId(rowID);
            return true;
        } else {
            return false;
        }
    }

    public boolean update(Contraindication contra) {
        ContentValues cv = new ContentValues();
        if (contra.getId() != null) {
            cv.put(DBHelper.COLUMN_CONTRA_ID, contra.getId());
        }
        cv.put(DBHelper.COLUMN_DRUG_CODE, contra.getDrug().getCode());
        cv.put(DBHelper.COLUMN_CONTRAINDICATION, contra.getContraindication());


        return db.update(DBHelper.TABLE_CONTRAINDICATIONS, cv, DBHelper.COLUMN_CONTRA_ID + "=" + contra.getId(), null) > 0;
    }

    public boolean delete(long rowID) {
        return db.delete(DBHelper.TABLE_CONTRAINDICATIONS, DBHelper.COLUMN_CONTRA_ID + "=" + rowID, null) > 0;
    }

    //metoda za zemanje po lek t.e. po drug code
    public String getContraByDrugCode(String code) {
        String result = "";
        String[] selectionArgs={code};
        Cursor c = db.query(DBHelper.TABLE_CONTRAINDICATIONS, columns, DBHelper.COLUMN_DRUG_CODE + " =?" ,selectionArgs, null, null, null, null);

        if (c.moveToFirst()) {
            do {
                result += c.getString(c.getColumnIndex(DBHelper.COLUMN_CONTRAINDICATION)) + ".\n";
            } while (c.moveToNext());
        }
        c.close();
        return result;
    }
}
