import com.derongan.minecraft.mineinabyss.Relic.Behaviour.ConsumeRelicBehaviour;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import m3hTools.Potion;

public class EonDepthsRelicBehaviour implements ConsumeRelicBehaviour {
	@Override
	public void onConsume(PlayerItemConsumeEvent e) {
		Player player = e.getPlayer();
		Potion.addEffect(player, PotionEffectType.HEALTH_BOOST, 60, 5);
		player.setHealth(player.getHealth() + 20);
		e.setCancelled(true);
		if (player.getInventory().getItemInMainHand().equals(e.getItem())) {
			player.getInventory().getItemInMainHand().setAmount(e.getItem().getAmount() - 1);
		} else {
			player.getInventory().getItemInOffHand().setAmount(e.getItem().getAmount() - 1);
		} //delet
	}
}
