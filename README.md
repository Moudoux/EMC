The EMC (Easy Minecraft Client) Framework
===================

EMC (Easy Minecraft Client) is a framework for modifying Minecraft code without having to think about
obfuscation as the framework will act as a middle man handling your calls to Minecraft.

This framework also allows you to write a mod once, then use it on Minecraft 1.8 and above.

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

Minecraft currently versions supported
-------------------

* 1.12.2
* 1.12.1
* 1.12
* 1.11.2


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
* This framework was made specifically for creating Minecraft "hacked clients"

EULA compliant
-------------------

The EMC framework is compliant with the Minecraft EULA, it does not distribute any Minecraft source code in it's installer.
Any mods you make using this framework will be compliant with the Minecraft EULA as your mod will not contain ANY
Minecraft source code, only EMC wrapper calls.

Developing EMC (Not making client mods, the actual framework)
-------------------

1. Download the latest MCP
2. Decompile Minecraft
3. Add the EMC framework code
4. Apply the EMC Minecraft code hook patch file (Coming soon)
5. Start working

License
-------------------

EMC is licensed under GPL-3.0
