package me.LordSaad44.terramc;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.ArrayList;

public class HealFeedGod implements CommandExecutor, Listener {
    ArrayList<String> godModeUsers = new ArrayList<String>();

    @Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
                             String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (cmd.getName().equalsIgnoreCase("heal")) {
                if (args.length == 0) {
                    if (p.hasPermission("terra.heal")) {
                        p.setHealth(20.0D);
                        p.setFoodLevel(22);
                        sender.sendMessage(ChatColor.GRAY
                                + "You have been healed.");
                    } else {
                        sender.sendMessage(ChatColor.RED + "Nope...");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Too many arguments");
                }
            } else if (cmd.getName().equalsIgnoreCase("feed")) {
                if (args.length == 0) {
                    if (p.hasPermission("terra.feed")) {
                        p.setFoodLevel(22);
                        sender.sendMessage(ChatColor.GRAY
                                + "You have been fed.");
                    } else {
                        sender.sendMessage(ChatColor.RED + "Nope...");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Too many arguments");
                }
            } else if (cmd.getName().equalsIgnoreCase("god")) {

                if (args.length == 0) {
                    if (p.hasPermission("terra.god")) {
                        if (godModeUsers.contains(sender.getName())) {
                            godModeUsers.remove(sender.getName());
                            sender.sendMessage(ChatColor.GRAY
                                    + "God mode disabled");
                        } else {
                            godModeUsers.add(sender.getName());
                            sender.sendMessage(ChatColor.GRAY
                                    + "God mode enabled");
                            p.setHealth(20.0D);
                            p.setFoodLevel(22);
                        }

                    } else {
                        sender.sendMessage(ChatColor.RED + "Nope...");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Too many arguments");
                }
            }
        } else {
            sender.sendMessage(ChatColor.RED
                    + "Only players can do this, you silly console!");
        }
        return true;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {

        if (e.getEntity() != null && e.getEntity() instanceof Player) {
            if (godModeUsers.contains(((Player) e.getEntity()).getName())) {
                e.setCancelled(true);
            }
        }
    }

}