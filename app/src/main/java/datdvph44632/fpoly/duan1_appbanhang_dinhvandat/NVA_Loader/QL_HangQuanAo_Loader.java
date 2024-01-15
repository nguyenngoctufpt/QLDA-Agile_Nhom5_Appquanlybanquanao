package datdvph44632.fpoly.duan1_appbanhang_dinhvandat.NVA_Loader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.LoaiQuanAoDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.HangQuanAo;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.NAV_Adapter.QL_HangQuanAo_Adapter;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.R;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Support.ChangeType;

public class QL_HangQuanAo_Loader extends AsyncTask<String, Void, ArrayList<HangQuanAo>> {
    @SuppressLint("StaticFieldLeak")
    GridView gridView;
    @SuppressLint("StaticFieldLeak")
    Context context;
    String TAG = "LaptopLoader_____";
    LoaiQuanAoDAO loaiQuanAoDAO;
    ChangeType changeType = new ChangeType();
    @SuppressLint("StaticFieldLeak")
    LinearLayout linearLayout;

    public QL_HangQuanAo_Loader(Context context, GridView gridView, LinearLayout linearLayout) {
        this.gridView = gridView;
        this.context = context;
        this.linearLayout = linearLayout;
    }

    @Override
    protected ArrayList<HangQuanAo> doInBackground(String... strings) {

        loaiQuanAoDAO = new LoaiQuanAoDAO(context);
        ArrayList<HangQuanAo> listHang = loaiQuanAoDAO.selectHangQuanAo(null, null, null, "maLoaiQuanAo");

        if (listHang == null) {
            Log.d(TAG, "onCreateView: list null");
            addHangLaptop();
        } else {
            if (listHang.size() < 6) {
                Log.d(TAG, "onCreateView: list not null");
                Log.d(TAG, "onCreateView: list size = " + listHang.size());
                addHangLaptop();
            }
        }

        return loaiQuanAoDAO.selectHangQuanAo(null, null, null, "maLoaiQuanAo");
    }

    @Override
    protected void onPostExecute(ArrayList<HangQuanAo> listHang) {
        super.onPostExecute(listHang);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (linearLayout != null && gridView != null) {
                    linearLayout.setVisibility(View.GONE);
                    setupReView(listHang, gridView);
                }
            }
        }, 1000);
    }

    private void setupReView(ArrayList<HangQuanAo> listHang, GridView gridView) {
        QL_HangQuanAo_Adapter ql_hangQuanAo_adapter = new QL_HangQuanAo_Adapter(listHang);
        gridView.setAdapter(ql_hangQuanAo_adapter);
    }

    private void addHangLaptop() {
        Bitmap bmDell = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.ao_thun_t);
        HangQuanAo dell = new HangQuanAo("LAoKaki", "Áo kaki",
                changeType.checkByteInput(changeType.bitmapToByte(bmDell)));
        loaiQuanAoDAO.insertHangQuanAo(dell);

        Bitmap bmHp = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.ao_thun);
        HangQuanAo hp = new HangQuanAo("LAoThun", "Áo thun",
                changeType.checkByteInput(changeType.bitmapToByte(bmHp)));
        loaiQuanAoDAO.insertHangQuanAo(hp);

        Bitmap bmAsus = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.ao_hoddie);
        HangQuanAo asus = new HangQuanAo("LAoHoodie", "Áo Hoodie",
                changeType.checkByteInput(changeType.bitmapToByte(bmAsus)));
        loaiQuanAoDAO.insertHangQuanAo(asus);

        Bitmap bmRazer = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.ao_gio);
        HangQuanAo razer = new HangQuanAo("LAoGio", "Áo gió",
                changeType.checkByteInput(changeType.bitmapToByte(bmRazer)));
        loaiQuanAoDAO.insertHangQuanAo(razer);

        Bitmap bmSS = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.quan_bo_b);
        HangQuanAo ss = new HangQuanAo("LQuanBo", "Quần bò",
                changeType.checkByteInput(changeType.bitmapToByte(bmSS)));
        loaiQuanAoDAO.insertHangQuanAo(ss);

        Bitmap bmMac = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.quan_vai);
        HangQuanAo mac = new HangQuanAo("LQuanVai", "Quần Vải",
                changeType.checkByteInput(changeType.bitmapToByte(bmMac)));
        loaiQuanAoDAO.insertHangQuanAo(mac);
    }
}

