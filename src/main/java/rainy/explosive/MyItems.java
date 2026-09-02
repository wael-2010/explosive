package rainy.explosive;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class MyItems {

    public static final Item NITRIC_ACID = registerItem("nitric_acid", new Item(new Item.Settings()));

    public static final Item SULFRUIC_ACID = registerItem("sulfuric _acid", new Item(new Item.Settings()));

    public static final Item SPONGE_EXPLOSIVE = registerItem("sponge_explosive", new Item(new Item.Settings()));

    public static final Item DIRT_EXPLOSIVE = registerItem("stone_explosive", new Item(new Item.Settings()));



    private static Item registerItem(String name, Item item) {

        return Registry.register(Registries.ITEM, Identifier.of(Explosive.MOD_ID, name), item);
    }
    public static void registerMyItems(){
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(NITRIC_ACID);
            entries.add(SULFRUIC_ACID);
        });
    }


}
