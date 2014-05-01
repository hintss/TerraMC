package me.LordSaad44.terramc;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Gamemode implements CommandExecutor {
    @Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
                             String[] args) {
        if (cmd.getName().equalsIgnoreCase("gamemode")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (args.length == 0) {
                    if (p.getGameMode() == GameMode.SURVIVAL) {
                        p.setGameMode(GameMode.CREATIVE);
                        p.sendMessage(ChatColor.GRAY
                                + "You are now in creative mode");
                    } else {
                        p.setGameMode(GameMode.SURVIVAL);
                        p.sendMessage(ChatColor.GRAY
                                + "You are now in survival mode");
                    }
                }
                if (args.length >= 1) {
                    if (args[0].equals("0")
                            || args[0].equalsIgnoreCase("survival")) {
                        p.setGameMode(GameMode.SURVIVAL);
                        p.sendMessage(ChatColor.GRAY
                                + "You are now in survival mode");
                    } else if (args[0].equals("1")
                            || args[0].equalsIgnoreCase("creative")) {
                        p.setGameMode(GameMode.CREATIVE);
                        p.sendMessage(ChatColor.GRAY
                                + "You are now in creative mode");
                    } else if (args[0].equals("2")
                            || args[0].equalsIgnoreCase("adventure")) {
                        p.setGameMode(GameMode.ADVENTURE);
                        p.sendMessage(ChatColor.GRAY
                                + "You are now in adventure mode");
                    } else {
                        p.sendMessage(ChatColor.GRAY + "wtf is that gamemode?");
                    }
                }
            } else {
                sender.sendMessage(ChatColor.RED
                        + "Only players can do this, you silly console!");
            }
            return true;
        }
        return false;
    }
}