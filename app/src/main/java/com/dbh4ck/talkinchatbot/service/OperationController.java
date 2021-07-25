package com.dbh4ck.talkinchatbot.service;

import android.os.Looper;
import android.widget.Toast;
import com.dbh4ck.talkinchatbot.MainApp;
import com.dbh4ck.talkinchatbot.utils.Constants;
import com.dbh4ck.talkinchatbot.utils.Utils;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;
import java.util.logging.Handler;

import static com.dbh4ck.talkinchatbot.utils.Constants.*;

public class OperationController {

    private static OperationController _instance = null;
    public WebSocket webSocket;
    public WebSocketFactory webSocketFactory;

    private OperationController(){
        webSocketFactory = new WebSocketFactory();
    }

    public static OperationController getController() {
        if (_instance == null) {
            _instance = new OperationController();
        }
        return _instance;
    }

    public void attemptLogin(String userName, String passWord, String roomName) {
        try {
            if (webSocket != null && webSocket.isOpen()) {
                this.webSocket.disconnect();
            }
            this.webSocket = this.webSocketFactory.createSocket(SOCKET_URL);
            this.webSocket.addListener(new SocketEventListener(webSocket, userName, passWord, roomName));
            String buildInfo = Utils.getInstance().getBuildInfo();
            String androidId = Utils.getInstance().getAndroidId();

            if (androidId == null) {
                androidId = "null";
            }
            this.webSocket.addHeader("m", buildInfo);
            this.webSocket.addHeader("i", androidId);
            new Thread(
                    () -> {
                        try {
                            this.webSocket.connect();
                        } catch (WebSocketException e) {
                            e.printStackTrace();
                        }
                    }
            ).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleSocketMsg(WebSocket webSocket, String userName, String roomName, String rawTxt) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(rawTxt);
            String objStr = jsonObject.getString(HANDLER);

            // On Success Login, make Bot Join Group
            if(objStr.equals(EVENT_LOGIN)){
                if(jsonObject.getString(TYPE).equals(SUCCESS)){
                    if(webSocket != null && webSocket.isOpen()){
                        webSocket.sendText(Utils.getInstance().prepareJsonForGroupJoin(roomName));
                    }

                    MainApp.getMainApp().runOnUiThread(
                            () -> {
                                Toast.makeText(MainApp.getMainApp(), "Success", Toast.LENGTH_SHORT).show();
                            }
                    );
                }
                if(jsonObject.getString(TYPE).equals(FAILED)){
                    String reason = jsonObject.getString(REASON);
                    MainApp.getMainApp().runOnUiThread(
                            () -> {
                                Toast.makeText(MainApp.getMainApp(), reason, Toast.LENGTH_SHORT).show();
                            }
                    );
                    //return;
                }
            }

            // Welcome User -- Bot Event
            if(objStr.equals(EVENT_ROOM)){
                if(jsonObject.getString(TYPE).equals(USER_JOINED)){
                    sendGroupMsg(webSocket, jsonObject.getString(NAME), jsonObject.getString(USERNAME) + ": Welcome \uD83D\uDC7B");
                }
            }

            // Handle Rest Room Events
            if(objStr.equals(EVENT_ROOM)){
                if(jsonObject.getString(TYPE).equals(MSG_TYPE_TEXT)){
                    // To make bot join other Groups

                    if(jsonObject.getString(MSG_BODY).equals("bot")){
                        sendGroupMsg(webSocket, jsonObject.getString(ROOM), "hey There! I'm Online Now (via Android)");
                    }

                    if(jsonObject.getString(MSG_BODY).startsWith("!join ")){
                        String groupToJoin = jsonObject.getString(MSG_BODY).toLowerCase();
                        if(webSocket != null && webSocket.isOpen()){
                            webSocket.sendText(Utils.getInstance().prepareJsonForGroupJoin(groupToJoin.substring(6)));
                        }
                    }

                    // Handle the Spin
                    if(jsonObject.getString(MSG_BODY).equalsIgnoreCase(".s") || jsonObject.getString(MSG_BODY).equalsIgnoreCase("spin")){

                        Random r = new Random();
                        int randomItem = r.nextInt(Constants.SPIN_MSG_STRINGS.size());
                        String randomElement = Constants.SPIN_MSG_STRINGS.get(randomItem);
                        sendGroupMsg(webSocket, jsonObject.getString(ROOM), randomElement);
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(MainApp.getMainApp(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void sendGroupMsg(WebSocket webSocket, String roomName, String msgBody) {
        String str = "";

        if (webSocket != null && webSocket.isOpen()){
            webSocket.sendText(Utils.getInstance().prepareJsonForSendGroupMsg(roomName, msgBody, str, str));
        }
    }

}
