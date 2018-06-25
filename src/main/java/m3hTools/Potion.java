package m3hTools;

import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Potion {
	public static void addEffect(LivingEntity entity, PotionEffectType effect, double seconds, int level) {

		PotionEffect currentEffect = entity.getPotionEffect(effect);
		if (currentEffect == null) { //if there isn't already an effect of the type
			if (level == 0) { return; } //nothing to remove
			entity.addPotionEffect(new PotionEffect(effect,	(int) seconds * 20,	level - 1));
		} else {

			if (level == 0) { //remove dat shit
				entity.removePotionEffect(effect);
			} else {
				if (currentEffect.getAmplifier() == level - 1) { //if the same amp
					if (seconds * 20 > currentEffect.getDuration()) { //if its got a shorter duration than the new one
						entity.removePotionEffect(effect);
						entity.addPotionEffect(new PotionEffect(effect, (int) seconds * 20, level - 1));
					}
				} else if (currentEffect.getAmplifier() < level - 1) { //replace if its weaker
					entity.removePotionEffect(effect);
					entity.addPotionEffect(new PotionEffect(effect, (int) seconds * 20, level - 1));
				} //leave it if its stronker
			}

		}

	}
}
