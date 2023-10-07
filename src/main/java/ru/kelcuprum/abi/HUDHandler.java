package ru.kelcuprum.abi;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import ru.kelcuprum.abi.info.Entity;
import ru.kelcuprum.abi.info.block.NoteBlock;
import ru.kelcuprum.abi.localization.Localization;
import ru.kelcuprum.abi.config.UserConfig;

import java.util.List;

public class HUDHandler implements HudRenderCallback, ClientTickEvents.StartTick {
    private final List<Component> textList = new ObjectArrayList<>();

    private final Minecraft client = Minecraft.getInstance();

    @Override
    public void onStartTick(Minecraft client) {
        NoteBlock.update();
        Entity.update();
        this.textList.clear();
        if (!UserConfig.ENABLE_AB_INFORMATION) return;
        if(UserConfig.TYPE_RENDER_ACTION_BAR < 1 || UserConfig.TYPE_RENDER_ACTION_BAR > 5) return;
        String[] args = Localization.getLocalization("info", true).split("\\\\n");
        for (String arg : args) {
            this.textList.add(Localization.toText(arg));
        }
    }
    @Override
    public void onHudRender(GuiGraphics drawContext, float tickDelta) {
        boolean isDebugOverlay = this.client.options.renderDebug;
        if (UserConfig.ENABLE_AB_INFORMATION) {
            int l = 0;
            int x;
            int y;
            for (Component text : this.textList) {
                switch (UserConfig.TYPE_RENDER_ACTION_BAR) {
                    case 1 -> {
                        x = (this.client.getWindow().getGuiScaledWidth() / 2) - (this.client.font.width(text) / 2);
                        y = this.client.getWindow().getGuiScaledHeight() + this.client.font.lineHeight - (((textList.size()-l)*this.client.font.lineHeight)-2) - 85;
                        this.drawString(drawContext, text, x, y);
                    }
                    case 2 -> {
                        if (isDebugOverlay && !UserConfig.RENDER_IN_DEBUG_SCREEN) return;
                        x = UserConfig.INDENT_X;
                        y = this.client.getWindow().getGuiScaledHeight() + this.client.font.lineHeight - (((textList.size()-l)*this.client.font.lineHeight)-2) - this.client.font.lineHeight - UserConfig.INDENT_Y;
                        this.drawString(drawContext, text, x, y);
                    }
                    case 3 -> {
                        if (isDebugOverlay && !UserConfig.RENDER_IN_DEBUG_SCREEN) return;
                        x = this.client.getWindow().getGuiScaledWidth() - this.client.font.width(text) - UserConfig.INDENT_X;
                        y = this.client.getWindow().getGuiScaledHeight() + this.client.font.lineHeight - (((textList.size()-l)*this.client.font.lineHeight)-2) - UserConfig.INDENT_Y;
                        this.drawString(drawContext, text, x, y);
                    }
                    case 4 -> {
                        if (isDebugOverlay && !UserConfig.RENDER_IN_DEBUG_SCREEN) return;
                        x = UserConfig.INDENT_X;
                        y = UserConfig.INDENT_Y + ((l*this.client.font.lineHeight)+2);
                        this.drawString(drawContext, text, x, y);
                    }
                    case 5 -> {
                        if (isDebugOverlay && !UserConfig.RENDER_IN_DEBUG_SCREEN) return;
                        x = this.client.getWindow().getGuiScaledWidth() - this.client.font.width(text) - UserConfig.INDENT_X;
                        y = UserConfig.INDENT_Y + ((l*this.client.font.lineHeight)+2);
                        this.drawString(drawContext, text, x, y);
                    }
                }
                l++;
            }
        }
    }

    private void drawString(GuiGraphics guiGraphics, Component text, int x, int y) {
        guiGraphics.drawString(this.client.font, text, x, y, 16777215);
    }
}
