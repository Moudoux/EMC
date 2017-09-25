package me.deftware.client.framework.Utils;

import java.util.ArrayList;

public abstract class ICachedList {

	protected ArrayList<?> list = new ArrayList();

	public abstract void execute();

	public ArrayList<?> getList() {
		return list;
	}

}
