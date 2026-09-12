package rainy.explosive;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import rainy.Ability.DirtAbility;
import rainy.Ability.IronAbility;
import rainy.Ability.SpongeAbility;

public class MyItems {

    public static final Item NITRIC_ACID = registerItem("nitric_acid", new DetonateItem(new Item.Settings()));

    public static final Item NITRIC_POWDER = registerItem("nitric_powder", new Item(new Item.Settings()));

    public static final Item SULFURIC_ACID = registerItem("sulfuric_acid", new Item(new Item.Settings()));

    public static final Item SULFURIC_POWDER = registerItem("sulfuric_powder", new Item(new Item.Settings()));

    public static final Item SPONGE_EXPLOSIVE = registerItem("sponge_explosive", new SpongeAbility(new Item.Settings()));

    public static final Item DIRT_EXPLOSIVE = registerItem("dirt_explosive", new DirtAbility(new Item.Settings()));

    public static final Item IRON_EXPLOSIVE = registerItem("iron_explosive", new IronAbility(new Item.Settings()));



    private static Item registerItem(String name, Item item) {

        return Registry.register(Registries.ITEM, Identifier.of(Explosive.MOD_ID, name), item);
    }
    public static void registerMyItems(){
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(NITRIC_ACID);
            entries.add(SULFURIC_ACID);
            entries.add(NITRIC_POWDER);
            entries.add(DIRT_EXPLOSIVE);
            entries.add(SPONGE_EXPLOSIVE);
            entries.add(IRON_EXPLOSIVE);
            entries.add(SULFURIC_POWDER);
        });
    }


}
