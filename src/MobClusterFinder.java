import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Finds groups of mobs that are close together and kills them if they meet
 * certain criteria
 * 
 * @author nosefish, nosefish@gmx.net
 * 
 */
public class MobClusterFinder extends Thread {

	private World world;

	// temporarily stores all LivingEnitites for the duration of the culling
	public LivingEntity[] population;

	// all mobs in here will be killed by the MobAssassin
	public static ConcurrentLinkedQueue<LivingEntity> deathRow = new ConcurrentLinkedQueue<LivingEntity>();

	// synchronization stuff
	public AtomicBoolean shouldStop = new AtomicBoolean(false);
	public CountDownLatch latch;

	public MobClusterFinder(World w) {
		this.world = w;
	}

	/**
	 * Does all the hard work.
	 * 
	 * @param population
	 */
	private void cullMobClusters(LivingEntity[] population) {
		// Count all nearby LivingEntitys of every LivingEntity in this world
		int[] counter = new int[population.length];
		for (int i = 0; i < population.length - 1; i++) {
			if (population[i] instanceof Player)
				continue;
			for (int k = i + 1; k < population.length; k++) {
				if (population[k] instanceof Player)
					continue;
				if (population[i].getWorld().getType() == population[k].getWorld().getType()
						&& MPCMath.distanceSquared(population[i], population[k]) < MPCProperties.getClusterRadiusSquared()) {
					counter[i]++;
					counter[k]++;
				}
			}
		}
		// sentence all mobs in crowded places to death
		for (int i = 0; i < counter.length; i++) {
			if (counter[i] > MPCProperties.getClusterSize() && population[i] != null) {
				MobClusterFinder.deathRow.add(population[i]);
			}
		}
	}

	@Override
	public void run() {
		while (!this.shouldStop.get()) {
			long startTime = new Date().getTime();

			// get list of living entities as an array
			population = new LivingEntity[1];
			this.latch = new CountDownLatch(1);
			etc.getServer().addToServerQueue(
					new LivingEntityArrayGetter(this, this.world));
			try {
				this.latch.await();
			} catch (InterruptedException e1) {
				// ignore interruptions
			}

			// if there are too many mobs in this world,
			// find mob clusters and send them to deathRow
			if (population != null && population.length > MPCProperties.getCriticalPopulation()) {
				cullMobClusters(population);
			}
			// don't hold on to references longer than necessary
			population = null;

			// send in the killer
			etc.getServer().addToServerQueue(
					new MobAssassin(MobClusterFinder.deathRow));

			// wait until one period is over
			long endTime = new Date().getTime();
			long sleepyTime = MPCProperties.getInterval() - (endTime - startTime);
			if (sleepyTime >0){
				try {
					Thread.sleep(MPCProperties.getInterval() - (endTime - startTime));
				} catch (InterruptedException e) {
					// ignore interruptions
				}
			}
		}
	}

}
