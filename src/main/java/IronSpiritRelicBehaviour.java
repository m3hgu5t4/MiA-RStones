import com.derongan.minecraft.mineinabyss.Relic.Behaviour.UseRelicBehaviour;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import RelicCooldown.RC;

public class IronSpiritRelicBehaviour implements UseRelicBehaviour {
	@Override
	public void onUse(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		if (!RC.cooldownFinished(e.getItem())) {
			RC.notifyCooldown(player, e.getItem());
			return;
		}
		RC.startCooldown(e.getItem(), 60);

		int dur = 10 * 20;
		player.addPotionEffect(new PotionEffect(
				PotionEffectType.DAMAGE_RESISTANCE,
				dur,
				2
		));
		player.addPotionEffect(new PotionEffect(
				PotionEffectType.SLOW,
				dur,
				3
		));
		player.addPotionEffect(new PotionEffect(
				PotionEffectType.REGENERATION,
				dur,
				0
		));
	}
}
