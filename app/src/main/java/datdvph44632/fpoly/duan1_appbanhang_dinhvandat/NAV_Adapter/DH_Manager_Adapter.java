package datdvph44632.fpoly.duan1_appbanhang_dinhvandat.NAV_Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.KhachHang;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.NhanVien;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.QuanAo;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.Voucher;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.R;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Support.ChangeType;

public class DH_Manager_Adapter extends BaseAdapter {

    ArrayList<QuanAo> listLap;
    ArrayList<NhanVien> listNV;
    ArrayList<KhachHang> listKH;
    ArrayList<Voucher> listVou;
    String TAG = "DH_Manager_Adapter_____";
    TextView stt, name, other;
    ChangeType changeType = new ChangeType();

    public DH_Manager_Adapter(ArrayList<QuanAo> listLap, ArrayList<NhanVien> listNV, ArrayList<KhachHang> listKH, ArrayList<Voucher> listVou) {
        this.listLap = listLap;
        this.listNV = listNV;
        this.listKH = listKH;
        this.listVou = listVou;
    }

    @Override
    public int getCount() {
        if (listLap != null) {
            return listLap.size();
        }
        if (listKH != null) {
            return listKH.size();
        }
        if (listNV != null) {
            return listNV.size();
        }
        if (listVou != null) {
            return listVou.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int pos, View v, ViewGroup vGroup) {
        View row = LayoutInflater.from(vGroup.getContext()).inflate(R.layout.cardview_item_donhang, vGroup, false);
        stt = row.findViewById(R.id.textView_STT);
        name = row.findViewById(R.id.textView_Ten);
        other = row.findViewById(R.id.textView_Other);

        setRow(pos);
        return row;
    }

    public void setRow(int pos) {
        if (listLap != null) {
            QuanAo quanAo = listLap.get(pos);
            stt.setText(String.valueOf(pos + 1));
            name.setText(quanAo.getTenQuanAo());
            other.setText(quanAo.getGiaTien());
        }
        if (listKH != null) {
            KhachHang khachHang = listKH.get(pos);
            stt.setText(String.valueOf(pos + 1));
            name.setText(changeType.fullNameKhachHang(khachHang));
            other.setText(khachHang.getEmail());
        }
        if (listNV != null) {
            NhanVien nhanVien = listNV.get(pos);
            stt.setText(String.valueOf(pos + 1));
            name.setText(changeType.fullNameNhanVien(nhanVien));
            other.setText(nhanVien.getEmail());
        }
        if (listVou != null) {
            Voucher voucher = listVou.get(pos);
            stt.setText(String.valueOf(pos + 1));
            name.setText(voucher.getTenVoucher());
            other.setText(voucher.getGiamGia());
        }
    }
}
