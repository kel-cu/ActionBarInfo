package ru.kelcuprum.abi.info;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import ru.kelcuprum.abi.ActionBarInfo;
import ru.kelcuprum.abi.localization.Localization;
import ru.kelcuprum.abi.config.UserConfig;

public class Player {
    static Minecraft CLIENT = Minecraft.getInstance();
    public static String getItemName(){
        ItemStack main_hand = CLIENT.player.getItemInHand(InteractionHand.MAIN_HAND);
        String main_hand_item = main_hand.getItem().toString();
        if(main_hand_item.equals("air") && UserConfig.VIEW_ITEM_OFF_HAND){
            ItemStack off_hand = CLIENT.player.getItemInHand(InteractionHand.OFF_HAND);
            String off_hand_item = off_hand.getItem().toString();
            if(off_hand_item.equals("air") || off_hand.getHoverName() == null) return null;
            else return off_hand.getHoverName().getString();
        } else {
            if(main_hand_item.equals("air") || main_hand.getHoverName() == null) return null;
            else return main_hand.getHoverName().getString();
        }
    }
    public static int getItemCount(){
        ItemStack main_hand = CLIENT.player.getItemInHand(InteractionHand.MAIN_HAND);
        String main_hand_item = main_hand.getItem().toString();
        if(main_hand_item.equals("air") && UserConfig.VIEW_ITEM_OFF_HAND){
            ItemStack off_hand = CLIENT.player.getItemInHand(InteractionHand.OFF_HAND);
            String off_hand_item = off_hand.getItem().toString();
            if(off_hand_item.equals("air") || off_hand.getHoverName() == null) return 0;
            else return off_hand.getCount();
        } else {
            if(main_hand_item.equals("air") || main_hand.getHoverName() == null) return 0;
            else return main_hand.getCount();
        }
    }
    public static String getHealth(){
        return ActionBarInfo.DF.format(CLIENT.player.getHealth()/2);
    }
    public static String getMaxHealth(){
        return ActionBarInfo.DF.format(CLIENT.player.getMaxHealth()/2);
    }
    public static String getPercentHealth(){
        return ActionBarInfo.DF.format((CLIENT.player.getHealth()*100)/CLIENT.player.getMaxHealth());
    }
    public static String getArmor(){
        return ActionBarInfo.DF.format(CLIENT.player.getArmorValue()/2);
    }
    public static String getX(){
        return Localization.getRounding(CLIENT.getCameraEntity().getX());
    }
    public static String getY(){
        return Localization.getRounding(CLIENT.getCameraEntity().getY());
    }
    public static String getZ(){
        return Localization.getRounding(CLIENT.getCameraEntity().getZ());
    }
    public static String getDirection(boolean oneSymbol){
        Direction direction = CLIENT.player.getDirection();
        return switch (direction) {
            case NORTH -> oneSymbol ? "N" : Localization.getLocalization("north", false);
            case SOUTH -> oneSymbol ? "S" : Localization.getLocalization("south", false);
            case WEST -> oneSymbol ? "W" : Localization.getLocalization("west", false);
            case EAST -> oneSymbol ? "E" : Localization.getLocalization("east", false);
            default -> oneSymbol ? "?" : Localization.getLocalization("unknown", false);
        };
    }
}
