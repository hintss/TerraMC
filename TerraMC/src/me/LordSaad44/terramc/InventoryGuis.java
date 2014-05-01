package me.LordSaad44.terramc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryGuis implements Listener, CommandExecutor {

	public static Inventory SpawnInv = Bukkit.createInventory(null, 27,
			ChatColor.BLACK + "              Spawn Gui");
	static {
		SpawnInv.setItem(13, new ItemStack(Material.COMPASS, 1));
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (cmd.getName().equalsIgnoreCase("spawngui")) {
			if (sender instanceof Player) {
				if (args.length == 0) {
					if (sender.hasPermission("terra.spawninv")) {

						((Player) sender).openInventory(SpawnInv);

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
		return true;
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (player.getItemInHand() != null) {
			ItemStack item = player.getItemInHand();
			if (item.getType() == Material.DIAMOND_SPADE) {

				player.openInventory(SpawnInv);
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
				event.setCancelled(true);
				player.teleport(new Location(Bukkit.getWorld("potato"), 215.99,
						77, 151.95));
			}
		}
	}
}