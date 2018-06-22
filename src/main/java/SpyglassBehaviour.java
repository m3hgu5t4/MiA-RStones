import com.derongan.minecraft.mineinabyss.Relic.Behaviour.UseRelicBehaviour;
import com.derongan.minecraft.mineinabyss.Relic.Relics.RelicType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.block.Block;

public class SpyglassBehaviour implements UseRelicBehaviour {
	@Override
	public void onUse(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		Block target = e.getClickedBlock();

		player.chat(target.getType().name());
	}
}