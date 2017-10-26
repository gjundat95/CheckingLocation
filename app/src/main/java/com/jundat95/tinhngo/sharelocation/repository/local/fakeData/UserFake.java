package com.jundat95.tinhngo.sharelocation.repository.local.fakeData;

import android.os.Bundle;

import com.jundat95.tinhngo.sharelocation.repository.local.model.UserModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tinhngo on 10/26/17.
 */

public class UserFake {

    public static List<UserModel> userModels = new ArrayList<>();
    public static UserModel userModel = null;

    public UserFake() {

        userModels.add(new UserModel(
                "1", "ducictu94", "123456", "Duc Nguyen", "DTC135D48010300"
        ));
        userModels.add(new UserModel(
                "2", "ducnguyen", "123456", "Duc Nguyen", "DTC135D48010307"
        ));
        userModels.add(new UserModel(
                "3", "tinhngo", "123456", "Tinh Ngo", "DTC135D48010306"
        ));
        userModels.add(new UserModel(
                "4", "vanan", "123456", "Van An", "DTC135D48010301"
        ));
        userModels.add(new UserModel(
                "5", "xuanlinh", "123456", "Linh Chu", "DTC135D48010344"
        ));
        userModels.add(new UserModel(
                "6", "thanhhuyen", "123456", "Thanh Huyen", "DTC135D48010312"
        ));

    }

    public static UserFake newInstance() {
        UserFake instance = new UserFake();
        return instance;
    }

    public boolean Login(String userName, String password) {
        for (UserModel item : userModels) {
            if(item.getUserName().equals(userName) && item.getPassword().equals(password)) {
                if(userModel == null){
                    userModel = item;
                } else {
                    userModel.setId(item.getId());
                    userModel.setFullName(item.getFullName());
                    userModel.setEmployeeCode(item.getEmployeeCode());
                    userModel.setPassword(item.getPassword());
                    userModel.setUserName(item.getUserName());
                }
                return true;
            }
        }
        return false;
    }

    public List<UserModel> getUserModels() {
        return userModels;
    }

    public void setUserModels(List<UserModel> userModels) {
        UserFake.userModels = userModels;
    }

    public void setUserModel(UserModel userModel) {
        userModels.add(userModel);
    }

    public UserModel getUserModel() {
        return userModel;
    }
}
