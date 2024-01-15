package datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity;

import android.os.Binder;

import java.util.Arrays;

public class HangQuanAo extends Binder {
    private String maHangQA;
    private String tenHangQA;
    
    private byte[] anhHang;

    public HangQuanAo(String maHangQuanAo, String tenHangQA, byte[] anhHang) {
        this.maHangQA = maHangQuanAo;
        this.tenHangQA = tenHangQA;
        this.anhHang = anhHang;
    }

    public String getMaHangLap() {
        return maHangQA;
    }

    public void setMaHangLap(String maHangLap) {
        this.maHangQA = maHangLap;
    }

    public String getTenHangLap() {
        return tenHangQA;
    }

    public void setTenHangLap(String tenHangLap) {
        this.tenHangQA = tenHangLap;
    }

    public byte[] getAnhHang() {
        return anhHang;
    }

    public void setAnhHang(byte[] anhHang) {
        this.anhHang = anhHang;
    }

    @Override
    public String toString() {
        return "HangLaptop{" +
                "maHangLap = '" + maHangQA + '\'' +
                ", tenHangLap = '" + tenHangQA + '\'' +
                ", anhHang = " + anhHang +
                " : " + Arrays.toString(anhHang) +
                '}';
    }
}
