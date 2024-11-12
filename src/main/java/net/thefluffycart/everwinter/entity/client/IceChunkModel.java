package net.thefluffycart.everwinter.entity.client;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.thefluffycart.everwinter.entity.custom.IceChunkEntity;
import net.thefluffycart.everwinter.entity.custom.IceologerEntity;

public class IceChunkModel extends SinglePartEntityModel<IceChunkEntity> {
    private final ModelPart ice_chunk;

    public IceChunkModel(ModelPart root) {
        this.ice_chunk = root.getChild("ice_chunk");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData ice_chunk = modelPartData.addChild("ice_chunk", ModelPartBuilder.create().uv(0, 0).cuboid(-32.0F, 8.0F, -48.0F, 48.0F, 16.0F, 48.0F, new Dilation(0.0F))
                .uv(0, 106).cuboid(-16.0F, -8.0F, -32.0F, 16.0F, 16.0F, 16.0F, new Dilation(0.0F))
                .uv(130, 106).cuboid(0.0F, 0.0F, -32.0F, 16.0F, 8.0F, 16.0F, new Dilation(0.0F))
                .uv(97, 65).cuboid(-32.0F, 0.0F, -32.0F, 16.0F, 8.0F, 32.0F, new Dilation(0.0F))
                .uv(130, 131).cuboid(-16.0F, 0.0F, -48.0F, 16.0F, 8.0F, 16.0F, new Dilation(0.0F))
                .uv(130, 106).cuboid(-16.0F, 0.0F, -16.0F, 16.0F, 8.0F, 16.0F, new Dilation(0.0F))
                .uv(65, 106).cuboid(0.0F, -4.0F, -16.0F, 16.0F, 12.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(8.0F, 0.0F, 24.0F));
        return TexturedModelData.of(modelData, 256, 256);
    }

    @Override
    public void setAngles(IceChunkEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        ice_chunk.render(matrices, vertexConsumer, light, overlay, color);
    }

    @Override
    public ModelPart getPart() {
        return ice_chunk;
    }
}