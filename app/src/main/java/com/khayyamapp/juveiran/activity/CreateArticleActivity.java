package com.khayyamapp.juveiran.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.khayyamapp.juveiran.R;
import com.khayyamapp.juveiran.api_service.ApiService;
import com.khayyamapp.juveiran.custom_views.IranSansButton;
import com.khayyamapp.juveiran.custom_views.IranSansEditText;
import com.khayyamapp.juveiran.data_model.CreateArticle;
import com.khayyamapp.juveiran.globals.SpGlobals;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.khayyamapp.juveiran.activity.UpdateProfileActivity.PIC_IMAGE_REQUEST;
import static com.khayyamapp.juveiran.activity.UpdateProfileActivity.STORAGE_PERMISION_CODE;
import static com.khayyamapp.juveiran.activity.UpdateProfileActivity.WRITE_STORAGE_PERMISION_CODE;

public class CreateArticleActivity extends AppCompatActivity {


    String mediaPath;

    IranSansEditText etTitle, etContent;
    IranSansButton btnEnter;
    SharedPreferences userStats;
    RelativeLayout rlProgress;
    Toolbar toolbar;
    RoundedImageView rImgCrticlePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_article);

        userStats = getSharedPreferences(SpGlobals.userStatsSpName, MODE_PRIVATE);

        toolbar = findViewById(R.id.tb_AcreateArticle_toolbar);
        etTitle = findViewById(R.id.et_AcreateArticle_title);
        etContent = findViewById(R.id.et_AcreateArticle_content);
        btnEnter = findViewById(R.id.btn_AcreateArticle_enter);
        rlProgress = findViewById(R.id.rl_FcreateArticle_progress);
        rImgCrticlePic = findViewById(R.id.rImg_AcreateArticle_pic);

        setupToolbar();

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mediaPath != null) {

                    File file = new File(mediaPath);
                    if ((file.length() / 1024 )/1024 < 1) {

                        RequestBody requestBody = RequestBody.create(MediaType.parse("images/*"), file);
                        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("Image", file.getName(), requestBody);

                        RequestBody articleTite = RequestBody.create(MediaType.parse("text/plain"), etTitle.getText().toString());
                        RequestBody articleContent = RequestBody.create(MediaType.parse("text/plain"), etContent.getText().toString());

                        Log.i("THE_TEEST", "onClick: " + file.length() / 2048);


                        rlProgress.setVisibility(View.VISIBLE);
                        String title = etTitle.getText().toString();
                        String content = etContent.getText().toString();
                        if (!title.equals("") && !content.equals("")) {
                            ApiService.getApi().createArticle(
                                    articleTite
                                    , articleContent
                                    , imagePart
                                    , "bearer " + userStats.getString(SpGlobals.KEY_USER_TOKEN, "error"))
                                    .enqueue(new Callback<CreateArticle>() {
                                        @Override
                                        public void onResponse(Call<CreateArticle> call, Response<CreateArticle> response) {
                                            CreateArticle createArticle = response.body();
                                            Log.i("test", "onResponse: "+createArticle);

                                            try {
                                                Toast.makeText(CreateArticleActivity.this, createArticle.getMsg(), Toast.LENGTH_SHORT).show();
                                                CreateArticleActivity.this.finish();
                                                rlProgress.setVisibility(View.GONE);
                                            } catch (Exception e) {
                                                Toast.makeText(CreateArticleActivity.this, getResources().getString(R.string.errorHappens), Toast.LENGTH_SHORT).show();
                                                rlProgress.setVisibility(View.GONE);
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<CreateArticle> call, Throwable t) {
                                            rlProgress.setVisibility(View.GONE);

                                        }
                                    });

                        } else {
                            Toast.makeText(CreateArticleActivity.this, getResources().getString(R.string.fRegister_error_checkInputs), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(CreateArticleActivity.this, R.string.theImageSizeIsMoreThanOneMeg, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CreateArticleActivity.this, R.string.pleaseEnterAnImage, Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activitycreatearticle_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_AcreateArticle_selectPhoto:

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, PIC_IMAGE_REQUEST);

                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISION_CODE);
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_STORAGE_PERMISION_CODE);
                    }
                }
                return true;

            default:
                return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == PIC_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
                Uri selectedImage = data.getData();
                String[] imageColumnPath = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, imageColumnPath, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(imageColumnPath[0]);
                mediaPath = cursor.getString(columnIndex);
                rImgCrticlePic.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                cursor.close();
            }

        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == STORAGE_PERMISION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(this, R.string.pemissionError, Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    void setupToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        toolbar.setTitle(" ");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
    }

}
