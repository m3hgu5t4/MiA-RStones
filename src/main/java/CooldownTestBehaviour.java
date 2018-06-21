import com.derongan.minecraft.mineinabyss.Relic.Behaviour.UseRelicBehaviour;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import RelicCooldown.RC;

public class CooldownTestBehaviour implements UseRelicBehaviour {
	@Override
	public void onUse(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		ItemStack item = player.getInventory().getItemInMainHand();

		if (RC.cooldownFinished(item)) {
			RC.startCooldown(item, 20);
			player.chat("20 second cooldown started");
		} else {
			player.chat(Long.toString(RC.getRemainingCooldown(item) / 1000) + "seconds remaining");
		}
	}
}
