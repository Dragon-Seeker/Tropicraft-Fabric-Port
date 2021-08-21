package net.tropicraft.core.client.entity.models;

import net.tropicraft.core.common.entity.TropiBeeEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BeeEntityModel;

public class TropiBeeModel extends BeeEntityModel<TropiBeeEntity> {
    private final ModelPart hat1;
    private final ModelPart hat2;
    private final ModelPart hat3;

    public TropiBeeModel() {
        super();
        hat1 = new ModelPart(this, 0, 32);
        hat1.addCuboid(-5F, -6F, -5F, 12, 1, 6);
        hat1.setPivot(-1F, 1F, -1F);
        hat1.mirror = true;
        ModelPart body = getBody();
        body.addChild(hat1);
        hat2 = new ModelPart(this, 0, 48);
        hat2.addCuboid(0F, -6F, 0F, 6, 2, 6);
        hat2.setPivot(-3F, -1F, -3F);
        hat2.mirror = false;
        body.addChild(hat2);
        hat3 = new ModelPart(this, 0, 32);
        hat3.addCuboid(-5F, -6F, 0F, 12, 1, 6);
        hat3.setPivot(-1F, 1F, 0F);
        hat3.mirror = false;
        body.addChild(hat3);
    }

    public ModelPart getBody() {
        ModelPart body = null;
        for (ModelPart b : getBodyParts()) {
            body = b;
            break;
        }
        return body;
    }
}
