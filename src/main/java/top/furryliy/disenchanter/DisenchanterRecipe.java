package top.furryliy.disenchanter;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import java.util.ArrayList;

public class DisenchanterRecipe extends SpecialCraftingRecipe {
    public DisenchanterRecipe(Identifier id) {
        super(id);
    }

    private int bookSlot = 0;
    private ItemStack tool = ItemStack.EMPTY;

    @Override
    public boolean matches(CraftingInventory inventory, World world) {
        ArrayList<ItemStack> list = new ArrayList<>();
        for (int i = 0; i < inventory.size(); ++i){
            ItemStack stack = inventory.getStack(i);
            if (stack != ItemStack.EMPTY){
                if (stack.getItem() == Main.DISENCHANTER_CORE){
                    list.add(stack);
                }
                else if (stack.getItem() == Items.BOOK){
                    list.add(stack);
                }
                else if (stack.hasEnchantments()){
                    list.add(stack);
                }
            }
        }
        return list.size() == 3;
    }

    @Override
    public ItemStack craft(CraftingInventory inventory) {
        boolean hasCore = false;
        boolean hasBook = false;
        boolean hasTool = false;
        int count = 0;
        for (int i = 0; i < inventory.size(); i++){
            ItemStack stack = inventory.getStack(i);
            if (stack != ItemStack.EMPTY){
                count++;
                if (stack.getItem() == Main.DISENCHANTER_CORE){
                    hasCore = true;
                }
                else if (stack.getItem() == Items.BOOK){
                    hasBook = true;
                    bookSlot = i;
                }
                else if (stack.hasEnchantments()){
                    hasTool = true;
                    tool = stack;
                }
            }
        }

        if (hasBook && hasCore && hasTool && count==3){
            return tool.getItem().getDefaultStack();
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean fits(int width, int height) {
        return width*height >= 2;
    }

    @Override
    public DefaultedList<ItemStack> getRemainder(CraftingInventory inventory) {
        DefaultedList<ItemStack> list = DefaultedList.ofSize(9, ItemStack.EMPTY);
        ItemStack book = Items.ENCHANTED_BOOK.getDefaultStack();
        NbtCompound nbt = new NbtCompound();
        NbtList n_list = tool.getEnchantments();
        nbt.put(EnchantedBookItem.STORED_ENCHANTMENTS_KEY, n_list);
        book.setNbt(nbt);
        list.set(bookSlot, book);
        return list;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Main.SERIALIZER_DE;
    }
}
