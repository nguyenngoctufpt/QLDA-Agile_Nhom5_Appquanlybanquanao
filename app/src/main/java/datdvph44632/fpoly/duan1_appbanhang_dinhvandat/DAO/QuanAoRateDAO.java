package datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Database.QLQuanAoDB;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.QuanAoRate;

import java.util.ArrayList;

public class QuanAoRateDAO {
    QLQuanAoDB qlQuanAoDB;
    SQLiteDatabase db;
    Context context;
    String TAG = "QuanAoRateDAO_____";

    public QuanAoRateDAO(Context context) {
        this.context = context;
        qlQuanAoDB = new QLQuanAoDB(context);
        db = qlQuanAoDB.getWritableDatabase();
    }

    public ArrayList selectQuanAoRate(String[] columns, String selection, String[] selectionArgs, String orderBy) {
        ArrayList<QuanAoRate> listVou = new ArrayList<>();
        qlQuanAoDB = new QLQuanAoDB(context);
        db = qlQuanAoDB.getWritableDatabase();
        Cursor c = db.query("QuanAoRate", columns, selection, selectionArgs, null, null, orderBy);
        Log.d(TAG, "selectQuanAoRate: Cursor: " + c.toString());

        if (c.getCount() > 0) {
            Log.d(TAG, "selectQuanAoRate: Cursor not null");
            c.moveToFirst();
            while (!c.isAfterLast()) {
                Log.d(TAG, "selectQuanAoRate: Cursor not last");
                String maRate = c.getString(0) + "";
                String maLaptop = c.getString(1);
                String danhGia = c.getString(2);
                float rating = 0;
                try {
                    rating = Float.parseFloat(c.getString(3));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                QuanAoRate newLR = new QuanAoRate(maRate, maLaptop, danhGia, rating);
                Log.d(TAG, "selectQuanAoRate: new QuanAoRate: " + newLR.toString());

                listVou.add(newLR);
                c.moveToNext();
            }
            c.close();
        } else {
            Log.d(TAG, "selectQuanAoRate: Cursor null");
        }
        db.close();

        return listVou;
    }

    public void insertQuanAoRate(QuanAoRate quanAoRate) {
        qlQuanAoDB = new QLQuanAoDB(context);
        db = qlQuanAoDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maRate", quanAoRate.getMaRate());
        values.put("maQuanAo", quanAoRate.getMaQuanAo());
        values.put("danhGia", quanAoRate.getDanhGia());
        values.put("rating", String.valueOf(quanAoRate.getRating()));
        Log.d(TAG, "insertQuanAoRate: QuanAoRate: " + quanAoRate.toString());
        Log.d(TAG, "insertQuanAoRate: Values: " + values);

        long ketqua = db.insert("QuanAoRate", null, values);
        if (ketqua > 0) {
            Log.d(TAG, "insertQuanAoRate: Thêm thành công");
        } else {
            Log.d(TAG, "insertQuanAoRate: Thêm thất bại");
        }
        db.close();
    }

    public void updateQuanAoRate(QuanAoRate quanAoRate) {
        qlQuanAoDB = new QLQuanAoDB(context);
        db = qlQuanAoDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maRate", quanAoRate.getMaRate());
        values.put("maQuanAo", quanAoRate.getMaQuanAo());
        values.put("danhGia", quanAoRate.getDanhGia());
        values.put("rating", String.valueOf(quanAoRate.getRating()));
        Log.d(TAG, "updateQuanAoRate: QuanAoRate: " + quanAoRate.toString());
        Log.d(TAG, "updateQuanAoRate: Values: " + values);

        long ketqua = db.update("QuanAoRate", values, "maRate=?", new String[]{String.valueOf(quanAoRate.getMaRate())});
        if (ketqua > 0) {
            Log.d(TAG, "updateQuanAoRate: Sửa thành công");
        } else {
            Log.d(TAG, "updateQuanAoRate: Sửa thất bại");
        }
        db.close();
    }

    public void deleteQuanAoRate(QuanAoRate quanAoRate) {
        qlQuanAoDB = new QLQuanAoDB(context);
        db = qlQuanAoDB.getWritableDatabase();
        Log.d(TAG, "deleteQuanAoRate: QuanAoRate: " + quanAoRate.toString());

        long ketqua = db.delete("QuanAoRate", "maRate=?", new String[]{String.valueOf(quanAoRate.getMaRate())});
        if (ketqua > 0) {
            Log.d(TAG, "deleteQuanAoRate: Xóa thành công");
        } else {
            Log.d(TAG, "deleteQuanAoRate: Xóa thất bại");
        }
        db.close();
    }
}
