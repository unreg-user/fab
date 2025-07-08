package wta.blocks;

import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;

public class PropertiesMod {
    public static DirectionProperty ROTATE=Properties.FACING;
    public static DirectionProperty H_ROTATE=Properties.HORIZONTAL_FACING;
    public static BooleanProperty STATIC=BooleanProperty.of("static");
    public static BooleanProperty TRAP=BooleanProperty.of("trap");
    public static BooleanProperty IN_TREE=BooleanProperty.of("in_tree");
    public static class NBT_DO {
        public static BooleanProperty do0=BooleanProperty.of("do0");
        public static BooleanProperty do1=BooleanProperty.of("do1");
        public static BooleanProperty do2=BooleanProperty.of("do2");
        public static BooleanProperty do3=BooleanProperty.of("do3");
        public static BooleanProperty do4=BooleanProperty.of("do4");
        public static BooleanProperty do5=BooleanProperty.of("do5");
    }
    public static BooleanProperty START=BooleanProperty.of("start");
}
