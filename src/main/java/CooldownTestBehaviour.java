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
			System.out.println("lore is " + item.getItemMeta().getLore().get(0));
		} else {
			player.chat(Long.toString(RC.getRemainingCooldown(item)) + "milliseconds remaining");
		}
	}
}
