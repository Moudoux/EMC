package me.deftware.client.framework.Wrappers.Render;

import org.lwjgl.opengl.GL11;

public class IGL11 {
	
	public static void glNormal3f(float nx, float ny, float nz) {
		GL11.glNormal3f(nx, ny, nz);
	}
	
	public static void glRotatef(float angle, float x, float y, float z) {
		GL11.glRotatef(angle, x, y, z);
	}

	public static void glColor3f(float red, float green, float blue) {
		GL11.glColor3f(red, green, blue);
	}

	public static void glLineWidth(float width) {
		GL11.glLineWidth(width);
	}

	public static void glBegin(int mode) {
		GL11.glBegin(mode);
	}

	public static void glVertex2d(double x, double y) {
		GL11.glVertex2d(x, y);
	}

	public static void glEnd() {
		GL11.glEnd();
	}

	public static void glDisable(int cap) {
		GL11.glDisable(cap);
	}

	public static void glTranslatef(float x, float y, float z) {
		GL11.glTranslatef(x, y, z);
	}

}
