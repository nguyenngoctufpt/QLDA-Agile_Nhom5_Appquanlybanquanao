package datdvph44632.fpoly.duan1_appbanhang_dinhvandat.NAV_Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Activity.Info_QuanAo_Activity;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.QuanAoDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.HangQuanAo;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.QuanAo;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.FragmentQuanLy.Tab_QuanAo_Fragment;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.R;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Support.ChangeType;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Support.GetData;

public class QL_QuanAo_Adapter extends RecyclerView.Adapter<QL_QuanAo_Adapter.AuthorViewHolder> {

    Spinner hangLapSpinner;
    ChangeType changeType = new ChangeType();
    GetData getData;
    TextInputLayout textInput_TenQuanAo, textInput_GiaTien, textInput_SoLuong;
    ImageView imageView_QuanAo;
    AppCompatButton button_QuanAo_Manager;
    Context context;
    ArrayList<HangQuanAo> listHang;
    ArrayList<QuanAo> listquanAo, list8QuanAo;
    QuanAo quanAo;
    QuanAoDAO quanAoDAO;
    int posLap, posPage, maxPage;
    String TAG = "QL_Laptop_Adapter_____";
    Tab_QuanAo_Fragment tab_quanAo_fragment;
    TextView tvPrev, tvPage, tvNext;

    public QL_QuanAo_Adapter(ArrayList<QuanAo> listquanAo, ArrayList<HangQuanAo> listHang, Context context, Tab_QuanAo_Fragment tab_quanAo_fragment) {
        this.listquanAo = listquanAo;
        this.listHang = listHang;
        this.context = context;
        this.tab_quanAo_fragment = tab_quanAo_fragment;
        quanAoDAO = new QuanAoDAO(context);
        getData = new GetData(context);
        posPage = 0;
        list8QuanAo = getData.getQuanAo(listquanAo, posPage);
    }

