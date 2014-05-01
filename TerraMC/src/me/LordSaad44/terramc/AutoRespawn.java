package me.LordSaad44.terramc;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class AutoRespawn implements Listener {
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player p = event.getEntity();
		try {
			Object nmsPlayer = p.getClass().getMethod("getHandle").invoke(p);
			Object packet = Class.forName(
					nmsPlayer.getClass().getPackage().getName()
							+ ".PacketPlayInClientCommand").newInstance();
			Class<?> enumClass = Class.forName(nmsPlayer.getClass()
					.getPackage().getName()
					+ ".EnumClientCommand");

			for (Object ob : enumClass.getEnumConstants()) {
				if (ob.toString().equals("PERFORM_RESPAWN")) {
					packet = packet.getClass().getConstructor(enumClass)
							.newInstance(ob);
				}
			}

			Object con = nmsPlayer.getClass().getField("playerConnection")
					.get(nmsPlayer);
			con.getClass().getMethod("a", packet.getClass())
					.invoke(con, packet);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}