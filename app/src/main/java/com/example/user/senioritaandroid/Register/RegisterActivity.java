package com.example.user.senioritaandroid.Register;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.user.senioritaandroid.Extra.Connection;
import com.example.user.senioritaandroid.Login.LoginActivity;
import com.example.user.senioritaandroid.R;
import com.example.user.senioritaandroid.Service.ApiService;
import com.example.user.senioritaandroid.User.UserDto;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity {

    Button register;
    EditText loginView, emailView, passwordView, confirmPasswordView;
    Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
        super.onCreate(savedInstanceState);
        final Spinner spinner = (Spinner) findViewById(R.id.roleSpinnerRegister);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        retrofit = (new Connection(RegisterActivity.this)).getRetrofit();
        register = (Button) findViewById(R.id.buttonRegister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String role = spinner.getSelectedItem().toString();
                prepareData(role);
            }
        });
    }

    public void prepareData(String role) {
        loginView = findViewById(R.id.loginRegister);
        emailView = findViewById(R.id.emailRegister);
        passwordView = findViewById(R.id.passwordRegister);
        confirmPasswordView = findViewById(R.id.confirmPasswordRegister);
        String login = loginView.getText().toString();
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();
        String confirmPassword = confirmPasswordView.getText().toString();
        Log.v("Register",email+" "+password+" "+confirmPassword);
        if (login.length() < 1) {
            loginView.setError("Login can not be empty");
        } else if (!email.contains("@nu.edu.kz")) {
            emailView.setError("Your organization is not subscribed");
            return;
        } else if (password.length() < 8) {
            passwordView.setError("The password should at least contain 8 characters");
            return;
        } else if (!password.equals(confirmPassword)) {
            passwordView.setError("Passwords should match");
            return;
        }
        UserDto userDto;
        Long roleId;
        if (role == "client") {
            roleId = (long)2;
        } else {
            roleId = (long)3;
        }
        userDto = new UserDto(login, password, email, roleId);
        register(userDto);
    }

    public void register(UserDto user) {
        ApiService apiService = retrofit.create(ApiService.class);
        Single<String> request = apiService.register(user);
        request.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.v("disposable", d.toString());
                    }
                    @Override
                    public void onSuccess(String request) {
                        Log.v("Register:", request.toString());
                        final android.support.v7.app.AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        builder.setMessage(R.string.register_success)
                                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    }
                                });
                        builder.setOnDismissListener(
                                new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialogInterface) {
                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    }
                                }
                        );
                        builder.show();
                    }
                    @Override
                    public void onError(Throwable e) {
                        final android.support.v7.app.AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        builder.setMessage(R.string.register_error)
                                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                    }
                                });
                        builder.show();
                        Log.e("error", e.toString());
                    }
                });
    }
}
