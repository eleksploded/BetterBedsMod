package net.minecraft.command.arguments;

import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.state.IProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.CachedBlockInfo;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorld;

public class BlockStateInput implements Predicate<CachedBlockInfo> {
   private final BlockState field_197234_a;
   private final Set<IProperty<?>> properties;
   @Nullable
   private final CompoundNBT field_197236_c;

   public BlockStateInput(BlockState stateIn, Set<IProperty<?>> propertiesIn, @Nullable CompoundNBT nbtIn) {
      this.field_197234_a = stateIn;
      this.properties = propertiesIn;
      this.field_197236_c = nbtIn;
   }

   public BlockState getState() {
      return this.field_197234_a;
   }

   public boolean test(CachedBlockInfo p_test_1_) {
      BlockState blockstate = p_test_1_.getBlockState();
      if (blockstate.getBlock() != this.field_197234_a.getBlock()) {
         return false;
      } else {
         for(IProperty<?> iproperty : this.properties) {
            if (blockstate.get(iproperty) != this.field_197234_a.get(iproperty)) {
               return false;
            }
         }

         if (this.field_197236_c == null) {
            return true;
         } else {
            TileEntity tileentity = p_test_1_.getTileEntity();
            return tileentity != null && NBTUtil.areNBTEquals(this.field_197236_c, tileentity.write(new CompoundNBT()), true);
         }
      }
   }

   public boolean place(ServerWorld worldIn, BlockPos pos, int flags) {
      if (!worldIn.setBlockState(pos, this.field_197234_a, flags)) {
         return false;
      } else {
         if (this.field_197236_c != null) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity != null) {
               CompoundNBT compoundnbt = this.field_197236_c.copy();
               compoundnbt.putInt("x", pos.getX());
               compoundnbt.putInt("y", pos.getY());
               compoundnbt.putInt("z", pos.getZ());
               tileentity.read(compoundnbt);
            }
         }

         return true;
      }
   }
}