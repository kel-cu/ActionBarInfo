package ru.kelcuprum.abi.screens.config;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import ru.kelcuprum.abi.ActionBarInfo;
import ru.kelcuprum.abi.localization.Localization;
import ru.kelcuprum.alinlib.gui.InterfaceUtils;
import ru.kelcuprum.alinlib.gui.components.buttons.Button;
import ru.kelcuprum.alinlib.gui.components.buttons.vanilla.VanillaButton;
import ru.kelcuprum.alinlib.gui.components.buttons.vanilla.VanillaButtonBoolean;
import ru.kelcuprum.alinlib.gui.components.selector.vanilla.VanillaSelectorStringWithIntButton;
import ru.kelcuprum.alinlib.gui.components.sliders.vanilla.VanillaSliderInteger;
import ru.kelcuprum.alinlib.gui.components.text.TextBox;

public class MainConfigsScreen extends Screen {
    private final Screen parent;
    private static final Component TITLE = Localization.getText("abi.name");
    // CATEGORYES
    private static final Component MainConfigCategory = Localization.getText("abi.config");
    private VanillaButton MainConfigCategoryButton;
    private static final Component LocalizationConfigCategory = Localization.getText("abi.localization");
    private VanillaButton LocalizationConfigCategoryButton;
    // CATEGORY CONTENT
    private TextBox titleBox;
    private VanillaButtonBoolean enableABI;
    private static final Component enableABIText = Localization.getText("abi.config.enable_ab_information");
    private VanillaButtonBoolean viewItemOffHand;
    private static final Component viewItemOffHandText = Localization.getText("abi.config.view_item_off_hand");
    private VanillaButtonBoolean useExtendedCoordinates;
    private static final Component useExtendedCoordinatesText = Localization.getText("abi.config.use_extended_coordinates");
    //
    private VanillaSelectorStringWithIntButton typeRenderABI; //TYPE_RENDER_ACTION_BAR
    private static final Component typeRenderABIText = Localization.getText("abi.config.type_render_action_bar");
    private VanillaSliderInteger indentY;
    private static final Component indentYText = Localization.getText("abi.config.intend_y");
    private VanillaSliderInteger indentX; 
    private static final Component indentXText = Localization.getText("abi.config.intend_x");

    //
    private VanillaButtonBoolean renderInDebugScreen;
    private static final Component renderInDebugScreenText = Localization.getText("abi.config.render_in_debug_screen");
    //
    private static final Component EXIT = Localization.getText("abi.config.exit");
    private int scrolled = 0;

    public MainConfigsScreen(Screen parent) {
        super(MainConfigCategory);
        this.parent = parent;
    }

    public void tick() {
        this.titleBox.setYPos(15 - this.scrolled);

        this.enableABI.setYPos(40-scrolled);
        this.viewItemOffHand.setYPos(65-scrolled);
        this.useExtendedCoordinates.setYPos(90-scrolled);
        //
        this.typeRenderABI.setYPos(115-scrolled);
        this.indentX.setYPos(140-scrolled);
        this.indentY.setYPos(165-scrolled);
        //
        this.renderInDebugScreen.setYPos(190-scrolled);
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
        this.enableABI = new VanillaButtonBoolean(140, 40, x, 20, ActionBarInfo.config, "ENABLE_AB_INFORMATION", false, enableABIText);
        this.addRenderableWidget(enableABI);

        this.viewItemOffHand = new VanillaButtonBoolean(140, 40, x, 20, ActionBarInfo.config, "VIEW_ITEM_OFF_HAND", false, viewItemOffHandText);
        this.addRenderableWidget(viewItemOffHand);

        this.useExtendedCoordinates = new VanillaButtonBoolean(140, 40, x, 20, ActionBarInfo.config, "USE_EXTENDED_COORDINATES", false, useExtendedCoordinatesText);
        this.addRenderableWidget(useExtendedCoordinates);
        //
        String[] types = {
                "Default",
                "ABI Render",
                "Bottom left",
                "Bottom right",
                "Top left",
                "Top right"
        };
        this.typeRenderABI = new VanillaSelectorStringWithIntButton(140, 40, x, 20, types, ActionBarInfo.config, "TYPE_RENDER_ACTION_BAR", 0, typeRenderABIText);
        this.addRenderableWidget(typeRenderABI);

        this.indentX = new VanillaSliderInteger(140, 40, x, 20, ActionBarInfo.config, "INDENT_X", 20, 5, 100, indentXText);
        this.addRenderableWidget(indentX);

        this.indentY = new VanillaSliderInteger(140, 40, x, 20, ActionBarInfo.config, "INDENT_Y", 20, 5, 100, indentYText);
        this.addRenderableWidget(indentY);

        //
        this.renderInDebugScreen = new VanillaButtonBoolean(140, 40, x, 20, ActionBarInfo.config, "RENDER_IN_DEBUG_SCREEN", false, renderInDebugScreenText);
        this.addRenderableWidget(renderInDebugScreen);
    }

    private void initButton() {
        this.MainConfigCategoryButton = this.addRenderableWidget(new VanillaButton(10, 40, 110, 20, MainConfigCategory, (OnPress) -> {
            this.minecraft.setScreen(new MainConfigsScreen(this.parent));
        }));
        this.LocalizationConfigCategoryButton = this.addRenderableWidget(new VanillaButton(10, 65, 110, 20, LocalizationConfigCategory, (OnPress) -> {
            this.minecraft.setScreen(new LocalizationConfigsScreen(this.parent));
        }));
        this.MainConfigCategoryButton.setActive(false);
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
