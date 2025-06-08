package plugin.infodumper;

import com.lilithsthrone.game.character.npc.NPC;
import com.lilithsthrone.game.character.npc.fields.Eisek;
import com.lilithsthrone.main.Main;
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
        // HACK: eisek is totally fucking broken as of 0.4.9.9, and just endlessly causes exceptions.
        // try {
        //     Main.game.addNPC(new Eisek(), false);
        // } catch (Exception e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        // }
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
        System.out.println("    Subspecies...");
        SubSpeciesDumper.dump();
        System.out.println("    Places...");
        PlaceDumper.dump();
        System.out.println("  Data dump complete!");
    }
}
