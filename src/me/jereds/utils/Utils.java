package me.jereds.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.transform.AffineTransform;
import com.sk89q.worldedit.session.ClipboardHolder;

public class Utils {
	
	public static Block getRandomBlock(Chunk chunk) {
		return chunk.getBlock(new Random().nextInt(15), new Random().nextInt(15), new Random().nextInt(15));
	}
	
	public static void spawnSchem(File file, Location loc) {
		double x = loc.getX();
		double y = loc.getY();
		double z = loc.getZ();
		
		ClipboardFormat format = ClipboardFormats.findByFile(file);
		Clipboard clipboard = null;
		try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
		   clipboard = reader.read();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try (@SuppressWarnings("deprecation")
		EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(BukkitAdapter.adapt(loc.getWorld()), -1)) {
			
			int rotations[] = {0, 0, 90, -90, 180, -180, 270, -270, 360, -360};
			
			AffineTransform transform = new AffineTransform();
			transform.rotateX(rotations[new Random().nextInt(rotations.length)]);
			transform.rotateZ(rotations[new Random().nextInt(rotations.length)]);
			
			clipboard.paste(BukkitAdapter.adapt(loc.getWorld()), BlockVector3.at(x, y, z), true, transform);
		    Operation operation = new ClipboardHolder(clipboard)
		            .createPaste(editSession)
		            .to(BlockVector3.at(x, y, z))
		            .ignoreAirBlocks(true)
		            .build();
		    Operations.complete(operation);
		}
	}
	
	private static final String[] contains = {"ocean", "desert", "plains", "forest", "end", "mountain", "swamp", "jungle", "mushroom"};
	public static String getSimpleName(Biome biome) {
		String name = biome.name().toLowerCase();
		
		for(String contain : contains) {
			if(name.contains(contain))
				return contain;
		}
		
		if(name.contains("ice") || name.contains("snow"))
			return "cold";
		
		return "null";
	}
}
