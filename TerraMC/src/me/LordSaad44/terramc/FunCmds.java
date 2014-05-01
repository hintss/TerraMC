package me.LordSaad44.terramc;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class FunCmds implements CommandExecutor, Listener {
	public static ArrayList<String> autojumpusers = new ArrayList<String>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (cmd.getName().equalsIgnoreCase("throwme")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (args.length == 0) {
					if (sender.hasPermission("terra.throwme")) {
						p.setVelocity(p.getLocation().getDirection()
								.multiply(7.0D).setY(5.0D));
					} else {
						sender.sendMessage(ChatColor.RED + "Nope...");
					}

				} else {
					sender.sendMessage(ChatColor.RED + "Too many Arguments.");
				}
			} else {
				sender.sendMessage(ChatColor.RED
						+ "Only players can do this, you silly console!");
			}
		}

		if (cmd.getName().equalsIgnoreCase("autojump")) {
			if (sender instanceof Player) {
				if (args.length == 0) {
					if (sender.hasPermission("terra.autojump")) {
						if (autojumpusers.contains(sender.getName())) {
							autojumpusers.remove(sender.getName());
							sender.sendMessage(ChatColor.GRAY
									+ "Auto jump mode " + ChatColor.RED
									+ "disabled.");

						} else {
							autojumpusers.add(sender.getName());
							sender.sendMessage(ChatColor.GRAY
									+ "Auto jump mode " + ChatColor.GREEN
									+ "enabled.");
						}
					} else {
						sender.sendMessage(ChatColor.RED + "Nope...");
					}

				} else {
					sender.sendMessage(ChatColor.RED + "Too many Arguments.");
				}
			} else {
				sender.sendMessage(ChatColor.RED
						+ "Only players can do this, you silly console!");
			}
		}
		return true;

	}

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		if ((event.getPlayer().hasPermission("terra.autojump"))
				&& autojumpusers.contains(event.getPlayer().getName())
				&& (event.getPlayer().getLocation().getBlock()
						.getRelative(BlockFace.DOWN).getType() != Material.AIR)) {
			Vector v = event.getPlayer().getLocation().getDirection()
					.multiply(0.5).setY(0.5);
			event.getPlayer().setVelocity(v);
		}
	}
}
