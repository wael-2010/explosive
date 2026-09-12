package rainy.Ability;

import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import rainy.explosive.Detenatorcheck;

public class LogAbility extends Item {

    private static final int CHARGE = 40; // idk i might INCREASE THIS IN FUTURE PLS REMEMBER FUTURE ME
    private static final int RADIUS = 5;

    public LogAbility(Settings settings) {
        super(settings);
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 72000; // i didnot really get how this work , BUT IM supposed to make it longer than the charge time
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
        int POWERINGUP = getMaxUseTime(stack, user) - remainingUseTicks;

        if (POWERINGUP >= CHARGE) {
            ServerWorld serverWorld = (ServerWorld) world;
            BlockPos center = user.getBlockPos();

            Detenatorcheck.queue(() -> explode(serverWorld, center));

            user.clearActiveItem();
            if (user instanceof PlayerEntity player) {
                stack.decrement(1);
            }
        }
    }

    private void explode(ServerWorld world, BlockPos center) {
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
                    if (!world.getBlockState(pos).isIn(BlockTags.LOGS)) {
                        continue;
                    }
                    world.breakBlock(pos, true);
                }
            }
        }

        world.createExplosion(null, center.getX(), center.getY(), center.getZ(), 0.0F, World.ExplosionSourceType.NONE);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }
}