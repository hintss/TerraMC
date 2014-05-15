package me.LordSaad44.terramc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryGuis implements Listener, CommandExecutor {

	public static Inventory SpawnInv = Bukkit.createInventory(null, 27,
			ChatColor.BLACK + "              Spawn Gui");
	public static Inventory Trails = Bukkit.createInventory(null, 9,
			ChatColor.BLACK + "               Trails");
	static {
		SpawnInv.setItem(13, new ItemStack(Material.COMPASS, 1));
		Trails.setItem(5, new ItemStack(Material.FEATHER, 1));
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (cmd.getName().equalsIgnoreCase("spawngui")) {
			if (sender instanceof Player) {
				if (args.length == 0) {
					if (sender.hasPermission("terra.spawninv")) {

						((Player) sender).openInventory(SpawnInv);

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
		} else if (cmd.getName().equalsIgnoreCase("trails")) {
			if (sender instanceof Player) {
				if (args.length == 0) {
					if (sender.hasPermission("terra.trails")) {

						((Player) sender).openInventory(Trails);

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
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (player.getItemInHand() != null) {
			ItemStack item = player.getItemInHand();

			if (item.getType() == Material.DIAMOND_SPADE) {

				player.openInventory(SpawnInv);

			} else if (item.getType() == Material.PAPER) {

				player.openInventory(Trails);

			} else if (item.getType() == Material.STICK) {

				Projectile egg = event.getPlayer().launchProjectile(Egg.class);
				egg.setVelocity(event.getPlayer().getLocation().getDirection()
						.multiply(1).setY(0.5));
			}
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack clicked = event.getCurrentItem();
		Inventory inventory = event.getInventory();

		if (inventory.getName().equals(SpawnInv.getName())) {
			if (clicked.getType() == Material.COMPASS) {
				player.teleport(new Location(Bukkit.getWorld("potato"), 215.99,
						77, 151.95));
			}
			event.setCancelled(true);
		} else if (inventory.getName().equals(Trails.getName())) {
			if (clicked.getType() == Material.FEATHER) {
				player.getInventory().addItem(new ItemStack(Material.STICK, 1));
			}
			event.setCancelled(true);
		}
	}
}
