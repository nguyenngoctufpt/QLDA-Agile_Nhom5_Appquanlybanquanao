package datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity;

import android.os.Binder;

public class QuanAoRate extends Binder {
    private String maRate;
    private String maQuanAo;
    private String danhGia;
    private float rating;

    public QuanAoRate(String maRate, String maQuanAo, String danhGia, float rating) {
        this.maRate = maRate;
        this.maQuanAo = maQuanAo;
        this.danhGia = danhGia;
        this.rating = rating;
    }

    public String getMaRate() {
        return maRate;
    }

    public void setMaRate(String maRate) {
        this.maRate = maRate;
    }

    public String getMaQuanAo() {
        return maQuanAo;
    }

    public void setMaQuanAo(String maQuanAo) {
        this.maQuanAo = maQuanAo;
    }

    public String getDanhGia() {
        return danhGia;
    }

    public void setDanhGia(String danhGia) {
        this.danhGia = danhGia;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "LaptopRate{" +
                "maRate = '" + maRate + '\'' +
                ", maLaptop = '" + maQuanAo + '\'' +
                ", danhGia = '" + danhGia + '\'' +
                ", rating = " + rating +
                '}';
    }
}
