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
import net.minecraft.client.Minecraft;

import org.apache.logging.log4j.Level;
import ru.kelcuprum.abi.ActionBarInfo;
import ru.kelcuprum.abi.info.Player;
import ru.kelcuprum.abi.info.World;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class StarScript {
    public static Starscript ss = new Starscript();
    static Minecraft mc = Minecraft.getInstance();

    public static void init() {
        StandardLib.init(ss);

        // General
        ss.set("minecraft", new ValueMap()
                .set("version", SharedConstants.getCurrentVersion().getName())
                .set("loader", Minecraft.getInstance().getVersionType())
        );
        ss.set("fps", () -> Value.number(mc.getFps()));
        ss.set("time", () -> Value.string(LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))));
        // Player
        ss.set("player", new ValueMap()
                .set("name", () -> Value.string(mc.getUser().getName()))
                .set("health", () -> Value.string(mc.player != null ? Player.getHealth() : ""))
                .set("health_max", () -> Value.string(mc.player != null ? Player.getMaxHealth() : ""))
                .set("health_percent", () -> Value.string(mc.player != null ? Player.getPercentHealth() : ""))
                .set("armor", () -> Value.string(mc.player != null ? Player.getArmor() : ""))
                .set("direction", () -> Value.string(mc.player != null ? Player.getDirection(false) : ""))
                .set("direction_symbol", () -> Value.string(mc.player != null ? Player.getDirection(true) : ""))
                .set("hunger", () -> Value.number(mc.player != null ? mc.player.getFoodData().getFoodLevel() : 0))
                .set("pos", new ValueMap()
                        .set("x", () -> Value.string(mc.player != null ? Player.getX() : ""))
                        .set("y", () -> Value.string(mc.player != null ? Player.getY() : ""))
                        .set("z", () -> Value.string(mc.player != null ? Player.getZ() : ""))
                )

                .set("item", () -> Value.string(mc.player != null ? Player.getItemName() : ""))
                .set("item_count", () -> Value.number(mc.player != null ? Player.getItemCount() : 0))

                .set("xp", new ValueMap()
                        .set("level", () -> Value.number(mc.player != null ? mc.player.experienceLevel : 0))
                        .set("progress", () -> Value.number(mc.player != null ? mc.player.experienceProgress : 0))
                        .set("total", () -> Value.number(mc.player != null ? mc.player.totalExperience : 0))
                )
        );
        // World
        ss.set("world", new ValueMap()
                .set("name", () -> Value.string(mc.level != null ? World.getName() : ""))
                .set("time_type", () -> Value.string(mc.level != null ? World.getTimeType() : ""))
                .set("time", () -> Value.string(mc.level != null ? World.getTime() : ""))
                .set("difficulty", () -> Value.string(mc.level != null ? mc.level.getDifficulty().getDisplayName().getString() : ""))
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
            error.printStackTrace();
            return null;
        }
    }
    public static String run(Script script, StringBuilder sb) {
        Section section = runSection(script, sb);
        return section != null ? section.toString() : null;
    }

    public static Section runSection(Script script) {
        return runSection(script, new StringBuilder());
    }
    public static String run(Script script) {
        return run(script, new StringBuilder());
    }
}
