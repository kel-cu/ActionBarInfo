package ru.kelcuprum.abi.info.block;

import com.google.common.collect.UnmodifiableIterator;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import ru.kelcuprum.abi.ActionBarInfo;

import java.util.Map;

public class NoteBlock {
    public static boolean isNoteBlock = false;
    public static String powered = "";
    public static String instrument = "";
    public static String note = "";
    public static HitResult block = null;

    private static void clear(){
        note = "";
        powered = "";
        isNoteBlock = false;
        instrument = "";
        block = null;
    }
    public static String getNote(){
        return note;
    }
    public static String getInstrument(){
        return instrument;
    }
    public static String getPowered(){
        return powered;
    }
    public static boolean getIsNoteBlock(){
        return isNoteBlock;
    }
    @SuppressWarnings("rawtypes")
    public static void update(){
        if(ActionBarInfo.MINECRAFT.getCameraEntity() == null){ clear(); return;}
        if(ActionBarInfo.MINECRAFT.player == null){ clear(); return;}
        block = ActionBarInfo.MINECRAFT.getCameraEntity().pick(20.0, 0.0F, false);
        BlockPos blockPos;
        UnmodifiableIterator var12;
        Map.Entry entry;
        if (block.getType() == HitResult.Type.BLOCK) {
            blockPos = ((BlockHitResult)block).getBlockPos();
            BlockState blockState = ActionBarInfo.MINECRAFT.level.getBlockState(blockPos);
            if(!String.valueOf(BuiltInRegistries.BLOCK.getKey(blockState.getBlock())).equals("minecraft:note_block")){ clear(); return;}
            isNoteBlock = true;
            var12 = blockState.getValues().entrySet().iterator();
            while(var12.hasNext()) {
                entry = (Map.Entry)var12.next();
                Property<?> property = (Property)entry.getKey();
                Comparable<?> comparable = (Comparable)entry.getValue();
                String string = Util.getPropertyName(property, comparable);
//                ActionBarInfo.log(property.getName() + " " + string);
                if(property.getName().equals("note")) note = string;
                else if(property.getName().equals("instrument")) instrument = string;
                else if(property.getName().equals("powered")) powered = string;
            }
        } else clear();
    }
}
