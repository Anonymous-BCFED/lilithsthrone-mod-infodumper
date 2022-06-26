# AnonymousBCFED_InfoDumper
This is a very, very simple plugin for just dumping some game data from Lilith's Throne.  It also demonstrates how to merge dependencies.

While there is a res directory, all the fun stuff is in the JAR.

## This Mod

THis mod will poop out some JSON files in data/internalData/ after the `onInitUniqueNPCs()` signal is called.

InfoDumper will also add some additional lines to console corresponding to certain signal calls.

## Terminology

| Term | Meaning |
|---|---|
| **Mod** | Collection of data, graphics, and plugins that add or modify content in the game. |
| **Plugin** | A JAR containing Java classes and metadata that can modify the game at runtime. |

## What can a Plugin Do?

Plugins have access to (almost) all of the game, as Java has a very robust reflection API that allows game code structures to be changed at runtime.

This excludes enums, unfortunately, but Lilith's Throne developers have added some tools to make modifying enums unnecessary.

Examples of Plugin Usage:

* Adding NPC classes
* Adding fetishes
* Overriding hardcoded content
* Changing content from the base game or another mod
* Debugging
* Cheats

## OMG Executable Code

Be aware that plugins can carry all sorts of heinous things, so it's recommended to only download them from trustworthy sources, and only make plugins if you have no other choice.

**If in doubt, don't install it.**

## Directory Structure

The output directory structure looks like this:

```
.
├── LilithsThrone_#_#_#_#.jar
└── res
    └── mods
        └── $AUTHORNAME
            └── plugins
                └── infodumper-#.#.#-SNAPSHOT.jar
```

I also recommend adding a `README` to the root of the mod explaining what it is and how to install it.

## Plugin JAR Requirements

* Each JAR needs a class that extends from `com.lilithsthrone.modding.BasePlugin`.  This class is generally called something like `ModNamePlugin`.
* In the root of the JAR must be a file named `PLUGIN_METADATA.xml`. See [our file](src/PLUGIN_METADATA.xml) for an example.
  * Plugins are identified uniquely by a Universally Unique Identifier (UUID).  This ensures other plugins can test for their existence without getting broken by renames or forks.
  * Plugins can advertise that they *provide* certain things by providing by listing them as small `<tag>`s in `<providesTags>`.
  * Other plugins can delay loading until all of their `<requiredTags>` are satisfied by loaded mods.
* No version data should be in the filename, to avoid having 30 different versions installed due to user overwrites.
## Plugin Class

The `Plugin` class is your mod's entry point.  When loaded, the plugin receives a call to `onStartup()`.  Other signals are available simply by overriding the method in your `Plugin` class.

| Name | Notes
|---|---|
| `onStartup()` | The plugin has been fully loaded by the PluginLoader. |
| `onInitUniqueNPCs()` | The game is setting up and checking unique NPCs. |
