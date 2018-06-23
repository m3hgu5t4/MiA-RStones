import com.derongan.minecraft.mineinabyss.Relic.Behaviour.UseRelicBehaviour;
import com.derongan.minecraft.mineinabyss.Relic.Behaviour.EntityHitRelicBehaviour;
import com.derongan.minecraft.mineinabyss.Relic.Relics.RelicType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import RelicCooldown.RC;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class AncientIcepickRelicBehaviour implements UseRelicBehaviour, EntityHitRelicBehaviour {
	@Override
	public void onUse(PlayerInteractEvent e) {
		if (true) { // change to false if no breaky blocky
			Player player = e.getPlayer();
			if (!RC.doEverything(player, e.getItem(), 10)) {
				return;
			}

			e.getClickedBlock().setType(Material.ICE);
		}
	}
	@Override
	public void onHit(EntityDamageByEntityEvent e) {
		Player player = (Player) e.getDamager();
		if (!RC.cooldownFinished(player.getInventory().getItemInMainHand())) {
			RC.notifyCooldown(player, player.getInventory().getItemInMainHand());
			return;
		}
		if (e.getEntityType() == EntityType.PLAYER) { //doesn't freeze players as often
			RC.startCooldown(player.getInventory().getItemInMainHand(), 30);
		} else if (e.getEntity() instanceof LivingEntity) {
			RC.startCooldown(player.getInventory().getItemInMainHand(), 15);
		} else {
			return;
		}

		int dur = 3;
		LivingEntity ent = (LivingEntity) e.getEntity();
		ent.addPotionEffect(new PotionEffect(
				PotionEffectType.SLOW,
				dur,
				100 //freeze in place
		));
		ent.addPotionEffect(new PotionEffect(
				PotionEffectType.SLOW_DIGGING,
				dur,
				100 //no attac
		));
		ent.addPotionEffect(new PotionEffect(
				PotionEffectType.JUMP,
				dur,
				128 //no jump
		));
	}
}
