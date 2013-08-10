package com.domsplace.Listeners;

import com.domsplace.Events.VillageGriefEvent;
import com.domsplace.Events.VillageMayorDeathEvent;
import com.domsplace.Objects.Village;
import com.domsplace.Objects.VillageGriefType;
import com.domsplace.Utils.VillageVillagesUtils;
import com.domsplace.VillageBase;
import com.domsplace.VillagesPlugin;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class VillageCustomEventListener extends VillageBase implements Listener {
    
    private VillagesPlugin plugin;
    
    public VillageCustomEventListener(VillagesPlugin plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler(ignoreCancelled=true)
    public void onBlockBreak(BlockBreakEvent e) {
        List<Block> blocks = new ArrayList<Block>();
        VillageGriefEvent event = new VillageGriefEvent(e.getPlayer(), e.getBlock(), blocks, VillageGriefType.BREAK);
        Bukkit.getServer().getPluginManager().callEvent(event);
        e.setCancelled(event.isCancelled());
    }
    
    @EventHandler(ignoreCancelled=true)
    public void onBlockPlace(BlockPlaceEvent e) {
        List<Block> blocks = new ArrayList<Block>();
        VillageGriefEvent event = new VillageGriefEvent(e.getPlayer(), e.getBlock(), blocks, VillageGriefType.PLACE);
        Bukkit.getServer().getPluginManager().callEvent(event);
        e.setCancelled(event.isCancelled());
    }
    
    @EventHandler(ignoreCancelled=true)
    public void onPlayerInteract(PlayerInteractEvent e) {
        List<Block> blocks = new ArrayList<Block>();
        VillageGriefEvent event = new VillageGriefEvent(e.getPlayer(), e.getClickedBlock(), blocks, VillageGriefType.INTERACT);
        Bukkit.getServer().getPluginManager().callEvent(event);
        e.setCancelled(event.isCancelled());
    }
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Village v = VillageVillagesUtils.getPlayerVillage(e.getEntity());
        if(v == null) {
            return;
        }
        
        if(!v.isMayor(e.getEntity())) {
            return;
        }
        
        VillageMayorDeathEvent event = new VillageMayorDeathEvent(e.getEntity(), v);
        Bukkit.getServer().getPluginManager().callEvent(event);
    }
}