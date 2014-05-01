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
					}

					// ROCK
					if (cmd.getName().equalsIgnoreCase("rock")) {

						Random dicerock = new Random();
						int maxrock = 3;
						int minrock = 1;
						int counterrock = minrock;
						int numrock = 1 + dicerock.nextInt(maxrock);

						if (numrock == 1) {

							sender.sendMessage(ChatColor.GRAY
									+ "Computer's choice:" + ChatColor.BLUE
									+ " Rock");
							sender.sendMessage(ChatColor.GRAY + "Your choice:"
									+ ChatColor.BLUE + "         Rock");
							sender.sendMessage(" ");
							sender.sendMessage(ChatColor.GREEN + "It's a tie!");

						} else if (numrock == 2) {

							sender.sendMessage(ChatColor.GRAY
									+ "Computer's choice:" + ChatColor.BLUE
									+ " Paper");
							sender.sendMessage(ChatColor.GRAY + "Your choice:"
									+ ChatColor.BLUE + "         Rock");
							sender.sendMessage(" ");
							sender.sendMessage(ChatColor.GREEN
									+ "Computer wins!");

						} else if (numrock == 3) {

							sender.sendMessage(ChatColor.GRAY
									+ "Computer's choice:" + ChatColor.BLUE
									+ " Scissors");
							sender.sendMessage(ChatColor.GRAY + "Your choice:"
									+ ChatColor.BLUE + "         Rock");
							sender.sendMessage(" ");
							sender.sendMessage(ChatColor.GREEN + "You win!");
						}
					}

					// PAPER
					if (cmd.getName().equalsIgnoreCase("paper")) {

						Random dicepaper = new Random();
						int maxpaper = 3;
						int minpaper = 1;
						int counterpaper = minpaper;
						int numpaper = 1 + dicepaper.nextInt(maxpaper);

						if (numpaper == 1) {

							sender.sendMessage(ChatColor.GRAY
									+ "Computer's choice:" + ChatColor.BLUE
									+ " Paper");
							sender.sendMessage(ChatColor.GRAY + "Your choice:"
									+ ChatColor.BLUE + "         Paper");
							sender.sendMessage(" ");
							sender.sendMessage(ChatColor.GREEN + "It's a tie!");

						} else if (numpaper == 2) {

							sender.sendMessage(ChatColor.GRAY
									+ "Computer's choice:" + ChatColor.BLUE
									+ " Scissors");
							sender.sendMessage(ChatColor.GRAY + "Your choice:"
									+ ChatColor.BLUE + "         Paper");
							sender.sendMessage(" ");
							sender.sendMessage(ChatColor.GREEN
									+ "Computer wins!");

						} else if (numpaper == 3) {

							sender.sendMessage(ChatColor.GRAY
									+ "Computer's choice:" + ChatColor.BLUE
									+ " Rock");
							sender.sendMessage(ChatColor.GRAY + "Your choice:"
									+ ChatColor.BLUE + "         Paper");
							sender.sendMessage(" ");
							sender.sendMessage(ChatColor.GREEN + "You win!");
						}
					}

					// SCISSORS
					if (cmd.getName().equalsIgnoreCase("scissors")) {

						Random dicescis = new Random();
						int maxscis = 3;
						int minscis = 1;
						int counterscis = minscis;
						int numscis = 1 + dicescis.nextInt(maxscis);

						if (numscis == 1) {

							sender.sendMessage(ChatColor.GRAY
									+ "Computer's choice:" + ChatColor.BLUE
									+ " Scissors");
							sender.sendMessage(ChatColor.GRAY + "Your choice:"
									+ ChatColor.BLUE + "         Scissors");
							sender.sendMessage(" ");
							sender.sendMessage(ChatColor.GREEN + "It's a tie!");

						} else if (numscis == 2) {

							sender.sendMessage(ChatColor.GRAY
									+ "Computer's choice:" + ChatColor.BLUE
									+ " Rock");
							sender.sendMessage(ChatColor.GRAY + "Your choice:"
									+ ChatColor.BLUE + "         Scissors");
							sender.sendMessage(" ");
							sender.sendMessage(ChatColor.GREEN
									+ "Computer wins!");

						} else if (numscis == 3) {

							sender.sendMessage(ChatColor.GRAY
									+ "Computer's choice:" + ChatColor.BLUE
									+ " Paper");
							sender.sendMessage(ChatColor.GRAY + "Your choice:"
									+ ChatColor.BLUE + "         Scissors");
							sender.sendMessage(" ");
							sender.sendMessage(ChatColor.GREEN + "You win!");
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
