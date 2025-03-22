package ru.kelcuprum.abi.modules.abstracts;

import net.minecraft.network.chat.Component;
import ru.kelcuprum.abi.ActionBarInfo;
import ru.kelcuprum.alinlib.config.Config;
import ru.kelcuprum.alinlib.gui.components.builder.AbstractBuilder;
import ru.kelcuprum.alinlib.gui.components.builder.button.ButtonBooleanBuilder;
import ru.kelcuprum.alinlib.gui.components.builder.editbox.EditBoxBuilder;
import ru.kelcuprum.alinlib.gui.components.builder.selector.SelectorBuilder;
import ru.kelcuprum.alinlib.gui.components.builder.slider.SliderBuilder;


public class Option {
    public Component title;
    public Config config;
    public String key;
    public String[] selector;
    public Object defaultValue;
    public Type type;
    public NumberType numberType;

    public Number max = 1;
    public Number min = 0;

    public Option(String key, Object defaultValue, Component title, Type type){
        this(key, defaultValue, title, type, ActionBarInfo.config);
    }
    public Option(String key, Object defaultValue, Component title, Type type, Config config){
        this(key, defaultValue, 0, 1, NumberType.INTEGER, title, null, type, config);
    }
    public Option(String key, Object defaultValue, Number max, Number min, NumberType numberType, Component title, Type type){
        this(key, defaultValue, max, min, numberType, title, type, ActionBarInfo.config);
    }
    public Option(String key, Object defaultValue, Number max, Number min, NumberType numberType, Component title, Type type, Config config){
        this(key, defaultValue, max, min, numberType, title, null, type, config);
    }
    public Option(String key, Object defaultValue, Number max, Number min, NumberType numberType, Component title, String[] selector, Type type, Config config){
        this.config = config;
        this.key = key;
        this.defaultValue = defaultValue;
        this.type = type;
        this.numberType = numberType;
        this.selector = selector;
        this.title = title;
        this.max = max;
        this.min = min;
    }

    public AbstractBuilder getBuilder(){
        return switch (type){
            case STRING -> {
                if(selector == null) yield new EditBoxBuilder(title).setValue((String) defaultValue).setConfig(config, key);
                else yield new SelectorBuilder(title).setList(selector).setValue((String) defaultValue).setConfig(config, key);
            }
            case NUMBER -> {
                if(selector == null) yield switch (numberType){
                    case LONG -> new SliderBuilder(title).setMin(min.longValue()).setMax(max.longValue()).setDefaultValue((long) defaultValue).setConfig(config, key);
                    case DOUBLE -> new SliderBuilder(title).setMin(min.doubleValue()).setMax(max.doubleValue()).setDefaultValue((double) defaultValue, false).setConfig(config, key);
                    case PERCENT -> new SliderBuilder(title).setMin(min.doubleValue()).setMax(max.doubleValue()).setDefaultValue((double) defaultValue, true).setConfig(config, key);
                    case FLOAT -> new SliderBuilder(title).setMin(min.floatValue()).setMax(max.floatValue()).setDefaultValue((float) defaultValue).setConfig(config, key);
                    default -> new SliderBuilder(title).setMin(min.intValue()).setMax(max.intValue()).setDefaultValue((int) defaultValue).setConfig(config, key);
                };
                else yield new SelectorBuilder(title).setList(selector).setValue((int) defaultValue).setConfig(config, key);
            }
            default -> new ButtonBooleanBuilder(title, (boolean) defaultValue).setConfig(config, key);
        };
    }

    public enum Type{
        STRING,
        NUMBER,
        BOOLEAN
    }
    public enum NumberType{
        INTEGER,
        LONG,
        DOUBLE,
        PERCENT,
        FLOAT,
    }
}
