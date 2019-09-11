package com.khayyamapp.juveiran.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.khayyamapp.juveiran.R;
import com.khayyamapp.juveiran.api_service.ApiService;
import com.khayyamapp.juveiran.data_model.UpdateProfile;
import com.khayyamapp.juveiran.data_model.UserPosts;
import com.khayyamapp.juveiran.data_model.user_commetns.UserComments;
import com.khayyamapp.juveiran.fragment.UserArticlesBottomSheet;
import com.khayyamapp.juveiran.fragment.UserCommentsBottomSheet;
import com.khayyamapp.juveiran.globals.GlobalMethods;
import com.khayyamapp.juveiran.globals.SpGlobals;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.khayyamapp.juveiran.globals.SpGlobals.KEY_USER_EDIT_STATE;
import static com.khayyamapp.juveiran.globals.SpGlobals.KEY_USER_IMAGEURL;
import static com.khayyamapp.juveiran.globals.SpGlobals.KEY_USER_NAME;
import static com.khayyamapp.juveiran.globals.SpGlobals.KEY_USER_TOKEN;

public class UpdateProfileActivity extends AppCompatActivity {

    public static final int STORAGE_PERMISION_CODE = 1;
    public static final int WRITE_STORAGE_PERMISION_CODE = 2;
    public static final int PIC_IMAGE_REQUEST = 2;


    String mediaPath;
    String name;

    Button btnLogOut;
    RelativeLayout rlProgress;
    LinearLayout llUserArticles, llUserComments;

    RoundedImageView rImgUserPic;
    Button btnUpload;
    ImageButton btnChangePic;
    CardView cvEditName;
    TextView tvUserName, tvCountOfComments, tvCountOfPosts;
    EditText etUserName;
    ImageView changeIcon;
    SharedPreferences userStats;
    Toolbar toolbar;

