package me.LordSaad44.terramc;

import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class DoubleJump implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		event.getPlayer().setAllowFlight(true);
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent e) {
		if (((e.getEntity() instanceof Player))
				&& (e.getCause() == EntityDamageEvent.DamageCause.FALL)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		if ((event.getPlayer().hasPermission("terra.doublejump"))
				&& (event.getPlayer().getGameMode() != GameMode.CREATIVE)
				&& (event.getPlayer().getLocation().getBlock()
						.getRelative(BlockFace.DOWN).getType() != Material.AIR)) {
			event.getPlayer().setAllowFlight(true);
		}
	}

	@SuppressWarnings({ "deprecation" })
	@EventHandler
	public void onFly(PlayerToggleFlightEvent event) {
		Player player = event.getPlayer();
		if ((player.hasPermission("terra.doublejump"))
				&& (player.getGameMode() != GameMode.CREATIVE)
				&& !Fly.flyusers.contains(event.getPlayer().getName())) {
			event.setCancelled(true);
			player.setAllowFlight(false);
			player.setFlying(false);
			player.setVelocity(player.getLocation().getDirection()
					.multiply(0.35D).setY(0.75));
			player.playEffect(player.getLocation(), Effect.SMOKE, 0);
			player.playSound(player.getLocation(), Sound.ENDERDRAGON_WINGS, 5,
					1);
		}
	}
}