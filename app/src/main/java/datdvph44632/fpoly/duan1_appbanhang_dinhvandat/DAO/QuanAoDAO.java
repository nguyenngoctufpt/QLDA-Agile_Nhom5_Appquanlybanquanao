package datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Database.QLQuanAoDB;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.QuanAo;

import java.util.ArrayList;

public class QuanAoDAO {
    QLQuanAoDB qlQuanAoDB;
    SQLiteDatabase db;
    Context context;
    String TAG = "QuanAoDAO_____";

    public QuanAoDAO(Context context) {
        this.context = context;
        qlQuanAoDB = new QLQuanAoDB(context);
        db = qlQuanAoDB.getWritableDatabase();
    }

    public ArrayList selectQuanAo(String[] columns, String selection, String[] selectionArgs, String orderBy) {
        ArrayList<QuanAo> listLap = new ArrayList<>();
        qlQuanAoDB = new QLQuanAoDB(context);
        db = qlQuanAoDB.getWritableDatabase();
        Cursor c = db.query("QuanAo", columns, selection, selectionArgs, null, null, orderBy);
        Log.d(TAG, "selectQuanAo: Cursor: " + c.toString());

        if (c.getCount() > 0) {
            Log.d(TAG, "selectQuanAo: Cursor not null");
            c.moveToFirst();
            while (!c.isAfterLast()) {
                Log.d(TAG, "selectQuanAo: Cursor not last");
                String maLaptop = c.getString(0)+"";
                String maHangLap = c.getString(1);
                byte[] anhLaptop = c.getBlob(2);
                String tenLaptop = c.getString(3);
                String giaTien = c.getString(4);
                int soLuong = 0;
                try {
                    soLuong = Integer.parseInt(c.getString(5));
                } catch (Exception e){
                    e.printStackTrace();
                }
                int daBan = 0;
                try {
                    daBan = Integer.parseInt(c.getString(6));
                } catch (Exception e){
                    e.printStackTrace();
                }
                QuanAo newLap = new QuanAo(maLaptop, maHangLap, tenLaptop, giaTien, soLuong, daBan, anhLaptop);
                Log.d(TAG, "selectQuanAo: new QuanAo: " + newLap.toString());

                listLap.add(newLap);
                c.moveToNext();
            }
            c.close();
        } else {
            Log.d(TAG, "selectQuanAo: Cursor null");
        }
        db.close();

        return listLap;
    }

    public void insertQuanAo(QuanAo quanAo) {
        qlQuanAoDB = new QLQuanAoDB(context);
        db = qlQuanAoDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maLoaiQuanAo", quanAo.getMaHangQuanAo());
        values.put("anhQuanAo", quanAo.getAnhquanAo());
        values.put("tenQuanAo", quanAo.getTenQuanAo());
        values.put("giaTien", quanAo.getGiaTien());
        values.put("soLuong", quanAo.getSoLuong());
        values.put("daBan", quanAo.getDaBan());
        Log.d(TAG, "insertQuanAo: QuanAo: " + quanAo.toString());
        Log.d(TAG, "insertQuanAo: Values: " + values);

        long ketqua = db.insert("QuanAo", null, values);
        if (ketqua > 0) {
            Log.d(TAG, "insertQuanAo: Thêm thành công");
        } else {
            Log.d(TAG, "insertQuanAo: Thêm thất bại");
        }
        db.close();
    }

    public void updateQuanAo(QuanAo quanAo) {
        qlQuanAoDB = new QLQuanAoDB(context);
        db = qlQuanAoDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maQuanAo", quanAo.getMaQuanAo());
        values.put("maLoaiQuanAo", quanAo.getMaHangQuanAo());
        values.put("anhQuanAo", quanAo.getAnhquanAo());
        values.put("tenQuanAo", quanAo.getTenQuanAo());
        values.put("giaTien", quanAo.getGiaTien());
        values.put("soLuong", quanAo.getSoLuong());
        values.put("daBan", quanAo.getDaBan());
        Log.d(TAG, "updateQuanAo: QuanAo: " + quanAo.toString());
        Log.d(TAG, "updateQuanAo: Values: " + values);

        long ketqua = db.update("QuanAo", values, "maQuanAo=?", new String[]{String.valueOf(quanAo.getMaQuanAo())});
        if (ketqua > 0) {
            Log.d(TAG, "updateQuanAo: Sửa thành công");
            Toast.makeText(context, "Sửa thành công!", Toast.LENGTH_SHORT).show();
        } else {
            Log.d(TAG, "updateQuanAo: Sửa thất bại");
            Toast.makeText(context, "Sửa thất bại!", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    public void deleteQuanAo(QuanAo quanAo){
        qlQuanAoDB = new QLQuanAoDB(context);
        db = qlQuanAoDB.getWritableDatabase();
        Log.d(TAG, "deleteQuanAo: QuanAo: " + quanAo.toString());

        long ketqua = db.delete("QuanAo", "maQuanAo=?", new String[]{String.valueOf(quanAo.getMaQuanAo())});
        if (ketqua > 0) {
            Log.d(TAG, "deleteQuanAo: Xóa thành công");
            Toast.makeText(context, "Xóa thành công!", Toast.LENGTH_SHORT).show();
        } else {
            Log.d(TAG, "deleteQuanAo: Xóa thất bại");
            Toast.makeText(context, "Xóa thất bại!", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }
}
