package pw.teg.bsm.util;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Map;

public class ServerHelper {

    public static boolean serverExists(String name) {
        return getServerInfo(name) != null;
    }

    public static boolean serverExists(String name, boolean checkCase) {
        if (checkCase) {
            for (String s : getServers().keySet()) {
                if (name.equals(s)) {
                    return true;
                }
            }

            return false;
        }

        return serverExists(name);
    }

    public static ServerInfo getServerInfo(String name) {
        for (String s : getServers().keySet()) {
            if (s.equalsIgnoreCase(name)) {
                return getServers().get(s);
            }
        }

        return null;
    }

    public static void addServer(ServerInfo serverInfo) {
        if (serverExists(serverInfo.getName())) {
            return;
        }

        getServers().put(serverInfo.getName(), serverInfo);
        ConfigHelper.addToConfig(serverInfo);
    }

    public static void removeServer(String name) {
        if (!serverExists(name)) {
            return;
        }

        ServerInfo info = getServerInfo(name);

        for (ProxiedPlayer p : info.getPlayers()) {
            p.connect(getServers().get(p.getPendingConnection().getListener().getFallbackServer()));
        }

        getServers().remove(name);
        ConfigHelper.removeFromConfig(name);
    }

    public static Map<String, ServerInfo> getServers() {
        return ProxyServer.getInstance().getServers();
    }

}
