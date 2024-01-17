package com.msa.supervisor.tool.signalr;

import android.util.Log;

import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;
import com.microsoft.signalr.HubConnectionState;
import java.util.UUID;


/**
 * create by Ali Soleymani.
 */


public class SignalRListener {

    private static SignalRListener instance;
    private final HubConnection hubConnection;
    private final RemoteSignalREmitter remoteSignalREmitter;
    private Thread thread;

    //---------------------------------------------------------------------------------------------- SignalRListener
    public SignalRListener(RemoteSignalREmitter remoteSignalREmitter, String token) {
        this.remoteSignalREmitter = remoteSignalREmitter;

        String url = "" + token;
        hubConnection = HubConnectionBuilder
                .create(url)
                .build();

        hubConnection.on(
                "ReceiveVisitorLocation",
                (tourId, visitorId, lat, lng) -> this.remoteSignalREmitter.onGetPoint(lat, lng, visitorId),
                String.class, String.class, String.class, String.class);
    }
    //---------------------------------------------------------------------------------------------- SignalRListener


    //---------------------------------------------------------------------------------------------- getInstance
    public static SignalRListener getInstance(RemoteSignalREmitter remote, String token) {
        if (instance == null)
            instance = new SignalRListener(remote, token);
        return instance;
    }
    //---------------------------------------------------------------------------------------------- getInstance


    //---------------------------------------------------------------------------------------------- startConnection
    public void startConnection() {
        thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    if (hubConnection.getConnectionState() == HubConnectionState.DISCONNECTED)
                        hubConnection
                                .start()
                                .doOnError(throwable -> remoteSignalREmitter.onErrorConnectToSignalR())
                                .doOnComplete(remoteSignalREmitter::onConnectToSignalR)
                                .blockingAwait();

                    hubConnection.onClosed(exception -> {
                        remoteSignalREmitter.onReConnectToSignalR();
                        interruptThread();
                    });
                } catch (Exception ignored) {
                    remoteSignalREmitter.onReConnectToSignalR();
                    interruptThread();
                }
            }
        };
        thread.start();
    }

    //---------------------------------------------------------------------------------------------- startConnection
    public void DistJoinGroup() {
        Log.e("DistJoinGroup", "DistJoinGroup");
        if (hubConnection.getConnectionState() == HubConnectionState.CONNECTED)
            hubConnection.send("DistJoinGroup");
    }

    public void sendVisitorLocation(UUID torId, String lat, String lon) {
        Log.e("sendVisitorLocation", hubConnection.getConnectionState() + "torId");
        if (hubConnection.getConnectionState() == HubConnectionState.CONNECTED)
            hubConnection.send("sendVisitorLocation", torId, lat, lon);
    }

    //---------------------------------------------------------------------------------------------- stopConnection
    public void stopConnection() {
        if (hubConnection.getConnectionState() == HubConnectionState.CONNECTED)
            hubConnection.stop();
        interruptThread();
    }
    //---------------------------------------------------------------------------------------------- stopConnection


    //---------------------------------------------------------------------------------------------- isConnection
    public boolean isConnection() {
        return hubConnection.getConnectionState() == HubConnectionState.CONNECTED;
    }
    //---------------------------------------------------------------------------------------------- isConnection


    //---------------------------------------------------------------------------------------------- sendToServer
    public void sendToServer(Integer tripId, Integer driverId, String lat, String lon) {
        String arg = "trip" + tripId;
        if (hubConnection.getConnectionState() == HubConnectionState.CONNECTED)
            hubConnection.send("TrackDriver", arg, driverId, lat, lon);
    }
    //---------------------------------------------------------------------------------------------- sendToServer


    //---------------------------------------------------------------------------------------------- NotificationToServer
    public void NotificationToServer(Integer tripId, Integer stationId) {
        String arg = "trip" + tripId + "station" + stationId;
        if (hubConnection.getConnectionState() == HubConnectionState.CONNECTED)
            hubConnection.send("InformNextStation", arg);
    }
    //---------------------------------------------------------------------------------------------- NotificationToServer


    //---------------------------------------------------------------------------------------------- interruptThread
    public void interruptThread() {
        if (thread != null)
            thread.interrupt();
    }
    //---------------------------------------------------------------------------------------------- interruptThread

    /*        hubConnection.on("ReceiveNewPosition", (receive) -> {
            this.remoteSignalREmitter.onReceiveSignalR("receive : " + receive);
        }, Boolean.class);*/

}

