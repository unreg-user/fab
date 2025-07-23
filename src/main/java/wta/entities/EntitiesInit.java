package wta.entities;

import wta.entities.mobs.MobsInit;
import wta.entities.projectiles.ProjectilesInit;

public class EntitiesInit {
    public static void init(){
        MobsInit.init();
        ProjectilesInit.init();
    }

    public static void clientInit() {
        MobsInit.clientInit();
        ProjectilesInit.clientInit();
    }
}