    @NonNull
    @Override
    public AuthorViewHolder onCreateViewHolder(@NonNull ViewGroup vGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.cardview_nva_quanao, vGroup, false);
        return new AuthorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AuthorViewHolder author, @SuppressLint("RecyclerView") final int pos) {
        setRow(pos, author);
        set8Laptop();

        author.delete.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
//                if (getPosLap() >= 0 && getPosLap() < list8QuanAo.size()) {
//                    quanAo = list8QuanAo.get(getPosLap());
//                    // Rest of your code here...
//                } else {
//                    // Handle the case where the index is out of bounds
//                    Log.e(TAG, "Invalid index: " + getPosLap());
//                }
//
//                quanAoDAO.deleteQuanAo(quanAo);
//                list8QuanAo.remove(getPosLap());
//                notifyDataSetChanged();

                int posToRemove = getPosLap();
                if (posToRemove >= 0 && posToRemove < list8QuanAo.size()) {
                    quanAo = list8QuanAo.get(posToRemove);
                    quanAoDAO.deleteQuanAo(quanAo);
                    list8QuanAo.remove(posToRemove);
                    notifyDataSetChanged();
                } else {
                    // Handle the case where the index is out of bounds
                    Log.e(TAG, "Invalid index: " + posToRemove);
                }
            }
        });

        author.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Info_QuanAo_Activity.class);
                QuanAo quanAo = list8QuanAo.get(pos);
                if (quanAo != null) {
                    final Bundle bundle = new Bundle();
                    bundle.putBinder("laptop", quanAo);
                    Log.d(TAG, "onBindViewHolder: Quanao: " + quanAo.toString());
                    intent.putExtras(bundle);
                    intent.putExtra("openFrom", "other");
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "Load thông tin sản phẩm lỗi!\nXin vui lòng thử lại sau!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        author.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosLap(pos);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list8QuanAo.size();
    }

    public class AuthorViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        ImageView imgLaptop, imgHang;
        TextView name, gia, soLuong, daBan;
        ImageButton delete;

        public AuthorViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLaptop = itemView.findViewById(R.id.imageView_QuanAo);
            imgHang = itemView.findViewById(R.id.imageView_HangQuanAo);
            name = itemView.findViewById(R.id.textView_TenQuanAo);
            gia = itemView.findViewById(R.id.textView_GiaTien);
            soLuong = itemView.findViewById(R.id.textView_Soluong);
            delete = itemView.findViewById(R.id.imageButton_Delete);
            daBan = itemView.findViewById(R.id.textView_SoSP_DaBan);
            itemView.setOnCreateContextMenuListener((View.OnCreateContextMenuListener) this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem edit = menu.add(Menu.NONE, R.id.item_CapNhat, Menu.NONE, "Cập nhật");
            edit.setOnMenuItemClickListener(onEditMenu);
        }

        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                QuanAo quanAo = list8QuanAo.get(getPosLap());
                if (item.getItemId() == R.id.item_CapNhat) {
                    openDialogUpdate(quanAo);
                }
                return true;
            }
        };
    }

    public void setRow(int pos, @NonNull AuthorViewHolder author) {
        Log.d(TAG, "setRow: " + pos);
        QuanAo quanAo = list8QuanAo.get(pos);
        HangQuanAo hangQuanAo = new HangQuanAo("No Data", "No Data", new byte[]{});
        Log.d(TAG, "setRow: quanao: " + quanAo.toString());


        for (int i = 0; i < listHang.size(); i++) {
            HangQuanAo getHang = listHang.get(i);
            if (quanAo.getMaHangQuanAo().equals(getHang.getMaHangLap())) {
                hangQuanAo = getHang;
            }
        }

        ChangeType changeType = new ChangeType();
        Bitmap anhLap = changeType.byteToBitmap(quanAo.getAnhquanAo());
        Bitmap anhHang = changeType.byteToBitmap(hangQuanAo.getAnhHang());

        author.imgLaptop.setImageBitmap(anhLap);
        author.imgHang.setImageBitmap(anhHang);
        author.name.setText(quanAo.getTenQuanAo());
        author.gia.setText("Giá tiền: " + quanAo.getGiaTien());
        author.soLuong.setText(String.valueOf(quanAo.getSoLuong()));
        author.daBan.setText(String.valueOf(quanAo.getDaBan()));
    }

    private void set8Laptop() {
        if (tab_quanAo_fragment != null) {
            tvPage = tab_quanAo_fragment.getActivity().findViewById(R.id.textView_Page);
            tvPrev = tab_quanAo_fragment.getActivity().findViewById(R.id.textView_Prev);
            tvNext = tab_quanAo_fragment.getActivity().findViewById(R.id.textView_Next);
            maxPage = listquanAo.size() / 8;

            if (listquanAo.size() <= 8) {
                tvPage.setText("1/1");
                tvPrev.setVisibility(View.INVISIBLE);
                tvNext.setVisibility(View.INVISIBLE);
            } else {
                tvNext.setVisibility(View.VISIBLE);
                if (listquanAo.size() % 8 != 0) {
                    tvPage.setText((posPage + 1) + "/" + (maxPage + 1));
                } else {
                    tvPage.setText((posPage + 1) + "/" + maxPage);
                }
            }

            if (posPage == 0) {
                tvPrev.setVisibility(View.INVISIBLE);
            }
            if (posPage == maxPage){
                tvNext.setVisibility(View.INVISIBLE);
            }

            tvPrev.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onClick(View v) {
                    if (posPage > 0) {
                        posPage--;
                        Log.d(TAG, "onClick: posPage = " + posPage);
                        list8QuanAo = getData.getQuanAo(listquanAo, posPage);
                        tvPrev.setVisibility(View.VISIBLE);
                        tvNext.setVisibility(View.VISIBLE);
                        notifyDataSetChanged();
                    }
                }
            });

            tvNext.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onClick(View v) {
                    if (posPage < maxPage) {
                        posPage++;
                        Log.d(TAG, "onClick: posPage = " + posPage);
                        list8QuanAo = getData.getQuanAo(listquanAo, posPage);
                        tvPrev.setVisibility(View.VISIBLE);
                        tvNext.setVisibility(View.VISIBLE);
                        notifyDataSetChanged();
                    }
                }
            });
        }
    }

    private void openDialogUpdate(QuanAo quanAo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inft = ((Activity) context).getLayoutInflater();
        View view = inft.inflate(R.layout.dialog_quanao_manager, null);

        hangLapSpinner = view.findViewById(R.id.spinner_HangQuanao);
        textInput_GiaTien = view.findViewById(R.id.textInput_GiaTien);
        textInput_TenQuanAo = view.findViewById(R.id.textInput_Tenquanao);
        textInput_SoLuong = view.findViewById(R.id.textInput_SoLuong);
        button_QuanAo_Manager = view.findViewById(R.id.button_quanao_Manager);
        imageView_QuanAo = view.findViewById(R.id.imageView_QuanAo);

        setTextInput(quanAo);
        button_QuanAo_Manager.setText("Cập nhật");
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        button_QuanAo_Manager.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                QuanAo update = getTextInput(quanAo);
                if (update != null) {
                    quanAoDAO.updateQuanAo(update);
                    list8QuanAo.set(posLap, update);
                    dialog.dismiss();
                    notifyDataSetChanged();
                }
            }
        });
    }

    private QuanAo getTextInput(QuanAo quanAo) {
        ChangeType changeType = new ChangeType();
        String ten = changeType.deleteSpaceText(textInput_TenQuanAo.getEditText().getText().toString());
        String gia = changeType.deleteSpaceText(textInput_GiaTien.getEditText().getText().toString());
        String soluong = changeType.deleteSpaceText(textInput_SoLuong.getEditText().getText().toString());
        String hang = "L" + hangLapSpinner.getSelectedItem().toString();


        if (ten.equals("")) {
            textInput_TenQuanAo.setError("Tên không được trống!");
            textInput_TenQuanAo.setErrorEnabled(true);
            return null;
        } else {
            quanAo.setTenQuanAo(ten);
            textInput_TenQuanAo.setError("");
            textInput_TenQuanAo.setErrorEnabled(false);
        }

        if (gia.equals("")) {
            textInput_GiaTien.setError("Giá tiền không được trống!");
            textInput_GiaTien.setErrorEnabled(true);
            return null;
        } else {
            quanAo.setGiaTien(changeType.stringToStringMoney(changeType.stringMoneyToInt(gia) + ""));
            textInput_GiaTien.setError("");
            textInput_GiaTien.setErrorEnabled(false);
        }

        if (changeType.stringMoneyToInt(soluong) == 0) {
            textInput_SoLuong.setError("Số lượng phải lớn hơn 0");
            textInput_SoLuong.setErrorEnabled(true);
            return null;
        } else {
            quanAo.setSoLuong(Integer.parseInt(soluong));
            textInput_SoLuong.setError("");
            textInput_SoLuong.setErrorEnabled(false);
        }

        quanAo.setMaHangQuanAo(hang);

        return quanAo;
    }

    private void setTextInput(QuanAo quanAo) {
//        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(context,
//                R.array.ram_array, android.R.layout.simple_spinner_item);
//        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        ramSpinner.setAdapter(adapter1);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(context,
                R.array.loai_quan_ao_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hangLapSpinner.setAdapter(adapter2);

        imageView_QuanAo.setImageBitmap(changeType.byteToBitmap(quanAo.getAnhquanAo()));
        textInput_TenQuanAo.getEditText().setText(quanAo.getTenQuanAo());
        textInput_GiaTien.getEditText().setText(quanAo.getGiaTien());
        textInput_SoLuong.getEditText().setText(String.valueOf(quanAo.getSoLuong()));


    }

    public int getPosLap() {
        return posLap;
    }

    public void setPosLap(int posLap) {
        this.posLap = posLap;
    }
}