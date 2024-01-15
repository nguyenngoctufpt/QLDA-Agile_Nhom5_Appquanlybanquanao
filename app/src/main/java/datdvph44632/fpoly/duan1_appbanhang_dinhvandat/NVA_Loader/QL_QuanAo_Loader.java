package datdvph44632.fpoly.duan1_appbanhang_dinhvandat.NVA_Loader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.LoaiQuanAoDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.QuanAoDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.HangQuanAo;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.QuanAo;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.FragmentQuanLy.Tab_QuanAo_Fragment;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.NAV_Adapter.QL_QuanAo_Adapter;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Support.GetData;

public class QL_QuanAo_Loader extends AsyncTask<String, Void, ArrayList<QuanAo>> {
    @SuppressLint("StaticFieldLeak")
    Context context;
    String TAG = "QuanAoLoader_____";
    QuanAoDAO quanAoDAO;
    ArrayList<HangQuanAo> listHang = new ArrayList<>();
    LoaiQuanAoDAO loaiQuanAoDAO;
    @SuppressLint("StaticFieldLeak")
    RecyclerView reView;
    @SuppressLint("StaticFieldLeak")
    LinearLayout loadingView, linearEmpty;
    @SuppressLint("StaticFieldLeak")
    RelativeLayout relativeLayout, layoutLaptop;
    Tab_QuanAo_Fragment tab_quanAo_fragment;

    public QL_QuanAo_Loader(Context context, RecyclerView reView, LinearLayout loadingView, LinearLayout linearEmpty, RelativeLayout relativeLayout, RelativeLayout layoutLaptop, Tab_QuanAo_Fragment tab_quanAo_fragment) {
        this.context = context;
        this.reView = reView;
        this.loadingView = loadingView;
        this.linearEmpty = linearEmpty;
        this.relativeLayout = relativeLayout;
        this.tab_quanAo_fragment = tab_quanAo_fragment;
        this.layoutLaptop = layoutLaptop;
    }

    @Override
    protected ArrayList<QuanAo> doInBackground(String... strings) {
        quanAoDAO = new QuanAoDAO(context);
        loaiQuanAoDAO = new LoaiQuanAoDAO(context);
        String hangL = strings[0];
        ArrayList<QuanAo> list = quanAoDAO.selectQuanAo(null, null, null, "maLoaiQuanAo");
        if (list != null) {
            if (list.size() == 0) {
                GetData getData = new GetData(context);
                getData.addDemoAoKaki();
                getData.addDemoAoThun();
                getData.addDemoAoGio();
                getData.addDemoAoHoodie();
                getData.addDemoQuanBo();
                getData.addDemoQuanVai();
            }
        }

        if ("all".equals(hangL)) {
            return quanAoDAO.selectQuanAo(null, null, null, "maLoaiQuanAo");
        } else {
            return quanAoDAO.selectQuanAo(null, "maLoaiQuanAo=?", new String[]{hangL}, "maLoaiQuanAo");
        }
    }

    @Override
    protected void onPostExecute(ArrayList<QuanAo> listLap) {
        super.onPostExecute(listLap);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (loadingView != null && relativeLayout != null && linearEmpty != null && reView != null) {
                    if (listLap != null) {
                        if (listLap.size() == 0) {
                            relativeLayout.setVisibility(View.VISIBLE);
                            loadingView.setVisibility(View.GONE);
                            reView.setVisibility(View.GONE);
                            layoutLaptop.setVisibility(View.GONE);
                            linearEmpty.setVisibility(View.VISIBLE);
                        } else {
                            relativeLayout.setVisibility(View.VISIBLE);
                            loadingView.setVisibility(View.GONE);
                            reView.setVisibility(View.VISIBLE);
                            linearEmpty.setVisibility(View.GONE);
                            layoutLaptop.setVisibility(View.VISIBLE);
                            setupReView(listLap, reView);
                        }
                    }
                }
            }
        }, 1000);
    }

    private void setupReView(ArrayList<QuanAo> listLap, RecyclerView recyclerView) {
        listHang = loaiQuanAoDAO.selectHangQuanAo(null, null, null, null);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        QL_QuanAo_Adapter ql_quanAo_adapter = new QL_QuanAo_Adapter(listLap, listHang, context, tab_quanAo_fragment);
        recyclerView.setAdapter(ql_quanAo_adapter);
    }

}
