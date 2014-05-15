package me.LordSaad44.terramc;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RPS implements CommandExecutor {
	@SuppressWarnings("unused")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (sender.hasPermission("terra.rps")) {
			if (sender instanceof Player) {
				if (args.length == 0) {

					// RPS
					if (cmd.getName().equalsIgnoreCase("rockpaperscissors")
							|| cmd.getName().equalsIgnoreCase("rps")) {

						sender.sendMessage(ChatColor.GREEN
								+ "Do you choose rock, paper, or scissors?");
					} else {
						int num = new Random().nextInt(3);
						
						if (cmd.getName().equalsIgnoreCase("rock")) {
							if (num == 0) {
								sender.sendMessage(" ");
								sender.sendMessage(ChatColor.GRAY
										+ "Computer's choice:" + ChatColor.BLUE
										+ " Rock");
								sender.sendMessage(ChatColor.GRAY + "Your choice:"
										+ ChatColor.BLUE + "         Rock");
								sender.sendMessage(" ");
								sender.sendMessage(ChatColor.GREEN + "It's a tie!");
								sender.sendMessage(" ");
	
							} else if (num == 1) {
								sender.sendMessage(" ");
								sender.sendMessage(ChatColor.GRAY
										+ "Computer's choice:" + ChatColor.BLUE
										+ " Paper");
								sender.sendMessage(ChatColor.GRAY + "Your choice:"
										+ ChatColor.BLUE + "         Rock");
								sender.sendMessage(" ");
								sender.sendMessage(ChatColor.GREEN
										+ "Computer wins!");
								sender.sendMessage(" ");
	
							} else if (num == 2) {
								sender.sendMessage(ChatColor.GRAY
										+ "Computer's choice:" + ChatColor.BLUE
										+ " Scissors");
								sender.sendMessage(ChatColor.GRAY + "Your choice:"
										+ ChatColor.BLUE + "         Rock");
								sender.sendMessage(" ");
								sender.sendMessage(ChatColor.GREEN + "You win!");
								sender.sendMessage(" ");
							}
						} else if (cmd.getName().equalsIgnoreCase("paper")) {
							if (num == 0) {
								sender.sendMessage(" ");
								sender.sendMessage(ChatColor.GRAY
										+ "Computer's choice:" + ChatColor.BLUE
										+ " Paper");
								sender.sendMessage(ChatColor.GRAY + "Your choice:"
										+ ChatColor.BLUE + "         Paper");
								sender.sendMessage(" ");
								sender.sendMessage(ChatColor.GREEN + "It's a tie!");
								sender.sendMessage(" ");
	
							} else if (num == 1) {
								sender.sendMessage(" ");
								sender.sendMessage(ChatColor.GRAY
										+ "Computer's choice:" + ChatColor.BLUE
										+ " Scissors");
								sender.sendMessage(ChatColor.GRAY + "Your choice:"
										+ ChatColor.BLUE + "         Paper");
								sender.sendMessage(" ");
								sender.sendMessage(ChatColor.GREEN
										+ "Computer wins!");
								sender.sendMessage(" ");
	
							} else if (num == 2) {
								sender.sendMessage(" ");
								sender.sendMessage(ChatColor.GRAY
										+ "Computer's choice:" + ChatColor.BLUE
										+ " Rock");
								sender.sendMessage(ChatColor.GRAY + "Your choice:"
										+ ChatColor.BLUE + "         Paper");
								sender.sendMessage(" ");
								sender.sendMessage(ChatColor.GREEN + "You win!");
								sender.sendMessage(" ");
							}
						} else if (cmd.getName().equalsIgnoreCase("scissors")) {
							if (num == 0) {
								sender.sendMessage(" ");
								sender.sendMessage(ChatColor.GRAY
										+ "Computer's choice:" + ChatColor.BLUE
										+ " Scissors");
								sender.sendMessage(ChatColor.GRAY + "Your choice:"
										+ ChatColor.BLUE + "         Scissors");
								sender.sendMessage(" ");
								sender.sendMessage(ChatColor.GREEN + "It's a tie!");
								sender.sendMessage(" ");
	
							} else if (num == 1) {
								sender.sendMessage(" ");
								sender.sendMessage(ChatColor.GRAY
										+ "Computer's choice:" + ChatColor.BLUE
										+ " Rock");
								sender.sendMessage(ChatColor.GRAY + "Your choice:"
										+ ChatColor.BLUE + "         Scissors");
								sender.sendMessage(" ");
								sender.sendMessage(ChatColor.GREEN
										+ "Computer wins!");
								sender.sendMessage(" ");
	
							} else if (num == 2) {
								sender.sendMessage(" ");
								sender.sendMessage(ChatColor.GRAY
										+ "Computer's choice:" + ChatColor.BLUE
										+ " Paper");
								sender.sendMessage(ChatColor.GRAY + "Your choice:"
										+ ChatColor.BLUE + "         Scissors");
								sender.sendMessage(" ");
								sender.sendMessage(ChatColor.GREEN + "You win!");
								sender.sendMessage(" ");
							}
						}
					}
				} else {
					sender.sendMessage(ChatColor.RED + "Too many arguments");
				}
			} else {
				sender.sendMessage(ChatColor.RED
						+ "Only players can do this, you silly console!");
			}
		} else {
			sender.sendMessage(ChatColor.RED + "Nope...");
		}
		return true;
	}
}
