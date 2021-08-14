package website.skylorbeck.minecraft.axolotl;

import net.minecraft.entity.LivingEntity;

public class EntityRetainer {
    static LivingEntity entity;
    public static void setEntity(LivingEntity entity){
        EntityRetainer.entity = entity;
    }
    public static LivingEntity getEntity(){
        return entity;
    }
}
