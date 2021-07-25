package com.dbh4ck.talkinchatbot.service;

import android.util.Log;
import com.dbh4ck.talkinchatbot.utils.Utils;
import com.neovisionaries.ws.client.*;

import java.util.List;
import java.util.Map;

public class SocketEventListener implements WebSocketListener {

    private String userName = "";
    private String passWord = "";
    private String roomName = "";

    public SocketEventListener(WebSocket socket, String user, String pass, String room){
        this.userName = user;
        this.passWord = pass;
        this.roomName = room;
    }

    @Override
    public void onStateChanged(WebSocket websocket, WebSocketState newState) throws Exception {
        //Log.e("dbh4ck", "onStateChanged");
    }

    @Override
    public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception {
        Log.e("dbh4ck", "onConnected");

        if(websocket != null && websocket.isOpen()){
            websocket.sendText(Utils.getInstance().prepareJsonForLogin(userName, passWord));
        }
    }

    @Override
    public void onConnectError(WebSocket websocket, WebSocketException cause) throws Exception {
        //Log.e("dbh4ck", "onConnectError");
    }

    @Override
    public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {
        Log.e("dbh4ck", "onDisconnected");
    }

    @Override
    public void onFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {

    }

    @Override
    public void onContinuationFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {

    }

    @Override
    public void onTextFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {

    }

    @Override
    public void onBinaryFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
    }

    @Override
    public void onCloseFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
    }

    @Override
    public void onPingFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
        Log.e("dbh4ck", "onPingFrame");
    }

    @Override
    public void onPongFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
        Log.e("dbh4ck", "onPongFrame");
    }

    @Override
    public void onTextMessage(WebSocket websocket, String text) throws Exception {
        Log.e("dbh4ck", "onTextMessage");
        OperationController.getController().handleSocketMsg(websocket, userName, roomName, text);
    }

    @Override
    public void onTextMessage(WebSocket websocket, byte[] data) throws Exception {
    }

    @Override
    public void onBinaryMessage(WebSocket websocket, byte[] binary) throws Exception {
        //Log.e("dbh4ck", "onBinaryMessage");
    }

    @Override
    public void onSendingFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
        //Log.e("dbh4ck", "onSendingFrame");
    }

    @Override
    public void onFrameSent(WebSocket websocket, WebSocketFrame frame) throws Exception {
        //Log.e("dbh4ck", "onFrameSent");
    }

    @Override
    public void onFrameUnsent(WebSocket websocket, WebSocketFrame frame) throws Exception {
        //Log.e("dbh4ck", "onFrameUnsent");
    }

    @Override
    public void onThreadCreated(WebSocket websocket, ThreadType threadType, Thread thread) throws Exception {
        //Log.e("dbh4ck", "onThreadCreated");
    }

    @Override
    public void onThreadStarted(WebSocket websocket, ThreadType threadType, Thread thread) throws Exception {
        //Log.e("dbh4ck", "onThreadStarted");
    }

    @Override
    public void onThreadStopping(WebSocket websocket, ThreadType threadType, Thread thread) throws Exception {
        //Log.e("dbh4ck", "onThreadStopping");
    }

    @Override
    public void onError(WebSocket websocket, WebSocketException cause) throws Exception {
        Log.e("dbh4ck", "onError");
    }

    @Override
    public void onFrameError(WebSocket websocket, WebSocketException cause, WebSocketFrame frame) throws Exception {
        //Log.e("dbh4ck", "onFrameError");
    }

    @Override
    public void onMessageError(WebSocket websocket, WebSocketException cause, List<WebSocketFrame> frames) throws Exception {
        //Log.e("dbh4ck", "onMessageError");
    }

    @Override
    public void onMessageDecompressionError(WebSocket websocket, WebSocketException cause, byte[] compressed) throws Exception {
        //Log.e("dbh4ck", "onMessageDecompressionError");
    }

    @Override
    public void onTextMessageError(WebSocket websocket, WebSocketException cause, byte[] data) throws Exception {
        //Log.e("dbh4ck", "onTextMessageError");
    }

    @Override
    public void onSendError(WebSocket websocket, WebSocketException cause, WebSocketFrame frame) throws Exception {
        //Log.e("dbh4ck", "onSendError");
    }

    @Override
    public void onUnexpectedError(WebSocket websocket, WebSocketException cause) throws Exception {
        //Log.e("dbh4ck", "onUnexpectedError");
    }

    @Override
    public void handleCallbackError(WebSocket websocket, Throwable cause) throws Exception {
        //Log.e("dbh4ck", "handleCallbackError");
    }

    @Override
    public void onSendingHandshake(WebSocket websocket, String requestLine, List<String[]> headers) throws Exception {
        //Log.e("dbh4ck", "onSendingHandshake");
    }

}
