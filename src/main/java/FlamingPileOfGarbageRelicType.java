import com.derongan.minecraft.mineinabyss.Relic.Behaviour.RelicBehaviour;
import com.derongan.minecraft.mineinabyss.Relic.RelicRarity;
import com.derongan.minecraft.mineinabyss.Relic.Relics.RelicType;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

public enum FlamingPileOfGarbageRelicType implements RelicType {
	SPYGLASS(Material.STICK, 0,
			new SpyglassBehaviour(),
			"Spyglass",
			Arrays.asList("Check what something is made of"),
			RelicRarity.NOT_IMPLEMENTED
	),
	REPUGNANT_STONES(Material.POISONOUS_POTATO, 0,
			new RepugnantStonesRelicBehaviour(),
			"Repugnant Stones",
			Arrays.asList("This makes me sick!"),
			RelicRarity.THIRD_GRADE
	),
	HOE_OF_PLENTY(Material.IRON_HOE, 0,
			new HoeOfPlentyRelicBehaviour(),
			"Hoe of Plenty",
			Arrays.asList("A hoe that makes you food"),
			RelicRarity.SECOND_GRADE
	);

	private final Material material;
	private final short durability;
	private final String name;
	private final List<String> lore;
	private final RelicBehaviour behaviour;
	private final RelicRarity rarity;

	FlamingPileOfGarbageRelicType(Material material, long durability, RelicBehaviour behaviour, String name, List<String> lore, RelicRarity rarity) {
		this.durability = (short) durability;
		this.material = material;
		this.behaviour = behaviour;
		this.name = name;
		this.lore = lore;
		this.rarity = rarity;

		behaviour.setRelicType(this);

		RelicType.registerRelicType(this);
	}


	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<String> getLore() {
		return lore;
	}

	@Override
	public Material getMaterial() {
		return material;
	}

	@Override
	public short getDurability() {
		return durability;
	}

	@Override
	public RelicBehaviour getBehaviour() {
		return behaviour;
	}

	@Override
	public RelicRarity getRarity() {
		return rarity;
	}
}
