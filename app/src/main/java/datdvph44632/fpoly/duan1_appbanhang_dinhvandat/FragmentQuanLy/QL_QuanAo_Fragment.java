package datdvph44632.fpoly.duan1_appbanhang_dinhvandat.FragmentQuanLy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.tabs.TabLayout;

import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.R;

public class QL_QuanAo_Fragment extends Fragment {

    FragmentManager fragmentManager;
    Tab_QuanAo_Fragment tab_quanao_fragment;
    Tab_HangQuanAo_Fragment tab_hangquanAo_fragment;
    TabLayout quanaoTab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ql_quanao, container, false);
        fragmentManager = getChildFragmentManager();
        tab_quanao_fragment = (Tab_QuanAo_Fragment) fragmentManager.findFragmentById(R.id.laptopFrag);
        tab_hangquanAo_fragment = (Tab_HangQuanAo_Fragment) fragmentManager.findFragmentById(R.id.hangLaptopFrag);
        quanaoTab = view.findViewById(R.id.laptopTab);
        quanaotab();
        return view;
    }

    public void quanaotab() {
        fragmentManager.beginTransaction().hide(tab_hangquanAo_fragment).commit();
        quanaoTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        fragmentManager.beginTransaction().show(tab_quanao_fragment).commit();
                        fragmentManager.beginTransaction().hide(tab_hangquanAo_fragment).commit();
                        break;
                    case 1:
                        fragmentManager.beginTransaction().show(tab_hangquanAo_fragment).commit();
                        fragmentManager.beginTransaction().hide(tab_quanao_fragment).commit();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}