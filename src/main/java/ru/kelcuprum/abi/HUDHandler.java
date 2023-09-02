package ru.kelcuprum.abi;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import ru.kelcuprum.abi.localization.Localization;
import ru.kelcuprum.abi.config.UserConfig;

import java.util.List;

public class HUDHandler implements HudRenderCallback, ClientTickEvents.StartTick {
    private final List<Component> textList = new ObjectArrayList<>();

    private final Minecraft client = Minecraft.getInstance();

    @Override
    public void onStartTick(Minecraft client) {
        this.textList.clear();
        if (!UserConfig.ENABLE_AB_INFORMATION) return;
        this.textList.add(Localization.toText(Localization.getLocalization("info", true)));
    }
    @Override
    public void onHudRender(GuiGraphics drawContext, float tickDelta) {
        if (UserConfig.ENABLE_AB_INFORMATION) {
            int x;
            int y;
            for (Component text : this.textList) {
                switch (UserConfig.TYPE_RENDER_ACTION_BAR) {
                    case 1 -> {
                        x = (this.client.getWindow().getGuiScaledWidth() / 2) - (this.client.font.width(text) / 2);
                        y = this.client.getWindow().getGuiScaledHeight() - 85;
                        this.drawString(drawContext, text, x, y);
                    }
                    case 2 -> {
                        if (client.options.renderDebug && !UserConfig.RENDER_IN_DEBUG_SCREEN) return;
                        x = UserConfig.INDENT_X;
                        y = this.client.getWindow().getGuiScaledHeight() - this.client.font.lineHeight - UserConfig.INDENT_Y;
                        this.drawString(drawContext, text, x, y);
                    }
                    case 3 -> {
                        if (client.options.renderDebug && !UserConfig.RENDER_IN_DEBUG_SCREEN) return;
                        x = this.client.getWindow().getGuiScaledWidth() - this.client.font.width(text) - UserConfig.INDENT_X;
                        y = this.client.getWindow().getGuiScaledHeight() - this.client.font.lineHeight - UserConfig.INDENT_Y;
                        this.drawString(drawContext, text, x, y);
                    }
                    case 4 -> {
                        if (client.options.renderDebug && !UserConfig.RENDER_IN_DEBUG_SCREEN) return;
                        x = UserConfig.INDENT_X;
                        y = UserConfig.INDENT_Y;
                        this.drawString(drawContext, text, x, y);
                    }
                    case 5 -> {
                        if (client.options.renderDebug && !UserConfig.RENDER_IN_DEBUG_SCREEN) return;
                        x = this.client.getWindow().getGuiScaledWidth() - this.client.font.width(text) - UserConfig.INDENT_X;
                        y = UserConfig.INDENT_Y;
                        this.drawString(drawContext, text, x, y);
                    }
                }

            }
        } else if(UserConfig.EXPERIMENT){
            Component text1 = Localization.toText("ActionBarInfo info");
            this.drawString(drawContext, text1, (this.client.getWindow().getGuiScaledWidth()/2) - (this.client.font.width(text1)/2),
                    this.client.getWindow().getGuiScaledHeight() - 85);
            Component text2 = Localization.toText("Down left");
            if(!client.options.renderDebug) this.drawString(drawContext, text2,
                    UserConfig.INDENT_X, this.client.getWindow().getGuiScaledHeight() - this.client.font.lineHeight - UserConfig.INDENT_Y);
            Component text3 = Localization.toText("Down right");
            if(!client.options.renderDebug) this.drawString(drawContext, text3,
                    this.client.getWindow().getGuiScaledWidth() - this.client.font.width(text3) - UserConfig.INDENT_X,
                    this.client.getWindow().getGuiScaledHeight() - this.client.font.lineHeight - UserConfig.INDENT_Y);
            Component text4 = Localization.toText("Up left");
            if(!client.options.renderDebug) this.drawString(drawContext, text4,
                    UserConfig.INDENT_X, UserConfig.INDENT_Y);
            Component text5 = Localization.toText("Up right");
            if(!client.options.renderDebug) this.drawString(drawContext, text5,
                    this.client.getWindow().getGuiScaledWidth() - this.client.font.width(text5) - UserConfig.INDENT_X, UserConfig.INDENT_Y);
        }
    }

    private void drawString(GuiGraphics guiGraphics, Component text, int x, int y) {
        guiGraphics.drawString(this.client.font, text, x, y, 16777215);
    }
}
