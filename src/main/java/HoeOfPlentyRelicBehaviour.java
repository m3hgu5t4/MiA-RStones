import com.derongan.minecraft.mineinabyss.Relic.Behaviour.UseRelicBehaviour;
import com.derongan.minecraft.mineinabyss.Relic.Relics.RelicType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.block.Block;
import org.bukkit.Particle;
import org.bukkit.block.BlockFace;
import org.bukkit.Sound;

import java.util.Collection;
import java.util.List;
import java.util.Random;

public class HoeOfPlentyRelicBehaviour implements UseRelicBehaviour {
	public static Material[] HoeFood = {
			Material.WHEAT,
			Material.BEETROOT,
			Material.MELON,
			Material.POTATO_ITEM,
			Material.CARROT_ITEM,
			Material.APPLE,
			Material.SUGAR,
	};
	public static Material[] HoeableBlocks = {
		Material.DIRT,
		Material.GRASS,
		Material.GRASS_PATH,
	};

	public boolean canHoe(Block block) {
		boolean hoeable = false;
		for (Material mat : HoeableBlocks) {
			if (block.getType() == mat) {
				hoeable = true;
				break;
			}
		}
		if (hoeable) {
			return block.getRelative(BlockFace.UP).getType() == Material.AIR; //if there ain't shit above so it can be hoed
		} else {
			return false;
		}
	}
	@Override
	public void onUse(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		Block target = e.getClickedBlock();

		if (canHoe(target)) {
			Random rng = new Random();
			int count;
			//ItemStack toDrop;

			/* Location toDropLoc = new Location(
					player.getWorld(),
					target.getLocation().getX(),
					target.getLocation().getY() + 1, //so this be upon thine blocc
					target.getLocation().getZ()
			); */
			int dropX = target.getLocation().getBlockX();
			int dropY = target.getLocation().getBlockY() + 1;
			int dropZ = target.getLocation().getBlockZ();

			for (Material food : HoeFood) {
				count = rng.nextInt(2);
				player.getWorld().dropItem(
						new Location(target.getWorld(), dropX + 0.5D, dropY, dropZ + 0.5D),
						new ItemStack(food, count) //toDrop = new ItemStack(food, count);
				); //player.getWorld().dropItem(toDropLoc, toDrop);
			}

			for (double i = 0.2; i < 1; i += 0.3) {
				for (double j = 0.2; j < 1; j += 0.3) {
					player.getWorld().spawnParticle(
							Particle.VILLAGER_HAPPY, //bonemeal particles apparently
							new Location(player.getWorld(),
									dropX + i,
									dropY,
									dropZ + j
							),
							3 //3 is probably fine
					);
				}
			}
		} /* else {
			player.chat(target.getType().name()); //player.getInventory().getItemInMainHand().setAmount(0); //just to confirm it activates
		} */
	}
}
