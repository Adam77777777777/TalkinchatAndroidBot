package com.dbh4ck.talkinchatbot.utils;

import android.annotation.SuppressLint;
import android.os.Build;
import android.provider.Settings;
import com.dbh4ck.talkinchatbot.MainApp;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import static com.dbh4ck.talkinchatbot.utils.Constants.*;

public class Utils {

    private static Utils mInstance = null;

    private Utils(){

    }

    public static Utils getInstance() {
        if(mInstance == null){
            mInstance = new Utils();
        }
        return mInstance;
    }

    public String prepareJsonForLogin(String userName, String passWord){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(HANDLER, LOGIN);
            jsonObject.put(REQ_ID, generateRandomId());
            jsonObject.put(USERNAME, userName);
            jsonObject.put(PASSWORD, passWord);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  jsonObject.toString();
    }

    public String prepareJsonForGroupJoin(String roomName){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(REQ_ID, generateRandomId());
            jsonObject.put(HANDLER, ROOM_JOIN);
            jsonObject.put(NAME, roomName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  jsonObject.toString();
    }

    public String prepareJsonForSendGroupMsg(String roomName, String msgBody, String msgUrl, String msgLength){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(HANDLER, ROOM_MESSAGE);
            jsonObject.put(REQ_ID, Utils.getInstance().generateRandomId());
            jsonObject.put(TYPE, MSG_TYPE_TEXT);
            jsonObject.put(ROOM, roomName);
            jsonObject.put(MSG_BODY, msgBody);
            jsonObject.put(MSG_URL, msgUrl);
            jsonObject.put(MSG_LENGTH, msgLength);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  jsonObject.toString();
    }

    public String generateRandomId() {
        char[] charArray = "abcdefghijklmnopqrstuvwxyzABCDEFGHJKLMNOPQRSTUVWXYZ".toCharArray();
        StringBuilder sb = new StringBuilder(20);
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            sb.append(charArray[random.nextInt(charArray.length)]);
        }
        return sb.toString();
    }

    public String getAndroidId(){
        @SuppressLint("HardwareIds") String string = Settings.Secure.getString(MainApp.getMainApp().getContentResolver(), "android_id");
        return string != null ? string : "null";
    }

    public String getBuildInfo() {
        String sdkInt = Integer.toString(Build.VERSION.SDK_INT);
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        String nullStr = "null";
        if (manufacturer == null) manufacturer = nullStr;
        if (model == null) model = nullStr;
        String separator = "-";
        return "258-" +
                manufacturer +
                separator +
                model +
                separator +
                sdkInt;
    }
}
