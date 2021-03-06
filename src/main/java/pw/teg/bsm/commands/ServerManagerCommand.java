package pw.teg.bsm.commands;

import com.google.common.base.Joiner;
import net.md_5.bungee.BungeeServerInfo;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Command;
import pw.teg.bsm.util.ServerHelper;

import java.net.InetSocketAddress;

public class ServerManagerCommand extends Command {

    private String prefix = ChatColor.GRAY + "[" + ChatColor.GREEN + "ServerManager" + ChatColor.GRAY + "] ";

    public ServerManagerCommand() {
        super("servermanager", "servermanager.use", "svm");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sendHelp(sender);
            return;
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("help")) {
                sendHelp(sender);
                return;
            }

            if (args[0].equalsIgnoreCase("list")) {
                if (ServerHelper.getServers().isEmpty()) {
                    sender.sendMessage(TextComponent.fromLegacyText(prefix + "No servers."));
                    return;
                }

                sender.sendMessage(TextComponent.fromLegacyText(prefix + "Servers: " + ChatColor.GREEN + Joiner.on(ChatColor.GRAY + ", " + ChatColor.GREEN).join(ServerHelper.getServers().keySet())));
                return;
            }

            if (args[0].equalsIgnoreCase("info")) {
                sendUsage(sender, "/svm info <server>");
                return;
            }

            if (args[0].equalsIgnoreCase("add")) {
                sendUsage(sender, "/svm add <server>");
                return;
            }

            if (args[0].equalsIgnoreCase("remove")) {
                sendUsage(sender, "/svm remove <server>");
                return;
            }

