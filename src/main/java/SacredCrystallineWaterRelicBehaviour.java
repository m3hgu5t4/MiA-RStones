import com.derongan.minecraft.mineinabyss.Relic.Behaviour.UseRelicBehaviour;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import m3hTools.RC;
import m3hTools.Potion;

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
		if (!RC.doEverything(player, e.getItem(), 5*60)) {
			return;
		}

		Random rng = new Random();
		Potion.addEffect(player, effects[rng.nextInt(effects.length)], 10, 5);
	}
}
