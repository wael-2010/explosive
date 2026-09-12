package rainy.Ability;

import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import rainy.explosive.Detenatorcheck;

public class SpongeAbility extends Item {

    private final int CHARGE = 40;
    private static int RADIUS = 15;

    public SpongeAbility(Settings settings) {
        super(settings);
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 9999;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        player.setCurrentHand(hand);
        return TypedActionResult.consume(player.getStackInHand(hand));
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (world.isClient()) {
            return;
        }
        int LOOOADINGTIMEE = getMaxUseTime(stack, user) - remainingUseTicks;

        if (LOOOADINGTIMEE >= CHARGE) {
            ServerWorld serverWorld = (ServerWorld) world;
            BlockPos center = user.getBlockPos();

            Detenatorcheck.queue(() -> drain(serverWorld, center));

            user.clearActiveItem();
            if (user instanceof PlayerEntity player) {
                stack.decrement(1);
            }
        }
    }

    private void drain(ServerWorld world, BlockPos center) {
        for (int x = -RADIUS; x <= RADIUS; x++) {
            for (int y = -RADIUS; y <= RADIUS; y++) {
                for (int z = -RADIUS; z <= RADIUS; z++) {
                    double square = x * x + y * y + z * z;

                    if (square > RADIUS * RADIUS) {
                        continue;
                    }
                    BlockPos pos = center.add(x, y, z);

                    if (world.getBlockState(pos).isAir()) {
                        continue;
                    }
                    if (world.getFluidState(pos).isOf(Fluids.WATER)) {
                        world.setBlockState(pos, Blocks.AIR.getDefaultState());
                    }
                }
            }
        }
        world.createExplosion(null, center.getX(), center.getY(), center.getZ(), 0F, World.ExplosionSourceType.NONE);
    }
}