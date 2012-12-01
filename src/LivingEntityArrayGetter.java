/**
 * Retrieves the LivingEntityList from the server and stores it in the
 * population array of a MobClusterFinder.
 * 
 * @author nosefish, nosefish@gmx.net
 * 
 */
public class LivingEntityArrayGetter implements Runnable {
	private MobClusterFinder mcf;
	private World world;

	/**
	 * 
	 * @param mcf
	 *            The MobCusterFinder which is instantiating this
	 * @param w
	 *            The Minecraft world to operate in.
	 */
	public LivingEntityArrayGetter(MobClusterFinder mcf, World w) {
		this.mcf = mcf;
		this.world = w;
	}

	public void run() {
		synchronized (mcf.population) {
			mcf.population = this.world.getLivingEntityList().toArray(
					mcf.population);
			mcf.latch.countDown();
		}
	}

}
