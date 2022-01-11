package com.github.kacperleague9.spray;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.plugin.java.JavaPlugin;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static org.bukkit.entity.EntityType.ITEM_FRAME;

public final class Spray extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new SwapEvent(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}


class SwapEvent implements Listener {

    @EventHandler
    public void onF(PlayerSwapHandItemsEvent e) throws IOException {
		
        Player p = e.getPlayer();

        Entity targetEntity = Objects.requireNonNull(p.getTargetEntity(10)); // to be cahnged, possible spigot incompat

        EntityType targetEntityType = targetEntity.getType();

        if (targetEntityType == ITEM_FRAME) {
			
            e.setCancelled(true);

            PaginatedGui gui = Gui.paginated()
                    .title(Component.text("SPRAY GUI"))
                    .rows(4)
                    .pageSize(26)
                    .create();

            gui.setItem(4, 1, ItemBuilder.from(Material.PAPER).setName("Previous").asGuiItem(event -> gui.previous()));
            gui.setItem(4, 9, ItemBuilder.from(Material.PAPER).setName("Next").asGuiItem(event -> gui.next()));
            BufferedImage catImage = ImageIO.read(new File("/cat.png")); // We will change dat
            GuiItem guiItem = ItemBuilder.from(Material.STONE).asGuiItem(event -> {
                ItemFrame itemFrame = (ItemFrame) targetEntity;
                itemFrame.setItem();
            });
            gui.addItem(guiItem);
			
        }
		
    }
	
}

