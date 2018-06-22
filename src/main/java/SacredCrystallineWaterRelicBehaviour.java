import com.derongan.minecraft.mineinabyss.Relic.Behaviour.UseRelicBehaviour;
import com.derongan.minecraft.mineinabyss.Relic.Relics.RelicType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import RelicCooldown.RC;

import java.util.Random;

public class SacredCrystallineWaterRelicBehaviour implements UseRelicBehaviour {
	public static PotionEffectType[] effects = {
			PotionEffectType.SPEED,
			PotionEffectType.INCREASE_DAMAGE,
			PotionEffectType.JUMP,
			PotionEffectType.REGENERATION,
	};

	@Override
	public void onUse(PlayerInteractEvent e) {
		//e.setCancelled(true); //so it don't disappear
		Player player = e.getPlayer();
		if (!RC.cooldownFinished(e.getItem())) {
			RC.notifyCooldown(player, e.getItem());
			return;
		}
		RC.startCooldown(e.getItem(), 5 * 60);

		Random rng = new Random();
		player.addPotionEffect(new PotionEffect(
				effects[rng.nextInt(effects.length)],
				10 * 20,
				0
		));
	}
}
