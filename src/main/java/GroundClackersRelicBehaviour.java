import com.derongan.minecraft.mineinabyss.API.Relic.Behaviour.EntityHitRelicBehaviour;
import com.derongan.minecraft.mineinabyss.API.Relic.Behaviour.UseRelicBehaviour;
import com.derongan.minecraft.mineinabyss.API.Relic.Relics.RelicType;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.List;

public class GroundClackersRelicBehaviour implements UseRelicBehaviour, EntityHitRelicBehaviour {
    @Override
    public void onUse(PlayerInteractEvent playerInteractEvent) {
        Player player = playerInteractEvent.getPlayer();

        playerInteractEvent.setCancelled(true);

        Block block = playerInteractEvent.getClickedBlock();

        if(isUseable(player))
            doClack(player, block);
    }

    @Override
    public void onHit(EntityDamageByEntityEvent entityDamageByEntityEvent) {
        Entity target = entityDamageByEntityEvent.getEntity();

        if(entityDamageByEntityEvent.getDamager() instanceof Player){
            Player player = (Player) entityDamageByEntityEvent.getDamager();

            if(target.getLocation().distance(player.getLocation()) < 5 && target.isOnGround()){
                Block b = target.getLocation().getBlock().getRelative(BlockFace.DOWN);

                if(isUseable(player))
                    doClack(player, b);
            }
        }
    }

    private boolean isUseable(Player player) {
        return RelicType.getRegisteredRelicType(player.getInventory().getItemInOffHand()) == TestRelicType.GROUND_CLACKERS && RelicType.getRegisteredRelicType(player.getInventory().getItemInMainHand()) == TestRelicType.GROUND_CLACKERS;
    }

    private void doClack(Player player, Block block) {
        if (block != null && block.getLocation().distance(player.getLocation()) < 5) {

            Collection<Entity> nearby = player.getWorld().getNearbyEntities(block.getLocation(), 4.6, 2, 4.6);

            nearby.stream().filter(Entity::isOnGround).filter(a->a != player).forEach(a -> {
                double power;
                if (block.getLocation().equals(a.getLocation()))
                    power = .6;
                else {
                    power = Math.min(2 / a.getLocation().distance(block.getLocation()), .6);
                }

                if (a instanceof LivingEntity)
                    ((LivingEntity) a).damage(power / 2);

                a.setVelocity(new Vector(0, power, 0));

                player.getWorld().spawnParticle(Particle.BLOCK_CRACK, a.getLocation(), 10, .3, .1, .3, new MaterialData(Material.STONE));
            });
            player.getWorld().spawnParticle(Particle.BLOCK_CRACK, block.getLocation().add(.5,1,.5), 200, 2.3, .1, 2.3, new MaterialData(Material.DIRT));
        }
    }
}
