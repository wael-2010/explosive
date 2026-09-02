package rainy.explosive;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class MyItems {

    public static final Item NITRIC_ACID = registerItem("nitric_acid", new Item(new Item.Settings()));


    private static Item registerItem(String name, Item item) {

        return Registry.register(Registries.ITEM, Identifier.of(Explosive.MOD_ID, name), item);
    }
    public static void registerMyItems(){}
}
