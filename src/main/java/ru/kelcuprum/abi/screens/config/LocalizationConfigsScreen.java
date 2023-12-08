package ru.kelcuprum.abi.screens.config;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import ru.kelcuprum.abi.ActionBarInfo;
import ru.kelcuprum.abi.localization.Localization;
import ru.kelcuprum.alinlib.gui.InterfaceUtils;
import ru.kelcuprum.alinlib.gui.components.buttons.Button;
import ru.kelcuprum.alinlib.gui.components.buttons.ButtonBoolean;
import ru.kelcuprum.alinlib.gui.components.buttons.vanilla.VanillaButton;
import ru.kelcuprum.alinlib.gui.components.editbox.EditBoxString;
import ru.kelcuprum.alinlib.gui.components.editbox.vanilla.VanillaEditBoxString;
import ru.kelcuprum.alinlib.gui.components.sliders.SliderInteger;
import ru.kelcuprum.alinlib.gui.components.text.TextBox;

public class LocalizationConfigsScreen extends Screen {
    private final Screen parent;
    private static final Component TITLE = Localization.getText("abi.name");
    // CATEGORYES
    private static final Component MainConfigCategory = Localization.getText("abi.config");
    private VanillaButton MainConfigCategoryButton;
    private static final Component LocalizationConfigCategory = Localization.getText("abi.localization");
    private VanillaButton LocalizationConfigCategoryButton;
    // CATEGORY CONTENT
    private TextBox titleBox;

    private VanillaEditBoxString info;
    private static final Component infoText = Localization.getText("abi.localization.info");
    private VanillaEditBoxString dateTime;
    private static final Component dateTimeText = Localization.getText("abi.localization.date.time");
    //
    private VanillaEditBoxString north;
    private static final Component northText = Localization.getText("abi.localization.north");
    private VanillaEditBoxString south;
    private static final Component southText = Localization.getText("abi.localization.south");
    private VanillaEditBoxString west;
    private static final Component westText = Localization.getText("abi.localization.west");
    private VanillaEditBoxString east;
    private static final Component eastText = Localization.getText("abi.localization.east");
    private VanillaEditBoxString unknown;
    private static final Component unknownText = Localization.getText("abi.localization.unknown");
    //
    private static final Component EXIT = Localization.getText("abi.config.exit");
    private int scrolled = 0;

    public LocalizationConfigsScreen(Screen parent) {
        super(LocalizationConfigCategory);
        this.parent = parent;
    }

    public void tick() {
        this.titleBox.setYPos(15 - this.scrolled);

        this.info.setYPos(40-scrolled);
        this.dateTime.setYPos(65-scrolled);

        this.north.setYPos(90-scrolled);
        this.south.setYPos(115-scrolled);
        this.west.setYPos(140-scrolled);
        this.east.setYPos(165-scrolled);
        this.unknown.setYPos(190-scrolled);
        super.tick();
    }

    public void init() {
        this.scrolled = 0;
        this.initButton();
        this.initButtonsCategory();
    }

    private void initButtonsCategory() {
        int x = this.width - 150;
        this.titleBox = this.addRenderableWidget(new TextBox(140, 15, x, 9, this.title, true));
        //
        this.info = new VanillaEditBoxString(140, 40, x, 20, infoText);
        this.info.setContent(Localization.getLocalization("info", false));
        this.info.setResponse(s->{
            Localization.setLocalization("info", s);
        });

        this.addRenderableWidget(info);
        //
        this.dateTime = new VanillaEditBoxString(140, 40, x, 20, dateTimeText);
        this.dateTime.setContent(Localization.getLocalization("date.time", false));
        this.dateTime.setResponse(s->{
            Localization.setLocalization("date.time", s);
        });
        this.addRenderableWidget(dateTime);
        ///////
        //
        this.north = new VanillaEditBoxString(140, 40, x, 20, northText);
        this.north.setContent(Localization.getLocalization("north", false));
        this.north.setResponse(s->{
            Localization.setLocalization("north", s);
        });
        this.addRenderableWidget(north);
        //
        this.south = new VanillaEditBoxString(140, 40, x, 20, southText);
        this.south.setContent(Localization.getLocalization("south", false));
        this.south.setResponse(s->{
            Localization.setLocalization("south", s);
        });
        this.addRenderableWidget(south);
        //
        this.west = new VanillaEditBoxString(140, 40, x, 20, westText);
        this.west.setContent(Localization.getLocalization("west", false));
        this.west.setResponse(s->{
            Localization.setLocalization("west", s);
        });
        this.addRenderableWidget(west);
        //
        this.east = new VanillaEditBoxString(140, 40, x, 20, eastText);
        this.east.setContent(Localization.getLocalization("east", false));
        this.east.setResponse(s->{
            Localization.setLocalization("east", s);
        });
        this.addRenderableWidget(east);
        //
        this.unknown = new VanillaEditBoxString(140, 40, x, 20, unknownText);
        this.unknown.setContent(Localization.getLocalization("unknown", false));
        this.unknown.setResponse(s->{
            Localization.setLocalization("unknown", s);
        });
        this.addRenderableWidget(unknown);
    }

    private void initButton() {
        this.MainConfigCategoryButton = this.addRenderableWidget(new VanillaButton(10, 40, 110, 20, MainConfigCategory, (OnPress) -> {
            this.minecraft.setScreen(new MainConfigsScreen(this.parent));
        }));
        this.LocalizationConfigCategoryButton = this.addRenderableWidget(new VanillaButton(10, 65, 110, 20, LocalizationConfigCategory, (OnPress) -> {
            this.minecraft.setScreen(new LocalizationConfigsScreen(this.parent));
        }));
        this.LocalizationConfigCategoryButton.setActive(false);
        //
        this.addRenderableWidget(new VanillaButton(10, this.height - 30, 110, 20, EXIT, (OnPress) -> {
            ActionBarInfo.config.save();
            this.minecraft.setScreen(this.parent);
        }));
    }

    public void renderBackground(GuiGraphics guiGraphics, int i, int j, float f) {
        if (this.minecraft.level != null) {
            guiGraphics.fillGradient(0, 0, this.width, this.height, -1072689136, -804253680);
        } else {
            this.renderDirtBackground(guiGraphics);
        }

        InterfaceUtils.renderTextureLeftPanel(guiGraphics, 130, this.height);
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        guiGraphics.drawCenteredString(this.minecraft.font, TITLE, 65, 15, -1);
    }

    public boolean mouseScrolled(double d, double e, double f, double g) {
        int scrolled = (int)((double)this.scrolled + g * 10.0 * -1.0);
        int size = 215;
        if (scrolled <= 0 || size <= this.height) {
            this.scrolled = 0;
        } else this.scrolled = Math.min(scrolled, size - this.height);

        return super.mouseScrolled(d, e, f, g);
    }
}
