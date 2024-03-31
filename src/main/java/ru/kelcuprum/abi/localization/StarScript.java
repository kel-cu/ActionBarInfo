package ru.kelcuprum.abi.localization;

import meteordevelopment.starscript.Script;
import meteordevelopment.starscript.Section;
import meteordevelopment.starscript.StandardLib;
import meteordevelopment.starscript.Starscript;
import meteordevelopment.starscript.compiler.Compiler;
import meteordevelopment.starscript.compiler.Parser;
import meteordevelopment.starscript.utils.Error;
import meteordevelopment.starscript.utils.StarscriptError;
import meteordevelopment.starscript.value.Value;
import meteordevelopment.starscript.value.ValueMap;
import net.minecraft.SharedConstants;

import org.apache.logging.log4j.Level;
import ru.kelcuprum.abi.ActionBarInfo;
import ru.kelcuprum.abi.info.Player;
import ru.kelcuprum.abi.info.World;

import java.text.SimpleDateFormat;

public class StarScript {
    public static Starscript ss = new Starscript();

    public static void init() {
        StandardLib.init(ss);

        // General
        ss.set("minecraft", new ValueMap()
                .set("version", SharedConstants.getCurrentVersion().getName())
                .set("loader", ActionBarInfo.MINECRAFT.getVersionType())
                .set("fps", () -> Value.number(ActionBarInfo.MINECRAFT.getFps()))
        );
        ss.set("time", () -> Value.string(new SimpleDateFormat(ActionBarInfo.localization.getLocalization("date.time")).format(System.currentTimeMillis())));
        // Player
        ss.set("player", new ValueMap()
                .set("name", () -> Value.string(ActionBarInfo.MINECRAFT.getUser().getName()))
                .set("health", () -> Value.string(ActionBarInfo.MINECRAFT.player != null ? Player.getHealth() : ""))
                .set("health_max", () -> Value.string(ActionBarInfo.MINECRAFT.player != null ? Player.getMaxHealth() : ""))
                .set("health_percent", () -> Value.string(ActionBarInfo.MINECRAFT.player != null ? Player.getPercentHealth() : ""))
                .set("armor", () -> Value.string(ActionBarInfo.MINECRAFT.player != null ? Player.getArmor() : ""))
                .set("direction", () -> Value.string(ActionBarInfo.MINECRAFT.player != null ? Player.getDirection(false) : ""))
                .set("direction_symbol", () -> Value.string(ActionBarInfo.MINECRAFT.player != null ? Player.getDirection(true) : ""))
                .set("hunger", () -> Value.number(ActionBarInfo.MINECRAFT.player != null ? ActionBarInfo.MINECRAFT.player.getFoodData().getFoodLevel() : 0))
                .set("pos", new ValueMap()
                        .set("x", () -> Value.string(ActionBarInfo.MINECRAFT.player != null ? Player.getX() : ""))
                        .set("y", () -> Value.string(ActionBarInfo.MINECRAFT.player != null ? Player.getY() : ""))
                        .set("z", () -> Value.string(ActionBarInfo.MINECRAFT.player != null ? Player.getZ() : ""))
                )

                .set("item", () -> Value.string(ActionBarInfo.MINECRAFT.player != null ? Player.getItemName() : ""))
                .set("item_count", () -> Value.number(ActionBarInfo.MINECRAFT.player != null ? Player.getItemCount() : 0))

                .set("xp", new ValueMap()
                        .set("level", () -> Value.number(ActionBarInfo.MINECRAFT.player != null ? ActionBarInfo.MINECRAFT.player.experienceLevel : 0))
                        .set("progress", () -> Value.number(ActionBarInfo.MINECRAFT.player != null ? ActionBarInfo.MINECRAFT.player.experienceProgress : 0))
                        .set("total", () -> Value.number(ActionBarInfo.MINECRAFT.player != null ? ActionBarInfo.MINECRAFT.player.totalExperience : 0))
                )
        );
        // World
        ss.set("world", new ValueMap()
                .set("name", () -> Value.string(ActionBarInfo.MINECRAFT.level != null ? World.getName() : ""))
                .set("time_type", () -> Value.string(ActionBarInfo.MINECRAFT.level != null ? World.getTimeType() : ""))
                .set("time", () -> Value.string(ActionBarInfo.MINECRAFT.level != null ? World.getTime() : ""))
                .set("difficulty", () -> Value.string(ActionBarInfo.MINECRAFT.level != null ? ActionBarInfo.MINECRAFT.level.getDifficulty().getDisplayName().getString() : ""))
        );
    }
    // Helpers

    public static Script compile(String source) {
        Parser.Result result = Parser.parse(source);

        if (result.hasErrors()) {
            for (Error error : result.errors) ActionBarInfo.log(error.message, Level.ERROR);
            return null;
        }

        return Compiler.compile(result);
    }

    public static Section runSection(Script script, StringBuilder sb) {
        try {
            return ss.run(script, sb);
        }
        catch (StarscriptError error) {
            ActionBarInfo.log(error.getLocalizedMessage(), Level.ERROR);
            return null;
        }
    }
    public static String run(Script script, StringBuilder sb) {
        try {
            Section section = runSection(script, sb);
            return section == null ? "" : section.toString();
        } catch (Exception e){
            ActionBarInfo.log(e.getLocalizedMessage(), Level.ERROR);
            return e.getLocalizedMessage();
        }
    }

    public static String run(Script script) {
        return run(script, new StringBuilder());
    }
}
