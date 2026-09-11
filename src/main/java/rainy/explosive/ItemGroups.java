package rainy.explosive;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ItemGroups {
    public static final ItemGroup EXPLOSIVE_ITEMS_GROUP  = Registry.register(Registries.ITEM_GROUP, Identifier.of(
            Explosive.MOD_ID, "explosive_items"
    ), FabricItemGroup.builder().icon(() -> new ItemStack(MyItems.SULFURIC_ACID))
                    .displayName(Text.translatable("itemgroup.explosive.explosive_items"))
                    .entries(((displayContext, entries) ->{
                        entries.add(MyItems.SULFURIC_POWDER);
                        entries.add(MyItems.SULFURIC_ACID);
                        entries.add(MyItems.IRON_EXPLOSIVE);
                        entries.add(MyItems.DIRT_EXPLOSIVE);
                        entries.add(MyItems.SPONGE_EXPLOSIVE);
                        entries.add(MyItems.NITRIC_POWDER);
                        entries.add(MyItems.NITRIC_ACID);
                    }))

            .build());

    public static void registerItemGroups() {}
}
