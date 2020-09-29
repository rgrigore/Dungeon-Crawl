package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;
import com.codecool.dungeoncrawl.logic.actors.Player;

public abstract class Item implements Drawable {
   private Cell cell;

   public Item(Cell cell) {
      cell.setItem(this);
      this.cell = cell;
   }

   public void execute(Player player) {
      cell.setItem(null);
      cell.setType(CellType.FLOOR);
      cell = null;
   }
}
