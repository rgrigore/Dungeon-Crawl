package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;
import com.codecool.dungeoncrawl.logic.actors.Player;

public abstract class Item implements Drawable {
   private Cell cell;
   private ItemType type;

   public Item(Cell cell) {
      if (cell != null) {
         cell.setItem(this);
         this.cell = cell;
      }
   }

   public void execute(Player player) {
      cell.setItem(null);
      cell.setType(CellType.FLOOR);
      cell = null;
   }

   public Cell getCell() {
      return cell;
   }

   protected void setType(ItemType type) {
      this.type = type;
   }

   public ItemType getType() {
      return type;
   }

   public String getName() {
      return type.getName();
   }

   @Override
   public String getTileName() {
      return type.getTileName();
   }

   @Override
   public char getSymbol() {
      return type.getSymbol();
   }
}
