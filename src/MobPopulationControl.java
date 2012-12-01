import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Kills all mobs that are clustered together in a small space.
 * 
 * @author nosefish, nosefish@gmx.net
 * 
 */
public class MobPopulationControl extends Plugin {
	private static final Logger logger = Logger
			.getLogger("Minecraft.MobPopulationControl");
	private MobClusterFinder mcf;

	@Override
	public void disable() {
		logger.log(Level.INFO, "Waiting for threads to finish...");
		mcf.shouldStop.set(true);
		try {
			mcf.join();
		} catch (InterruptedException e) {
			// e.printStackTrace();
		}
		MobClusterFinder.deathRow.clear();
		logger.log(Level.INFO, "MobPopulationControl disabled.");
	}

	@Override
	public void enable() {
		logger.log(Level.INFO, "MobPopulationControl enabled.");
	}
	
	@Override
	public void initialize(){
		MPCProperties.readProperties(this);
		// TODO multiworld support
		mcf = new MobClusterFinder(etc.getServer().getDefaultWorld());
		mcf.start();
	}

}
