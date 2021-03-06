package com.webmets.vanishedmc.modules;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;
import com.webmets.vanishedmc.VanishedMC;
import com.webmets.vanishedmc.controllers.MouseController;
import com.webmets.vanishedmc.gui.settings.Configurable;
import com.webmets.vanishedmc.settings.GuiHudCOORDSView;
import com.webmets.vanishedmc.settings.GuiHudCPSView;
import com.webmets.vanishedmc.utils.effects.EffectUtils;
import com.webmets.vanishedmc.utils.ping.PingUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class GuiHudModule implements Configurable {

	/**
	 * The main HUD module, showing FPS,CPS, and similair game stats
	 */

	// Variables
	private Minecraft mc = Minecraft.getMinecraft();
	private FontRenderer fr = mc.fontRendererObj;
	private VanishedMC client = VanishedMC.instance;
	private EffectUtils effects;

	// Settings
	private String name = "&lVanishedMC";
	private boolean showFPS = true;
	private boolean showCOORDS = true;
	private boolean CoordsOneLine = false;
	private boolean showCPS = true;
	private boolean showPING = true;
	private boolean isLowerCase = false;
	private GuiHudCPSView cpsView = GuiHudCPSView.SEPARATE;
	private GuiHudCOORDSView coordsView = GuiHudCOORDSView.COMPACT;

	// Constructor
	public GuiHudModule() {
		effects = new EffectUtils();
	}

	public void render(int x, int y) {
		List<String> toRender = new ArrayList<>();
		int offset = 0;

		if (!name.isEmpty()) {
			toRender.add(name);
		}

		if (isShowFPS()) {
			int fps = Minecraft.debugFPS;
			toRender.add("Fps " + fps);
		}

		if (isShowCPS()) {
			MouseController mouse = client.getMouseController();
			String cps = client.getMouseController().getCps(cpsView);
			toRender.add("Cps " + cps);
		}

		if (isShowCOORDS()) {
			String xCoord = "";
			String yCoord = "";
			String zCoord = "";

			switch (coordsView) {
			case SMALL:
				xCoord = String.format("%.0f", (float) Minecraft.getMinecraft().thePlayer.posX);
				yCoord = String.format("%.0f", (float) Minecraft.getMinecraft().thePlayer.posY);
				zCoord = String.format("%.0f", (float) Minecraft.getMinecraft().thePlayer.posZ);
				break;
			case COMPACT:
				xCoord = String.format("%.1f", (float) Minecraft.getMinecraft().thePlayer.posX);
				yCoord = String.format("%.1f", (float) Minecraft.getMinecraft().thePlayer.posY);
				zCoord = String.format("%.1f", (float) Minecraft.getMinecraft().thePlayer.posZ);
				break;
			case EXPANDED:
				xCoord = String.format("%.2f", (float) Minecraft.getMinecraft().thePlayer.posX);
				yCoord = String.format("%.2f", (float) Minecraft.getMinecraft().thePlayer.posY);
				zCoord = String.format("%.2f", (float) Minecraft.getMinecraft().thePlayer.posZ);
				break;
			}
			if (isCoordsOneLine()) {
				toRender.add("X " + xCoord + ", Y " + yCoord + ", Z " + zCoord);
			} else {
				toRender.add("X " + xCoord);
				toRender.add("Y " + yCoord);
				toRender.add("Z " + zCoord);
			}
		}

		if (isShowPING()) {
			PingUtils.ping();
			toRender.add("Ping " + PingUtils.serverPing + "ms");
		}

		SprintModule sprint = (SprintModule) client.getModuleManager().getModule(SprintModule.class);
		if (sprint.isEnabled()) {
			toRender.add("Sprint");
		}

		for (String s : toRender) {
			if (isLowerCase()) {
				fr.drawString(s.replace("&", "§").toLowerCase(), x, y + offset, effects.getColorForY(y, offset));
			} else {
				fr.drawString(s.replace("&", "§"), x, y + offset, effects.getColorForY(y, offset));
			}
			offset += 10;
		}
		toRender = null;
	}

	// Getters
	public boolean isShowCOORDS() {
		return showCOORDS;
	}

	public boolean isShowFPS() {
		return showFPS;
	}

	public boolean isCoordsOneLine() {
		return CoordsOneLine;
	}

	public boolean isShowCPS() {
		return showCPS;
	}

	public boolean isShowPING() {
		return showPING;
	}

	public GuiHudCPSView getCpsView() {
		return cpsView;
	}

	public GuiHudCOORDSView getCoordsView() {
		return coordsView;
	}

	public boolean isLowerCase() {
		return isLowerCase;
	}

	public EffectUtils getEffectUtils() {
		return effects;
	}

	// Setters
	public void setCpsView(GuiHudCPSView cpsView) {
		this.cpsView = cpsView;
	}

	public void setLowerCase(boolean isLowerCase) {
		this.isLowerCase = isLowerCase;
	}

	public void setCoordsOneLine(boolean coordsOneLine) {
		this.CoordsOneLine = coordsOneLine;
	}

	public void setCoordsView(GuiHudCOORDSView coordsView) {
		this.coordsView = coordsView;
	}

	public void setShowCOORDS(boolean showCOORDS) {
		this.showCOORDS = showCOORDS;
	}

	public void setShowCPS(boolean showCPS) {
		this.showCPS = showCPS;
	}

	public void setShowFPS(boolean showFPS) {
		this.showFPS = showFPS;
	}

	public void setShowPING(boolean showPING) {
		this.showPING = showPING;
	}

	@Override
	public String getKey() {
		return "hud";
	}

	@Override
	public JsonObject getSettings() {
		JsonObject hud = new JsonObject();
		hud.addProperty("fps", isShowFPS());
		hud.addProperty("cps", isShowCPS());
		hud.addProperty("cpsView", getCpsView().toString());
		hud.addProperty("coords", isShowCOORDS());
		hud.addProperty("coordsOneLine", isCoordsOneLine());
		hud.addProperty("coordsView", getCoordsView().toString());
		hud.addProperty("ping", isShowPING());
		hud.addProperty("pingDelay", PingUtils.getDelay());
		hud.add(effects.getKey(), effects.getSettings());
		return hud;
	}

	@Override
	public void loadSettings(JsonObject json) {
		setShowFPS(json.get("fps").getAsBoolean());
		setShowCPS(json.get("cps").getAsBoolean());
		setCpsView(GuiHudCPSView.valueOf(json.get("cpsView").getAsString()));
		setShowCOORDS(json.get("coords").getAsBoolean());
		setCoordsOneLine(json.get("coordsOneLine").getAsBoolean());
		setCoordsView(GuiHudCOORDSView.valueOf(json.get("coordsView").getAsString()));
		setShowPING(json.get("ping").getAsBoolean());
		PingUtils.setDelay(json.get("pingDelay").getAsInt());
		effects.loadSettings(json.get(effects.getKey()).getAsJsonObject());
	}
}
