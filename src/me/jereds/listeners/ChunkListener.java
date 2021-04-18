package me.jereds.listeners;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

import me.jereds.utils.Utils;

public class ChunkListener implements Listener {

	@EventHandler
	public void onChunkGen(ChunkLoadEvent event) {
		if(!event.isNewChunk())
			return;
		
		if(new Random().nextInt(1000) <= 999) //.1% chance
			return;
		
		Block randomBlock = Utils.getRandomBlock(event.getChunk());
		Biome biome = randomBlock.getBiome();
		
		List<String> names = new ArrayList<>();
		
		File file = new File("plugins/WorldEdit/schematics/");
		File[] files = file.listFiles();
		Arrays.stream(files)
			.filter(schemFile -> schemFile.getName().toLowerCase().startsWith(Utils.getSimpleName(biome)))
			.forEach(schemFile -> names.add(schemFile.getName()));
		
		if(names.isEmpty())
			return;
		
		String schem = names.get(new Random().nextInt(names.size()));
		
		Utils.spawnSchem(new File("plugins/WorldEdit/schematics/" + schem +".schem"), randomBlock.getLocation());
		
	}
}
