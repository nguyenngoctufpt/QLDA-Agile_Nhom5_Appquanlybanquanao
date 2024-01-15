package datdvph44632.fpoly.duan1_appbanhang_dinhvandat.KH_Loader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.DiaChiDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.DiaChi;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.KH_Adapter.KH_DiaChi_Adapter;

public class KH_DiaChi_Loader extends AsyncTask<String, Void, ArrayList<DiaChi>> {
    @SuppressLint("StaticFieldLeak")
    Context context;
    String TAG = "KH_DiaChi_Loader_____";
    DiaChiDAO diaChiDAO;
    @SuppressLint("StaticFieldLeak")
    RecyclerView reView;
    public KH_DiaChi_Loader(Context context, RecyclerView reView) {
        this.context = context;
        this.reView = reView;
    }

    @Override
    protected ArrayList<DiaChi> doInBackground(String... strings) {
        diaChiDAO = new DiaChiDAO(context);
        String maKH = strings[0];

        return diaChiDAO.selectDiaChi(null, "maKH=?", new String[]{maKH}, null);
    }

    @Override
    protected void onPostExecute(ArrayList<DiaChi> listVou) {
        super.onPostExecute(listVou);

        if (reView != null) {
            setupReView(listVou, reView);
        }
    }

    private void setupReView(ArrayList<DiaChi> listDC, RecyclerView recyclerView) {
        Log.d(TAG, "setUpReView: true");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        KH_DiaChi_Adapter kh_diaChi_adapter = new KH_DiaChi_Adapter(listDC, context);
        recyclerView.setAdapter(kh_diaChi_adapter);
    }

}