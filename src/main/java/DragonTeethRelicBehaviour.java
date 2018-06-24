import com.derongan.minecraft.mineinabyss.Relic.Behaviour.EntityHitRelicBehaviour;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class DragonTeethRelicBehaviour implements EntityHitRelicBehaviour {
	@Override
	public void onHit(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof LivingEntity) {
			int amp = 0;
			float count = ((Player) e.getDamager()).getInventory().getItemInMainHand().getAmount();
			while (count >= 2) { //low budget log2
				count /= 2;
				amp++;
			}
			LivingEntity ent = (LivingEntity) e.getEntity();
			ent.addPotionEffect(new PotionEffect(
					PotionEffectType.WITHER,
					4 * 20,
					amp //level is equal to power of 2 cuz i can
			));
		}
	}
}
