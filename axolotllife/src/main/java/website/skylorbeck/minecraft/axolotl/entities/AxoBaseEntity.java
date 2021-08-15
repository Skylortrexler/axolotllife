package website.skylorbeck.minecraft.axolotl.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.world.World;

public class AxoBaseEntity extends PathAwareEntity  {
    private int state = 0;

    protected AxoBaseEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void useAbility(){

    }
}
