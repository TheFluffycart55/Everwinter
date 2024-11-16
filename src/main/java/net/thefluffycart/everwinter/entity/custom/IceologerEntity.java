package net.thefluffycart.everwinter.entity.custom;

import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class IceologerEntity extends SpellcastingIllagerEntity {
    @Nullable
    private SheepEntity wololoTarget;
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public IceologerEntity(EntityType<? extends IceologerEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 10;
    }


    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new IceologerEntity.LookAtTargetOrWololoTarget());
        this.goalSelector.add(2, new FleeEntityGoal(this, PlayerEntity.class, 6.0F, 0.6, 1.0));
        this.goalSelector.add(5, new IceologerEntity.ConjureFangsGoal());
        this.goalSelector.add(5, new IceologerEntity.WololoGoal());
        this.goalSelector.add(8, new WanderAroundGoal(this, 0.6));
        this.goalSelector.add(9, new LookAtEntityGoal(this, PlayerEntity.class, 3.0F, 1.0F));
        this.goalSelector.add(10, new LookAtEntityGoal(this, MobEntity.class, 8.0F));
        this.targetSelector.add(1, new RevengeGoal(this, RaiderEntity.class).setGroupRevenge());
        this.targetSelector.add(2, new ActiveTargetGoal(this, PlayerEntity.class, true).setMaxTimeWithoutVisibility(300));
        this.targetSelector.add(3, new ActiveTargetGoal(this, MerchantEntity.class, false).setMaxTimeWithoutVisibility(300));
        this.targetSelector.add(3, new ActiveTargetGoal(this, IronGolemEntity.class, false));
    }

    public static DefaultAttributeContainer.Builder createIceologerAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.5)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 12.0)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 48.0f);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
    }

    @Override
    public SoundEvent getCelebratingSound() {
        return SoundEvents.ENTITY_EVOKER_CELEBRATE;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
    }

    @Override
    protected void mobTick() {
        super.mobTick();
    }

    @Override
    public boolean isTeammate(Entity other) {
        if (other == null) {
            return false;
        } else if (other == this) {
            return true;
        } else if (super.isTeammate(other)) {
            return true;
        } else {
            return other instanceof VexEntity vexEntity ? this.isTeammate(vexEntity.getOwner()) : false;
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_EVOKER_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_EVOKER_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_EVOKER_HURT;
    }

    void setWololoTarget(@Nullable SheepEntity wololoTarget) {
        this.wololoTarget = wololoTarget;
    }

    @Nullable
    SheepEntity getWololoTarget() {
        return this.wololoTarget;
    }

    @Override
    protected SoundEvent getCastSpellSound() {
        return SoundEvents.ENTITY_EVOKER_CAST_SPELL;
    }

    @Override
    public void addBonusForWave(ServerWorld world, int wave, boolean unused) {
    }

    class ConjureFangsGoal extends SpellcastingIllagerEntity.CastSpellGoal {
        @Override
        protected int getSpellTicks() {
            return 40;
        }

        @Override
        protected int startTimeDelay() {
            return 100;
        }

        @Override
        protected void castSpell() {
            LivingEntity livingEntity = IceologerEntity.this.getTarget();
            double d = Math.min(livingEntity.getY(), IceologerEntity.this.getY());
            double e = Math.max(livingEntity.getY(), IceologerEntity.this.getY()) + 1.0;
            float f = (float)MathHelper.atan2(livingEntity.getZ() - IceologerEntity.this.getZ(), livingEntity.getX() - IceologerEntity.this.getX());
            if (IceologerEntity.this.squaredDistanceTo(livingEntity) < 9.0) {
                for (int i = 0; i < 5; i++) {
                    float g = f + (float)i * (float) Math.PI * 0.4F;
                    this.conjureFangs(IceologerEntity.this.getX() + (double)MathHelper.cos(g) * 1.5, IceologerEntity.this.getZ() + (double)MathHelper.sin(g) * 1.5, d, e, g, 0);
                }

                for (int i = 0; i < 8; i++) {
                    float g = f + (float)i * (float) Math.PI * 2.0F / 8.0F + (float) (Math.PI * 2.0 / 5.0);
                    this.conjureFangs(IceologerEntity.this.getX() + (double)MathHelper.cos(g) * 2.5, IceologerEntity.this.getZ() + (double)MathHelper.sin(g) * 2.5, d, e, g, 3);
                }
            } else {
                for (int i = 0; i < 16; i++) {
                    double h = 1.25 * (double)(i + 1);
                    int j = 1 * i;
                    this.conjureFangs(IceologerEntity.this.getX() + (double)MathHelper.cos(f) * h, IceologerEntity.this.getZ() + (double)MathHelper.sin(f) * h, d, e, f, j);
                }
            }
        }

        private void conjureFangs(double x, double z, double maxY, double y, float yaw, int warmup) {
            BlockPos blockPos = BlockPos.ofFloored(x, y, z);
            boolean bl = false;
            double d = 0.0;

            do {
                BlockPos blockPos2 = blockPos.down();
                BlockState blockState = IceologerEntity.this.getWorld().getBlockState(blockPos2);
                if (blockState.isSideSolidFullSquare(IceologerEntity.this.getWorld(), blockPos2, Direction.UP)) {
                    if (!IceologerEntity.this.getWorld().isAir(blockPos)) {
                        BlockState blockState2 = IceologerEntity.this.getWorld().getBlockState(blockPos);
                        VoxelShape voxelShape = blockState2.getCollisionShape(IceologerEntity.this.getWorld(), blockPos);
                        if (!voxelShape.isEmpty()) {
                            d = voxelShape.getMax(Direction.Axis.Y);
                        }
                    }

                    bl = true;
                    break;
                }

                blockPos = blockPos.down();
            } while (blockPos.getY() >= MathHelper.floor(maxY) - 1);

            if (bl) {
                IceologerEntity.this.getWorld()
                        .spawnEntity(new EvokerFangsEntity(IceologerEntity.this.getWorld(), x, (double)blockPos.getY() + d, z, yaw, warmup, IceologerEntity.this));
                IceologerEntity.this.getWorld().emitGameEvent(GameEvent.ENTITY_PLACE, new Vec3d(x, (double)blockPos.getY() + d, z), GameEvent.Emitter.of(IceologerEntity.this));
            }
        }

        @Override
        protected SoundEvent getSoundPrepare() {
            return SoundEvents.ENTITY_EVOKER_PREPARE_ATTACK;
        }

        @Override
        protected SpellcastingIllagerEntity.Spell getSpell() {
            return SpellcastingIllagerEntity.Spell.FANGS;
        }
    }

    class LookAtTargetOrWololoTarget extends SpellcastingIllagerEntity.LookAtTargetGoal {
        @Override
        public void tick() {
            if (IceologerEntity.this.getTarget() != null) {
                IceologerEntity.this.getLookControl()
                        .lookAt(IceologerEntity.this.getTarget(), (float)IceologerEntity.this.getMaxHeadRotation(), (float)IceologerEntity.this.getMaxLookPitchChange());
            } else if (IceologerEntity.this.getWololoTarget() != null) {
                IceologerEntity.this.getLookControl()
                        .lookAt(IceologerEntity.this.getWololoTarget(), (float)IceologerEntity.this.getMaxHeadRotation(), (float)IceologerEntity.this.getMaxLookPitchChange());
            }
        }
    }

    public class WololoGoal extends SpellcastingIllagerEntity.CastSpellGoal {
        private final TargetPredicate convertibleSheepPredicate = TargetPredicate.createNonAttackable()
                .setBaseMaxDistance(16.0)
                .setPredicate(livingEntity -> ((SheepEntity)livingEntity).getColor() == DyeColor.RED);

        @Override
        public boolean canStart() {
            if (IceologerEntity.this.getTarget() != null) {
                return false;
            } else if (IceologerEntity.this.isSpellcasting()) {
                return false;
            } else if (IceologerEntity.this.age < this.startTime) {
                return false;
            } else if (!IceologerEntity.this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
                return false;
            } else {
                List<SheepEntity> list = IceologerEntity.this.getWorld()
                        .getTargets(SheepEntity.class, this.convertibleSheepPredicate, IceologerEntity.this, IceologerEntity.this.getBoundingBox().expand(16.0, 4.0, 16.0));
                if (list.isEmpty()) {
                    return false;
                } else {
                    IceologerEntity.this.setWololoTarget((SheepEntity)list.get(IceologerEntity.this.random.nextInt(list.size())));
                    return true;
                }
            }
        }

        @Override
        public boolean shouldContinue() {
            return IceologerEntity.this.getWololoTarget() != null && this.spellCooldown > 0;
        }

        @Override
        public void stop() {
            super.stop();
            IceologerEntity.this.setWololoTarget(null);
        }

        @Override
        protected void castSpell() {
            SheepEntity sheepEntity = IceologerEntity.this.getWololoTarget();
            if (sheepEntity != null && sheepEntity.isAlive()) {
                sheepEntity.setColor(DyeColor.BLUE);
            }
        }

        @Override
        protected int getInitialCooldown() {
            return 30;
        }

        @Override
        protected int getSpellTicks() {
            return 60;
        }

        @Override
        protected int startTimeDelay() {
            return 140;
        }

        @Override
        protected SoundEvent getSoundPrepare() {
            return SoundEvents.ENTITY_EVOKER_PREPARE_WOLOLO;
        }

        @Override
        protected SpellcastingIllagerEntity.Spell getSpell() {
            return SpellcastingIllagerEntity.Spell.WOLOLO;
        }
    }



private void setupAnimationStates() {
        if(this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 100;
            this.idleAnimationState.start(this.age);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    /*private class SummonIceGoal extends SpellcastingIllagerEntity.CastSpellGoal {
        private final TargetPredicate closeVexPredicate = TargetPredicate.createNonAttackable()
                .setBaseMaxDistance(16.0)
                .ignoreVisibility()
                .ignoreDistanceScalingFactor();

        @Override
        public boolean canStart() {
            if (!super.canStart()) {
                return false;
            } else {
                int i = IceologerEntity.this.getWorld()
                        .getTargets(VexEntity.class, this.closeVexPredicate, IceologerEntity.this, IceologerEntity.this.getBoundingBox().expand(16.0))
                        .size();
                return IceologerEntity.this.random.nextInt(8) + 1 > i;
            }
        }

        @Override
        protected int getSpellTicks() {
            return 100;
        }

        @Override
        protected int startTimeDelay() {
            return 340;
        }

        @Override
        protected SoundEvent getSoundPrepare() {
            return SoundEvents.ENTITY_EVOKER_PREPARE_WOLOLO;
        }

        @Override
        protected SpellcastingIllagerEntity.Spell getSpell() {
            return SpellcastingIllagerEntity.Spell.WOLOLO;
        }

        @Override
        protected void castSpell() {
            ServerWorld serverWorld = (ServerWorld) IceologerEntity.this.getWorld();
            Team team = IceologerEntity.this.getScoreboardTeam();

            for (int i = 0; i < 3; i++) {
                BlockPos blockPos = IceologerEntity.this.getBlockPos().add(-2 + IceologerEntity.this.random.nextInt(5), 1, -2 + IceologerEntity.this.random.nextInt(5));
                IceologerEntity vexEntity = ModEntities.ICEOLOGER.create(IceologerEntity.this.getWorld());
                if (vexEntity != null) {
                    vexEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                    vexEntity.initialize(serverWorld, IceologerEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, null);
                    vexEntity.setOwner(IceologerEntity.this);
                    vexEntity.setBounds(blockPos);
                    vexEntity.setLifeTicks(20 * (30 + IceologerEntity.this.random.nextInt(90)));
                    if (team != null) {
                        serverWorld.getScoreboard().addScoreHolderToTeam(vexEntity.getNameForScoreboard(), team);
                    }

                    serverWorld.spawnEntityAndPassengers(vexEntity);
                    serverWorld.emitGameEvent(GameEvent.ENTITY_PLACE, blockPos, GameEvent.Emitter.of(IceologerEntity.this));
                }
            }
        }
    }*/
}
