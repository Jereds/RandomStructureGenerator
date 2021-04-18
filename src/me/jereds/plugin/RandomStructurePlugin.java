package me.jereds.plugin;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.jereds.listeners.ChunkListener;

public class RandomStructurePlugin extends JavaPlugin implements Listener {
	
	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(new ChunkListener(), this);
	}
}
