package me.LordSaad44.terramc;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class WarpAndHome implements CommandExecutor {

	Main plugin;

	public WarpAndHome(Main instance) {
		plugin = instance;
	}

	private HashMap<String, World> worldList = new HashMap<String, World>();

	private String warpFile;
	private String homeFile;
	private World defWorld;
	private static boolean noPrefix = false;
	private boolean bedHome = false;

	public boolean onCommand1(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (cmd.getName().equalsIgnoreCase("reloadwarps")) {
			if (sender.hasPermission("terra.warpadmin")) {
				Locations.clear();
				
				Locations.loadList(warpFile, Locations.warps, worldList,
						defWorld);
				Locations.loadList(homeFile, Locations.homes, worldList,
						defWorld);
				
				Locations.updateList();
				plugin.reloadConfig();
				sender.sendMessage("Warpz0r database and config reloaded");
			} else {
				sender.sendMessage("Permissions denied");
			}
			return true;
		}

		if (!(sender instanceof Player))
			return false;

		Player player = (Player) sender;
		String comName = cmd.getName().toLowerCase();

		// WARP

		if (comName.equals("warp")) {
			if (!hasPerm(player, "terra.warp")) {
				sendMessage(player, ChatColor.RED + "Nope...", true);
				return true;
			}
			if (args.length != 1) {
				return false;
			}
			Location loc = Locations.getWarp(args[0]);
			if (loc != null) {
				if (!loc.getWorld().getName()
						.equals(player.getWorld().getName())
						&& !hasPerm(player, "terra.warp")) {
					sendMessage(player, ChatColor.RED + "Nope...", true);
					return true;
				}

				// Load the chunk
				
				loc.getBlock().getChunk().load();
				
				// Keep the current vertical looking direction
				
				loc.setPitch(player.getLocation().getPitch());
				player.teleport(loc);
				sendMessage(player, ChatColor.GREEN + "Ypu have been teleported to: " + args[0], false);
				
			} else {
				sendMessage(player, ChatColor.RED + "That is not a warp. Check for typos.", true);

			}
			return true;
			
			//SETWARP
			
		} else if (comName.equals("setwarp")) {
			if (!hasPerm(player, "terra.warpadmin")) {
				sendMessage(player, ChatColor.RED + "Nope...", true);
				return true;
			}
			if (args.length == 0 || args.length > 2) {
				return false;
			}

			Location loc = player.getLocation();
			Locations.addWarp(loc, args[0]);
			Locations.saveList(warpFile, Locations.warps);
			sendMessage(player, ChatColor.AQUA + "Warp has been successfully set as: " + args[0], false);
			
			// Command: /removewarp
			
		} else if (comName.equals("removewarp")) {
			if (!hasPerm(player, "terra.warpadmin")) {
				sendMessage(player, ChatColor.RED + "Nope...", true);
				return true;
				
			}
			if (args.length != 1) {
				return false;
			}
			
			Location loc = Locations.getWarp(args[0]);
			if (loc == null) {
				sendMessage(player,  ChatColor.RED + "That is not a warp. Check for typos.", true);

				return true;
			}
			
			Locations.removeWarp(args[0]);
			Locations.saveList(warpFile, Locations.warps);
			sendMessage(player, ChatColor.AQUA + "Warp has been successfully remove: " + args[0], false);
			log.info("[Warpz0r] " + player.getName() + " removed warp "
					+ args[0]);
			return true;
			
			// LIST WARPS
			
		} else if (comName.equals("warps")) {
			if (!hasPerm(player, "terra.warp")) {
				sendMessage(player, ChatColor.RED + "Nope...", true);
				return true;
			}

			String[] warpList = Locations.getWarpList();
			if (warpList.length != 0) {
				StringBuilder sb = new StringBuilder();
				sb.append(warpList[0]);
				for (int i = 1; i < warpList.length; i++)
					sb.append(" | ").append(warpList[i]);
				sendMessage(player,
						ChatColor.AQUA + "Warps: " + ChatColor.WHITE + ChatColor.BOLD + sb.toString(), false);
			} else {
				sendMessage(player, ChatColor.RED + "No warps found. Sowwy...", true);
			}
			return true;
			
			// SETHOME
			
		} else if (comName.equals("sethome")) {
			String pName = player.getName();
			if (args.length > 0) {
				if (!hasPerm(player, "terra.home.set")) {
					sendMessage(player, ChatColor.RED + "Nope...", true);
					return true;
				}
				pName = args[0];
				sendMessage(player, ChatColor.GREEN + "Your home has been set here.", false);
				
			} else {
				if (!hasPerm(player, "terra.home.set")) {
					sendMessage(player, ChatColor.RED + "Nope...", true);
					return true;
				}
				
				sendMessage(player, ChatColor.GREEN + "Your home has been set here.", false);
			}
		
			
			Location loc = player.getLocation();
			Locations.addHome(loc, pName);
			Locations.saveList(homeFile, Locations.homes);
			return true;
			
			// HOME
			
			 else if (comName.equals("home")) {
		        	String pName = player.getName();
		        	if (args.length > 0) {
		        		if (!hasPerm(player, "terra.home")) {
		        			sendMessage(player, ChatColor.RED + "Nope...", true);
		        			return true;
		        		}
		        		pName = args[0];
		        	} else {
			            if (!hasPerm(player, "terra.home")) {
			                sendMessage(player, ChatColor.RED + "Nope...", true);
			                return true;
			            }
		        	}
		        	
		            Location loc = Locations.getHome(pName);
		            if (loc == null) {
		            	sendMessage(player, ChatColor.RED + "That home is not found. Check for typos.", false);
		            	return true;
		            }
		            
		            if (args.length == 0) {
			            if (!loc.getWorld().getName().equals(player.getWorld().getName()) && !hasPerm(player, "terra.home")) {
			                sendMessage(player, ChatColor.RED + "Nope...", true);
			                return true;
			            }
			            sendMessage(player, ChatColor.GREEN + "You have been teleported to your home.", false);
			            
		            } else {
		            	sendMessage(player, ChatColor.GREEN + "You have been the home of: " + pName, false);
		            }
		            
		            loc.setPitch(player.getLocation().getPitch());
		            player.teleport(loc);
		            return true;
			 }

	private Player getPlayer(String name) {
		List<Player> players = server.matchPlayer(name);
		if (players.size() < 1) {
			return null;
		}
		Player target = players.get(0);
		return target;
	}

	public boolean hasPerm(Player player, String perm) {
		return player.hasPermission(perm);
	}
}
