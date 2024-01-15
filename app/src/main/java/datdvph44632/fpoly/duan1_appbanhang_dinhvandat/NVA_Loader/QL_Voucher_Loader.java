package datdvph44632.fpoly.duan1_appbanhang_dinhvandat.NVA_Loader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.VoucherDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.Voucher;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.NAV_Adapter.QL_Voucher_Adapter;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Support.GetData;

public class QL_Voucher_Loader extends AsyncTask<String, Void, ArrayList<Voucher>> {
    @SuppressLint("StaticFieldLeak")
    Context context;
    String TAG = "QL_Voucher_Loader_____";
    VoucherDAO voucherDAO;
    @SuppressLint("StaticFieldLeak")
    RecyclerView reView;
    @SuppressLint("StaticFieldLeak")
    LinearLayout loadingView, linearEmpty;
    @SuppressLint("StaticFieldLeak")
    RelativeLayout relativeLayout;

    public QL_Voucher_Loader(Context context, RecyclerView reView, LinearLayout loadingView, LinearLayout linearEmpty, RelativeLayout relativeLayout) {
        this.reView = reView;
        this.context = context;
        this.loadingView = loadingView;
        this.linearEmpty = linearEmpty;
        this.relativeLayout = relativeLayout;
    }

    @Override
    protected ArrayList<Voucher> doInBackground(String... strings) {
        voucherDAO = new VoucherDAO(context);
        ArrayList<Voucher> listVou = voucherDAO.selectVoucher(null, null, null, null);

        if (listVou == null) {
            Log.d(TAG, "onCreateView: list null");
            addDemoVoucher();
        } else {
            if (listVou.size() == 0) {
                Log.d(TAG, "onCreateView: list not null");
                Log.d(TAG, "onCreateView: list size = " + listVou.size());
                addDemoVoucher();
            }
        }

        return voucherDAO.selectVoucher(null, null, null, null);
    }

    @Override
    protected void onPostExecute(ArrayList<Voucher> listVou) {
        super.onPostExecute(listVou);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (loadingView != null && reView != null && linearEmpty != null && relativeLayout != null) {
                    if (listVou != null) {
                        if (listVou.size() == 0) {
                            relativeLayout.setVisibility(View.VISIBLE);
                            loadingView.setVisibility(View.GONE);
                            reView.setVisibility(View.GONE);
                            linearEmpty.setVisibility(View.VISIBLE);
                        } else {
                            relativeLayout.setVisibility(View.VISIBLE);
                            reView.setVisibility(View.VISIBLE);
                            loadingView.setVisibility(View.GONE);
                            linearEmpty.setVisibility(View.GONE);
                            setupReView(listVou, reView);
                        }
                    }
                }
            }
        }, 1000);
    }

    private void setupReView(ArrayList<Voucher> listVou, RecyclerView recyclerView) {
        Log.d(TAG, "setUpReView: true");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        QL_Voucher_Adapter ql_voucher_adapter = new QL_Voucher_Adapter(listVou, context);
        recyclerView.setAdapter(ql_voucher_adapter);
    }

    private void addDemoVoucher() {
        GetData getData = new GetData(context);
        Voucher vou1 = new Voucher("", "NOVEM1611", "20%", "2023-07-11", "2023-09-20");
        voucherDAO.insertVoucher(vou1);
        getData.addDataUseVoucher();

        Voucher vou2 = new Voucher("", "DECEM1212", "12%", "2023-07-12", "2023-09-12");
        voucherDAO.insertVoucher(vou2);
        getData.addDataUseVoucher();

        Voucher vou3 = new Voucher("", "NOEL2512", "25%", "2023-07-25", "2023-09-25");
        voucherDAO.insertVoucher(vou3);
        getData.addDataUseVoucher();
    }
}
