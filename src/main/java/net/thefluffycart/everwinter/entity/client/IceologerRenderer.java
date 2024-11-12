package net.thefluffycart.everwinter.entity.client;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.EvokerEntityRenderer;
import net.minecraft.client.render.entity.IllagerEntityRenderer;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.thefluffycart.everwinter.Everwinter;
import net.thefluffycart.everwinter.entity.custom.IceologerEntity;

public class IceologerRenderer extends IllagerEntityRenderer<IceologerEntity> {

    public IceologerRenderer(EntityRendererFactory.Context context) {
        super(context, new IllagerEntityModel<>(context.getPart(ModEntityModelLayers.ICEOLOGER)), 0.5f);
    }

    @Override
    public Identifier getTexture(IceologerEntity entity) {
        return Identifier.of(Everwinter.MOD_ID, "textures/entity/iceologer.png");
    }

    @Override
    public void render(IceologerEntity livingEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
        if (livingEntity.isBaby())
        {
            matrixStack.scale(0.5f, 0.5f, 0.5f);
        }
        this.model.getHat().visible = true;
        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

}
