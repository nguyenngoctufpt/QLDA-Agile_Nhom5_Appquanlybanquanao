package datdvph44632.fpoly.duan1_appbanhang_dinhvandat.ActivityNV_Admin;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.QuanAoDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.QuanAo;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.R;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Support.ChangeType;

public class QuanAo_Manager_Activity extends AppCompatActivity {

    Context context = this;
    FrameLayout uploadImageQuanAo;
    Spinner hangQuanAoSpinner;
    ImageView imageView_anhQuanAo, imageView_AddQuanAo;
    TextInputLayout til_TenQuanAoMoi, til_GiaTien, til_SoLuong;
    AppCompatButton buttonThemQuanAoNgay;
    QuanAoDAO quanAoDAO;
    ChangeType changeType = new ChangeType();
    int REQUEST_CODE = 2;
    String currentPhotoPath;
    private static final int SELECT_PHOTO = 100;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    String TAG = "Laptop_Manager_Activity_____";
    Bitmap bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanao_manager);
        uploadImageQuanAo = findViewById(R.id.upload_Image_quanao);
        imageView_anhQuanAo = findViewById(R.id.imageView_QuanAo);
        imageView_AddQuanAo = findViewById(R.id.imageView_AddQuanao);
        til_TenQuanAoMoi = findViewById(R.id.textInput_Tenquanao);
        hangQuanAoSpinner = findViewById(R.id.spinner_HangQuanao);

        til_GiaTien = findViewById(R.id.textInput_GiaTien);
        til_SoLuong = findViewById(R.id.textInput_SoLuong);
        buttonThemQuanAoNgay = findViewById(R.id.button_quanao_Manager);

//        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
//                R.array.ram_array, android.R.layout.simple_spinner_item);
//        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        ramSpinner.setAdapter(adapter1);

        // Phương thức createFromResource được gọi để tạo ra ArrayAdapter
        // từ một mảng chuỗi được định nghĩa trong tài nguyên của ứng dụng (R.array.loai_quan_ao_array)
        // đoạn code trên được sử dụng để lấy dữ liệu từ mảng chuỗi trong tài nguyên và hiển thị chúng trong một Spinner
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.loai_quan_ao_array, android.R.layout.simple_spinner_item);
        // để hiển thị
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // gán dữ liệu để hiển thị spinner
        hangQuanAoSpinner.setAdapter(adapter2);

        uploadImageQuanAo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context);
                View dialogV = LayoutInflater.from(context).inflate(R.layout.dialog_pick_image, null);
                dialog.setContentView(dialogV);
                dialog.show();

                Button gallery = dialogV.findViewById(R.id.openGallery);

                gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Dòng này kiểm tra xem ứng dụng có quyền
                        // đọc bộ nhớ ngoài hay không bằng cách sử dụng checkSelfPermission và so sánh với PackageManager.PERMISSION_GRANTED.
                        if (context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                            Log.d(TAG, "galleryOnClick: Chưa cấp quyền đọc bộ nhớ");
                            ActivityCompat.requestPermissions(QuanAo_Manager_Activity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
                            Toast.makeText(context, "Cần cấp quyền để thực hiện", Toast.LENGTH_LONG).show();
                        } else {
                            Log.d(TAG, "galleryOnClick: Đã cấp quyền đọc bộ nhớ");
                            openGallery();
                            dialog.cancel();
                        }
                    }
                });
            }
        });

        //                Button camera = dialogV.findViewById(R.id.openCamera);

