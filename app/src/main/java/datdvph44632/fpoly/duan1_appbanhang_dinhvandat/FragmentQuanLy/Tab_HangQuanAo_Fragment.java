package datdvph44632.fpoly.duan1_appbanhang_dinhvandat.FragmentQuanLy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.NVA_Loader.QL_HangQuanAo_Loader;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.R;

public class Tab_HangQuanAo_Fragment extends Fragment {

    String TAG = "Tab_HangQuanAo_Fragment_____";
    LinearLayout linearLayout;
    GridView gridView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_hang_quanao, container, false);
        linearLayout = view.findViewById(R.id.loadingView);
        gridView = view.findViewById(R.id.gridView_hangLaptop);

        QL_HangQuanAo_Loader QL_hangQuanAo_loader = new QL_HangQuanAo_Loader(getContext(), gridView, linearLayout);
        QL_hangQuanAo_loader.execute("");

        return view;
    }

}