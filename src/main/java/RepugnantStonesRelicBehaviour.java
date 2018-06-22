import com.derongan.minecraft.mineinabyss.Relic.Behaviour.UseRelicBehaviour;
import com.derongan.minecraft.mineinabyss.Relic.Relics.RelicType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;
import java.util.List;

import static java.lang.Math.sqrt;

public class RepugnantStonesRelicBehaviour implements UseRelicBehaviour {
	@Override
	public void onUse(PlayerInteractEvent e) {
		Player player = e.getPlayer();

		e.getItem().setAmount(
				e.getItem().getAmount() - 1
		);

		double x = player.getLocation().getX();
		double y = player.getLocation().getY();
		double z = player.getLocation().getZ();

		Location l;
		double dist, xdist, ydist, zdist;

		for (Player otherPlayer : player.getWorld().getPlayers()) {

			l = otherPlayer.getLocation();
			xdist = l.getX() - x;
			ydist = l.getY() - y;
			zdist = l.getZ() - z;
			dist = sqrt(xdist * xdist + ydist * ydist + zdist * zdist);

			if (dist < 20 && dist > -20) {
				otherPlayer.addPotionEffect(new PotionEffect(
						PotionEffectType.CONFUSION,
						120 * 20,  //2 minutes enough for smell stones?
						0
				));
			}
		}
	}
}
