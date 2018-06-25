import com.derongan.minecraft.mineinabyss.Relic.Behaviour.EntityHitRelicBehaviour;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import m3hTools.Potion;

public class DragonTeethRelicBehaviour implements EntityHitRelicBehaviour {
	@Override
	public void onHit(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof LivingEntity) {
			int amp = 1; //1 tooth gives lv1 wither
			float count = ((Player) e.getDamager()).getInventory().getItemInMainHand().getAmount();
			while (count >= 2) { //low budget log2
				count /= 2;
				amp++;
			}
			LivingEntity ent = (LivingEntity) e.getEntity();
			Potion.addEffect(ent, PotionEffectType.WITHER, 4, amp); //level is equal to power of 2 cuz i can
		}
	}
}
