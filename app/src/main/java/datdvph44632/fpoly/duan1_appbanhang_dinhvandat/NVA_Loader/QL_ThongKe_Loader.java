package datdvph44632.fpoly.duan1_appbanhang_dinhvandat.NVA_Loader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.NhanVienDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.NhanVien;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.NAV_Adapter.QL_ThongKe_Adapter;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Support.GetData;

import java.util.ArrayList;

public class QL_ThongKe_Loader extends AsyncTask<String, Void, ArrayList<NhanVien>> {
    @SuppressLint("StaticFieldLeak")
    Context context;
    String TAG = "QL_ThongKe_Loader_____";
    NhanVienDAO nhanVienDAO;
    ArrayList<NhanVien> listNV = new ArrayList<>();
    @SuppressLint("StaticFieldLeak")
    RecyclerView reView;
    @SuppressLint("StaticFieldLeak")
    LinearLayout loadingView;
    String[] getDate;

    public QL_ThongKe_Loader(Context context, RecyclerView reView, LinearLayout loadingView, String[] getDate) {
        this.context = context;
        this.reView = reView;
        this.loadingView = loadingView;
        this.getDate = getDate;
    }

    @Override
    protected ArrayList<NhanVien> doInBackground(String... strings) {
        GetData data = new GetData(context);
        nhanVienDAO = new NhanVienDAO(context);
        listNV = nhanVienDAO.selectNhanVien(null, null, null, null);
        data.addDataDoanhSo(listNV, getDate);

        listNV.clear();
        return nhanVienDAO.selectNhanVien(null, null, null, "doanhSo DESC");
    }

    @Override
    protected void onPostExecute(ArrayList<NhanVien> listNV) {
        super.onPostExecute(listNV);

        if (loadingView != null && reView != null){
            loadingView.setVisibility(View.GONE);
            setupReView(listNV, reView);
        }
    }

    private void setupReView(ArrayList<NhanVien> listNV, RecyclerView recyclerView) {
        Log.d(TAG, "setUpReView: true");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        QL_ThongKe_Adapter ql_thongKe_adapter = new QL_ThongKe_Adapter(listNV, context);
        recyclerView.setAdapter(ql_thongKe_adapter);
    }

}
