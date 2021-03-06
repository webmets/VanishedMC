package com.webmets.vanishedmc.gui.settings.menu;

import java.io.IOException;
import java.util.Arrays;

import com.webmets.vanishedmc.gui.buttons.ButtonAction;
import com.webmets.vanishedmc.gui.buttons.SelectorButton;
import com.webmets.vanishedmc.gui.buttons.SliderButton;
import com.webmets.vanishedmc.gui.buttons.ToggleButton;
import com.webmets.vanishedmc.gui.settings.Menu;
import com.webmets.vanishedmc.modules.GuiHudModule;
import com.webmets.vanishedmc.settings.GuiHudCOORDSView;
import com.webmets.vanishedmc.settings.GuiHudCPSView;
import com.webmets.vanishedmc.utils.ping.PingUtils;

import net.minecraft.client.gui.GuiButton;

public class MenuHud extends Menu {

	@Override
	public void initGui() {
		// Variables
		super.initGui();
		((ToggleButton) buttonList.get(0)).setToggled(true);
		final GuiHudModule hud = client.getHudModule();

		// Initialize buttons
		final ToggleButton coords = new ToggleButton(0, 140, 30, 100, 20, "Coords");
		final ToggleButton coordsOneLine = new ToggleButton(0, 245, 30, 100, 20, "One line");
		final SelectorButton coordsMode = new SelectorButton(0, 350, 30, 100, 20, "Mode:",
				Arrays.asList("Small", "Compact", "Expanded"));

		final ToggleButton cps = new ToggleButton(0, 140, 55, 100, 20, "Cps");
		final SelectorButton cpsMode = new SelectorButton(0, 245, 55, 100, 20, "Mode:",
				Arrays.asList("Separate", "Combined", "Left", "Right"));

		final ToggleButton fps = new ToggleButton(0, 140, 80, 100, 20, "Fps");

		final ToggleButton ping = new ToggleButton(0, 140, 105, 100, 20, "Ping");
		final SliderButton pingDelay = new SliderButton(0, 245, 105, 100, 20, 5, 5, "delay");

		// Set states
		fps.setToggled(hud.isShowFPS());
		cps.setToggled(hud.isShowCPS());
		cpsMode.visible = cps.isToggled();
		cpsMode.setCurrent(hud.getCpsView().toString().toLowerCase());
		coords.setToggled(hud.isShowCOORDS());
		coordsOneLine.setToggled(hud.isCoordsOneLine());
		coordsOneLine.visible = coords.isToggled();
		coordsMode.setCurrent(hud.getCoordsView().toString().toLowerCase());
		coordsMode.visible = coords.isToggled();
		ping.setToggled(hud.isShowPING());
		pingDelay.setValue((PingUtils.getDelay() / 1000) - 5);

		// Actions
		pingDelay.addAction(new ButtonAction() {
			@Override
			public void execute() {
				PingUtils.setDelay((int) pingDelay.getDisplayValue() * 1000);
			}
		});

		coordsMode.addAction(new ButtonAction() {
			@Override
			public void execute() {
				hud.setCoordsView(GuiHudCOORDSView.valueOf(coordsMode.getCurrent().toUpperCase()));
			}
		});

		cpsMode.addAction(new ButtonAction() {
			@Override
			public void execute() {
				hud.setCpsView(GuiHudCPSView.valueOf(cpsMode.getCurrent().toUpperCase()));
			}
		});

		fps.addAction(new ButtonAction() {
			@Override
			public void execute() {
				fps.setToggled(!fps.isToggled());
				hud.setShowFPS(fps.isToggled());
			}
		});

		cps.addAction(new ButtonAction() {
			@Override
			public void execute() {
				cps.setToggled(!cps.isToggled());
				hud.setShowCPS(cps.isToggled());
				cpsMode.visible = cps.isToggled();
			}
		});

		coords.addAction(new ButtonAction() {
			@Override
			public void execute() {
				coords.setToggled(!coords.isToggled());
				hud.setShowCOORDS(coords.isToggled());
				coordsOneLine.visible = coords.isToggled();
				coordsMode.visible = coords.isToggled();
			}
		});

		coordsOneLine.addAction(new ButtonAction() {
			@Override
			public void execute() {
				coordsOneLine.setToggled(!coordsOneLine.isToggled());
				hud.setCoordsOneLine(coordsOneLine.isToggled());
			}
		});

		ping.addAction(new ButtonAction() {
			@Override
			public void execute() {
				ping.setToggled(!ping.isToggled());
				hud.setShowPING(ping.isToggled());
			}
		});

		// Adding to list
		buttonList.add(fps);
		buttonList.add(cps);
		buttonList.add(coords);
		buttonList.add(coordsOneLine);
		buttonList.add(ping);
		buttonList.add(pingDelay);
		buttonList.add(cpsMode);
		buttonList.add(coordsMode);
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button instanceof ToggleButton) {
			((ToggleButton) button).press();
		}
		if (button instanceof SelectorButton) {
			((SelectorButton) button).press();
		}
	}
}
