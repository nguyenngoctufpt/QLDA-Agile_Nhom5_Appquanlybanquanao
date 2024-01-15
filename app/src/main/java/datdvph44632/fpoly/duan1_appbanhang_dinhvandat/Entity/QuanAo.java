package datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity;

import android.os.Binder;

public class QuanAo extends Binder {
    private String maQuanAo;
    private String maHangQuanAo;
    private String tenQuanAo;
    private String giaTien;
    private int soLuong;
    private int daBan;
    private byte[] anhquanAo;

    public QuanAo(){}

    public QuanAo(String maQuanAo, String maHangQuanAo, String tenQuanAo, String giaTien, int soLuong, int daBan, byte[] anhquanAo) {
        this.maQuanAo = maQuanAo;
        this.maHangQuanAo = maHangQuanAo;
        this.tenQuanAo = tenQuanAo;
        this.giaTien = giaTien;
        this.soLuong = soLuong;
        this.daBan = daBan;
        this.anhquanAo = anhquanAo;
    }

    public String getMaQuanAo() {
        return maQuanAo;
    }

    public void setMaQuanAo(String maQuanAo) {
        this.maQuanAo = maQuanAo;
    }

    public String getMaHangQuanAo() {
        return maHangQuanAo;
    }

    public void setMaHangQuanAo(String maHangQuanAo) {
        this.maHangQuanAo = maHangQuanAo;
    }

    public String getTenQuanAo() {
        return tenQuanAo;
    }

    public void setTenQuanAo(String tenQuanAo) {
        this.tenQuanAo = tenQuanAo;
    }


    public String getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(String giaTien) {
        this.giaTien = giaTien;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getDaBan() {
        return daBan;
    }

    public void setDaBan(int daBan) {
        this.daBan = daBan;
    }

    public byte[] getAnhquanAo() {
        return anhquanAo;
    }

    public void setAnhquanAo(byte[] anhquanAo) {
        this.anhquanAo = anhquanAo;
    }

    @Override
    public String toString() {
        return "Laptop{" +
                "maLaptop = '" + maQuanAo + '\'' +
                ", maHangLap = '" + maHangQuanAo + '\'' +
                ", tenLaptop = '" + tenQuanAo + '\'' +
                ", giaTien = " + giaTien +
                ", soLuong = " + soLuong +
                ", daBan = " + daBan +
                ", anhLaptop = " + anhquanAo +
                '}';
    }
}
