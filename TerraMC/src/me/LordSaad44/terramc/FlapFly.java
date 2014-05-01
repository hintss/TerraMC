package me.LordSaad44.terramc;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class FlapFly implements CommandExecutor, Listener {
	public static ArrayList<String> flapusers = new ArrayList<String>();

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {

		if (cmd.getName().equalsIgnoreCase("flap")) {
			if (sender instanceof Player) {
				if (args.length == 0) {
					if (sender.hasPermission("terra.flap")) {
						if (flapusers.contains(sender.getName())) {
							flapusers.remove(sender.getName());
							sender.sendMessage(ChatColor.GRAY + "Flap mode "
									+ ChatColor.RED + "disabled.");
							((Player) sender).setAllowFlight(false);
							((Player) sender).setFlying(false);

							if (((Player) sender).getGameMode() == GameMode.CREATIVE) {
								((Player) sender).setAllowFlight(true);
								((Player) sender).setFlying(true);
							}
						} else {
							flapusers.add(sender.getName());
							sender.sendMessage(ChatColor.GRAY + "Flap mode "
									+ ChatColor.GREEN + "enabled.");
							if (((Player) sender).getGameMode() == GameMode.CREATIVE) {
								((Player) sender).setAllowFlight(true);
								((Player) sender).setFlying(false);
							}
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

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		if ((event.getPlayer().hasPermission("terra.flap"))
				&& (flapusers.contains(event.getPlayer().getName()))
				&& (event.getPlayer().getLocation().getBlock()
						.getRelative(BlockFace.DOWN).getType() != Material.AIR)) {
			event.getPlayer().setAllowFlight(true);
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onFly(PlayerToggleFlightEvent event) {
		Player player = event.getPlayer();
		if ((player.hasPermission("terra.flap"))
				&& flapusers.contains(event.getPlayer().getName())) {
			event.setCancelled(true);
			player.setAllowFlight(true);
			player.setVelocity(player.getLocation().getDirection()
					.multiply(0.35D).setY(0.5D));
			player.playEffect(player.getLocation(), Effect.SMOKE, 0);
			player.playSound(player.getLocation(), Sound.ENDERDRAGON_WINGS, 10,
					1);
		}
	}
}