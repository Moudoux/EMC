package me.deftware.client.framework.wrappers.gui;

public class TestGui extends IGuiScreen {

	public TestGui() {
		super(null);
	}

	@Override
	protected void onInitGui() {

	}

	@Override
	protected void onDraw(int mouseX, int mouseY, float partialTicks) {
		drawCenteredString("Test", 0, 0, 0xFFFFFF);
	}

	@Override
	protected void onUpdate() {

	}

	@Override
	protected void onKeyPressed(int keyCode, int action, int modifiers) {

	}

	@Override
	protected void onMouseReleased(int mouseX, int mouseY, int mouseButton) {

	}

	@Override
	protected void onMouseClicked(int mouseX, int mouseY, int mouseButton) {

	}

	@Override
	protected void onGuiResize(int w, int h) {

	}

}
