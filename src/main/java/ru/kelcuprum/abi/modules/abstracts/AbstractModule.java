package ru.kelcuprum.abi.modules.abstracts;

import net.minecraft.network.chat.Component;
import ru.kelcuprum.abi.ActionBarInfo;

import java.util.ArrayList;


public abstract class AbstractModule {
    public String id;
    public Component name;
    public String mod_id;
    public boolean enabledByDefault;
    public ArrayList<Option> options = new ArrayList<>();

    public AbstractModule(String id, String mod_id, String name){
        this(id, mod_id, name, true);
    }
    public AbstractModule(String id, String mod_id, Component name){
        this(id, mod_id, name, true);
    }

    public AbstractModule(String id, String mod_id, String name, boolean enabledByDefault){
        this(id, mod_id, Component.literal(name), enabledByDefault);
    }
    public AbstractModule(String id, String mod_id, Component name, boolean enabledByDefault){
        this.id = id;
        this.mod_id = mod_id;
        this.name = name;
        this.enabledByDefault = enabledByDefault;
    }

    public abstract Component getMessage();

    public abstract boolean isEnabled();

    public boolean isEnabledByUser(){
        return ActionBarInfo.config.getBoolean(String.format("module.%s.%s", mod_id, id), enabledByDefault);
    }


}
