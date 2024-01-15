package datdvph44632.fpoly.duan1_appbanhang_dinhvandat.KH_Loader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.DonHangDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.QuanAoDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.DonHang;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.QuanAo;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.KH_Adapter.KH_DonHang_Adapter;

public class KH_DonHang_Loader extends AsyncTask<String, Void, ArrayList<DonHang>> {
    @SuppressLint("StaticFieldLeak")
    Context context;
    String TAG = "NV_DonHang_Loader_____";
    QuanAoDAO quanAoDAO;
    DonHangDAO donHangDAO;
    ArrayList<QuanAo> listLap = new ArrayList<>();
    @SuppressLint("StaticFieldLeak")
    RecyclerView reView;
    @SuppressLint("StaticFieldLeak")
    LinearLayout loadingView, linearEmpty;
    String type;
    String trangthai;

    public KH_DonHang_Loader(Context context, RecyclerView reView, LinearLayout loadingView, LinearLayout linearEmpty, String type, String trangthai) {
        this.context = context;
        this.reView = reView;
        this.loadingView = loadingView;
        this.linearEmpty = linearEmpty;
        this.type = type;
        this.trangthai = trangthai;
    }

    @Override
    protected ArrayList<DonHang> doInBackground(String... strings) {
        quanAoDAO = new QuanAoDAO(context);
        donHangDAO = new DonHangDAO(context);
        String maKH = strings[0];
        listLap = quanAoDAO.selectQuanAo(null, null, null, null);

        if (type.equals("yep")){
            return donHangDAO.selectDonHang(null, "maKH=? and trangThai=?", new String[]{maKH, trangthai}, "ngayMua");
        }
        if (type.equals("none")){
            return donHangDAO.selectDonHang(null, "maKH=? and trangThai=?", new String[]{maKH, trangthai}, "ngayMua");
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<DonHang> listDon) {
        super.onPostExecute(listDon);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (loadingView != null && linearEmpty != null && reView != null) {
                    if (listDon != null) {
                        if (listDon.size() == 0) {
                            loadingView.setVisibility(View.GONE);
                            reView.setVisibility(View.GONE);
                            linearEmpty.setVisibility(View.VISIBLE);
                        } else {
                            loadingView.setVisibility(View.GONE);
                            reView.setVisibility(View.VISIBLE);
                            linearEmpty.setVisibility(View.GONE);
                            setupReView(listDon, reView);
                        }
                    }
                }
            }
        }, 1000);
    }

    private void setupReView(ArrayList<DonHang> listDon, RecyclerView recyclerView) {
        Log.d(TAG, "setUpReView: true");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        KH_DonHang_Adapter kh_donHang_adapter = new KH_DonHang_Adapter(listLap, listDon, context);
        recyclerView.setAdapter(kh_donHang_adapter);
    }

}
