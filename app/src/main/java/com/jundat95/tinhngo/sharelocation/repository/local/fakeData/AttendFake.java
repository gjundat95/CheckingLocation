package com.jundat95.tinhngo.sharelocation.repository.local.fakeData;

import com.jundat95.tinhngo.sharelocation.repository.local.model.AttendModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tinhngo on 10/26/17.
 */

public class AttendFake {

    public static List<AttendModel> attendModels = new ArrayList<>();

    public AttendFake() {
    }

    public static AttendFake newInstance() {
        AttendFake attendFake = new AttendFake();
        return attendFake;
    }

    public static List<AttendModel> getAttendModels() {
        return attendModels;
    }

    public static void setAttendModels(List<AttendModel> attendModels) {
        AttendFake.attendModels = attendModels;
    }

    public static void setAttendModels(AttendModel attendModel) {
        AttendFake.attendModels.add(attendModel);
    }
}
