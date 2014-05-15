package me.LordSaad44.terramc;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.bukkit.Location;
import org.bukkit.World;

public class Locations {

	public static HashMap<String, Warp> warps = new HashMap<String, Warp>();
	public static HashMap<String, Warp> homes = new HashMap<String, Warp>();
	public static String warpList[];

	public static void saveList(String locFile, HashMap<String, Warp> List) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(locFile,
					false));

			for (Map.Entry<String, Warp> entry : List.entrySet()) {
				Location l = entry.getValue().loc;
				String name = entry.getValue().fullName;
				bw.append(name + ":" + l.getX() + ":" + l.getY() + ":" + l.getZ() + ":" l.getYaw() + ":" + entry.getValue().world + ":" + entry.getValue().cost);
				bw.newLine();
			}
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void loadList(String locFile, HashMap<String, Warp> List,
			HashMap<String, World> worldList, World defWorld) {
		Scanner scanner;
		try {
			scanner = new Scanner(new FileReader(locFile));
			while (scanner.hasNextLine()) {
				String[] elements = scanner.nextLine().split(":");
				if (elements.length < 6) {
					continue;
				}

				World w = worldList.get(elements[5]);

				Location l = new Location(w, Double.parseDouble(elements[1]),
						Double.parseDouble(elements[2]),
						Double.parseDouble(elements[3]));
				l.setYaw(Float.parseFloat(elements[4]));
				double cost = -1;
				if (elements.length > 6)
					cost = Double.parseDouble(elements[6]);

				List.put(elements[0].toLowerCase(), new Warp(elements[0],
						elements[5], l, cost));
			}
		} catch (FileNotFoundException e) {
		} catch (Exception e) {

		} finally {
			if (scanner != null)
				scanner.close();
		}
	}

	public static void migrateWarps(String oldFile, String locFile, World world) {
		Scanner scanner = null;
		HashMap<String, Warp> list = new HashMap<String, Warp>();
		try {
			scanner = new Scanner(new FileReader(oldFile));
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.equals(""))
					continue;
				String[] elements = line.split(":");
				if (elements.length < 5) {
					continue;
				}

				Location l = new Location(world,
						Double.parseDouble(elements[1]),
						Double.parseDouble(elements[2]),
						Double.parseDouble(elements[3]));
				l.setYaw(Float.parseFloat(elements[4]));

				list.put(elements[0].toLowerCase(),
						new Warp(elements[0], world.getName(), l));
			}
			Locations.saveList(locFile, list);
		} catch (Exception e) {

		} finally {
			if (scanner != null)
				scanner.close();
		}
	}

	public static void clear() {
		Locations.warps.clear();
		Locations.warpList = new String[0];
	}

	public static void addHome(Location loc, String name) {
		Locations.homes.put(name.toLowerCase(), new Warp(name, loc, -1));
	}

	public static boolean removeHome(String name) {
		return (Locations.homes.remove(name.toLowerCase()) != null);
	}

	public static Location getHome(String name) {
		Warp warp = Locations.homes.get(name.toLowerCase());
		if (warp == null)
			return null;
		if (warp.loc.getWorld() == null) {
			warp.loc.setWorld(Main.server.getWorld(warp.world));
			if (warp.loc.getWorld() == null)
				return null;
		}
		return warp.loc;
	}

	public static void addWarp(Location loc, String name, double cost) {
		Locations.warps.put(name.toLowerCase(), new Warp(name, loc, cost));
		Locations.updateList();
	}

	public static void removeWarp(String name) {
		Locations.warps.remove(name.toLowerCase());
		Locations.updateList();
	}

	public static void updateList() {
		Collection<Warp> list = Locations.warps.values();
		Locations.warpList = new String[list.size()];
		int i = 0;
		for (Warp w : list) {
			Locations.warpList[i++] = w.fullName;
		}
		Arrays.sort(Locations.warpList);
	}

	public static Location getWarp(String name) {
		Warp warp = Locations.warps.get(name.toLowerCase());
		if (warp == null)
			return null;
		if (warp.loc.getWorld() == null) {
			warp.loc.setWorld(Main.server.getWorld(warp.world));
			if (warp.loc.getWorld() == null)
				return null;
		}
		return warp.loc;
	}

	public static double getWarpCost(String name) {
		Warp warp = Locations.warps.get(name.toLowerCase());
		if (warp == null)
			return -1;
		return warp.cost;
	}

	public static String[] getWarpList() {
		return Locations.warpList;
	}

	public static class Warp {
		public String fullName;
		public String world;
		public Location loc;
		public double cost;

		Warp(String fullName, String world, Location loc) {
			this(fullName, world, loc, -1);
		}

		Warp(String fullName, String world, Location loc, double cost) {
			this.fullName = fullName;
			this.loc = loc;
			this.world = world;
			this.cost = cost;
		}

		Warp(String fullName, Location loc, double cost) {
			this.fullName = fullName;
			this.loc = loc;
			this.world = "";
			if (this.loc.getWorld() != null)
				this.world = this.loc.getWorld().getName();
			this.cost = cost;
		}
	}
}