            if (args[0].equalsIgnoreCase("edit")) {
                sendUsage(sender, "/svm edit <server>");
                return;
            }
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("help")) {
                sendHelp(sender);
                return;
            }

            if (args[0].equalsIgnoreCase("list")) {
                sendUsage(sender, "/svm list");
                return;
            }

            if (args[0].equalsIgnoreCase("info")) {
                if (!ServerHelper.serverExists(args[1])) {
                    sender.sendMessage(TextComponent.fromLegacyText(prefix + "The server " + ChatColor.GREEN + args[1] + ChatColor.GRAY + " does not exist."));
                    return;
                }

                ServerInfo info = ServerHelper.getServerInfo(args[1]);

                if (info == null) {
                    sender.sendMessage(TextComponent.fromLegacyText(prefix + "The server " + ChatColor.GREEN + args[1] + ChatColor.GRAY + " does not exist."));
                    return;
                }

                sender.sendMessage(TextComponent.fromLegacyText(ChatColor.GRAY + "--- " + ChatColor.GREEN + info.getName() + " Info" + ChatColor.GRAY + " ---"));
                sender.sendMessage(TextComponent.fromLegacyText(ChatColor.GREEN + "Name: " + ChatColor.GRAY + info.getName()));
                sender.sendMessage(TextComponent.fromLegacyText(ChatColor.GREEN + "Address: " + ChatColor.GRAY + info.getAddress().getAddress().getHostAddress() + ":" + info.getAddress().getPort()));
                sender.sendMessage(TextComponent.fromLegacyText(ChatColor.GREEN + "Motd: " + ChatColor.GRAY + info.getMotd()));
                sender.sendMessage(TextComponent.fromLegacyText(ChatColor.GREEN + "Player Count: " + ChatColor.GRAY + info.getPlayers().size()));
                sender.sendMessage(TextComponent.fromLegacyText(ChatColor.GRAY + "--------"));
                return;
            }

            if (args[0].equalsIgnoreCase("add")) {
                if (ServerHelper.serverExists(args[1])) {
                    sender.sendMessage(TextComponent.fromLegacyText(prefix + "The server " + ChatColor.GREEN + args[1] + ChatColor.GRAY + " already exists."));
                    return;
                }

                ServerInfo info = new BungeeServerInfo(args[1], new InetSocketAddress(22565), "", false);

                ServerHelper.addServer(info);
                sender.sendMessage(TextComponent.fromLegacyText(prefix + "Added a server with the name " + ChatColor.GREEN + args[1]));
                return;
            }

            if (args[0].equalsIgnoreCase("remove")) {
                if (!ServerHelper.serverExists(args[1])) {
                    sender.sendMessage(TextComponent.fromLegacyText(prefix + "The server " + ChatColor.GREEN + args[1] + ChatColor.GRAY + " does not exist."));
                    return;
                }

                ServerHelper.removeServer(args[1]);
                sender.sendMessage(TextComponent.fromLegacyText(prefix + "Removed the server " + ChatColor.GREEN + args[1]));
                return;
            }

            if (args[0].equalsIgnoreCase("edit")) {
                if (!ServerHelper.serverExists(args[1])) {
                    sender.sendMessage(TextComponent.fromLegacyText(prefix + "The server " + ChatColor.GREEN + args[1] + ChatColor.GRAY + " does not exist."));
                    return;
                }

                sendEditMenu(sender, ServerHelper.getServerInfo(args[1]).getName());
                return;
            }
        }

        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("help")) {
                sendHelp(sender);
                return;
            }

            if (args[0].equalsIgnoreCase("list")) {
                sendUsage(sender, "/svm list");
                return;
            }

            if (args[0].equalsIgnoreCase("info")) {
                sendUsage(sender, "/svm info <server>");
                return;
            }

            if (args[0].equalsIgnoreCase("add")) {
                if (ServerHelper.serverExists(args[1])) {
                    sender.sendMessage(TextComponent.fromLegacyText(prefix + "The server " + ChatColor.GREEN + args[1] + ChatColor.GRAY + " already exists."));
                    return;
                }

                InetSocketAddress address = getIp(args[2]);

                if (address == null) {
                    sender.sendMessage(TextComponent.fromLegacyText(prefix + "Invalid ip address " + ChatColor.GREEN + args[2] + ChatColor.GRAY + ". Here's an example: " + ChatColor.GREEN + "127.0.0.1:25565"));
                    return;
                }

                ServerInfo info = new BungeeServerInfo(args[1], address, "", false);

                ServerHelper.addServer(info);
                sender.sendMessage(TextComponent.fromLegacyText(prefix + "Added a server with the name " + ChatColor.GREEN + args[1] + ChatColor.GRAY + " and ip address " + ChatColor.GREEN + args[2] + ChatColor.GREEN + "."));
                return;
            }

            if (args[0].equalsIgnoreCase("remove")) {
                sendUsage(sender, "/svm remove <server>");
                return;
            }

            if (args[0].equalsIgnoreCase("edit")) {
                sendUsage(sender, "/svm edit <server>");
                return;
            }
        }

        if (args.length == 4) {
            if (args[0].equalsIgnoreCase("help")) {
                sendHelp(sender);
                return;
            }

            if (args[0].equalsIgnoreCase("list")) {
                sendUsage(sender, "/svm list");
                return;
            }

            if (args[0].equalsIgnoreCase("info")) {
                sendUsage(sender, "/svm info <server>");
                return;
            }

            if (args[0].equalsIgnoreCase("add")) {
                sendUsage(sender, "/svm add <server>");
                return;
            }

            if (args[0].equalsIgnoreCase("remove")) {
                sendUsage(sender, "/svm remove <server>");
                return;
            }

            if (args[0].equalsIgnoreCase("edit")) {
                if (!ServerHelper.serverExists(args[1])) {
                    sender.sendMessage(TextComponent.fromLegacyText(prefix + "The server " + ChatColor.GREEN + args[1] + ChatColor.GRAY + " does not exist."));
                    return;
                }

                ServerInfo info = ServerHelper.getServerInfo(args[1]);

                if (info == null) {
                    sender.sendMessage(TextComponent.fromLegacyText(prefix + "The server " + ChatColor.GREEN + args[1] + ChatColor.GRAY + " does not exist."));
                    return;
                }

                if (args[2].equalsIgnoreCase("name")) {
                    if (ServerHelper.serverExists(args[3], true)) {
                        sender.sendMessage(TextComponent.fromLegacyText(prefix + "The server " + ChatColor.GREEN + args[1] + ChatColor.GRAY + " already exists."));
                        return;
                    }

                    ServerHelper.removeServer(info.getName());
                    ServerHelper.addServer(new BungeeServerInfo(args[3], info.getAddress(), info.getMotd(), false));
                    sender.sendMessage(TextComponent.fromLegacyText(prefix + "Renamed " + ChatColor.GREEN + info.getName() + ChatColor.GRAY + " to " + ChatColor.GREEN + args[3] + ChatColor.GRAY + "."));
                    return;
                }

                if (args[2].equalsIgnoreCase("ip")) {
                    InetSocketAddress address = getIp(args[3]);

                    if (address == null) {
                        sender.sendMessage(TextComponent.fromLegacyText(prefix + "Invalid ip address " + ChatColor.GREEN + args[2] + ChatColor.GRAY + ". Here's an example: " + ChatColor.GREEN + "127.0.0.1:25565"));
                        return;
                    }

                    ServerHelper.removeServer(info.getName());
                    ServerHelper.addServer(new BungeeServerInfo(info.getName(), address, info.getMotd(), false));
                    sender.sendMessage(TextComponent.fromLegacyText(prefix + "Set the ip address of " + ChatColor.GREEN + info.getName() + ChatColor.GRAY + " to " + ChatColor.GREEN + args[3] + ChatColor.GRAY + "."));
                    return;
                }

                if (args[2].equalsIgnoreCase("motd")) {
                    ServerHelper.removeServer(info.getName());
                    ServerHelper.addServer(new BungeeServerInfo(info.getName(), info.getAddress(), ChatColor.translateAlternateColorCodes('&', args[3]), false));
                    sender.sendMessage(TextComponent.fromLegacyText(prefix + "Set the motd of " + ChatColor.GREEN + info.getName() + ChatColor.GRAY + " to " + ChatColor.GREEN + ChatColor.translateAlternateColorCodes('&', args[3]) + ChatColor.GRAY + "."));
                    return;
                }

                return;
            }
        }

        if (args.length > 4) {
            if (args[0].equalsIgnoreCase("edit") && args[2].equalsIgnoreCase("motd")) {
                if (!ServerHelper.serverExists(args[1])) {
                    sender.sendMessage(TextComponent.fromLegacyText(prefix + "The server " + ChatColor.GREEN + args[1] + ChatColor.GRAY + " does not exist."));
                    return;
                }

                ServerInfo info = ServerHelper.getServerInfo(args[1]);

                if (info == null) {
                    sender.sendMessage(TextComponent.fromLegacyText(prefix + "The server " + ChatColor.GREEN + args[1] + ChatColor.GRAY + " does not exist."));
                    return;
                }

                StringBuilder builder = new StringBuilder();
                for (int i = 3; i < args.length; i++) {
                    builder.append(args[i]).append(" ");
                }

                ServerHelper.removeServer(info.getName());
                ServerHelper.addServer(new BungeeServerInfo(info.getName(), info.getAddress(), ChatColor.translateAlternateColorCodes('&', builder.toString().trim()), false));
                sender.sendMessage(TextComponent.fromLegacyText(prefix + "Set the motd of " + ChatColor.GREEN + info.getName() + ChatColor.GRAY + " to " + ChatColor.GREEN + ChatColor.translateAlternateColorCodes('&', builder.toString().trim()) + ChatColor.GRAY + "."));
                return;
            }

            if (args[0].equalsIgnoreCase("help")) {
                sendHelp(sender);
                return;
            }

            if (args[0].equalsIgnoreCase("list")) {
                sendUsage(sender, "/svm list");
                return;
            }

            if (args[0].equalsIgnoreCase("info")) {
                sendUsage(sender, "/svm info <server>");
                return;
            }

            if (args[0].equalsIgnoreCase("add")) {
                sendUsage(sender, "/svm add <server>");
                return;
            }

            if (args[0].equalsIgnoreCase("remove")) {
                sendUsage(sender, "/svm remove <server>");
                return;
            }

            if (args[0].equalsIgnoreCase("edit")) {
                sendUsage(sender, "/svm edit <server>");
                return;
            }
        }

        sender.sendMessage(TextComponent.fromLegacyText(prefix + "Unknown argument " + ChatColor.GREEN + args[0] + ChatColor.GRAY + " use " + ChatColor.GREEN + "/svm help" + ChatColor.GRAY + " for help."));
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage(TextComponent.fromLegacyText(prefix + "Help:"));
        sender.sendMessage(getHelpString("/svm help", "Display this help menu"));
        sender.sendMessage(getHelpString("/svm list", "List servers"));
        sender.sendMessage(getHelpString("/svm info <server>", "Display info about a server"));
        sender.sendMessage(getHelpString("/svm add <name> [ip:port]", "Add a server to BungeeCord"));
        sender.sendMessage(getHelpString("/svm remove <server>", "Remove a server from BungeeCord"));
        sender.sendMessage(getHelpString("/svm edit <server>", "Edit a server's information"));
    }

    private BaseComponent[] getHelpString(String command, String info) {
        return new ComponentBuilder(ChatColor.GREEN + command + ChatColor.GRAY + " - " + info)
                .event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, command))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(ChatColor.GREEN + "Click to execute this command")))
                .create();
    }

    private void sendUsage(CommandSender sender, String usage) {
        sender.sendMessage(new ComponentBuilder(prefix + "Correct usage: " + ChatColor.GREEN + usage)
                .event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "command"))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(ChatColor.GREEN + "Click to execute this command")))
                .create());
    }

    private void sendEditMenu(CommandSender sender, String serverName) {
        sender.sendMessage(TextComponent.fromLegacyText(prefix + " Edit Help:"));
        sender.sendMessage(getHelpString("/svm edit " + serverName + " name <name>", "Change this server's name"));
        sender.sendMessage(getHelpString("/svm edit " + serverName + " ip <ip>", "Change this server's ip address"));
        sender.sendMessage(getHelpString("/svm edit " + serverName + " motd <motd>", "Change this server's motd"));
    }

    private InetSocketAddress getIp(String input) {
        if (!input.contains(":") || !input.contains(".")) {
            return null;
        }

        String[] parts = input.split(":");

        if (input.split(":").length != 2) {
            return null;
        }

        if (input.split("\\.").length != 4) {
            return null;
        }

        for (char c : parts[0].replace(".", "").toCharArray()) {
            if (!Character.isDigit(c)) {
                return null;
            }
        }

        for (char c : parts[1].toCharArray()) {
            if (!Character.isDigit(c)) {
                return null;
            }
        }

        return new InetSocketAddress(parts[0], Integer.valueOf(parts[1]));
    }
}
