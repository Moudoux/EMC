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

Why use EMC ?
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

Installing EMC/Setting up for making mods
-------------------

Download EMC for any Minecraft version (It doesn't matter since all versions are compatible with your mod)

Install it, then open your favourite Java IDE and make a new project, import the EMC Minecraft jar in `.minecraft/versions/<MC Version>_EMC/<MC Version>_EMC.jar`

Make a class called `Main.java`, make it extend `Client` and add the required methods.

Create a file called `client.json` in the root of your project. In the file, add the following:

```
{

    "name":"<Client name>",
    "website":"<Client website>",
    "author":"<Who made the client>",
    "minversion":<Minimum version of EMC required to run your client, integer>,
    "version":<Your client version, integer>,
    "main":"<The main class>"

}
```

After you've written your mod, export it to a file named `Client.jar`, drop that jar in `.minecraft/versions/<MC Version>_EMC/` and start Minecraft, your
mod should now be loaded.

A simple one class client example
-------------------

```
package me.deftware.client;

import me.deftware.client.framework.Client.Client;
import me.deftware.client.framework.Event.Event;
import me.deftware.client.framework.Event.Events.EventClientCommand;
import me.deftware.client.framework.Wrappers.IChat;

public class Main extends Client {
	
	private ClientInfo clientInfo;
	
	@Override
	public void initialize() {
		// Protocol version, Minecraft version, Client name, Client version
		clientInfo = new ClientInfo(316, "1.11.2", "Example client", "1");
		// We can even set the window title with one call to EMC
		IMinecraft.setTitle("Example client");
	}

	@Override
	public ClientInfo getClientInfo() {
		// This is used for the framework to know what client it has
		return clientInfo;
	}

	@Override
	public void onEvent(Event event) {
		// Our events come in here
		// Check if it's a chat message starting with a .
		if (event instanceof EventClientCommand) {
			// Get our event
			EventClientCommand cmd = (EventClientCommand) event;
			// Check the command
			if (cmd.getCommand().equals(".client")) {
				// Prints a message in the Minecraft chat, client side, servers cannot see it
				IChat.sendClientMessage("It works!");
				// Cancel the event so Minecraft won't process the sent chat message
				event.setCanceled(true);
			}
		}
	}

}
```

And that's it! If you now type `.client` in the Minecraft chat it will print `It works!` in the Minecraft chat.

Developing EMC
-------------------

1. Download the latest MCP
2. Decompile Minecraft
3. Add the EMC framework code
4. Apply the EMC Minecraft code hook patch file (Coming soon)
5. Start working

License
-------------------

EMC is licensed under GPL-3.0