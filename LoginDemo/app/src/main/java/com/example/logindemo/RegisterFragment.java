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

public class RegisterFragment extends Fragment {

    private EditText etUsername, etPassword;
    private Button btnRegister;
    private UserDao userDao;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        etUsername = view.findViewById(R.id.et_username);
        etPassword = view.findViewById(R.id.et_password);
        btnRegister = view.findViewById(R.id.btn_register);
        userDao = AppDatabase.getInstance(getContext()).userDao();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    Toast.makeText(getContext(), "用户名和密码不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    User user = userDao.findByUsername(username);
                    if (user != null) {
                        Toast.makeText(getContext(), "用户名已存在", Toast.LENGTH_SHORT).show();
                    } else {
                        userDao.insert(new User(username, password));
                        NavController navController = Navigation.findNavController(v);
                        navController.navigate(R.id.action_registerFragment_to_loginFragment);
                        Toast.makeText(getContext(), "注册成功", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return view;
    }
}

