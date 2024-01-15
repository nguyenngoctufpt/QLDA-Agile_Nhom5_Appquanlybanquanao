package datdvph44632.fpoly.duan1_appbanhang_dinhvandat.KH_Loader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.GioHangDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.QuanAoDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.GioHang;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.QuanAo;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.FragmentKH.KH_GioHang_Fragment;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.KH_Adapter.KH_GioHang_Adapter;

public class KH_GioHang_Loader extends AsyncTask<String, Void, ArrayList<GioHang>> {
    @SuppressLint("StaticFieldLeak")
    KH_GioHang_Fragment khGioHangFragment;
    @SuppressLint("StaticFieldLeak")
    Context context;
    String TAG = "KH_GioHang_Loader_____";
    QuanAoDAO quanAoDAO;
    GioHangDAO gioHangDAO;
    ArrayList<QuanAo> listLap = new ArrayList<>();
    @SuppressLint("StaticFieldLeak")
    RecyclerView reView;
    @SuppressLint("StaticFieldLeak")
    LinearLayout loadingView;
    @SuppressLint("StaticFieldLeak")
    RelativeLayout relativeLayout;

    public KH_GioHang_Loader(KH_GioHang_Fragment khGioHangFragment, Context context, RecyclerView reView, LinearLayout loadingView, RelativeLayout relativeLayout) {
        this.khGioHangFragment = khGioHangFragment;
        this.context = context;
        this.reView = reView;
        this.loadingView = loadingView;
        this.relativeLayout = relativeLayout;
    }

    @Override
    protected ArrayList<GioHang> doInBackground(String... strings) {
        quanAoDAO = new QuanAoDAO(context);
        gioHangDAO = new GioHangDAO(context);
        String maKH = strings[0];

        listLap = quanAoDAO.selectQuanAo(null, null, null, null);
        ArrayList<GioHang> list = gioHangDAO.selectGioHang(null, "maKH=?", new String[]{maKH}, null);
        for (int i = 0; i < list.size(); i++){
            GioHang gioHang = list.get(i);
            GioHang resetGH = new GioHang(gioHang.getMaGio(), gioHang.getMaQuanAo(), gioHang.getMaKH(),
                    gioHang.getNgayThem(), "Null", gioHang.getSoLuong());
            gioHangDAO.updateGioHang(resetGH);
        }

        return gioHangDAO.selectGioHang(null, "maKH=?", new String[]{maKH}, null);
    }

    @Override
    protected void onPostExecute(ArrayList<GioHang> listGio) {
        super.onPostExecute(listGio);

        if (loadingView != null && relativeLayout != null && reView != null){
            loadingView.setVisibility(View.GONE);
            relativeLayout.setVisibility(View.VISIBLE);
            setupReView(listGio, reView);
        }
    }

    private void setupReView(ArrayList<GioHang> listGio, RecyclerView recyclerView) {
        Log.d(TAG, "setUpReView: true");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        KH_GioHang_Adapter kh_gioHang_adapter = new KH_GioHang_Adapter(listLap, listGio, context, khGioHangFragment);
        recyclerView.setAdapter(kh_gioHang_adapter);
    }
}
