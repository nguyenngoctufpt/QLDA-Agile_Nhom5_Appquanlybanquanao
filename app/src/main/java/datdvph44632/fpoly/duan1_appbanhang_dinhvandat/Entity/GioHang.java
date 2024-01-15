package datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity;

import android.os.Binder;

public class GioHang extends Binder {
    private String maGio;
    private String maQuanAo;
    private String maKH;
    private String ngayThem;
    private String maVou;
    private int soLuong;

    public GioHang(String maGio) {
        this.maGio = maGio;
    }

    public GioHang(String maGio, String maQuanAo, String maKH, String ngayThem, String maVou, int soLuong) {
        this.maGio = maGio;
        this.maQuanAo = maQuanAo;
        this.maKH = maKH;
        this.ngayThem = ngayThem;
        this.soLuong = soLuong;
        this.maVou = maVou;
    }

    public String getMaGio() {
        return maGio;
    }

    public void setMaGio(String maGio) {
        this.maGio = maGio;
    }

    public String getMaQuanAo() {
        return maQuanAo;
    }

    public void setMaQuanAo(String maQuanAo) {
        this.maQuanAo = maQuanAo;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getMaVou() {
        return maVou;
    }

    public void setmaVou(String maVou) {
        this.maVou = maVou;
    }

    public String getNgayThem() {
        return ngayThem;
    }

    public void setNgayThem(String ngayThem) {
        this.ngayThem = ngayThem;
    }

    @Override
    public String toString() {
        return "GioHang{" +
                "maGio = '" + maGio + '\'' +
                ", maLaptop = '" + maQuanAo + '\'' +
                ", maKH = '" + maKH + '\'' +
                ", ngayThem = '" + ngayThem + '\'' +
                ", maVou = '" + maVou + '\'' +
                ", soLuong = '" + soLuong + '\'' +
                '}';
    }
}
