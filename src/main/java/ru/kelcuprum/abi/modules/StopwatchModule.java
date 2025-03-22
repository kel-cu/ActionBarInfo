package ru.kelcuprum.abi.modules;

import net.minecraft.network.chat.Component;
import ru.kelcuprum.abi.ActionBarInfo;
import ru.kelcuprum.abi.modules.abstracts.AbstractModule;
import ru.kelcuprum.abi.modules.abstracts.Option;

public class StopwatchModule extends AbstractModule {
    public StopwatchModule() {
        super("stopwatch", "actionbarinfo", Component.translatable("abi.module.stopwatch"));
        options.add(new Option("ENABLE.STOPWATCH", true, Component.translatable("abi.config.enable.stopwatch"), Option.Type.BOOLEAN));
    }

    @Override
    public Component getMessage() {
        return Component.literal(getStopwatch());
    }

    public static int stateStopwatch = 0;
    public static long startStopwatch = 0;
    public static long stopStopwatch = 0;

    public static String getStopwatch() {
        if (stateStopwatch == 0) startStopwatch = stopStopwatch = 0;
        else if (stateStopwatch == 1) stopStopwatch = System.currentTimeMillis();

        long milliseconds = stopStopwatch - startStopwatch;

        int ms = (int) milliseconds % 1000;
        int seconds = (int) (milliseconds / 1000) % 60;
        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
        int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);
        return String.format("%02d:%02d:%02d,%03d", hours, minutes, seconds, ms);
    }

    @Override
    public boolean isEnabled() {
        return stateStopwatch != 0;
    }
}
