package datdvph44632.fpoly.duan1_appbanhang_dinhvandat.NVA_Loader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.DonHangDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.KhachHangDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.QuanAoDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.DonHang;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.KhachHang;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.QuanAo;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.NAV_Adapter.QL_DonHang_Adapter;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Support.ChangeType;

public class QL_DonHang_Loader extends AsyncTask<String, Void, ArrayList<DonHang>> {
    @SuppressLint("StaticFieldLeak")
    Context context;
    String TAG = "QL_DonHang_Loader_____";
    @SuppressLint("StaticFieldLeak")
    RecyclerView reView;
    @SuppressLint("StaticFieldLeak")
    TextView countDH;
    @SuppressLint("StaticFieldLeak")
    LinearLayout loadingView, linearEmpty;
    @SuppressLint("StaticFieldLeak")
    RelativeLayout relativeLayout;
    QuanAoDAO quanAoDAO;
    DonHangDAO donHangDAO;
    KhachHangDAO khachHangDAO;
    ArrayList<QuanAo> listLap = new ArrayList<>();
    ArrayList<DonHang> listDon = new ArrayList<>();
    ArrayList<KhachHang> listKH = new ArrayList<>();
    ChangeType changeType = new ChangeType();

    public QL_DonHang_Loader(Context context, RecyclerView reView, TextView countDH, LinearLayout loadingView, LinearLayout linearEmpty, RelativeLayout relativeLayout) {
        this.context = context;
        this.reView = reView;
        this.countDH = countDH;
        this.loadingView = loadingView;
        this.relativeLayout = relativeLayout;
        this.linearEmpty = linearEmpty;
    }

    @Override
    protected ArrayList<DonHang> doInBackground(String... strings) {
        quanAoDAO = new QuanAoDAO(context);
        donHangDAO = new DonHangDAO(context);
        khachHangDAO = new KhachHangDAO(context);
        listLap = quanAoDAO.selectQuanAo(null, null, null, null);
        listDon = donHangDAO.selectDonHang(null, null, null, null);
        listKH = khachHangDAO.selectKhachHang(null, null, null, null);
        Date currentTime = Calendar.getInstance().getTime();
        int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(currentTime));
        int month = Integer.parseInt(new SimpleDateFormat("MM").format(currentTime));
        String[] time = changeType.intDateToStringDate(month, year);
        String type = strings[0];

        if (listDon != null) {
            if (listDon.size() == 0) {
                addDemoDH();
            }
        }

        if (type.equals("NVD")) {
            return donHangDAO.selectDonHang(null, "trangThai=? and maNV=?", new String[]{"Chờ xác nhận", "No Data"}, "ngayMua");
        } else if (type.equals("create")) {
            return donHangDAO.selectDonHang(null, "trangThai=? and ngayMua>=? and ngayMua<?", new String[]{"Hoàn thành", time[0], time[1]}, "ngayMua");
        } else {
            return donHangDAO.selectDonHang(null, "trangThai=?", new String[]{"Hoàn thành"}, "ngayMua");
        }
    }

    @Override
    protected void onPostExecute(ArrayList<DonHang> listDon) {
        super.onPostExecute(listDon);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (loadingView != null && relativeLayout != null && linearEmpty != null && reView != null && countDH != null) {
                    if (listDon != null) {
                        if (listDon.size() == 0) {
                            relativeLayout.setVisibility(View.VISIBLE);
                            loadingView.setVisibility(View.GONE);
                            reView.setVisibility(View.GONE);
                            linearEmpty.setVisibility(View.VISIBLE);
                            countDH.setText("0");
                        } else {
                            relativeLayout.setVisibility(View.VISIBLE);
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
        QL_DonHang_Adapter ql_donHang_adapter = new QL_DonHang_Adapter(listLap, listDon, listKH, context, countDH);
        recyclerView.setAdapter(ql_donHang_adapter);
        setCountDH(listDon);
    }

    private void setCountDH(ArrayList<DonHang> listDon) {
        countDH.setText(String.valueOf(listDon.size()));
    }

    private void addDemoDH() {
        ChangeType changeType = new ChangeType();

        DonHang dh0 = new DonHang("DH0", "1", "1", "4", "1", "No Data",
                "The Reverie Saigon nằm bên trong tòa nhà Times Square Sài Gòn - Nguyễn Huệ - Quận 1 - TP Hồ Chí Minh",
                "2022-11-19", "Thanh toán khi nhận hàng", "Hoàn thành", "false", changeType.stringToStringMoney("63816000"), 3);
        donHangDAO.insertDonHang(dh0);

        DonHang dh1 = new DonHang("DH1", "2", "2", "1", "Null", "No Data",
                "The Reverie Saigon nằm bên trong tòa nhà Times Square Sài Gòn - Nguyễn Huệ - Quận 1 - TP Hồ Chí Minh",
                "2022-11-25", "Thanh toán khi nhận hàng", "Hoàn thành", "false", changeType.stringToStringMoney("24180000"), 2);
        donHangDAO.insertDonHang(dh1);

        DonHang dh2 = new DonHang("DH2", "1", "3", "2", "Null", "No Data",
                "Sofitel Metropole Hà Nội Số 15 - Phố Ngô Quyền - Hoàn Kiếm - Hà Nội",
                "2022-12-02", "Thanh toán khi nhận hàng", "Hoàn thành", "false", changeType.stringToStringMoney("61640000"), 1);
        donHangDAO.insertDonHang(dh2);
    }
}

