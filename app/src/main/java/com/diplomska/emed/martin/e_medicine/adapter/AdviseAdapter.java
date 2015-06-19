package com.diplomska.emed.martin.e_medicine.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.diplomska.emed.martin.e_medicine.dao.DBHelper;
import com.diplomska.emed.martin.e_medicine.models.Advise;
import com.diplomska.emed.martin.e_medicine.models.Advise;

/**
 * Created by Martin on 18-Jun-15.
 */
public class AdviseAdapter {

    private DBHelper helper;
    private SQLiteDatabase db;

    private String[] columns = {DBHelper.COLUMN_ADVISE_ID, DBHelper.COLUMN_D_CODE, DBHelper.COLUMN_ADVISE};

    public AdviseAdapter(Context ctx) {
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
    public boolean insert(Advise advise) {
        if (advise.getId() != null) {
            return update(advise);
        }

        ContentValues cv = new ContentValues();
        if (advise.getId() != null) {
            cv.put(DBHelper.COLUMN_ADVISE_ID, advise.getId());
        }
        cv.put(DBHelper.COLUMN_D_CODE, advise.getDrug().getCode());
        cv.put(DBHelper.COLUMN_ADVISE, advise.getAdvise());

        long rowID = db.insert(DBHelper.TABLE_ADVISES, null, cv);//vtoriot argument se koristi za da se postavi NULL na nekoja kolona ako ja ima takva

        if (rowID > 0) {
            advise.setId(rowID);
            return true;
        } else {
            return false;
        }
    }

    public boolean update(Advise advise) {
        ContentValues cv = new ContentValues();
        if (advise.getId() != null) {
            cv.put(DBHelper.COLUMN_ADVISE_ID, advise.getId());
        }
        cv.put(DBHelper.COLUMN_D_CODE, advise.getDrug().getCode());
        cv.put(DBHelper.COLUMN_ADVISE, advise.getAdvise());


        return db.update(DBHelper.TABLE_ADVISES, cv, DBHelper.COLUMN_ADVISE_ID + "=" + advise.getId(), null) > 0;
    }

    public boolean delete(long rowID) {
        return db.delete(DBHelper.TABLE_ADVISES, DBHelper.COLUMN_ADVISE_ID + "=" + rowID, null) > 0;
    }

    //metod za zemanje na sovet po drug code
    public String getAdviseByDrugCode(String code) {
        String result = "";
        Cursor c = db.query(DBHelper.TABLE_ADVISES, columns, DBHelper.COLUMN_D_CODE + "=" + code, null, null, null, null);

        if (c.moveToFirst()) {
            do {
                result += c.getString(c.getColumnIndex(DBHelper.COLUMN_ADVISE)) + ".\n";
            } while (c.moveToNext());
        }

        c.close();
        return result;
    }
}
