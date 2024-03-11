package com.example.logindemo;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import org.greenrobot.eventbus.EventBus;

public class LoginFragment extends Fragment {
    private UserDao userDao;
    private Button btnRegister, btnLogin;
    private EditText etUsername, etPassword;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        userDao = AppDatabase.getInstance(getContext()).userDao();
        etUsername = view.findViewById(R.id.edt_uid);
        etPassword = view.findViewById(R.id.edt_upwd);
        btnLogin = view.findViewById(R.id.btn_login);
        btnRegister = view.findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    Toast.makeText(getContext(), "用户名和密码不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    User user = userDao.findByUsername(username);
                    if (user == null) {
                        Toast.makeText(getContext(), "请先完成注册", Toast.LENGTH_SHORT).show();
                    } else {
                        User user1 = userDao.findUser(username, password);
                        if (user1 == null) {
                            Toast.makeText(getContext(), "用户名或密码不正确", Toast.LENGTH_SHORT).show();
                        } else {
                            NavController navController = Navigation.findNavController(v);
                            navController.navigate(R.id.action_loginFragment_to_welcomeFragment);
                            Toast.makeText(getContext(), "登录成功", Toast.LENGTH_SHORT).show();
                            MyEvent myEvent = new MyEvent();
                            myEvent.setMsg(username);
                            EventBus.getDefault().postSticky(myEvent);
                        }
                    }
                }
            }
        });
        return view;
    }
}

