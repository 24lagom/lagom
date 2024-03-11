package com.example.logindemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeFragment extends Fragment {
    private BottomNavigationView bottomNavigationView;
    private QueryFragment queryFragment;
    private ManageFragment manageFragment;
    private UserFragment userFragment;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // 实例化底部导航栏控件
        bottomNavigationView = view.findViewById(R.id.bottom_navigation_view);
        // 设置底部导航栏 ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_manage:
                        switchFragment(manageFragment);
                        return true;
                    case R.id.menu_query:
                        switchFragment(queryFragment);
                        return true;
                    case R.id.menu_my:
                        switchFragment(userFragment);
                        return true;
                }
                return false;
            }
        });

        manageFragment = new ManageFragment();
        queryFragment = new QueryFragment();
        userFragment = new UserFragment();

        // 设置默认 Fragment
        switchFragment(manageFragment);

        return view;
    }

    private void switchFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

}
