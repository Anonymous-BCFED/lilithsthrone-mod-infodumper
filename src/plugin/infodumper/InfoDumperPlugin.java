package plugin.infodumper;

import java.util.List;

import com.lilithsthrone.game.character.npc.NPC;
import com.lilithsthrone.modding.BasePlugin;

public class InfoDumperPlugin extends BasePlugin {
	public void onStartup() {
		System.out.println("Hello world!");
	}

	@Override
	public void onMainMain() {
		System.out.println("Main.main() completed.");
	}

	@Override
	public void onMainStart() {
		System.out.println("Main.start() completed.");
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
		System.out.println("  Data dump complete!");
	}
}
