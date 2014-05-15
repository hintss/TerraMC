package me.LordSaad44.terramc;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Logger;

import me.LordSaad44.terramc.WarpAndHome.pListener;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener, CommandExecutor {

	public static Logger log;
	public static Server server;
	private PluginManager pm;
	@SuppressWarnings("unused")
	private FileConfiguration newConfig;
	private HashMap<String, World> worldList = new HashMap<String, World>();

	private String warpFile;
	private String homeFile;
	private World defWorld;

	public static Economy econ = null;

	private boolean setupEconomy() {

		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer()
				.getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}

	private void initialiseConfig() {

		final FileConfiguration config = this.getConfig();
		config.addDefault("world", "potato");
		config.addDefault("X", 0.0);
		config.addDefault("Y", 0.0);
		config.addDefault("Z", 0.0);
		config.options().copyDefaults(true);
		saveConfig();
	}

	public void RegisterEvents() {

		getServer().getPluginManager().registerEvents(this, this);
		getServer().getPluginManager().registerEvents(new DoubleJump(), this);
		getServer().getPluginManager().registerEvents(new JoinLeaveMessages(),
				this);
		getServer().getPluginManager()
				.registerEvents(new InventoryGuis(), this);
		getServer().getPluginManager().registerEvents(new FlapFly(), this);
		getServer().getPluginManager().registerEvents(new AutoRespawn(), this);
		getServer().getPluginManager().registerEvents(new FunCmds(), this);
	}

	public void RegisterCommands() {

		getCommand("gamemode").setExecutor(new Gamemode());
		getCommand("spawngui").setExecutor(new InventoryGuis());
		getCommand("trails").setExecutor(new InventoryGuis());
		getCommand("flap").setExecutor(new FlapFly());
		getCommand("rps").setExecutor(new RPS());
		getCommand("rockpaperscissors").setExecutor(new RPS());
		getCommand("rock").setExecutor(new RPS());
		getCommand("paper").setExecutor(new RPS());
		getCommand("scissors").setExecutor(new RPS());
		getCommand("heal").setExecutor(new HealFeedGod());
		getCommand("feed").setExecutor(new HealFeedGod());
		getCommand("help").setExecutor(new HelpPage());
		getCommand("throwme").setExecutor(new FunCmds());
		getCommand("autojump").setExecutor(new FunCmds());
		getCommand("fly").setExecutor(new Fly());
	}

	public void RegisterOthers() {

		HealFeedGod blah = new HealFeedGod();
		getServer().getPluginManager().registerEvents(blah, this);
		getCommand("god").setExecutor(blah);
		FunCmds bloh = new FunCmds();
		getServer().getPluginManager().registerEvents(bloh, this);
		getCommand("fly").setExecutor(bloh);
		if (!setupEconomy()) {
			System.out.println(String.format(
					"Disabled. Sorry, but this plugin requires vault...",
					getDescription().getName()));
			getServer().getPluginManager().disablePlugin(this);
		}
	}

	public void WarpAndHome() {

		log = Logger.getLogger("Minecraft");
		pm = getServer().getPluginManager();
		newConfig = getConfig();

		if (!getDataFolder().exists())
			getDataFolder().mkdirs();
		warpFile = getDataFolder().getPath() + File.separator + "warps.db";
		homeFile = getDataFolder().getPath() + File.separator + "homes.db";
		server = getServer();
		reloadConfig();

		for (World w : getServer().getWorlds()) {
			worldList.put(w.getName(), w);
		}
		defWorld = getServer().getWorlds().get(0);

		File oldFile = new File(warpFile.substring(0, warpFile.length() - 2)
				+ "txt");
		File newFile = new File(warpFile);
		if (!newFile.exists() && oldFile.exists()) {
			Locations.migrateWarps(oldFile.getAbsolutePath(), warpFile,
					defWorld);
		}

		oldFile = new File(homeFile.substring(0, homeFile.length() - 2) + "txt");
		newFile = new File(homeFile);
		if (!newFile.exists() && oldFile.exists()) {
			Locations.migrateWarps(oldFile.getAbsolutePath(), homeFile,
					defWorld);
		}

		Locations.loadList(warpFile, Locations.warps, worldList, defWorld);
		Locations.loadList(homeFile, Locations.homes, worldList, defWorld);
		Locations.updateList();

		pm.registerEvents(new pListener(), this);
	}

	public void OnEnableMessage() {
		System.out.println("==============================================");
		System.out.println(" ");
		System.out.println("TerraMC plugin By LordSaad44");
		System.out.println(" ");
		System.out.println("Plugin has been succesfully enabled.");
		System.out.println(" ");
		System.out.println("Have Fun! ^_^");
		System.out.println(" ");
		System.out.println("==============================================");
	}

	public void OnDisableMessage() {
		System.out.println("==============================================");
		System.out.println(" ");
		System.out.println("TerraMC plugin By LordSaad44");
		System.out.println(" ");
		System.out.println("Plugin has been successfully disabled.");
		System.out.println(" ");
		System.out.println("==============================================");
	}

	@Override
	public void onEnable() {

		WarpAndHome();
		initialiseConfig();
		RegisterCommands();
		RegisterEvents();
		RegisterOthers();
		OnEnableMessage();
	}

	@Override
	public void onDisable() {

		OnDisableMessage();
		saveConfig();
		Locations.clear();
		worldList.clear();
		log.info(getDescription().getName() + " version "
				+ getDescription().getVersion() + " is disabled");
	}

	// MOTD
	@EventHandler
	public void onPlayerJoin(final PlayerJoinEvent event) {
		this.getServer().getScheduler()
				.scheduleSyncDelayedTask(this, new Runnable() {
					@Override
					public void run() {
						Player p = event.getPlayer();
						String d = p.getName();
						int maxp = getServer().getMaxPlayers();
						int onlinep = getServer().getOnlinePlayers().length;
						double money = econ.getBalance(event.getPlayer()
								.getDisplayName());
						p.sendMessage(" ");
						p.sendMessage(" ");
						p.sendMessage(" ");
						p.sendMessage(ChatColor.DARK_GRAY + "["
								+ ChatColor.GREEN + "*" + ChatColor.DARK_GRAY
								+ "]" + ChatColor.AQUA
								+ ChatColor.STRIKETHROUGH
								+ "-------------------" + ChatColor.DARK_GRAY
								+ "[" + ChatColor.GREEN + " TerraMC "
								+ ChatColor.DARK_GRAY + "]" + ChatColor.AQUA
								+ ChatColor.STRIKETHROUGH
								+ "--------------------" + ChatColor.DARK_GRAY
								+ "[" + ChatColor.GREEN + "*"
								+ ChatColor.DARK_GRAY + "]");
						p.sendMessage(ChatColor.GRAY
								+ "                           Hello " + d + "!");
						p.sendMessage(ChatColor.GRAY
								+ "                   Your current balance is: "
								+ money);
						p.sendMessage(ChatColor.GRAY
								+ "                        Current players: "
								+ onlinep + "/" + maxp);
						p.sendMessage(ChatColor.GRAY
								+ "    Type /help for a list of commands and useful information.");
						p.sendMessage(ChatColor.DARK_GRAY
								+ "["
								+ ChatColor.GREEN
								+ "*"
								+ ChatColor.DARK_GRAY
								+ "]"
								+ ChatColor.AQUA
								+ ChatColor.STRIKETHROUGH
								+ "-------------------------------------------------"
								+ ChatColor.DARK_GRAY + "[" + ChatColor.GREEN
								+ "*" + ChatColor.DARK_GRAY + "]");
						p.sendMessage(" ");
						p.sendMessage(" ");
					}
				}, 10L);
	}

	// MULTIPLAYER MENU MOTD

	@EventHandler
	public void ping(ServerListPingEvent event) {
		event.setMotd(ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "*"
				+ ChatColor.DARK_GRAY + "]" + ChatColor.AQUA
				+ ChatColor.STRIKETHROUGH + "---------------"
				+ ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + " TerraMC "
				+ ChatColor.DARK_GRAY + "]" + ChatColor.AQUA
				+ ChatColor.STRIKETHROUGH + "---------------"
				+ ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "*"
				+ ChatColor.DARK_GRAY + "]" + ChatColor.WHITE + ChatColor.BOLD
				+ "                Where The Fun Begins");
	}

	// SPAWN STUFF
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		Player p = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("setspawn")) {
			if (sender instanceof Player) {
				if (args.length == 0) {
					if (sender.hasPermission("terra.setspawn")) {
						int X = ((Player) sender).getLocation().getBlockX();
						int Y = ((Player) sender).getLocation().getBlockY();
						int Z = ((Player) sender).getLocation().getBlockZ();

						Bukkit.getWorld("potato").setSpawnLocation(X, Y, Z);
						p.sendMessage(ChatColor.GRAY + "Spawn Set!");
						p.sendMessage(ChatColor.GRAY + "coordinates: " + X
								+ ", " + Y + ", " + Z);
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
		}

		if (cmd.getName().equalsIgnoreCase("spawn")) {
			if (sender instanceof Player) {
				if (args.length == 0) {
					if (sender.hasPermission("terra.spawn")) {

						int X = Bukkit.getWorld("potato").getSpawnLocation()
								.getBlockX();
						int Y = Bukkit.getWorld("potato").getSpawnLocation()
								.getBlockY();
						int Z = Bukkit.getWorld("potato").getSpawnLocation()
								.getBlockZ();

						Location location = new Location(
								Bukkit.getWorld("potato"), X, Y, Z);
						p.teleport(location);
						p.sendMessage(ChatColor.GREEN
								+ "You have been teleported to the spawn.");
						p.playEffect(p.getLocation(), Effect.ENDER_SIGNAL, 0);
						p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT,
								10, 2);
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
