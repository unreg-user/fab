package wta.mixins.mixinInterfaces;

public interface LivingEntityFixerInterface {
    default int getStuckBurdockCount(){return 0;}
    default void setStuckBurdockCount(int count){}
    default void burdockDownTick(){}
    default boolean hasBurdockDownTick(){return true;}
    default void burdockEffectTick(){}
    default boolean hasBurdockEffectTick(){return true;}
}
