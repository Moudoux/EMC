The EMC (Easy Minecraft Client) Framework
===================

EMC (Easy Minecraft Client) is a framework for modifying Minecraft code without having to think about
obfuscation as the framework will act as a middle man handling your calls to Minecraft.

Unlike other mod loaders that require you to write your own Mixins to modify Minecraft code, EMC acts as an API that enables cross compatibility with multiple Minecraft
versions and you don't have to write any Mixins on your own.

This framework also allows you to write a mod once, then use it on Minecraft 1.12 and above.

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

EMC also has a very extensive event system with hooks for everything you could need in Minecraft.
All you need to do is make a new class, extend it by `EventListener` and use the `@EventHandler` annotation for each method as 
demonstrated below:

```
public class UpdateListener extends EventListener {

    @EventHandler(eventType = EventUpdate.class)
    public void onUpdate(EventUpdate event) {
        // This function will be called 20 times a second or each tick in Minecraft
    }

}
```

Minecraft versions currently supported
-------------------

* 1.14
* 1.13.2
* 1.13.1
* 1.13
* 1.12.2

Loading EMC (1.13.2)
-------------------

Minecraft uses Maven to download dependencies, EMC is loaded as a dependency. To load EMC in Minecraft add the following in the `libraries` array to your Minecraft json file:

```
{
	"name": "me.deftware:EMC:latest-1.13.2",
	"url": "https://gitlab.com/EMC-Framework/maven/raw/master/"
}
```

Then in the `arguments->game` array add `"--tweakClass", "me.deftware.launch.Launcher"` at the end. If you want to see an example on how to load EMC see [example_client.json](https://gitlab.com/EMC-Framework/EMC/blob/1.13.2/example_client.json)

For using EMC with 1.14 see the [1.14 Readme](https://gitlab.com/EMC-Framework/EMC/blob/1.14/README.md)

Making client mods with EMC 
-------------------

Check out the [EMC Development Kit](https://gitlab.com/EMC-Framework/EDK)
You can find documentation at the [EMC website](https://emc-framework.gitlab.io/)

Built in commands
-------------------

You can type `.version` to check what EMC version you are running, you can type `.mods` to see all loaded mods.
You can also type `.unload` to eject loaded mods.

Why use EMC?
-------------------

* Easy to use (You don't have to think about Minecraft obfuscation or Mixins)
* Completely compliant with the Minecraft EULA (You don't have to worry about DMCA takedown requests)
* Write once, use on multiple Minecraft versions
* Less code (This requires less code than writing your client with Minecraft)

EULA compliant
-------------------

The EMC framework is compliant with the Minecraft EULA, it does not distribute any Minecraft source code in it's installer.
Any mods you make using this framework will be compliant with the Minecraft EULA as your mod will not contain ANY
Minecraft source code, only EMC wrapper calls.

License
-------------------

EMC is licensed under GPL-3.0
