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
import org.bukkit.block.BlockFace;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import RelicCooldown.RC;

import java.util.ArrayList;
import static java.lang.Math.sqrt;

public class AncientIcepickRelicBehaviour implements UseRelicBehaviour, EntityHitRelicBehaviour {
	public static boolean ModifyTerrain = false;
	public static float reach = 2F; //how far to freeze water

	@Override
	public void onUse(PlayerInteractEvent e) {
		if (e.getClickedBlock() != null) {
			Player player = e.getPlayer();
			if (!RC.doEverything(player, e.getItem(), 10)) {
				return;
			}

			Block block = e.getClickedBlock();
			if (ModifyTerrain && block.getType() != Material.STATIONARY_WATER) {
				block.setType(Material.ICE);
			}
			ArrayList<Block> toFreeze = new ArrayList<Block>();
			for (int x = -(int) reach; x <= reach + 1; x++) {
				for (int y = -(int) reach; y <= reach + 1; y++) {
					for (int z = -(int) reach; z <= reach + 1; z++) {
						if (sqrt(x * x + y * y + z * z) + 1 > -reach &&
							sqrt(x * x + y * y + z * z) - 1 < reach &&
								block.getRelative(x, y, z).getType() == Material.STATIONARY_WATER) {
							toFreeze.add(block.getRelative(x, y, z));
						}
					}
				}
			}
			for (Block b : toFreeze) {
				b.setType(Material.FROSTED_ICE);
			}
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
