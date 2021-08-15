package website.skylorbeck.minecraft.axolotl;

import net.minecraft.entity.LivingEntity;

public interface PlayerEntityAccessor {
    int getAxostage();
    void setAxostage(int i);
    LivingEntity getStoredEntity();
    void setStoredEntity(LivingEntity entity);
    void useAbility();

}
