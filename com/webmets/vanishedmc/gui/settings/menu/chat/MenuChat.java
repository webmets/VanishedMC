package com.webmets.vanishedmc.gui.settings.menu.chat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.webmets.vanishedmc.chat.ChatManager;
import com.webmets.vanishedmc.gui.buttons.ButtonAction;
import com.webmets.vanishedmc.gui.buttons.ToggleButton;
import com.webmets.vanishedmc.gui.settings.Menu;

import net.minecraft.client.gui.GuiButton;

public class MenuChat extends Menu {

	@Override
	public void initGui() {
		// Variables
		final ChatManager chat = client.getChatManager();
		super.initGui();
		((ToggleButton) buttonList.get(6)).setToggled(true);
		
		// Initialize buttons
		final ToggleButton tabs = new ToggleButton(0, 140, 30, 100, 20, "Chat tabs");
		
		// Actions
		tabs.addAction(new ButtonAction() {
			@Override
			public void execute() {
				mc.displayGuiScreen(new MenuChatTabs());
			}
		});
		
		// Adding to list
		buttonList.add(tabs);
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
	}

}
