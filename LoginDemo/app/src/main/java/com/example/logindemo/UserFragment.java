package com.example.logindemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;


/**
 * 我的
 */
public class UserFragment extends Fragment {

    private LinearLayout llSecurity;
    private Button btnLogout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user,container,false);
        llSecurity = view.findViewById(R.id.security);
        btnLogout = view.findViewById(R.id.logout);
        initView();
        return view;
    }

    private void initView() {
        //账号安全
        llSecurity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转页面
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_homeFragment_to_changePassFragment);
            }
        });
        //退出登录
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_homeFragment_to_loginFragment);
                Toast.makeText(getActivity(), "退出登录成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
