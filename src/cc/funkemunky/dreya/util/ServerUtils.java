package cc.funkemunky.dreya.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;

public class ServerUtils {

	private static final String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
	public static final Class<?> EntityPlayer = ServerUtils.getNMSClass("EntityPlayer");
	public static final Class<?> Entity = ServerUtils.getNMSClass("Entity");
	private static final Class<?> CraftPlayer = ServerUtils.getCBClass("entity.CraftPlayer");
	private static final Class<?> CraftEntity = ServerUtils.getCBClass("entity.CraftEntity");
	private static final Class<?> CraftWorld = ServerUtils.getCBClass("CraftWorld");
	private static final Class<?> World = ServerUtils.getNMSClass("World");
	private static String spigot_id = "%%__USER__%%";
	private static final Method getCubes = ServerUtils.getMethod(World, "a", ServerUtils.getNMSClass("AxisAlignedBB"));

	public static Object getEntityPlayer(Player player) {
		return ServerUtils.getMethodValue(ServerUtils.getMethod(CraftPlayer, "getHandle"), player);
	}

	public static Entity getEntity(Player player, Object packet) {
		Object playerWorld = ServerUtils.getMethodValue(ServerUtils.getMethod(ServerUtils.getEntityPlayer(player).getClass(), "getWorld"), packet);
		int entityId = (int) ServerUtils.getMethodValue(ServerUtils.getMethod(playerWorld.getClass(), "getId"), playerWorld);

		for(org.bukkit.entity.Entity entity2 : player.getWorld().getEntities()) {
			if(entity2.getEntityId() == entityId) {
				return entity2;
			}
		}
		return null;
	}

	public static Object getTeleportBox(Player player) {
		Object box = getBoundingBox(player);
		return getMethodValue(getMethod(box.getClass(), "grow", double.class, double.class, double.class), box, 0.1, 0, 0.1);
	}

	public static Object getPhaseBox(Player player) {
		Object box = getBoundingBox(player);
		return getMethodValue(getMethod(box.getClass(), "shrink", double.class, double.class, double.class), box, 0.01, 0.01, 0.01);
	}

	public static Object getBoundingBox(Player player) {
		return ServerUtils.isBukkitVerison("1_7") ? ServerUtils.getFieldValue(ServerUtils.getFieldByName(Entity, "boundingBox"), getEntityPlayer(player)) : ServerUtils.getMethodValue(ServerUtils.getMethod(EntityPlayer, "getBoundingBox"), getEntityPlayer(player));
	}

	public static boolean isBukkitVerison(String version) {
		String bukkit = Bukkit.getServer().getClass().getPackage().getName().substring(23);
		
		return bukkit.contains(version);
	}

	public static boolean isNewVersion() {
		return isBukkitVerison("1_9") || isBukkitVerison("1_1");
	}

	public static Class<?> getCBClass(String string) {
		return getClass("org.bukkit.craftbukkit." + version + "." + string);
	}

	public static Class<?> getClass(String string) {
		try {
			return Class.forName(string);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked") public static Enum<?> getEnum(Class<?> clazz, String enumName) {
		return Enum.valueOf((Class<Enum>) clazz, enumName);
	}

	public static Field getFieldByName(Class<?> clazz, String fieldName) {
		try {
			Field field = clazz.getDeclaredField(fieldName);
			field.setAccessible(true);
			return field;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean inBlock(Player player, Object axisAlignedBB) {
		Object world = ServerUtils.getMethodValue(ServerUtils.getMethod(CraftWorld, "getHandle"), player.getWorld());
		return ((Collection<?>)ServerUtils.getMethodValue(getCubes, world, axisAlignedBB)).size() > 0;
	}

	public static Collection<?> getCollidingBlocks(Player player, Object axisAlignedBB) {
		Object world = ServerUtils.getMethodValue(ServerUtils.getMethod(CraftWorld, "getHandle"), player.getWorld());
		return ((Collection<?>)ServerUtils.getMethodValue(getCubes, world, axisAlignedBB));
	}

	public static Field getFirstFieldByType(Class<?> clazz, Class<?> type) {
		try {
			for (Field field : clazz.getDeclaredFields()) {
				if (field.getType().equals(type)) {
					field.setAccessible(true);
					return field;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Method getMethod(Class<?> clazz, String methodName, Class<?>... args) {
		try {
			Method method = clazz.getMethod(methodName, args);
			method.setAccessible(true);
			return method;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Object getMethodValue(Method method, Object object, Object... args) {
		try {
			return method.invoke(object, args);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Object getFieldValue(Field field, Object object) {
		try {
			field.setAccessible(true);
			return field.get(object);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Class<?> getNMSClass(String string) {
		return getClass("net.minecraft.server." + version + "." + string);
	}
}
