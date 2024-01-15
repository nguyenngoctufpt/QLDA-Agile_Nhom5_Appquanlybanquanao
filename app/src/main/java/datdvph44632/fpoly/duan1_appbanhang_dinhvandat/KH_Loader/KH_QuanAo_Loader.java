package datdvph44632.fpoly.duan1_appbanhang_dinhvandat.KH_Loader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.QuanAoDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.QuanAo;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.KH_Adapter.KH_QuanAo_Adapter;

public class KH_QuanAo_Loader extends AsyncTask<String, Void, ArrayList<QuanAo>> {
    @SuppressLint("StaticFieldLeak")
    Context context;
    String TAG = "QuanAo_Loader_____";
    QuanAoDAO quanAoDAO;
    @SuppressLint("StaticFieldLeak")
    RecyclerView reView;

    public KH_QuanAo_Loader(Context context, RecyclerView reView) {
        this.context = context;
        this.reView = reView;
    }

    @Override
    protected ArrayList<QuanAo> doInBackground(String... strings) {

        quanAoDAO = new QuanAoDAO(context);
        String maHangLap = strings[0];

        return quanAoDAO.selectQuanAo(null, "maLoaiQuanAo=?", new String[]{maHangLap}, null);
    }

    @Override
    protected void onPostExecute(ArrayList<QuanAo> listLap) {
        super.onPostExecute(listLap);
        quanAoDAO = new QuanAoDAO(context);

        if (reView != null){
            setupReView(listLap, reView);
        }

    }

    private void setupReView(ArrayList<QuanAo> listLap, RecyclerView recyclerView){
        KH_QuanAo_Adapter kh_quanAo_adapter = new KH_QuanAo_Adapter(listLap, context);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(kh_quanAo_adapter);
    }

}
