package com.webmets.vanishedmc.gui.buttons;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;

public class BindButton extends GuiButton {

	private List<ButtonAction> actions;
	private boolean toggled = false;
	private int bind = -1;
	private String name;

	public void drawBorderedRect(int x, int y, int x1, int y1, int size, int borderC, int insideC) {
		drawRect(x + size, y + size, x1 - size, y1 - size, insideC);
		drawRect(x + size, y + size, x1, y, borderC);
		drawRect(x, y, x + size, y1, borderC);
		drawRect(x1, y1, x1 - size, y + size, borderC);
		drawRect(x, y1 - size, x1, y1, borderC);
	}

	public BindButton(int id, int x, int y, int bind, String s) {
		this(id, x, y, 200, 20, bind, s);
	}

	public BindButton(int id, int x, int y, int l, int i1, int bind, String s) {
		super(id, x, y, l, i1, s);
		actions = new ArrayList<>();
		name = s;
		setBind(bind);
	}

	public void keyTyped(int key) {
		if (isToggled()) {
			setBind(key);
			press();
		}
	}

	public int getBind() {
		return bind;
	}

	public void setBind(int bind) {
		this.bind = bind;
		String key = Keyboard.getKeyName(bind);
		displayString = name + " " + (key.substring(0, 1).toUpperCase() + key.substring(1).toLowerCase());
	}

	public void press() {
		for (ButtonAction action : actions) {
			action.execute();
		}
	}

	public void addAction(ButtonAction action) {
		actions.add(action);
	}

	public boolean isToggled() {
		return toggled;
	}

	public void setToggled(boolean toggled) {
		this.toggled = toggled;
	}

	protected int getHoverState(boolean flag) {
		byte byte0 = 1;
		if (!enabled) {
			byte0 = 0;
		} else if (flag) {
			byte0 = 2;
		}
		return byte0;
	}

	public void drawButton(Minecraft mc, int mx, int my) {
		if (!visible) {
			return;
		}
		FontRenderer fontrenderer = mc.fontRendererObj;
		boolean flag = mx >= xPosition && my >= yPosition && mx < xPosition + width && my < yPosition + height; // Flag,
		// button
		if (flag || isToggled()) { // Hover Action
			drawBorderedRect(xPosition, yPosition, xPosition + width, yPosition + height, 1, 0xFF000000, 0x80000000);
			drawCenteredString(fontrenderer, displayString, xPosition + width / 2, yPosition + (height - 8) / 2,
					0xff666666);
		} else { // Normal
			drawBorderedRect(xPosition, yPosition, xPosition + width, yPosition + height, 1, 0x900d0d0d, 0x90262626);
			drawCenteredString(fontrenderer, displayString, xPosition + width / 2, yPosition + (height - 8) / 2,
					0xFFCCCCCC);
		}
	}

}
