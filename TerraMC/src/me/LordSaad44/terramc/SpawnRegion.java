package me.LordSaad44.terramc;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class SpawnRegion {
	private Shape shape;
	private World world;

	public SpawnRegion(Shape shape, Plugin plugin, World world) {
		this.shape = shape;
		this.world = world;
		monitor(plugin);
	}

	private void monitor(Plugin plugin) {
		Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
			@Override
			public void run() {
				for (Player player : world.getPlayers()) {
					if (shape.contains(player.getLocation())) {
						// do something with the player
					}
				}
			}
		}, 0, 20);
	}

	public interface Shape {
		public boolean contains(Location location);
	}

	public class Sphere implements Shape {
		private Location center;
		private double radiusSquared;

		public Sphere(Location center, double radius) {
			this.center = center;
			this.radiusSquared = radius * radius;
		}

		@Override
		public boolean contains(Location location) {
			double distSquared = center.distanceSquared(location);
			return distSquared < radiusSquared;
		}
	}
}
