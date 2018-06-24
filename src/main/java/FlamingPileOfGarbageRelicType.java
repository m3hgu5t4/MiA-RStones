import com.derongan.minecraft.mineinabyss.Relic.Behaviour.RelicBehaviour;
import com.derongan.minecraft.mineinabyss.Relic.RelicRarity;
import com.derongan.minecraft.mineinabyss.Relic.Relics.RelicType;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

public enum FlamingPileOfGarbageRelicType implements RelicType {
	SPYGLASS(Material.IRON_SPADE, 3,
			new SpyglassBehaviour(),
			"Spyglass",
			Arrays.asList("Check what something is made of"),
			RelicRarity.NOT_IMPLEMENTED
	),
	COOLDOWN_TEST(Material.WATCH, 3,
			new CooldownTestBehaviour(),
			"Cooldown Test",
			Arrays.asList("ye feck off, idk how to cooldown gud"),
			RelicRarity.NOT_IMPLEMENTED
	),
	REPUGNANT_STONES(Material.POISONOUS_POTATO, 3,
			new RepugnantStonesRelicBehaviour(),
			"Repugnant Stones",
			Arrays.asList("This makes me sick!"),
			RelicRarity.THIRD_GRADE
	),
	HOE_OF_PLENTY(Material.GOLD_HOE, 3,
			new HoeOfPlentyRelicBehaviour(),
			"Hoe of Plenty",
			Arrays.asList("A hoe that makes you food"),
			RelicRarity.SECOND_GRADE
	),
	SACRED_CRYSTALLINE_WATER(Material.BEETROOT_SOUP, 3,
			new SacredCrystallineWaterRelicBehaviour(),
			"Sacred Crystalline Water",
			Arrays.asList("Drink to gain an effect"),
			RelicRarity.SECOND_GRADE
	),
	IRON_SPIRIT(Material.IRON_NUGGET, 3,
			new IronSpiritRelicBehaviour(),
			"Iron Charm",
			Arrays.asList("Defense at the cost of speed"),
			RelicRarity.THIRD_GRADE
	),
	ANCIENT_ICEPICK(Material.DIAMOND_PICKAXE, 3,
			new AncientIcepickRelicBehaviour(),
			"Ancient Icepick",
			Arrays.asList("An icy touch"),
			RelicRarity.SECOND_GRADE
	),
	DRAGON_TEETH(Material.FLINT, 3,
			new DragonTeethRelicBehaviour(),
			"Dragon Teeth",
			Arrays.asList("A dangerous poison, less effective in higher doses"),
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
