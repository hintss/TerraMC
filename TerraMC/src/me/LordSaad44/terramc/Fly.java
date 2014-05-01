package me.LordSaad44.terramc;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class Fly implements CommandExecutor, Listener {
	public static ArrayList<String> flyusers = new ArrayList<String>();

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {

		if (cmd.getName().equalsIgnoreCase("fly")) {
			if (sender instanceof Player) {
				if (args.length == 0) {
					if (sender.hasPermission("terra.fly")) {
						if (flyusers.contains(sender.getName())) {
							flyusers.remove(sender.getName());
							sender.sendMessage(ChatColor.GRAY + "Fly mode "
									+ ChatColor.RED + "disabled.");
							((Player) sender).setFlying(false);
							((Player) sender).setAllowFlight(false);
							;
						} else {
							flyusers.add(sender.getName());
							sender.sendMessage(ChatColor.GRAY + "Fly mode "
									+ ChatColor.GREEN + "enabled.");
						}
					} else {
						sender.sendMessage(ChatColor.RED + "Nope...");
					}
				} else {
					sender.sendMessage(ChatColor.RED + "Too many arguments");
				}
			} else {
				sender.sendMessage(ChatColor.RED
						+ "Only players can do this, you silly console!");
			}
		}
		return true;
	}
}
