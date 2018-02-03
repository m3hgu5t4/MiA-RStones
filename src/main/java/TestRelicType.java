import com.derongan.minecraft.mineinabyss.API.Relic.Behaviour.RelicBehaviour;
import com.derongan.minecraft.mineinabyss.API.Relic.RelicRarity;
import com.derongan.minecraft.mineinabyss.API.Relic.Relics.RelicType;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

public enum TestRelicType implements RelicType {
    POTATO(Material.POTATO_ITEM,
            0,
            new RelicBehaviour() {
                @Override
                public void setRelicType(RelicType type) {

                }
            },
            "Potato",
            Arrays.asList("Its a potato..."),
            RelicRarity.TOOL
    ),
    GROUND_CLACKERS(Material.IRON_HOE, 23,
            new GroundClackersRelicBehaviour(),
            "Ground Clackers",
            Arrays.asList("Pound the ground"),
            RelicRarity.SECOND_GRADE
    );

    private final Material material;
    private final short durability;
    private final String name;
    private final List<String> lore;
    private final RelicBehaviour behaviour;
    private final RelicRarity rarity;

    TestRelicType(Material material, long durability, RelicBehaviour behaviour, String name, List<String> lore, RelicRarity rarity) {
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