    int numOfComments, numOfPosts;

    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        llUserArticles = findViewById(R.id.ll__AupdateProfile_userArticles);
        llUserComments = findViewById(R.id.ll__AupdateProfile_userComments);
        rImgUserPic = findViewById(R.id.rImg_AupdateProfile_userPic);
        btnChangePic = findViewById(R.id.btn_AupdateProfile_changePic);
        tvUserName = findViewById(R.id.tv_AupdateProfile_username);
        etUserName = findViewById(R.id.et_AupdateProfile_username);
        cvEditName = findViewById(R.id.cv_AupdateProfile_changeName);
        changeIcon = findViewById(R.id.img_AupdateProfile_changeIcon);
        toolbar = findViewById(R.id.tb_AupdateProfile_toolbar);
        userStats = getSharedPreferences(SpGlobals.userStatsSpName, MODE_PRIVATE);
        btnUpload = findViewById(R.id.btn_AupdateProfile_upload);
        rlProgress = findViewById(R.id.rl_Aupdate_profile_progress);
        tvCountOfComments = findViewById(R.id.tv_AupdateProfile_numOfCommets);
        tvCountOfPosts = findViewById(R.id.tv_AupdateProfile_numOfArticles);
        btnLogOut = findViewById(R.id.btn_AupdateProfile_logOut);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISION_CODE);
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_STORAGE_PERMISION_CODE);

        }

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userStats.edit().clear().apply();
                Toast.makeText(UpdateProfileActivity.this, R.string.youLoggedOut, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(),LoginAndRegisterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        setupToolbar();
        Log.i("ImageTest", "onCreate: " + userStats.getString(KEY_USER_IMAGEURL, ""));
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true);
        Glide.with(this).applyDefaultRequestOptions(requestOptions)
                .load(userStats.getString(KEY_USER_IMAGEURL, ""))
                .into(rImgUserPic);


        tvUserName.setText(userStats.getString(KEY_USER_NAME, ""));
        etUserName.setText(tvUserName.getText().toString());

        cvEditName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvUserName.getVisibility() == View.VISIBLE) {
                    changeIcon.setVisibility(View.GONE);
                    tvUserName.setVisibility(View.INVISIBLE);
                    etUserName.setVisibility(View.VISIBLE);

                    InputMethodManager inputMethodManager =
                            (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.toggleSoftInputFromWindow(
                            etUserName.getApplicationWindowToken(),
                            InputMethodManager.SHOW_FORCED, 0);
                    etUserName.setHint(R.string.new_name);

                } else {
                    changeIcon.setVisibility(View.VISIBLE);
                    tvUserName.setVisibility(View.VISIBLE);
                    etUserName.setVisibility(View.INVISIBLE);
                }

            }
        });

        btnChangePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, PIC_IMAGE_REQUEST);

            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPath != null) {
                    uploadFile();
                } else {
                    rlProgress.setVisibility(View.VISIBLE);
                    RequestBody newName = RequestBody.create(MediaType.parse("text/plain"), etUserName.getText().toString());
                    ApiService.getApi().updateProfile(null, newName, "Bearer "
                            + userStats.getString(KEY_USER_TOKEN, "")).enqueue(new Callback<UpdateProfile>() {
                        @Override
                        public void onResponse(Call<UpdateProfile> call, Response<UpdateProfile> response) {
                            rlProgress.setVisibility(View.GONE);
                            UpdateProfile updateProfile = response.body();
                            if (updateProfile != null) {
                                Toast.makeText(UpdateProfileActivity.this, updateProfile.getMsg(), Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(UpdateProfileActivity.this, getResources().getString(R.string.api_error_respons), Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<UpdateProfile> call, Throwable t) {
                            rlProgress.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });


        setupDetail();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == PIC_IMAGE_REQUEST && resultCode == RESULT_OK && null != data) {

                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);
                GlobalMethods.compressImage(mediaPath, 512);
                // Set the Image in ImageView for Previewing the Media
                rImgUserPic.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                cursor.close();
                Log.i("ImageTest", "onActivityResult: " + mediaPath);

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
        toolbar.setTitle(" ");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void showFileChoser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "عکس خود را انتخاب کنید"), PIC_IMAGE_REQUEST);

    }


    private String imageToString() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageByte, Base64.DEFAULT);
    }


    private void uploadFile() {

        rlProgress.setVisibility(View.VISIBLE);
        // Map is used to multipart the file using okhttp3.RequestBody
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        File file = new File(mediaPath);

        // Parsing any Media type file
        final RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("Image", file.getName(), requestBody);
        RequestBody newName = RequestBody.create(MediaType.parse("text/plain"), etUserName.getText().toString());


        ApiService.getApi().updateProfile(fileToUpload, newName
                , "Bearer " + userStats.getString(KEY_USER_TOKEN, "error")).enqueue(new Callback<UpdateProfile>() {
            @Override
            public void onResponse(Call<UpdateProfile> call, Response<UpdateProfile> response) {
                UpdateProfile serverResponse = response.body();

                Log.i("TEST", "onResponse: " + requestBody);

                rlProgress.setVisibility(View.GONE);
                if (serverResponse != null) {
                    if (serverResponse.getStatus() == 1) {

                        userStats.edit().putString(SpGlobals.KEY_USER_IMAGEURL, serverResponse.getImageThumb()).apply();

                        userStats.edit().putString(SpGlobals.KEY_USER_NAME, etUserName.getText().toString()).apply();
                        userStats.edit().putBoolean(KEY_USER_EDIT_STATE, true).apply();
                        Toast.makeText(getApplicationContext(), serverResponse.getMsg(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), serverResponse.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(UpdateProfileActivity.this, R.string.tryAgain, Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<UpdateProfile> call, Throwable t) {
                rlProgress.setVisibility(View.GONE);

            }
        });
    }

    private void setupDetail() {
        ApiService.getApi().getUserComments("Bearer " + userStats.getString(KEY_USER_TOKEN, ""))
                .enqueue(new Callback<UserComments>() {
                    @Override
                    public void onResponse(Call<UserComments> call, Response<UserComments> response) {
                        final UserComments userComments = response.body();
                        if (response.isSuccessful() && userComments != null) {
                            numOfComments = response.body().getData().size();
                            ApiService.getApi().getUserSaComments("Bearer " + userStats.getString(KEY_USER_TOKEN, "")).enqueue(new Callback<UserComments>() {
                                @Override
                                public void onResponse(Call<UserComments> call, Response<UserComments> response) {
                                    UserComments userSaComments = response.body();

                                    if (response.isSuccessful() && userSaComments != null) {
                                        numOfComments += userSaComments.getData().size();
                                        tvCountOfComments.setText(String.valueOf(numOfComments));
                                        llUserComments.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                if (numOfComments > 0) {
                                                    UserCommentsBottomSheet userCommentsBottomSheet = new UserCommentsBottomSheet();
                                                    userCommentsBottomSheet.show(getSupportFragmentManager(), "");
                                                }
                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onFailure(Call<UserComments> call, Throwable t) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<UserComments> call, Throwable t) {

                    }
                });
        ApiService.getApi().getUserPosts("Bearer " + userStats.getString(KEY_USER_TOKEN, ""))
                .enqueue(new Callback<UserPosts>() {
                    @Override
                    public void onResponse(Call<UserPosts> call, Response<UserPosts> response) {
                        UserPosts userPosts = response.body();
                        if (response.isSuccessful() && userPosts != null) {
                            numOfPosts = userPosts.getData().size();
                            tvCountOfPosts.setText(String.valueOf(numOfPosts));
                            llUserArticles.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (numOfPosts > 0) {
                                        UserArticlesBottomSheet userArticlesBottomSheet = new UserArticlesBottomSheet();
                                        userArticlesBottomSheet.show(getSupportFragmentManager(), "");
                                    }
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<UserPosts> call, Throwable t) {

                    }
                });
    }

}
