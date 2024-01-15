package datdvph44632.fpoly.duan1_appbanhang_dinhvandat.NVA_Loader;

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
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.NAV_Adapter.NV_DonHang_Adapter;

public class NV_DonHang_Loader extends AsyncTask<String, Void, ArrayList<DonHang>> {
    @SuppressLint("StaticFieldLeak")
    Context context;
    String TAG = "NV_DonHang_Loader_____";
    QuanAoDAO quanAoDAO;
    DonHangDAO donHangDAO;
    ArrayList<QuanAo> listQuanAo = new ArrayList<>();
    @SuppressLint("StaticFieldLeak")
    RecyclerView reView;
    @SuppressLint("StaticFieldLeak")
    LinearLayout loadingView, linearEmpty;
    String trangthai;

    public NV_DonHang_Loader(Context context, RecyclerView reView, LinearLayout loadingView, LinearLayout linearEmpty, String trangthai) {
        this.context = context;
        this.reView = reView;
        this.loadingView = loadingView;
        this.linearEmpty = linearEmpty;
        this.trangthai = trangthai;
    }

    @Override
    protected ArrayList<DonHang> doInBackground(String... strings) {

        quanAoDAO = new QuanAoDAO(context);
        donHangDAO = new DonHangDAO(context);
        String maNV = strings[0];
        listQuanAo = quanAoDAO.selectQuanAo(null, null, null, null);

        return donHangDAO.selectDonHang(null, "maNV=? and trangThai=?", new String[]{maNV, trangthai}, "ngayMua");
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
        NV_DonHang_Adapter nv_donHang_adapter = new NV_DonHang_Adapter(listQuanAo, listDon, context);
        recyclerView.setAdapter(nv_donHang_adapter);
    }

}
