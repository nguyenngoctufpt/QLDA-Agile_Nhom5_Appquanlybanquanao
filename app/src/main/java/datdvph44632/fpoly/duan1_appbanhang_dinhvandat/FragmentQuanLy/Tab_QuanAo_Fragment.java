package datdvph44632.fpoly.duan1_appbanhang_dinhvandat.FragmentQuanLy;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.ActivityNV_Admin.QuanAo_Manager_Activity;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.LoaiQuanAoDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.QuanAoDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.HangQuanAo;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.QuanAo;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.NAV_Adapter.QL_QuanAo_Adapter;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.NVA_Loader.QL_QuanAo_Loader;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.R;

public class Tab_QuanAo_Fragment extends Fragment {

    FloatingActionButton themquanAo;
    String TAG = "Tab_Quanao_Fragment_____";
    QL_QuanAo_Loader QL_quanAo_loader;
    RecyclerView reView;
    String hangL = "all";
    LinearLayout loadingView, linearLayoutEmpty;
    RelativeLayout relativeLayout, layoutquanAo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_quanao, container, false);
        themquanAo = view.findViewById(R.id.button_Add_QuanAo);
        loadingView = view.findViewById(R.id.loadingView);
        relativeLayout = view.findViewById(R.id.layoutView);
        layoutquanAo = view.findViewById(R.id.layoutQuanAo);
        reView = view.findViewById(R.id.recyclerView_QuanAo);
        linearLayoutEmpty = view.findViewById(R.id.linearQuanAoEmpty);

        useToolbar(view);
        getHangLaptop(view);
        setQuanAo();

        themquanAo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), QuanAo_Manager_Activity.class));
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setQuanAo();
    }

    private void useToolbar(View view) {
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar_Account);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        if (toolbar != null) {
            QuanAoDAO quanAoDAO = new QuanAoDAO(getContext());
            LoaiQuanAoDAO loaiQuanAoDAO = new LoaiQuanAoDAO(getContext());
            ArrayList<QuanAo> listLap = quanAoDAO.selectQuanAo(null, null, null, null);
            ArrayList<HangQuanAo> listHang = loaiQuanAoDAO.selectHangQuanAo(null, null, null, null);
            ImageButton btn_search = toolbar.findViewById(R.id.imageButton_Search_Toolbar);
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LayoutInflater inft = ((Activity) getContext()).getLayoutInflater();
            View dialogView = inft.inflate(R.layout.dialog_text_find, null);
            AppCompatButton buttonSearch = dialogView.findViewById(R.id.button_Search_Dialog);
            EditText editTextSearch = dialogView.findViewById(R.id.editText_Search);
            editTextSearch.setHint("Tên Quần áo...");

            builder.setView(dialogView);
            Dialog dialog = builder.create();
            btn_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.show();
                    if (editTextSearch.getHint().equals("Tên Quần áo...")){
                        editTextSearch.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                String input = editTextSearch.getText().toString();
                                ArrayList<QuanAo> getList = new ArrayList<>();
                                if (!input.equals("")) {
                                    for (QuanAo lap : listLap) {
                                        if (lap.getTenQuanAo().matches(".*?" + input + ".*")) {
                                            getList.add(lap);
                                        }
                                    }
                                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
                                    reView.setLayoutManager(mLayoutManager);
                                    QL_QuanAo_Adapter ql_quanAo_adapter = new QL_QuanAo_Adapter(getList, listHang, getContext(), Tab_QuanAo_Fragment.this);
                                    reView.setAdapter(ql_quanAo_adapter);
                                } else {
                                    getHangLaptop(view);
                                    setQuanAo();
                                }
                            }
                        });
                    }
                }
            });

            buttonSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
    }

    private void getHangLaptop(View view) {
        AppCompatButton all = view.findViewById(R.id.button_quanao_All);
        AppCompatButton aogio = view.findViewById(R.id.button_quanao_Aogio);
        AppCompatButton aohoodie = view.findViewById(R.id.button_quanao_Aohoodie);
        AppCompatButton aokaki = view.findViewById(R.id.button_quanao_aokaki);
        AppCompatButton aothun = view.findViewById(R.id.button_quanao_Aothun);
        AppCompatButton quanbo = view.findViewById(R.id.button_quanao_quanbo);
        AppCompatButton quanvai = view.findViewById(R.id.button_quanao_Quanvai);

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hangL = "all";
                setQuanAo();
            }
        });

        aogio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hangL = "LAoGio";
                setQuanAo();
            }
        });

        aohoodie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hangL = "LAoHoodie";
                setQuanAo();
            }
        });

        aokaki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hangL = "LAoKaki";
                setQuanAo();
            }
        });

        aothun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hangL = "LAoThun";
                setQuanAo();
            }
        });

        quanbo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hangL = "LQuanBo";
                setQuanAo();
            }
        });

        quanvai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hangL = "LQuanVai";
                setQuanAo();
            }
        });
    }

    private void setQuanAo() {
        if ("all".equals(hangL)) {
            QL_quanAo_loader = new QL_QuanAo_Loader(getContext(), reView, loadingView, linearLayoutEmpty, relativeLayout, layoutquanAo, Tab_QuanAo_Fragment.this);
            QL_quanAo_loader.execute("all");
        } else {
            QL_quanAo_loader = new QL_QuanAo_Loader(getContext(), reView, loadingView, linearLayoutEmpty, relativeLayout, layoutquanAo, Tab_QuanAo_Fragment.this);
            QL_quanAo_loader.execute(hangL);
        }
    }
}