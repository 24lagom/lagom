package com.example.logindemo;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Query("SELECT * FROM user WHERE username = :username")
    User findByUsername(String username);

    @Query("SELECT * FROM user WHERE username = :username AND password = :password")
    User findUser(String username, String password);

    @Update
    void updatePassword(User user);
}


