package ru.kelcuprum.abi.modules;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import ru.kelcuprum.abi.ActionBarInfo;
import ru.kelcuprum.abi.modules.abstracts.AbstractModule;

import java.util.ArrayList;
import java.util.HashMap;

public class ModulesManager {
    public static HashMap<String, ArrayList<String>> modsModules = new HashMap<>();
    public static HashMap<String, AbstractModule> modules = new HashMap<>();

    public static void registerModule(AbstractModule module){
        if(modules.containsKey(module.id)) ActionBarInfo.LOG.error("Регистрация модуля %s от мода %s была отменена, так как уже существует модуль под этим ID");
        else {
            ArrayList<String> list = modsModules.getOrDefault(module.mod_id, new ArrayList<>());
            list.add(module.id);
            modsModules.put(module.mod_id, list);
            modules.put(module.id, module);
        }
    }

    public static void registerDefaultModules(){
        registerModule(new DefaultModule());
        registerModule(new LegacyMomentModule());
        registerModule(new StopwatchModule());
        registerModule(new DirectionModule());
    }

    public static String getSymbol(){
        return ActionBarInfo.config.getNumber("TYPE_RENDER", 1).intValue() == 0 ? " " : "\\n";
    }

    public static Component getText(){
        MutableComponent component = Component.empty();
        boolean first = true;
        for(AbstractModule module : modules.values()){
            if(module.isEnabled() && module.isEnabledByUser()){
                if(!first) component.append(getSymbol());
                else first = false;
                component.append(module.getMessage());
            }
        }
        return component;
    }
}