//                camera.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (context.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
//                            Log.d(TAG, "cameraOnClick: Đã cấp quyền chụp ảnh");
//                            openCamera();
//                            dialog.cancel();
//                        } else {
//                            Log.d(TAG, "cameraOnClick: Chưa cấp quyền chụp ảnh");
//                            ActivityCompat.requestPermissions(QuanAo_Manager_Activity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE);
//                            Toast.makeText(context, "Cần cấp quyền để thực hiện", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });


        useToolbar();
        addQuanAoNew();
    }

    private void useToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_Normal));
        TextView titleToolbar = findViewById(R.id.textView_Title_Toolbar);
        titleToolbar.setText("Thêm Quần áo");
        ImageButton back = findViewById(R.id.imageButton_Back_Toolbar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void addQuanAoNew() {
        quanAoDAO = new QuanAoDAO(this);

        buttonThemQuanAoNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkInputQuanAo() == 1) {
                    String tenQuanAo = til_TenQuanAoMoi.getEditText().getText().toString();
                    String loaiQuanAo = (String) hangQuanAoSpinner.getSelectedItem();
                    String tien = til_GiaTien.getEditText().getText().toString();
                    int soluong = Integer.parseInt(til_SoLuong.getEditText().getText().toString());

                    Bitmap bm = getBitmap();// Dòng này lấy một đối tượng Bitmap từ phương thức getBitmap()(camera hoặc từ bộ nhớ.)
                    if (bm != null) {
                        Log.d(TAG, "onClick: here?");
                        QuanAo quanAo = new QuanAo("LP", "L" + loaiQuanAo, tenQuanAo, changeType.stringToStringMoney(tien), soluong,
                                0, changeType.checkByteInput(changeType.bitmapToByte(bm)));
                        quanAoDAO.insertQuanAo(quanAo);
                        finish();
                    } else {
                        Log.d(TAG, "onClick: or here?");
                        Toast.makeText(context, "Ảnh không được thiếu!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d(TAG, "onClick: or maybe here?");
                }
            }

        });

    }

    private int checkInputQuanAo() {
        int check = 1;
        String tenQuanAo = til_TenQuanAoMoi.getEditText().getText().toString();
        String tien = til_GiaTien.getEditText().getText().toString();
        String soluong = til_SoLuong.getEditText().getText().toString();

        if (tenQuanAo.isEmpty()) {
            check = -1;
            til_TenQuanAoMoi.setError("Tên  không được bỏ trống!");
            til_TenQuanAoMoi.setErrorEnabled(true);
        } else {
            til_TenQuanAoMoi.setError("");
            til_TenQuanAoMoi.setErrorEnabled(false);
        }

        if (tien.isEmpty()) {
            check = -1;
            til_GiaTien.setError("Giá tiền không được bỏ trống!");
            til_GiaTien.setErrorEnabled(true);
        } else {
            if (Integer.parseInt(tien) > 0) {
                til_GiaTien.setError("");
                til_GiaTien.setErrorEnabled(false);
            } else {
                check = -1;
                til_GiaTien.setError("Giá tiền phải lớn hơn 0!");
                til_GiaTien.setErrorEnabled(true);
            }
        }

        if (soluong.isEmpty()) {
            check = -1;
            til_SoLuong.setError("Số lượng không được bỏ trống!");
            til_SoLuong.setErrorEnabled(true);
        } else {
            if (Integer.parseInt(soluong) > 0) {
                til_SoLuong.setError("");
                til_SoLuong.setErrorEnabled(false);
            } else {
                check = -1;
                til_SoLuong.setError("Số lượng phải lớn hơn 0!");
                til_SoLuong.setErrorEnabled(true);
            }
        }

        return check;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        // Dòng này kiểm tra xem requestCode có phải là SELECT_PHOTO hay không.
        // SELECT_PHOTO là một hằng số được định nghĩa trước đó để xác định hoạt động chọn ảnh.
        if (requestCode == SELECT_PHOTO) {
            //người dùng đã chọn ảnh thành công.
            if (resultCode == RESULT_OK) {
                //sẽ lấy đường dẫn của ảnh được chọn từ imageReturnedIntent.
                Uri selectedImage = imageReturnedIntent.getData();
                InputStream imageStream = null;
                try {
//                   //mở luồng đọc của ảnh được chọn.
                    imageStream = context.getContentResolver().openInputStream(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                // giải mã luồng đọc thành một đối tượng Bitmap.
                Bitmap bm = BitmapFactory.decodeStream(imageStream);
                // Nếu bm khác null, tức là quá trình giải mã ảnh thành công,
                if (bm != null) {
                    // lưu trữ đối tượng bitmap
                    setBitmap(bm);
                    //đặt ảnh đã chọn lên imageView_anhQuanAo.
                    imageView_anhQuanAo.setImageBitmap(bm);
                    imageView_AddQuanAo.setVisibility(View.GONE);
                }
            }
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap bm = BitmapFactory.decodeFile(currentPhotoPath);
            if (bm != null) {
                setBitmap(bm);
                imageView_anhQuanAo.setImageBitmap(bm);
                imageView_AddQuanAo.setVisibility(View.GONE);
            }
        }
    }

//    private void openCamera() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(QuanAo_Manager_Activity.this.getPackageManager()) != null) {
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//
//            if (photoFile != null) {
//                Uri photoURI = FileProvider.getUriForFile(QuanAo_Manager_Activity.this,
//                        "com.nhom5.quanlylaptop.fileprovider", photoFile);
//
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//
//                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//            }
//        }
//    }

    private File createImageFile() throws IOException {
        // đại diện cho thời gian hiện tại theo định dạng "yyyyMMdd_HHmmss".
        // Chuỗi này thường được sử dụng để tạo tên duy nhất cho tệp tin ảnh.
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        // ấy đường dẫn đến thư mục lưu trữ bên ngoài của ứng dụng, trong thư mục "Pictures".
        File storageDir = QuanAo_Manager_Activity.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        // Dòng này tạo một tệp tin ảnh tạm thời bằng cách
        // sử dụng imageFileName làm tên, ".jpg" làm phần mở rộng và storageDir là thư mục lưu trữ.
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // lưu đường dẫn tuyệt đối của tệp tin ảnh tạm thời vào biến currentPhotoPath
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void openGallery() {
        // cho phép người dùng chọn một tệp tin từ một nguồn dữ liệu được chỉ định
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        // các tệp tin hình ảnh sẽ được hiển thị trong giao diện chọn ảnh.
        photoPickerIntent.setType("image/*");
        // bắt đầu hoạt động chọn ảnh với Intent đã được tạo,
        // và kết quả sẽ được trả về thông qua phương thức onActivityResult() với mã yêu cầu SELECT_PHOTO.
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    // lưu trữ biến trong bitmap
    public Bitmap getBitmap() {
        return bitmap;
    }

    // thiết lập giá trị của bitmap
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}