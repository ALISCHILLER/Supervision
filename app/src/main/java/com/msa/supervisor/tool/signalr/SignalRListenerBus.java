package com.msa.supervisor.tool.signalr;

import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;
import com.microsoft.signalr.HubConnectionState;

import java.util.ArrayList;
import java.util.List;

/**
 * create by Ali Soleymani.
 */
public class SignalRListenerBus {

    private final HubConnection hubConnection;
    private final RemoteSignalREmitter remoteSignalREmitter;
    private Thread thread;

    //---------------------------------------------------------------------------------------------- SignalRListener
    public SignalRListenerBus(RemoteSignalREmitter remoteSignalREmitter, String token) {
        this.remoteSignalREmitter = remoteSignalREmitter;

        String url = "" + token;
        hubConnection = HubConnectionBuilder
                .create(url)
                .build();

        hubConnection.on(
                "ReceiveVisitorLocation",
                (tourId, visitorId, lat, lng) -> remoteSignalREmitter.onGetPoint(lat, lng,visitorId),
                String.class, String.class, String.class, String.class);


        hubConnection.on("PreviousStationReached",
                remoteSignalREmitter::onPreviousStationReached, String.class);

    }
    //---------------------------------------------------------------------------------------------- SignalRListener



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


    //---------------------------------------------------------------------------------------------- registerToGroupForService
    public void registerToGroupForService(Integer tripId, Integer stationId) {
        List<String> groups = new ArrayList<>();
        String trip = "trip" + tripId;
        String station = trip + "station" + stationId;
        groups.add(trip);
        groups.add(station);
        if (hubConnection.getConnectionState() == HubConnectionState.CONNECTED)
            hubConnection.send("RegisterToGroup", groups);
    }
    //---------------------------------------------------------------------------------------------- registerToGroupForService



    //---------------------------------------------------------------------------------------------- interruptThread
    public void interruptThread() {
        if (thread != null)
            thread.interrupt();
    }
    //---------------------------------------------------------------------------------------------- interruptThread

}
