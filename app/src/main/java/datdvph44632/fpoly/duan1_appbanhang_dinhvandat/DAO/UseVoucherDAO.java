package datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Database.QLQuanAoDB;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.UseVoucher;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Support.ChangeType;

import java.util.ArrayList;

public class UseVoucherDAO {
    QLQuanAoDB qlQuanAoDB;
    SQLiteDatabase db;
    Context context;
    String TAG = "UseVoucherDAO_____";
    ChangeType changeType = new ChangeType();

    public UseVoucherDAO(Context context) {
        this.context = context;
        qlQuanAoDB = new QLQuanAoDB(context);
        db = qlQuanAoDB.getWritableDatabase();
    }

    public ArrayList selectUseVoucher(String[] columns, String selection, String[] selectionArgs, String orderBy) {
        ArrayList<UseVoucher> listUS = new ArrayList<>();
        qlQuanAoDB = new QLQuanAoDB(context);
        db = qlQuanAoDB.getWritableDatabase();
        Cursor c = db.query("UseVoucher", columns, selection, selectionArgs, null, null, orderBy);
        Log.d(TAG, "selectUseVoucher: Cursor: " + c.toString());

        if (c.getCount() > 0) {
            Log.d(TAG, "selectUseVoucher: Cursor not null");
            c.moveToFirst();
            while (!c.isAfterLast()) {
                Log.d(TAG, "selectUseVoucher: Cursor not last");
                String maUse = c.getString(0) + "";
                String maVoucher = c.getString(1);
                String maKH = c.getString(2);
                String isUsed = c.getString(3);
                UseVoucher newUS = new UseVoucher(maUse, maVoucher, maKH, isUsed);
                Log.d(TAG, "selectUseVoucher: new UseVoucher: " + newUS.toString());

                listUS.add(newUS);
                c.moveToNext();
            }
            c.close();
        } else {
            Log.d(TAG, "selectUseVoucher: Cursor null");
        }
        db.close();

        return listUS;
    }

    public void insertUseVoucher(UseVoucher useVoucher) {
        qlQuanAoDB = new QLQuanAoDB(context);
        db = qlQuanAoDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maKH", useVoucher.getMaKH());
        values.put("maVoucher", useVoucher.getMaVoucher());
        values.put("isUsed", useVoucher.getIsUsed());
        Log.d(TAG, "insertUseVoucher: UseVoucher: " + useVoucher.toString());
        Log.d(TAG, "insertUseVoucher: Values: " + values);

        long ketqua = db.insert("UseVoucher", null, values);
        if (ketqua > 0) {
            Log.d(TAG, "insertUseVoucher: Thêm thành công");
        } else {
            Log.d(TAG, "insertUseVoucher: Thêm thất bại");
        }
        db.close();
    }

    public int updateUseVoucher(UseVoucher useVoucher) {
        qlQuanAoDB = new QLQuanAoDB(context);
        db = qlQuanAoDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maKH", useVoucher.getMaKH());
        values.put("maVoucher", useVoucher.getMaVoucher());
        values.put("isUsed", useVoucher.getIsUsed());
        Log.d(TAG, "updateUseVoucher: UseVoucher: " + useVoucher.toString());
        Log.d(TAG, "updateUseVoucher: Values: " + values);

        long ketqua = db.update("UseVoucher", values, "maUse=?", new String[]{String.valueOf(useVoucher.getMaUse())});
        db.close();
        if (ketqua > 0) {
            Log.d(TAG, "updateUseVoucher: Sửa thành công");
            return 1;
        } else {
            Log.d(TAG, "updateUseVoucher: Sửa thất bại");
            return -1;
        }
    }

    public void deleteUseVoucher(String maVoucher) {
        qlQuanAoDB = new QLQuanAoDB(context);
        db = qlQuanAoDB.getWritableDatabase();
        Log.d(TAG, "deleteUseVoucher: UseVoucher maVoucher: " + maVoucher);

        long ketqua = db.delete("UseVoucher", "maVoucher=?", new String[]{maVoucher});
        if (ketqua > 0) {
            Log.d(TAG, "deleteViTien: Xóa thành công");
        } else {
            Log.d(TAG, "deleteViTien: Xóa thất bại");
        }
        db.close();
    }
}
