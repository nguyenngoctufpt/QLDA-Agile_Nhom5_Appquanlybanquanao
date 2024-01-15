package datdvph44632.fpoly.duan1_appbanhang_dinhvandat.KH_Adapter;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Activity.DiaChi_Manager_Activity;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.DiaChiDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.KhachHangDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.DiaChi;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.IdData;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.KhachHang;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.R;

public class KH_DiaChi_Adapter extends RecyclerView.Adapter<KH_DiaChi_Adapter.AuthorViewHolder> {

    Context context;
    ArrayList<DiaChi> listDC;
    DiaChiDAO diaChiDAO;
    KhachHang khachHang;
    String TAG = "KH_DiaChi_Adapter_____";
    int selectedPos = -1, posDC;

    public KH_DiaChi_Adapter(ArrayList<DiaChi> listDC, Context context) {
        this.listDC = listDC;
        this.context = context;
        diaChiDAO = new DiaChiDAO(context);
    }

    @NonNull
    @Override
    public AuthorViewHolder onCreateViewHolder(@NonNull ViewGroup vGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.cardview_diachi, vGroup, false);
        return new AuthorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AuthorViewHolder author, @SuppressLint("RecyclerView") final int pos) {
        String maDC = IdData.getInstance().getIdDC();
        DiaChi diaChi = setRow(pos, author, maDC);

        if (pos == selectedPos){
            author.itemDC.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_square_selected));
        } else {
            author.itemDC.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_square));
        }

        author.itemView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                selectedPos = pos;

                IdData.getInstance().setIdDC(diaChi.getMaDC());
                setRow(pos, author, maDC);
                notifyDataSetChanged();
            }
        });

        author.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosDC(pos);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return listDC.size();
    }



    public class AuthorViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView name, sdt, dc;
        LinearLayout itemDC;

        public AuthorViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView_TenKH);
            sdt = itemView.findViewById(R.id.textView_SDT);
            dc = itemView.findViewById(R.id.textView_DiaChi);
            itemDC = itemView.findViewById(R.id.item_DiaChi);
            itemView.setOnCreateContextMenuListener((View.OnCreateContextMenuListener) this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem edit = menu.add(Menu.NONE, R.id.item_CapNhat, Menu.NONE, "Cập nhật");
            MenuItem delete = menu.add(Menu.NONE, R.id.item_Xoa, Menu.NONE, "Xóa");
            edit.setOnMenuItemClickListener(onEditMenu);
            delete.setOnMenuItemClickListener(onEditMenu);
        }

        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @SuppressLint({"NonConstantResourceId", "NotifyDataSetChanged"})
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                DiaChi diaChi = listDC.get(getPosDC());
                getUser();

                if (item.getItemId() == R.id.item_Xoa){
                    diaChiDAO.deleteDiaChi(diaChi);
                    listDC.clear();
                    if (khachHang != null){
                        listDC.addAll(diaChiDAO.selectDiaChi(null, "maKH=?", new String[]{khachHang.getMaKH()}, null));
                    }
                    notifyDataSetChanged();
                } else if (item.getItemId() == R.id.item_CapNhat) {
                    Intent intent = new Intent(context, DiaChi_Manager_Activity.class);
                    intent.putExtra("typeDC" , 1);
                    intent.putExtra("maDC", diaChi.getMaDC());
                    context.startActivity(intent);
                }

//                switch (item.getItemId()) {
//                    case R.id.item_Xoa:
//
//                        break;
//                    case R.id.item_CapNhat:
//
//                        break;
//                }

                return true;
            }
        };
    }

    private void getUser() {
        SharedPreferences pref = context.getSharedPreferences("Who_Login", MODE_PRIVATE);
        if (pref == null) {
            khachHang = null;
        } else {
            String user = pref.getString("who", "");
            KhachHangDAO khachHangDAO = new KhachHangDAO(context);
            ArrayList<KhachHang> list = khachHangDAO.selectKhachHang(null, "maKH=?", new String[]{user}, null);
            if (list.size() > 0) {
                khachHang = list.get(0);
            }
        }
    }

    public DiaChi setRow(int pos, @NonNull AuthorViewHolder author, String maDC) {
        Log.d(TAG, "setRow: " + pos);
        DiaChi diaChi = listDC.get(pos);

        if (maDC != null){
            if (maDC.equals(diaChi.getMaDC())){
                selectedPos = pos;
            }
        }

        author.name.setText(diaChi.getTenNguoiNhan());
        author.sdt.setText(diaChi.getSDT());
        author.dc.setText(diaChi.getDiaChi() + " - " + diaChi.getXaPhuong()
                + " - " + diaChi.getQuanHuyen() + " - " + diaChi.getThanhPho());
        return diaChi;
    }

    public int getPosDC() {
        return posDC;
    }

    public void setPosDC(int posDC) {
        this.posDC = posDC;
    }
}
