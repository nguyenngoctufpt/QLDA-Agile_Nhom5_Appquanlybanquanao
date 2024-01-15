package datdvph44632.fpoly.duan1_appbanhang_dinhvandat.FragmentKH;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.KhachHangDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.QuanAoDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.KhachHang;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.QuanAo;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.KH_Adapter.KH_QuanAo_Adapter;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.R;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Support.ChangeType;

import java.util.ArrayList;

public class KH_Home_Fragment extends Fragment {

    QuanAoDAO quanAoDAO;
    KhachHang khachHang;
    ChangeType changeType = new ChangeType();
    ImageView imageView;
    RecyclerView recyclerView;
    ImageButton imageButton;
    EditText editText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kh_home, container, false);
        imageView = view.findViewById(R.id.imageView_Home);
        recyclerView = view.findViewById(R.id.recyclerView_QuanAo_Search);
        imageButton = view.findViewById(R.id.imageButton_Search);
        editText = view.findViewById(R.id.editText_Search);
        quanAoDAO = new QuanAoDAO(getContext());
        ArrayList<QuanAo> list = quanAoDAO.selectQuanAo(null, null, null, null);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<QuanAo> listSearch = new ArrayList<>();
                String input = editText.getText().toString();
                for (QuanAo quanAo : list) {
                    if (quanAo.getTenQuanAo().matches(".*?" + input + ".*") && !input.equals("")){
                        listSearch.add(quanAo);
                    }
                }
                if (listSearch.size() > 0){
                    imageView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    KH_QuanAo_Adapter kh_quanAo_adapter = new KH_QuanAo_Adapter(listSearch, getContext());
                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setAdapter(kh_quanAo_adapter);
                } else {
                    Toast.makeText(getContext(), "Không tìm thấy quan ao nào!", Toast.LENGTH_SHORT).show();
                    imageView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }
        });

        getUser();
        if (khachHang != null) {
            TextView textView = view.findViewById(R.id.textView_TenUser);
            textView.setText("Xin chào, " + changeType.fullNameKhachHang(khachHang));
        }
        return view;
    }

    private void getUser() {
        SharedPreferences pref = getContext().getSharedPreferences("Who_Login", MODE_PRIVATE);
        if (pref == null) {
            khachHang = null;
        } else {
            String user = pref.getString("who", "");
            KhachHangDAO khachHangDAO = new KhachHangDAO(getContext());
            ArrayList<KhachHang> list = khachHangDAO.selectKhachHang(null, "maKH=?", new String[]{user}, null);
            if (list.size() > 0) {
                khachHang = list.get(0);
            }
        }
    }

}