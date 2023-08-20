package ru.kelcuprum.abi.info;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import ru.kelcuprum.abi.ActionBarInfo;
import ru.kelcuprum.abi.config.Localization;
import ru.kelcuprum.abi.config.UserConfig;

public class Player {
    public static String getItemName(){
        Minecraft CLIENT = Minecraft.getInstance();
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
        Minecraft CLIENT = Minecraft.getInstance();
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
        Minecraft CLIENT = Minecraft.getInstance();
        return ActionBarInfo.DF.format(CLIENT.player.getHealth()/2);
    }
    public static String getMaxHealth(){
        Minecraft CLIENT = Minecraft.getInstance();
        return ActionBarInfo.DF.format(CLIENT.player.getMaxHealth()/2);
    }
    public static String getPercentHealth(){
        Minecraft CLIENT = Minecraft.getInstance();
        return ActionBarInfo.DF.format((CLIENT.player.getHealth()*100)/CLIENT.player.getMaxHealth());
    }
    public static String getArmor(){
        Minecraft CLIENT = Minecraft.getInstance();
        return ActionBarInfo.DF.format(CLIENT.player.getArmorValue()/2);
    }
    public static String getX(){
        Minecraft CLIENT = Minecraft.getInstance();
        return ActionBarInfo.DF.format(CLIENT.player.position().x);
    }
    public static String getY(){
        Minecraft CLIENT = Minecraft.getInstance();
        return ActionBarInfo.DF.format(CLIENT.player.position().y);
    }
    public static String getZ(){
        Minecraft CLIENT = Minecraft.getInstance();
        return ActionBarInfo.DF.format(CLIENT.player.position().z);
    }
    public static String getDirection(boolean oneSymbol){
        Minecraft CLIENT = Minecraft.getInstance();
        Direction direction = CLIENT.player.getDirection();
        String string2;
        switch (direction) {
            case NORTH:
                string2 = oneSymbol ? "N" : Localization.getLocalization("north", false);
                break;
            case SOUTH:
                string2 = oneSymbol ? "S" : Localization.getLocalization("south", false);
                break;
            case WEST:
                string2 = oneSymbol ? "W" : Localization.getLocalization("west", false);
                break;
            case EAST:
                string2 = oneSymbol ? "E" : Localization.getLocalization("east", false);
                break;
            default:
                string2 = oneSymbol ? "?" : Localization.getLocalization("unknown", false);
        }
        return string2;
//        return ActionBarInfo.DF.format(CLIENT.player.position().z);
    }
}
