package me.deftware.client.framework.Wrappers;

import java.util.Collection;

import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.Team;

public class ITeam {

	@Nullable
	public static Collection<String> getTeamMembers() {
		Team team = Minecraft.getMinecraft().player.getTeam();
		if (team == null) {
			return null;
		}
		return team.getMembershipCollection();
	}

}
