/*
 * Copyright (c) 2017, Progrema Studio. All rights reserved.
 */

package com.imobilenetid.helloj_oxy;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class User {

    public String uid;

    public String email;

    public String phoneNumber;

    public String displayName;

    public String dataAlamat;

    public String dataAlamatLengkap;

    public String pictureUrl;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String uid, String displayName, String email, String dataAlamat, String dataAlamatLengkap, String phoneNumber, String pictureUrl) {
        this.uid = uid;
        this.displayName = displayName;
        this.email = email;
        this.dataAlamat = dataAlamat;
        this.dataAlamatLengkap = dataAlamatLengkap;
        this.phoneNumber = phoneNumber;
        this.pictureUrl = pictureUrl;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("displayName", displayName);
        result.put("email", email);
        result.put("dataAlamat",dataAlamat);
        result.put("dataAlamatLengkap",dataAlamatLengkap);
        result.put("phoneNumber", phoneNumber);
        result.put("pictureUrl", pictureUrl);
        return result;
    }
}
