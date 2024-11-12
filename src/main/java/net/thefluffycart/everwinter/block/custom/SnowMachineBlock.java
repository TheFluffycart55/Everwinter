package net.thefluffycart.everwinter.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.thefluffycart.everwinter.block.ModBlocks;
import org.jetbrains.annotations.Nullable;

public class SnowMachineBlock extends PillarBlock {
    public static final MapCodec<PillarBlock> CODEC = PillarBlock.createCodec(PillarBlock::new);
    public static final EnumProperty<Direction.Axis> AXIS = Properties.AXIS;
    public static final BooleanProperty POWERED = Properties.POWERED;
    int blowableBound = 3;


    public SnowMachineBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)this.getDefaultState().with(AXIS, Direction.Axis.Y));
        this.setDefaultState((BlockState)this.getDefaultState().with(POWERED, Boolean.FALSE));

    }
    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(POWERED, Boolean.valueOf(ctx.getWorld().isReceivingRedstonePower(ctx.getBlockPos())));
    }

    @Override
    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (!world.isClient) {
            boolean bl = (Boolean)state.get(POWERED);
                if (bl) {
                    world.scheduleBlockTick(pos, this, 0);
                } else {
                    world.setBlockState(pos, state.cycle(POWERED), Block.NOTIFY_LISTENERS);
                }
            }
    }

    public MapCodec<? extends PillarBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return PillarBlock.changeRotation(state, rotation);
    }

    public void getBlockBelowBounds(World world, BlockPos pos, BlockState state)
    {
        BlockPos getBelow = pos.down();
        Block blockUnder = world.getBlockState(getBelow).getBlock();

        if (blockUnder == Blocks.ICE || blockUnder == ModBlocks.DRY_ICE) {
            blowableBound = 5;
        } else if (blockUnder == Blocks.SNOW_BLOCK || blockUnder == Blocks.PACKED_ICE) {
            blowableBound = 10;
        } else if (blockUnder == Blocks.BLUE_ICE || blockUnder == Blocks.POWDER_SNOW || blockUnder == Blocks.POWDER_SNOW_CAULDRON) {
            blowableBound = 15;
        }
        else
        {
            blowableBound = 3;
        }
    }

    public static BlockState changeRotation(BlockState state, BlockRotation rotation) {
        switch (rotation) {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90: {
                switch (state.get(AXIS)) {
                    case X: {
                        return (BlockState)state.with(AXIS, Direction.Axis.Z);
                    }
                    case Z: {
                        return (BlockState)state.with(AXIS, Direction.Axis.X);
                    }
                }
                return state;
            }
        }
        return state;
    }

    private static void setPowered(World world, BlockPos pos, BlockState state, boolean powered) {
        world.setBlockState(pos, state.with(POWERED, Boolean.valueOf(powered)), Block.NOTIFY_ALL);
        updateNeighborAlways(world, pos, state);
    }

    private static void updateNeighborAlways(World world, BlockPos pos, BlockState state) {
        world.updateNeighborsAlways(pos.down(), state.getBlock());
    }

    @Override
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            super.onStateReplaced(state, world, pos, newState, moved);
            if ((Boolean)state.get(POWERED)) {
                world.updateNeighborsAlways(pos.down(), this);
            }
        }
    }
    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        int i = pos.getX();
        int j = pos.getY() + (blowableBound/2);
        int k = pos.getZ();
        double d = (double)i + random.nextDouble();
        double e = (double)j + 0.7;
        double f = (double)k + random.nextDouble();
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int l = 0; l < 12; l++) {
            mutable.set(i + MathHelper.nextInt(random, -blowableBound, blowableBound), j - random.nextInt(blowableBound), k + MathHelper.nextInt(random, -blowableBound, blowableBound));
            BlockState blockState = world.getBlockState(mutable);
            getBlockBelowBounds(world, pos, state);
                    if (!blockState.isFullCube(world, mutable)) {
                        world.addParticle(ParticleTypes.WHITE_ASH, d, e, f, 0.0, 0.0, 0.0);
                        world.addParticle(
                                ParticleTypes.SNOWFLAKE,
                                (double)mutable.getX() + random.nextDouble(),
                                (double)mutable.getY() + random.nextDouble(),
                                (double)mutable.getZ() + random.nextDouble(),
                                0.0,
                                -0.01f,
                                0.0

                        );
                    }
                    else
                    {
                        if (!blockState.isFullCube(world, mutable)) {
                            world.addParticle(
                                    ParticleTypes.SMOKE,
                                    (double)mutable.getX() + random.nextDouble(),
                                    (double)mutable.getY() + random.nextDouble(),
                                    (double)mutable.getZ() + random.nextDouble(),
                                    0.0,
                                    -0.01f,
                                    0.0
                            );
                        }
                }
            }
        }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AXIS);
        builder.add(POWERED);
    }
}
