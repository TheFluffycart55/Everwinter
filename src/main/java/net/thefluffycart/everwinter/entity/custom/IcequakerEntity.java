package net.thefluffycart.everwinter.entity.custom;

import net.minecraft.block.PowderSnowBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.StrayEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.CamelEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PowderSnowBucketItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class IcequakerEntity extends AbstractHorseEntity {
    protected IcequakerEntity(EntityType<? extends AbstractHorseEntity> entityType, World world) {
        super(entityType, world);
        MobNavigation mobNavigation = (MobNavigation)this.getNavigation();
        mobNavigation.setCanSwim(true);
        mobNavigation.setCanWalkOverFences(true);
    }
    public static DefaultAttributeContainer.Builder createIcequakerAttributes() {
        return createBaseHorseAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 32.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.09F)
                .add(EntityAttributes.GENERIC_JUMP_STRENGTH, 0.42F)
                .add(EntityAttributes.GENERIC_STEP_HEIGHT, 1.5);
    }



    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (player.shouldCancelInteraction() && !this.isBaby()) {
            this.openInventory(player);
            return ActionResult.success(this.getWorld().isClient);
        } else {
            ActionResult actionResult = itemStack.useOnEntity(player, this, hand);
            if (actionResult.isAccepted()) {
                return actionResult;
            } else if (this.isBreedingItem(itemStack)) {
                return this.interactHorse(player, itemStack);
            } else {
                if (this.getPassengerList().size() < 2 && !this.isBaby()) {
                    this.putPlayerOnBack(player);
                }

                return ActionResult.success(this.getWorld().isClient);
            }
        }
    }

    
}
