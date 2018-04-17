The EMC (Easy Minecraft Client) Framework
===================

EMC (Easy Minecraft Client) is a framework for modifying Minecraft code without having to think about
obfuscation as the framework will act as a middle man handling your calls to Minecraft.

This framework also allows you to write a mod once, then use it on Minecraft 1.8 and above.

Discord
-------------------

We now have an official Discord server for EMC related development, feel free to join us, [EMC Discord](discord.gg/jcPDsGT).


How it works
-------------------

In short:

`Your code <-> EMC Wrapper <-> Obfuscated Minecraft code`

Example:

Let's say you want to change the walk speed. To do so, you would simply call the EMC wrapper and it does the hard work for you,
in this case `IEntityPlayer` is the Minecraft player wrapper that translates the EntityPlayer calls,
all wrappers start with an `I` followed by the name of the Minecraft class it handles:

```
public void setWalkspeed(float speed) {
	// Call the EMC wrapper
	IEntityPlayer.setWalkspeed(speed);
}
```

That's it. It is as easy as that. The IEntityPlayer.setWalkspeed then calls the obfuscated Minecraft call.

Minecraft versions currently supported
-------------------

* 1.12.2
* 1.12.1
* 1.12
* 1.11.2

Maven repo/Loading EMC
-------------------

Minecraft uses Maven to download dependencies, EMC is loaded as a dependency. To load EMC in Minecraft add the following in the `libraries` array to your Minecraft json file:

```
{
	"name": "me.deftware:EMC:13.1.0",
	"url": "https://github.com/Moudoux/EMC/raw/master/maven/"
}
```

Then in the `minecraftArguments` field append `--tweakClass me.deftware.launch.Launcher` at the end. If you want to see an example on how to load EMC see [example_client.json](https://github.com/Moudoux/EMC/blob/master/example_client.json)

Stacking on top of Forge
-------------------

EMC can be stacked on top of Forge to run EMC mods in conjunction with EMC mods. To build EMC for Forge load the `build.gradle.forge` file instead of the regular build.gradle, build then drop the EMC jar into your Forge mods folder, that's it.

Making client mods with EMC 
-------------------

Check out the [EMC Development Kit](https://github.com/Moudoux/EDK)

Built in commands
-------------------

You can type `.version` to check what EMC version you are running, you can type `.cinfo` to see what client is loaded.
You can also type `.unload` to eject mods running in your client.

Why use EMC?
-------------------

* Easy to use (You don't have to think about Minecraft obfuscation)
* Completely compliant with the Minecraft EULA (You don't have to worry about DMCA takedown requests)
* Write once, use on multiple Minecraft versions
* Less code (This requires less code than writing your client with Minecraft)

EULA compliant
-------------------

The EMC framework is compliant with the Minecraft EULA, it does not distribute any Minecraft source code in it's installer.
Any mods you make using this framework will be compliant with the Minecraft EULA as your mod will not contain ANY
Minecraft source code, only EMC wrapper calls.

Developing EMC (Not making client mods, the actual framework)
-------------------

1. Clone this git
2. Import it into your IDE as a gradle project
3. Run the `setupDecompWrokspace` task
4. Refresh the gradle project

You can now start modifying EMC, if you are using IDEA run the `genIntellijRuns` task as well.

License
-------------------

EMC is licensed under GPL-3.0
