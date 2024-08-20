package com.example.travelassistant.submit;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelassistant.LoginActivity;
import com.example.travelassistant.R;
import com.example.travelassistant.db.BlogBean;
import com.example.travelassistant.db.DBManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.hyphenate.chat.EMClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


public class SubmitFragment extends Fragment implements View.OnClickListener{
    private BottomSheetDialog bottomSheetDialog;

    Button cancelButton,submitButton;
    EditText editText,titleText;
    ImageView imageView;
    Uri imageUri;
    String base64ImageString=null;
    private int key=500;
    private Intent takePictureIntent;
    File image = null;
    private String imageAbsolutePath;
    private static final int REQUEST_IMAGE_CAPTURE = 1;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_submit, container, false);
        initView(view);
        bottomSheetDialog=new BottomSheetDialog(getContext());


        return view;
    }

    private void initView(View view) {
        submitButton=view.findViewById(R.id.button_submit);
        cancelButton=view.findViewById(R.id.button_cancel);
        editText=view.findViewById(R.id.submitfrag_et_addtext);
        imageView=view.findViewById(R.id.submitfrag_iv_addimage);
        titleText=view.findViewById(R.id.submitfrag_et_addTitle);

        submitButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        imageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_submit:
                String currentUser = EMClient.getInstance().getCurrentUser();
                String s1 = titleText.getText().toString();
                String s = editText.getText().toString();






                try {
                    // 读取图像文件内容
                    InputStream inputStream = getActivity().getContentResolver().openInputStream(imageUri);

                    Bitmap originalImage = BitmapFactory.decodeStream(inputStream);
                    Bitmap compressedImage = Bitmap.createScaledBitmap(originalImage, 200, 100, false);
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    compressedImage.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    // 将字节数组转换为Base64字符串
                     base64ImageString= Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);


                } catch (Exception e) {
                    e.printStackTrace();
                }

                BlogBean blogBean=new BlogBean();
                blogBean.setNum(currentUser);
                blogBean.setNickname(currentUser);
                blogBean.setIntroduce(s);
                if(imageAbsolutePath!=null)
                {
                    blogBean.setContent(imageAbsolutePath);
                }
                else{
                    blogBean.setContent("美丽景色欢迎您");

                }


//                if(base64ImageString!=null){
//                    blogBean.setContent(base64ImageString);
//                }
//                else{
//                    blogBean.setContent("美丽景色欢迎您");
//
//                }
                blogBean.setName(s1);
                blogBean.setAvatar("111");
                DBManager.addOneBlogInfo(blogBean);
                Toast.makeText(getContext(), "发表成功", Toast.LENGTH_SHORT).show();
                titleText.setText("");
                editText.setText("");

                break;
            case R.id.button_cancel:
                titleText.setText("");
                editText.setText("");
                break;
            case R.id.submitfrag_iv_addimage:
                showBottomSheet();




                //创建获取图片的连接


                break;
            case R.id.button1:

                //启动相机


                takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    if (ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.CAMERA) != PackageManager
                            .PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new
                                String[]{Manifest.permission.CAMERA }, 1);
                    } else {

                            File photoFile=null;
                            try{
                                photoFile=createImageFile();
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                            if(photoFile!=null)
                            {
                                imageUri= FileProvider.getUriForFile(getContext(),"com.example.travelassistant.provider",photoFile);
                                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                            }

                        }

                }




                break;
            case R.id.button2:
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,key);

                //相册
                break;
            case R.id.button3:
                //取消
                bottomSheetDialog.cancel();
                break;
            case R.id.button4:
                //视频
                Intent cameraIntent = new Intent(getActivity(), Camera2.class);
                startActivity(cameraIntent);
                break;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

                } else {
                    Toast.makeText(getContext(), "You denied the peimission",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    private File createImageFile() {
        // 创建一个文件名
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir =getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        try {
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 保存文件路径，以便在onActivityResult中使用
        imageAbsolutePath = image.getAbsolutePath();
        return image;

    }

    private void showBottomSheet() {

        View bottomSheetView = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_layout, null);
        TextView button1= bottomSheetView.findViewById(R.id.button1);
        TextView button2= bottomSheetView.findViewById(R.id.button2);
        TextView button3= bottomSheetView.findViewById(R.id.button3);
        TextView button4= bottomSheetView.findViewById(R.id.button4);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 500) {
            imageUri = data.getData(); // 获取所选图片的 URI
            System.out.println("输出图片地址");
            System.out.println(imageUri);
            imageView.setImageURI(imageUri);
            bottomSheetDialog.cancel();

            // 根据 URI 进行其他操作，比如显示图片或上传等
            // ...
        }
        else if(requestCode==REQUEST_IMAGE_CAPTURE){
            Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath());
            System.out.println("输出文件的地址");
            //文件地址存入数据库中
            System.out.println(image.getAbsolutePath());
            Toast.makeText(getContext(),image.getAbsolutePath(),Toast.LENGTH_SHORT).show();
            imageView.setImageBitmap(bitmap);
            bottomSheetDialog.cancel();
        }
    }
}