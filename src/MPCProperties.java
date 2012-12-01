/**
 * Reads properties from file and makes them available with getters
 * 
 * @author nosefish, nosefish@gmx.net
 * 
 */
public class MPCProperties {
	private static int criticalPopulation = 250;
	private static double clusterRadiusSquared = 9.0;
	private static int clusterSize = 20;
	private static int interval = 1000;

	/**
	 * Reads properties from file
	 * 
	 * @param plugin
	 */
	public static void readProperties(Plugin plugin) {
		boolean fileChanged = false;
		PropertiesFile f = plugin.getPropertiesFile();

		if (f.keyExists("criticalPopulation")) {
			criticalPopulation = f.getInt("criticalPopulation");
		} else {
			f.setInt("criticalPopulation", criticalPopulation);
			fileChanged = true;
		}

		if (f.keyExists("clusterRadius")) {
			double r = f.getDouble("clusterRadius");
			clusterRadiusSquared = r * r;
		} else {
			f.setDouble("clusterRadius", Math.sqrt(clusterRadiusSquared));
			fileChanged = true;
		}

		if (f.keyExists("clusterSize")) {
			clusterSize = f.getInt("clusterSize");
		} else {
			f.setInt("clusterSize", clusterSize);
			fileChanged = true;
		}
		
		if (f.keyExists("interval")) {
			interval = f.getInt("interval");
		} else {
			f.setInt("interval", interval);
			fileChanged = true;
		}

		if (fileChanged)
			f.save();

	}

	/**
	 * @return the criticalPopulation
	 */
	public static int getCriticalPopulation() {
		return criticalPopulation;
	}

	/**
	 * @return the clusterRadiusSquared
	 */
	public static double getClusterRadiusSquared() {
		return clusterRadiusSquared;
	}

	/**
	 * @return maximum allowed number of mobs within the radius
	 */
	public static int getClusterSize() {
		return clusterSize;
	}

	/**
	 * @return the thread analyzes the LivingEntityList every <i>interval</i> milliseconds.
	 */
	public static int getInterval() {
		return interval;
	}


}
