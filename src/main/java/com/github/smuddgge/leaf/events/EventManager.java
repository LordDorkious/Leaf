package com.github.smuddgge.leaf.events;

import com.github.smuddgge.leaf.Leaf;
import com.github.smuddgge.leaf.database.records.PlayerRecord;
import com.github.smuddgge.leaf.database.tables.HistoryTable;
import com.github.smuddgge.leaf.database.tables.PlayerTable;
import com.github.smuddgge.leaf.datatype.User;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.player.ServerPostConnectEvent;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.server.RegisteredServer;

import java.util.Optional;

/**
 * Manages proxy events for this plugin.
 */
public class EventManager {

    /**
     * Executed before a player connects to a server.
     *
     * @param event Post connection event.
     */
    public static void onPlayerJoin(ServerPostConnectEvent event) {
        User user = new User(event.getPlayer());

        // Update the player in the database
        user.updateDatabase();

        // Get server connecting to
        Optional<ServerConnection> optionalServer = event.getPlayer().getCurrentServer();
        if (optionalServer.isEmpty()) return;
        RegisteredServer server = optionalServer.get().getServer();

        // Add history
        user.addHistory(server, PlayerHistoryEventType.JOIN);
    }

    /**
     * Executed when a player disconnects from a server.
     *
     * @param event Disconnection event.
     */
    public static void onPlayerLeave(DisconnectEvent event) {
        User user = new User(event.getPlayer());

        // Update the player in the database
        user.updateDatabase();

        // Get server connecting to
        Optional<ServerConnection> optionalServer = event.getPlayer().getCurrentServer();
        if (optionalServer.isEmpty()) return;
        RegisteredServer server = optionalServer.get().getServer();

        // Add history
        user.addHistory(server, PlayerHistoryEventType.LEAVE);
    }
}
