package ru.kelcuprum.abi;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.apache.logging.log4j.Level;
import ru.kelcuprum.alinlib.api.events.client.ClientTickEvents;
import ru.kelcuprum.alinlib.api.events.client.GuiRenderEvents;
import ru.kelcuprum.alinlib.config.Localization;

import java.util.List;

public class HUDHandler implements GuiRenderEvents, ClientTickEvents.StartTick {
    private final List<Component> texts = new ObjectArrayList<>();

    @Override
    public void onStartTick(Minecraft client) {
        try {
            this.texts.clear();
            if (!ActionBarInfo.config.getBoolean("ENABLE_AB_INFORMATION", true)) return;
            if(ActionBarInfo.config.getNumber("TYPE_RENDER_ACTION_BAR", 0).intValue()  < 1 || ActionBarInfo.config.getNumber("TYPE_RENDER_ACTION_BAR", 0).intValue() > 5) return;
            String[] args = ActionBarInfo.localization.getLocalization("info").split("\\\\n");
            for (String arg : args) {
                this.texts.add(Localization.toText(arg));
            }
        } catch (Exception e){
            ActionBarInfo.log(e.getLocalizedMessage(), Level.ERROR);
        }
    }
    @Override
    public void onRender(GuiGraphics guiGraphics, float tickDelta) {
        int pos = ActionBarInfo.config.getNumber("TYPE_RENDER_ACTION_BAR", 0).intValue();
        int ix = ActionBarInfo.config.getNumber("INDENT_X", 20).intValue();
        int iy = ActionBarInfo.config.getNumber("INDENT_Y", 20).intValue();
        if(!texts.isEmpty()){
            if(pos == 1){
                int l = texts.size()-1;
                int f = ActionBarInfo.MINECRAFT.font.lineHeight+3;
                for(Component text : texts){
                    guiGraphics.drawCenteredString(ActionBarInfo.MINECRAFT.font, text, guiGraphics.guiWidth()/2, guiGraphics.guiHeight()-60-(l*f), -1);
                    l--;
                }
            } else {
                int l = pos == 2 || pos == 3 ? 0 : texts.size()-1;
                int f = ActionBarInfo.MINECRAFT.font.lineHeight+3;
                for(Component text : texts){
                    int x = pos == 2 || pos == 4 ? ix : guiGraphics.guiWidth() - ix - (ActionBarInfo.MINECRAFT.font.width(text));
                    int y = pos == 2 || pos == 3 ? iy+(l*f) : guiGraphics.guiHeight() - iy - ActionBarInfo.MINECRAFT.font.lineHeight - (l*f);
                    guiGraphics.drawString(ActionBarInfo.MINECRAFT.font, text, x, y, -1);
                    if(pos == 2 || pos == 3) l++; else l--;
                }
            }
        }
    }
}
