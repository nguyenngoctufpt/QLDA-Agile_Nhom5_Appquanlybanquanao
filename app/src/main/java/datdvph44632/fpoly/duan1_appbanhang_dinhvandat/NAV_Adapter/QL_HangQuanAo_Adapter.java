package datdvph44632.fpoly.duan1_appbanhang_dinhvandat.NAV_Adapter;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.LoaiQuanAoDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.HangQuanAo;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.R;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Support.ChangeType;


public class QL_HangQuanAo_Adapter extends BaseAdapter {

    ArrayList<HangQuanAo> listHang;
    LoaiQuanAoDAO loaiQuanAoDAO;
    String TAG = "QL_HangQuanAo_Adapter_____";
    ImageView img;
    TextView name;

    public QL_HangQuanAo_Adapter(ArrayList<HangQuanAo> listHang) {
        this.listHang = listHang;
    }

    @Override
    public int getCount() {
        int count = listHang.size();
        Log.d(TAG, "getCount: count: " + count);
        return count;
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
        View row = LayoutInflater.from(vGroup.getContext()).inflate(R.layout.cardview_nva_hangquanao, vGroup, false);
        img = row.findViewById(R.id.imageView_HangQuanAo);
        name = row.findViewById(R.id.textView_TenHang_quanAo);

        loaiQuanAoDAO = new LoaiQuanAoDAO(vGroup.getContext());
        setRow(pos);

        return row;
    }

    public void setRow(int pos) {
        Log.d(TAG, "setRow: " + pos);
        HangQuanAo hangQuanAo = listHang.get(pos);
//        byte[] avatar = bitmapToByte(bitmap);
//        avatar = checkByteInput(avatar);
        Log.d(TAG, "setRow: Hãng Quan Ao: " + hangQuanAo.toString());

        ChangeType changeType = new ChangeType();
        Bitmap ava = changeType.byteToBitmap(hangQuanAo.getAnhHang());

        img.setImageBitmap(ava);
        name.setText(hangQuanAo.getTenHangLap());
    }
}
