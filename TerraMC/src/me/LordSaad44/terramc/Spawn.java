package me.LordSaad44.terramc;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Spawn implements CommandExecutor {

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		Player p = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("setspawn")) {
			if (sender instanceof Player) {
				if (args.length == 0) {
					if (sender.hasPermission("terra.setspawn")) {
						Location loc = p.getLocation();
						int X = loc.getBlockX();
						int Y = loc.getBlockY();
						int Z = loc.getBlockZ();
						p.getWorld().setSpawnLocation(X, Y, Z);
						p.sendMessage(ChatColor.GRAY + "Spawn Set!");
						p.playEffect(p.getLocation(), Effect.ENDER_SIGNAL, 0);

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

			if (cmd.getName().equalsIgnoreCase("spawn")) {
				if (sender instanceof Player) {
					if (args.length == 0) {
						if (sender.hasPermission("terra.spawn")) {
							p.teleport(p.getWorld().getSpawnLocation());
							p.sendMessage(ChatColor.GREEN
									+ "You have been teleported to the spawn.");
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
		}
		return true;
	}
}
