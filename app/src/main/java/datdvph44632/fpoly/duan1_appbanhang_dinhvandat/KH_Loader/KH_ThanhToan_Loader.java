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

import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.ActivityKH.KH_ThanhToan_Activity;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.GioHangDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.QuanAoDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.VoucherDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.GioHang;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.QuanAo;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.Voucher;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.KH_Adapter.KH_ThanhToan_Adapter;

public class KH_ThanhToan_Loader extends AsyncTask<String, Void, ArrayList<GioHang>> {
    @SuppressLint("StaticFieldLeak")
    Context context;
    String TAG = "KH_ThanhToan_Loader_____";
    GioHangDAO gioHangDAO;
    QuanAoDAO quanAoDAO;
    VoucherDAO voucherDAO;
    ArrayList<QuanAo> listLap = new ArrayList<>();
    ArrayList<Voucher> listVou = new ArrayList<>();
    ArrayList<GioHang> listGio = new ArrayList<>();
    @SuppressLint("StaticFieldLeak")
    RecyclerView reView;
    @SuppressLint("StaticFieldLeak")
    LinearLayout loadingView;
    @SuppressLint("StaticFieldLeak")
    RelativeLayout relativeLayout;
    QuanAo quanAo;
    @SuppressLint("StaticFieldLeak")
    KH_ThanhToan_Activity khThanhToanActivity;

    public KH_ThanhToan_Loader(Context context, QuanAo quanAo, RecyclerView reView,
                               LinearLayout loadingView, RelativeLayout relativeLayout,
                               KH_ThanhToan_Activity khThanhToanActivity) {
        this.context = context;
        this.quanAo = quanAo;
        this.reView = reView;
        this.loadingView = loadingView;
        this.relativeLayout = relativeLayout;
        this.khThanhToanActivity = khThanhToanActivity;
    }

    @Override
    protected ArrayList<GioHang> doInBackground(String... strings) {
        gioHangDAO = new GioHangDAO(context);
        quanAoDAO = new QuanAoDAO(context);
        voucherDAO = new VoucherDAO(context);
        String maKH = strings[0];

        if (quanAo == null){
            listLap = quanAoDAO.selectQuanAo(null, null, null, null);
            listGio =  gioHangDAO.selectGioHang(null, "maKH=?", new String[]{maKH}, null);
            listVou = voucherDAO.selectVoucher(null, null, null, null);
        } else {
            listLap.add(quanAo);
            listGio.add(new GioHang("No Data", quanAo.getMaQuanAo(), "No Data", "No Data",
                    "Null", 1));
            listVou = voucherDAO.selectVoucher(null, null, null, null);
        }
        return listGio;
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
        KH_ThanhToan_Adapter kh_thanhToan_adapter = new KH_ThanhToan_Adapter(listLap, listGio, listVou, context, khThanhToanActivity);
        recyclerView.setAdapter(kh_thanhToan_adapter);
    }

}
