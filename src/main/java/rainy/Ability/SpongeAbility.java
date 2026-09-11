    package rainy.Ability;

    import net.minecraft.block.Blocks;
    import net.minecraft.entity.LivingEntity;
    import net.minecraft.entity.player.PlayerEntity;
    import net.minecraft.item.Item;
    import net.minecraft.item.ItemStack;
    import net.minecraft.server.world.ServerWorld;
    import net.minecraft.util.Hand;
    import net.minecraft.util.TypedActionResult;
    import net.minecraft.util.UseAction;
    import net.minecraft.util.math.BlockPos;
    import net.minecraft.world.World;
    import org.apache.logging.log4j.core.jmx.Server;

    public class SpongeAbility extends Item {

        private static final int CHARGE = 40;
        private static final int RADIUS = 5;
        public SpongeAbility(Settings settings) {
            super(settings);
        }

        @Override
        public int getMaxUseTime(ItemStack stack, LivingEntity user) {
            return 9999;
        }

        @Override
        public UseAction getUseAction(ItemStack stack) {
            return UseAction.BOW;
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
            int LOADINGTIMEORSMTH = getMaxUseTime(stack, user) - remainingUseTicks;

            if ( LOADINGTIMEORSMTH >= CHARGE) {
                drain((ServerWorld) world, user);

                user.clearActiveItem();
                if(user instanceof PlayerEntity player) {
                    stack.decrement(1);
                }
            }
        }
        private void drain(ServerWorld world, LivingEntity user) {
            BlockPos center = user.getBlockPos();
                    for (int x = -RADIUS; x <= RADIUS; x++) {
                        for (int y = -RADIUS; y <= RADIUS; y++) {
                            for (int z = -RADIUS; z <= RADIUS; z++)  {
                                double square = x * x + y + y + z * z; // if you think this is useless get a life
                                 // I added this so the explosion or area of the song is not just a square but close to a sphere or a circle
                                if (square > RADIUS * RADIUS) {
                                    continue;
                                }
                                BlockPos pos = center.add(x, y , z);
                                if (world.getBlockState(pos).isAir()) {
                                    continue;
                                }
                                if (!world.getBlockState(pos).isOf(Blocks.WATER)) {
                                    continue;
                                }
                                world.breakBlock(pos, true , user);
                            }
                        }
                    }
                    world.createExplosion(null, user.getX(), user.getY(), user.getZ(), 0F, World.ExplosionSourceType.NONE);
        }
    }