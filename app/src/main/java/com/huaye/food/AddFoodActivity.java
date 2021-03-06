package com.huaye.food;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.huaye.food.bean.Food;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

public class AddFoodActivity extends Activity {

    private static final int SELECT_PIC_BY_TACK_PHOTO = 1001;
    private static final int CUT_PHOTO = 1002;
    private TextView date_time;
    private EditText name;
    private EditText price;
    private ImageView img;
    private ProgressBar pb;
    private Spinner spinner, spinnerR, week;
    private Button right;
    private String photoName;
    private String photoUrl;
    private String photoPath;
    private Context context;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        context = this;
        date_time = (TextView) findViewById(R.id.date_time);
        name = (EditText) findViewById(R.id.name);
        right = (Button) findViewById(R.id.right);
        img = (ImageView) findViewById(R.id.img);
        pb = (ProgressBar) findViewById(R.id.pb);
        price = (EditText) findViewById(R.id.price);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinnerR = (Spinner) findViewById(R.id.spinnerR);
        week = (Spinner) findViewById(R.id.week);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item_type, new String[]{"Breakfast", "Lunch", "Dinner", "Lave night"});
        spinner.setAdapter(adapter);

        ArrayAdapter<String> adapterR = new ArrayAdapter<String>(this, R.layout.item_type, new String[]{"South Dining", "North Dining", "Library Cafe", "Grace Hall"});
        spinnerR.setAdapter(adapterR);

        ArrayAdapter<String> weekAdapter = new ArrayAdapter<String>(this, R.layout.item_type, new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"});
        week.setAdapter(weekAdapter);

        handler.postDelayed(runnable, 10);
        right.setText("Save");

        right.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Food food = new Food(spinnerR.getSelectedItemPosition(), spinner.getSelectedItemPosition(), price.getText().toString(), name.getText().toString(), photoUrl);
                food.setWeek(week.getSelectedItemPosition() + 1);
                food.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null) {
                            Toast.makeText(AddFoodActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            AddFoodActivity.this.finish();
                        } else {
                            Toast.makeText(AddFoodActivity.this, "fail", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });

        img.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });
    }


    private void takePhoto() {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "huaye";
            File path1 = new File(path);
            if (!path1.exists()) {
                path1.mkdirs();
            }
            photoName = path + File.separator + System.currentTimeMillis() + ".jpg";
            File file = new File(photoName);
            Uri mPhotoUri = Uri.fromFile(file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoUri);
            startActivityForResult(intent, SELECT_PIC_BY_TACK_PHOTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Runnable runnable = new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            date_time.setText(dateFormat.format(new Date()));

            handler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SELECT_PIC_BY_TACK_PHOTO:
                    photoPath = BitmapUtil.compressImage(photoName);
                    img.setImageBitmap(BitmapFactory.decodeFile(photoPath));
                    final BmobFile bmobFile = new BmobFile(new File(photoPath));
                    pb.setVisibility(View.VISIBLE);
                    bmobFile.uploadblock(new UploadFileListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                //bmobFile.getFileUrl()--返回的上传文件的完整地址
                                photoUrl = bmobFile.getFileUrl();
                                pb.setVisibility(View.GONE);
                            } else {
                                img.setImageResource(R.drawable.add_photo);
                                Toast.makeText(AddFoodActivity.this, "上传失败", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                    break;
            }
        }
    }
}
