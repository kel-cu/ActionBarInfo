package ru.kelcuprum.abi.info;

import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import ru.kelcuprum.abi.ActionBarInfo;
import ru.kelcuprum.alinlib.config.Localization;

public class Player {
    public static String getItemName(){
        if(ActionBarInfo.MINECRAFT.player == null) return "-";
        ItemStack main_hand = ActionBarInfo.MINECRAFT.player.getItemInHand(InteractionHand.MAIN_HAND);
        String main_hand_item = main_hand.getItem().toString();
        if(main_hand_item.equals("air") && ActionBarInfo.config.getBoolean("VIEW_ITEM_OFF_HAND", false)){
            ItemStack off_hand = ActionBarInfo.MINECRAFT.player.getItemInHand(InteractionHand.OFF_HAND);
            String off_hand_item = off_hand.getItem().toString();
            if(off_hand_item.equals("air")) return null;
            else return off_hand.getHoverName().getString();
        } else {
            if(main_hand_item.equals("air")) return null;
            else return main_hand.getHoverName().getString();
        }
    }
    public static int getItemCount(){
        if(ActionBarInfo.MINECRAFT.player == null) return 0;
        ItemStack main_hand = ActionBarInfo.MINECRAFT.player.getItemInHand(InteractionHand.MAIN_HAND);
        String main_hand_item = main_hand.getItem().toString();
        if(main_hand_item.equals("air") && ActionBarInfo.config.getBoolean("VIEW_ITEM_OFF_HAND", false)){
            ItemStack off_hand = ActionBarInfo.MINECRAFT.player.getItemInHand(InteractionHand.OFF_HAND);
            String off_hand_item = off_hand.getItem().toString();
            if(off_hand_item.equals("air")) return 0;
            else return off_hand.getCount();
        } else {
            if(main_hand_item.equals("air")) return 0;
            else return main_hand.getCount();
        }
    }
    public static String getHealth(){
        if(ActionBarInfo.MINECRAFT.player == null) return "-";
        return ActionBarInfo.DF.format(ActionBarInfo.MINECRAFT.player.getHealth()/2);
    }
    public static String getMaxHealth(){
        if(ActionBarInfo.MINECRAFT.player == null) return "-";
        return ActionBarInfo.DF.format(ActionBarInfo.MINECRAFT.player.getAttributeValue(Attributes.MAX_HEALTH)/2);
    }
    public static String getPercentHealth(){
        if(ActionBarInfo.MINECRAFT.player == null) return "-";
        return ActionBarInfo.DF.format((ActionBarInfo.MINECRAFT.player.getHealth()*100)/ActionBarInfo.MINECRAFT.player.getMaxHealth());
    }
    public static String getArmor(){
        if(ActionBarInfo.MINECRAFT.player == null) return "-";
        return ActionBarInfo.DF.format(ActionBarInfo.MINECRAFT.player.getArmorValue()/2);
    }
    public static String getX(){
        if(ActionBarInfo.MINECRAFT.getCameraEntity() == null) return "-";
        return Localization.getRounding(ActionBarInfo.MINECRAFT.getCameraEntity().getX(), !ActionBarInfo.config.getBoolean("USE_EXTENDED_COORDINATES", false));
    }
    public static String getY(){
        if(ActionBarInfo.MINECRAFT.getCameraEntity() == null) return "-";
        return Localization.getRounding(ActionBarInfo.MINECRAFT.getCameraEntity().getY(), !ActionBarInfo.config.getBoolean("USE_EXTENDED_COORDINATES", false));
    }
    public static String getZ(){
        if(ActionBarInfo.MINECRAFT.getCameraEntity() == null) return "-";
        return Localization.getRounding(ActionBarInfo.MINECRAFT.getCameraEntity().getZ(), !ActionBarInfo.config.getBoolean("USE_EXTENDED_COORDINATES", false));
    }
    public static String getDirection(boolean oneSymbol){
        if(ActionBarInfo.MINECRAFT.player == null) return "-";
        Direction direction = ActionBarInfo.MINECRAFT.player.getDirection();
        return switch (direction) {
            case NORTH -> oneSymbol ? "N" : ActionBarInfo.localization.getLocalization("north", false, false);
            case SOUTH -> oneSymbol ? "S" : ActionBarInfo.localization.getLocalization("south", false, false);
            case WEST -> oneSymbol ? "W" : ActionBarInfo.localization.getLocalization("west", false, false);
            case EAST -> oneSymbol ? "E" : ActionBarInfo.localization.getLocalization("east", false, false);
            default -> oneSymbol ? "?" : ActionBarInfo.localization.getLocalization("unknown", false, false);
        };
    }
}
