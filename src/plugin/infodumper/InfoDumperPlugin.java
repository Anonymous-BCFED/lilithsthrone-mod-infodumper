package plugin.infodumper;

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
	public void onInitUniqueNPCs() {
		System.out.println("Game.initUniqueNPCs() completed.");
		System.out.println("Beginning data dump.");
		PerkDumper.dump();
		System.out.println("Data dump complete!");
	}
}
