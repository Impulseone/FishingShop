package com.skynet.fish_shop.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey
    public int id;
    public String firstName;
    public String lastName;
    public String thirdName;
    public String phoneNumber;
    public String email;

    public User() {
    }

    public User(int id, String firstName, String lastName, String thirdName, String phoneNumber, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.thirdName = thirdName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}
