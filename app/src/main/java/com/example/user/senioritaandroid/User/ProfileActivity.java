package com.example.user.senioritaandroid.User;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.senioritaandroid.Extra.Connection;
import com.example.user.senioritaandroid.Login.LoginActivity;
import com.example.user.senioritaandroid.Service.ApiService;
import com.example.user.senioritaandroid.R;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class ProfileActivity extends AppCompatActivity {

    private User currentUser;
    private EditText userName;
    private EditText email;
    private TextView fullName;
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        retrofit = (new Connection(ProfileActivity.this)).getRetrofit();
        getUser();
        Button updateInfo = (Button) findViewById(R.id.butEditProfile);
        updateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });
        userName = (EditText) findViewById(R.id.userName);
        email = (EditText) findViewById(R.id.email);
    }

    public Boolean update() {
        ApiService apiService = this.retrofit.create(ApiService.class);
        currentUser.setLogin(userName.getText().toString());
        currentUser.setEmail(email.getText().toString());
        Single<String> result = apiService.updateUser(currentUser);// Comment
        result.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.v("disposable", d.toString());
                    }

                    @Override
                    public void onSuccess(String result) {
                        Log.v("Result:", result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        final android.support.v7.app.AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                        builder.setMessage(R.string.profile_update_error)
                                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                    }
                                });
                        builder.show();
                        Log.e("error", e.toString());
                    }
                });
        return true;
    }

    public Boolean getImage() {
        ApiService apiService = this.retrofit.create(ApiService.class);
        Single<ResponseBody> user = apiService.getImage(currentUser.getUserImage().getLocation());// Comment
        user.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.v("disposable", d.toString());
                    }
                    @Override
                    public void onSuccess(ResponseBody responseBody)
                    {
                        setImage(responseBody);
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", e.toString());
                    }
                });
        return true;
    }

    public Boolean setImage(ResponseBody responseBody) {
        ImageView profilePhoto = (ImageView) findViewById(R.id.imageView);
        Bitmap bmp = BitmapFactory.decodeStream(responseBody.byteStream());
        Bitmap resized = Bitmap.createScaledBitmap(bmp, 250, 250, true);
        profilePhoto.setImageBitmap(resized);
        return true;
    }

    public Boolean setFields() {
        email.setText(currentUser.getEmail());
        userName.setText(currentUser.getLogin());
        return true;
    }

    public Boolean getUser() {
        ApiService apiService = this.retrofit.create(ApiService.class);
        Single<User> user = apiService.getUser();// Comment
        user.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.v("disposable", d.toString());
                    }
                    @Override
                    public void onSuccess(User user) {
                        currentUser = user;
                        Log.v("User:", user.toString());
                        if (currentUser.getUserImage() != null)
                            getImage();
                        setFields();
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", e.toString());
                    }
                });
        return true;
    }
}
