package datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class QLQuanAoDB extends SQLiteOpenHelper {

    static String DB_Name = "db.QLQuanAo";
    static int VER = 1;
    String TAG = "QLQuanAoDB_____";

    public QLQuanAoDB(@Nullable Context context) {
        super(context, DB_Name, null, VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: DB: " + DB_Name);
        // Bảng NhanVien (BLOB lưu trữ dữ liệu hình ảnh của nhân viên, UNIQUE phải là duy nhất trong bảng)
        String tableNhanVien = "CREATE TABLE NhanVien( maNV INTEGER PRIMARY KEY AUTOINCREMENT, avatar BLOB," +
                " hoNV TEXT not null, tenNV TEXT not null, gioiTinh TEXT not null, email VARCHAR(50) UNIQUE not null, matKhau TEXT not null," +
                " queQuan TEXT, phone TEXT, doanhSo INT, soSP INT, roleNV TEXT)";

        // Bảng KhachHang
        String tableKhachHang = "CREATE TABLE KhachHang( maKH INTEGER PRIMARY KEY AUTOINCREMENT, avatar BLOB," +
                " hoKH TEXT not null, tenKH TEXT not null, gioiTinh TEXT not null, email VARCHAR(50) UNIQUE not null, matKhau TEXT not null," +
                " queQuan TEXT, phone TEXT)";

        // Bảng LoaiQuanAo
        String tableLoaiQuanAo = "CREATE TABLE LoaiQuanAo( maLoaiQuanAo VARCHAR(15) PRIMARY KEY not null" +
                ", anhHang BLOB, tenLoaiQuanAo TEXT not null)";

        // Bảng Voucher
        String tableVoucher = "CREATE TABLE Voucher( maVoucher INTEGER PRIMARY KEY AUTOINCREMENT," +
                " tenVoucher TEXT not null, giamGia TEXT not null, ngayBD DATE, ngayKT DATE)";

        // Bảng UseVoucher(isUsed là lưu trữ trạng thái voucher đã được sử dụng hay chưa)
        String tableUseVoucher = "CREATE TABLE UseVoucher( maUse INTEGER PRIMARY KEY AUTOINCREMENT," +
                " maVoucher INTEGER not null, maKH INTEGER not null, isUsed TEXT," +
                " FOREIGN KEY(maKH) REFERENCES KhachHang (maKH), FOREIGN KEY(maVoucher) REFERENCES Voucher (maVoucher))";

        // Bảng quanAo
        String tableQuanAo = "CREATE TABLE QuanAo( maQuanAo INTEGER PRIMARY KEY AUTOINCREMENT," +
                " maLoaiQuanAo VARCHAR(15) not null, anhQuanAo BLOB," +
                " tenQuanAo TEXT not null, giaTien TEXT, soLuong INT, daBan INT," +
                " FOREIGN KEY(maLoaiQuanAo) REFERENCES LoaiQuanAo (maLoaiQuanAo))";

        // Bảng QuanAoRate
        String tableQuanAoRate = "CREATE TABLE QuanAoRate( maRate INTEGER PRIMARY KEY not null," +
                " maQuanAo INT not null, danhGia TEXT, rating FLOAT," +
                " FOREIGN KEY(maQuanAo) REFERENCES QuanAo (maQuanAo))";

        // Bảng DonHang
        String tableDonHang = "CREATE TABLE DonHang( maDH INTEGER PRIMARY KEY not null, maNV INT not null," +
                " maKH INT not null, maQuanAo INT not null, maVoucher INT not null," +
                " maRate INT not null, soLuong INT not null, diaChi TEXT, ngayMua DATE not null," +
                " pthThanhToan TEXT, trangThai TEXT, isDanhGia TEXT, thanhTien TEXT not null," +
                " FOREIGN KEY(maNV) REFERENCES NhanVien (maNV), FOREIGN KEY(maKH) REFERENCES KhachHang (maKH)," +
                " FOREIGN KEY(maQuanAo) REFERENCES QuanAo (maQuanAo), FOREIGN KEY(maVoucher) REFERENCES Voucher (maVoucher)," +
                " FOREIGN KEY(maRate) REFERENCES QuanAoRate (maRate))";

        // Bảng GioHang
        String tableGioHang = "CREATE TABLE GioHang( maGio INTEGER PRIMARY KEY AUTOINCREMENT," +
                " maQuanAo INT not null, maKH INT not null, soLuong INT, ngayThem DATE, maVou TEXT," +
                " FOREIGN KEY(maQuanAo) REFERENCES QuanAo (maQuanAo), FOREIGN KEY(maKH) REFERENCES KhachHang (maKH))";

        // Bảng KH_ThongBao
        String tableThongBaoKH = "CREATE TABLE KH_ThongBao( maTB INTEGER PRIMARY KEY AUTOINCREMENT," +
                " maKH INT not null, title TEXT not null, chiTiet TEXT not null, ngayTB DATE," +
                " FOREIGN KEY(maKH) REFERENCES KhachHang (maKH))";

        // Bảng NV_ThongBao
        String tableThongBaoNV = "CREATE TABLE NV_ThongBao( maTB INTEGER PRIMARY KEY AUTOINCREMENT," +
                " maNV INT not null, title TEXT not null, chiTiet TEXT not null, ngayTB DATE," +
                " FOREIGN KEY(maNV) REFERENCES NhanVien (maNV))";

        // Bảng AD_ThongBao
        String tableThongBaoAD = "CREATE TABLE AD_ThongBao( maTB INTEGER PRIMARY KEY AUTOINCREMENT," +
                " admin INT not null, title TEXT not null, chiTiet TEXT not null, ngayTB DATE)";

        // Bảng DiaChi
        String tableDiaChi = "CREATE TABLE DiaChi( maDC INTEGER PRIMARY KEY AUTOINCREMENT," +
                " maKH INT not null, tenNguoiNhan TEXT not null, phone TEXT not null," +
                " diaChi TEXT, thanhPho TEXT, quanHuyen TEXT, phuongXa TEXT," +
                " FOREIGN KEY(maKH) REFERENCES KhachHang (maKH))";

        // Bảng DiaChi
        String tableViTien = "CREATE TABLE ViTien( maVi INTEGER PRIMARY KEY not null," +
                " maKH INT not null, soTien TEXT not null, nganHang TEXT," +
                " FOREIGN KEY(maKH) REFERENCES KhachHang (maKH))";

        String tableGiaoDich = "CREATE TABLE GiaoDich( maGD INTEGER PRIMARY KEY AUTOINCREMENT," +
                " maVi INT not null, title TEXT, chiTiet TEXT, soTien TEXT, ngayGD DATE," +
                " FOREIGN KEY(maVi) REFERENCES ViTien (maVi))";

        //execSQL
        db.execSQL(tableNhanVien);
        db.execSQL(tableKhachHang);
        db.execSQL(tableLoaiQuanAo);
        db.execSQL(tableVoucher);
        db.execSQL(tableUseVoucher);
        db.execSQL(tableQuanAo);
        db.execSQL(tableQuanAoRate);
        db.execSQL(tableGioHang);
        db.execSQL(tableThongBaoKH);
        db.execSQL(tableThongBaoNV);
        db.execSQL(tableThongBaoAD);
        db.execSQL(tableDiaChi);
        db.execSQL(tableViTien);
        db.execSQL(tableGiaoDich);
        db.execSQL(tableDonHang);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
