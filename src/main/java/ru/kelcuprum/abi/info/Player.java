package ru.kelcuprum.abi.info;

import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import ru.kelcuprum.abi.ActionBarInfo;
import ru.kelcuprum.alinlib.config.Localization;

public class Player {
    public static String getItemName(){
        ItemStack main_hand = ActionBarInfo.MINECRAFT.player.getItemInHand(InteractionHand.MAIN_HAND);
        String main_hand_item = main_hand.getItem().toString();
        if(main_hand_item.equals("air") && ActionBarInfo.config.getBoolean("VIEW_ITEM_OFF_HAND", false)){
            ItemStack off_hand = ActionBarInfo.MINECRAFT.player.getItemInHand(InteractionHand.OFF_HAND);
            String off_hand_item = off_hand.getItem().toString();
            if(off_hand_item.equals("air") || off_hand.getHoverName() == null) return null;
            else return off_hand.getHoverName().getString();
        } else {
            if(main_hand_item.equals("air") || main_hand.getHoverName() == null) return null;
            else return main_hand.getHoverName().getString();
        }
    }
    public static int getItemCount(){
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
        return ActionBarInfo.DF.format(ActionBarInfo.MINECRAFT.player.getHealth()/2);
    }
    public static String getMaxHealth(){
        return ActionBarInfo.DF.format(ActionBarInfo.MINECRAFT.player.getMaxHealth()/2);
    }
    public static String getPercentHealth(){
        return ActionBarInfo.DF.format((ActionBarInfo.MINECRAFT.player.getHealth()*100)/ActionBarInfo.MINECRAFT.player.getMaxHealth());
    }
    public static String getArmor(){
        return ActionBarInfo.DF.format(ActionBarInfo.MINECRAFT.player.getArmorValue()/2);
    }
    public static String getX(){
        return Localization.getRounding(ActionBarInfo.MINECRAFT.getCameraEntity().getX(), !ActionBarInfo.config.getBoolean("USE_EXTENDED_COORDINATES", false));
    }
    public static String getY(){
        return Localization.getRounding(ActionBarInfo.MINECRAFT.getCameraEntity().getY(), !ActionBarInfo.config.getBoolean("USE_EXTENDED_COORDINATES", false));
    }
    public static String getZ(){
        return Localization.getRounding(ActionBarInfo.MINECRAFT.getCameraEntity().getZ(), !ActionBarInfo.config.getBoolean("USE_EXTENDED_COORDINATES", false));
    }
    public static String getDirection(boolean oneSymbol){
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
