package me.LordSaad44.terramc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HelpPage implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label,
                             String[] args) {
        int maxp = Bukkit.getServer().getMaxPlayers();
        int onlinep = Bukkit.getServer().getOnlinePlayers().length;
        double money = Main.econ.getBalance(sender.getName());
        if (cmd.getName().equalsIgnoreCase("help")) {
            if (args.length == 0) {
                sender.sendMessage(" ");
                sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GREEN
                        + "*" + ChatColor.DARK_GRAY + "]" + ChatColor.AQUA
                        + ChatColor.STRIKETHROUGH + "---------------------"
                        + ChatColor.DARK_GRAY + "[" + ChatColor.GREEN
                        + " Help " + ChatColor.DARK_GRAY + "]" + ChatColor.AQUA
                        + ChatColor.STRIKETHROUGH + "---------------------"
                        + ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "*"
                        + ChatColor.DARK_GRAY + "]");
                sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GREEN
                        + "*" + ChatColor.DARK_GRAY + "]" + ChatColor.GRAY
                        + " For command help. Type" + ChatColor.BLUE
                        + " /help commands");
                sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GREEN
                        + "*" + ChatColor.DARK_GRAY + "]" + ChatColor.GRAY
                        + " For information. Type" + ChatColor.BLUE
                        + " /help information");
                sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GREEN
                        + "*" + ChatColor.DARK_GRAY + "]" + ChatColor.AQUA
                        + ChatColor.STRIKETHROUGH
                        + "-------------------------------------------------"
                        + ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "*"
                        + ChatColor.DARK_GRAY + "]");
                sender.sendMessage(" ");

            } else if (args[0].equalsIgnoreCase("information")) {

                sender.sendMessage(" ");
                sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GREEN
                        + "*" + ChatColor.DARK_GRAY + "]" + ChatColor.AQUA
                        + ChatColor.STRIKETHROUGH + "------------------"
                        + ChatColor.DARK_GRAY + "[" + ChatColor.GREEN
                        + " information " + ChatColor.DARK_GRAY + "]"
                        + ChatColor.AQUA + ChatColor.STRIKETHROUGH
                        + "-------------------" + ChatColor.DARK_GRAY + "["
                        + ChatColor.GREEN + "*" + ChatColor.DARK_GRAY + "]");
                sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GREEN
                        + "*" + ChatColor.DARK_GRAY + "]" + ChatColor.GRAY
                        + " Current players: " + onlinep + "/" + maxp);
                sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GREEN
                        + "*" + ChatColor.DARK_GRAY + "]" + ChatColor.GRAY
                        + " Your current balance is: " + money);
                sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GREEN
                        + "*" + ChatColor.DARK_GRAY + "]" + ChatColor.GRAY
                        + " Your current balance is: " + money);
                sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GREEN
                        + "*" + ChatColor.DARK_GRAY + "]" + ChatColor.AQUA
                        + ChatColor.STRIKETHROUGH
                        + "-------------------------------------------------"
                        + ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "*"
                        + ChatColor.DARK_GRAY + "]");
                sender.sendMessage(" ");
            }
        }
        return true;
    }
}