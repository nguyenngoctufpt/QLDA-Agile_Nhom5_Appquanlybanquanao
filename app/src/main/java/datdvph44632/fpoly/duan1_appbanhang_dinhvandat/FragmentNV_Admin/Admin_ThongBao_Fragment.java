package datdvph44632.fpoly.duan1_appbanhang_dinhvandat.FragmentNV_Admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.KH_Loader.All_ThongBao_Loader;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.R;

public class Admin_ThongBao_Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_thongbao, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_ThongBao);
        LinearLayout linearLayout = view.findViewById(R.id.loadingView);
        LinearLayout emptyLayout = view.findViewById(R.id.empty_ThongBao);
        TextView textView = view.findViewById(R.id.textView_Date_Notifi);
        Date currentTime = Calendar.getInstance().getTime();
        String date = new SimpleDateFormat("dd/MM/yyyy").format(currentTime);
        textView.setText(date);

        All_ThongBao_Loader all_thongBao_loader = new All_ThongBao_Loader(getContext(),
                recyclerView, linearLayout, emptyLayout, "ad");
        all_thongBao_loader.execute("Admin");

        return view;
    }
}