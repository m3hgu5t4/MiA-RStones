import com.derongan.minecraft.mineinabyss.Relic.Behaviour.UseRelicBehaviour;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import m3hTools.RC;

public class IronSpiritRelicBehaviour implements UseRelicBehaviour {
	@Override
	public void onUse(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		if (!RC.doEverything(player, e.getItem(), 60)) {
			return;
		}

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
