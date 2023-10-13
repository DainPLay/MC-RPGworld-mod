package net.dainplay.rpgworldmod.util;


import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.IFuelHandler;
public class FuelHandler implements IFuelHandler

{

    @Override

    public int getBurnTime(ItemStack par1Fuel)

    {

        if (par1Fuel.itemID == YOURBLOCKUNLOCALIZEDNAME.blockID)

        {

            return BURNINGTIME;

        }

        else

        {

            return 0;

        }

    }

}