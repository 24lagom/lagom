package com.example.logindemo;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ChangePassFragment extends Fragment {
    private UserDao userDao;
    private EditText newPass;
    private Button btnChange;
    private User User;

    public ChangePassFragment() {
        // Required empty public constructor
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMyEvent(MyEvent event) {
        // 处理接收到的事件
        userDao = AppDatabase.getInstance(getContext()).userDao();
        User = userDao.findByUsername(event.getMsg());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_pass, container, false);
        newPass = view.findViewById(R.id.et_new_password);
        btnChange = view.findViewById(R.id.btn_change);
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(newPass.getText().toString())) {
                    Toast.makeText(getActivity(), "新密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                User.setPassword(newPass.getText().toString());
                userDao.updatePassword(User);
                Toast.makeText(getActivity(), "修改密码成功", Toast.LENGTH_SHORT).show();
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_changePassFragment_to_homeFragment);
            }
        });
        return view;
    }


}
