package plugin.infodumper;

import com.lilithsthrone.game.character.npc.NPC;
import com.lilithsthrone.modding.BasePlugin;
import plugin.infodumper.dumpers.*;

import java.util.List;

public class InfoDumperPlugin extends BasePlugin {
    public void onStartup() {
        System.out.println("onStartup() called.");
        //PatchMaster.begin();
    }

    @Override
    public void onMainMain() {
        System.out.println("Main.main() completed.");
    }

    @Override
    public void onMainStart() {
        System.out.println("Main.start() completed.");
//		System.out.println("  Beginning data dump...");
//		System.out.println("  Data dump complete!");
    }

    @Override
    public void onInitUniqueNPCs(List<Class<? extends NPC>> addedNpcs) {
        System.out.println("Game.initUniqueNPCs() completed.");
        System.out.println("  Beginning data dump...");
        System.out.println("    Perks...");
        PerkDumper.dump();
        System.out.println("    Fetishes...");
        FetishDumper.dump();
        System.out.println("    Status Effects...");
        StatusEffectDumper.dump();
        System.out.println("    Clothing...");
        ClothingDumper.dump();
        System.out.println("    Items...");
        ItemDumper.dump();
        System.out.println("    Menus...");
        MenuDumper.dump();
        System.out.println("    Effects...");
        EffectsDumper.dump();
        System.out.println("    Slave Jobs...");
        JobsDumper.dump();
        System.out.println("    Body Parts...");
        BodyPartDumper.dump();
        System.out.println("    Racial Bodies...");
        RacialBodyDumper.dump();
        System.out.println("  Data dump complete!");
    }
}
