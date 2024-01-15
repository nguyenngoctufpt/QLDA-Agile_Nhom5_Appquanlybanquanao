package datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Database.QLQuanAoDB;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.NhanVien;

public class NhanVienDAO {
    QLQuanAoDB qlQuanAoDB;
    SQLiteDatabase db;
    Context context;
    String TAG = "NhanVienDAO_____";

    public NhanVienDAO(Context context) {
        this.context = context;
        qlQuanAoDB = new QLQuanAoDB(context);
        db = qlQuanAoDB.getWritableDatabase();
    }

    //columns: Một mảng chuỗi (String[]) đại diện cho các cột mà bạn muốn truy vấn trong bảng "NhanVien".
    //selection: Một chuỗi (String) đại diện cho điều kiện truy vấn.
    //selectionArgs: Một mảng chuỗi (String[]) chứa các đối số cho câu lệnh truy vấn.
    //orderBy: Một chuỗi (String) đại diện cho cách sắp xếp kết quả truy vấn.
    public ArrayList selectNhanVien(String[] columns, String selection, String[] selectionArgs, String orderBy) {
        ArrayList<NhanVien> listNV = new ArrayList<>();
        qlQuanAoDB = new QLQuanAoDB(context);
        db = qlQuanAoDB.getWritableDatabase();
        //Thực hiện truy vấn SELECT trên bảng "NhanVien" với các tham số
        // truyền vào như columns, selection, selectionArgs và orderBy. Kết quả truy vấn được trả về dưới dạng một đối tượng Cursor.
        Cursor c = db.query("NhanVien", columns, selection, selectionArgs, null, null, orderBy);
        Log.d(TAG, "selectNhanVien: Cursor: " + c.toString());

        //Kiểm tra xem đối tượng Cursor có dữ liệu không.
        if (c.getCount() > 0) {
            Log.d(TAG, "selectNhanVien: Cursor not null");
            c.moveToFirst();
            while (!c.isAfterLast()) {
                Log.d(TAG, "selectNhanVien: Cursor not last");
                String maNV = c.getString(0)+"";
                byte[] avatar = c.getBlob(1);
                String hoNV = c.getString(2);
                String tenNV = c.getString(3);
                String gioiTinh = c.getString(4);
                String email = c.getString(5);
                String matKhau = c.getString(6);
                String queQuan = c.getString(7);
                String phone = c.getString(8);
                int doanhSo = 0;
                try {
                    doanhSo = Integer.parseInt(c.getString(9));
                } catch (Exception e){
                    e.printStackTrace();
                }
                int soSP = 0;
                try {
                    soSP = Integer.parseInt(c.getString(10));
                } catch (Exception e){
                    e.printStackTrace();
                }
                String roleNV = c.getString(11);
                NhanVien newNV = new NhanVien(maNV, hoNV, tenNV, gioiTinh, email, matKhau, queQuan, phone, roleNV, doanhSo, soSP, avatar);
                Log.d(TAG, "selectNhanVien: new NhanVien: " + newNV.toString());

                listNV.add(newNV);
                c.moveToNext();
            }
            //Đóng đối tượng Cursor sau khi hoàn thành việc sử dụng.
            c.close();
        } else {
            Log.d(TAG, "selectNhanVien: Cursor null");
        }
        //Đóng đối tượng cơ sở dữ liệu sau khi hoàn thành việc sử dụng.
        db.close();

        return listNV;
    }

    public int insertNhanVien(NhanVien nhanVien) {
        qlQuanAoDB = new QLQuanAoDB(context);
        db = qlQuanAoDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("avatar", nhanVien.getAvatar());
        values.put("hoNV", nhanVien.getHoNV());
        values.put("tenNV", nhanVien.getTenNV());
        values.put("gioiTinh", nhanVien.getGioiTinh());
        values.put("email", nhanVien.getEmail());
        values.put("matKhau", nhanVien.getMatKhau());
        values.put("queQuan", nhanVien.getQueQuan());
        values.put("phone", nhanVien.getPhone());
        values.put("doanhSo", nhanVien.getDoanhSo());
        values.put("soSP", nhanVien.getSoSP());
        values.put("roleNV", nhanVien.getRoleNV());
        Log.d(TAG, "insertNhanVien: NhanVien: " + nhanVien.toString());
        Log.d(TAG, "insertNhanVien: Values: " + values);

        long ketqua = db.insert("NhanVien", null, values);
        db.close();
        if (ketqua > 0) {
            Log.d(TAG, "insertNhanVien: Thêm thành công");
            return 1;
        } else {
            Log.d(TAG, "insertNhanVien: Thêm thất bại");
            return -1;
        }
    }

    public int updateNhanVien(NhanVien nhanVien) {
        qlQuanAoDB = new QLQuanAoDB(context);
        db = qlQuanAoDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maNV", nhanVien.getMaNV());
        values.put("avatar", nhanVien.getAvatar());
        values.put("hoNV", nhanVien.getHoNV());
        values.put("tenNV", nhanVien.getTenNV());
        values.put("gioiTinh", nhanVien.getGioiTinh());
        values.put("email", nhanVien.getEmail());
        values.put("matKhau", nhanVien.getMatKhau());
        values.put("queQuan", nhanVien.getQueQuan());
        values.put("phone", nhanVien.getPhone());
        values.put("doanhSo", nhanVien.getDoanhSo());
        values.put("soSP", nhanVien.getSoSP());
        values.put("roleNV", nhanVien.getRoleNV());
        Log.d(TAG, "insertNhanVien: NhanVien: " + nhanVien.toString());
        Log.d(TAG, "insertNhanVien: Values: " + values);

        long ketqua = db.update("NhanVien", values, "maNV=?", new String[]{String.valueOf(nhanVien.getMaNV())});
        db.close();
        if (ketqua > 0) {
            Log.d(TAG, "updateVoucher: Sửa thành công");
            return 1;
        } else {
            Log.d(TAG, "updateVoucher: Sửa thất bại");
            return -1;
        }
    }

    public void deleteNhanVien(NhanVien nhanVien){
        qlQuanAoDB = new QLQuanAoDB(context);
        db = qlQuanAoDB.getWritableDatabase();
        Log.d(TAG, "deleteNhanVien: NhanVien: " + nhanVien.toString());

        long ketqua = db.delete("NhanVien", "maNV=?", new String[]{String.valueOf(nhanVien.getMaNV())});
        if (ketqua > 0) {
            Log.d(TAG, "deleteNhanVien: Xóa thành công"); 
        } else {
            Log.d(TAG, "deleteNhanVien: Xóa thất bại");  
        }
        db.close();
    }
}
