import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Kills all LivingEntities in deathRow when added to the server queue.
 * 
 * @author nosefish, nosefish@gmx.net
 * 
 */
public class MobAssassin implements Runnable {
	private ConcurrentLinkedQueue<LivingEntity> deathRow;

	public MobAssassin(ConcurrentLinkedQueue<LivingEntity> deathRow) {
		this.deathRow = deathRow;
	}

	// kill all mobs in deathRow
	public void run() {
		while (!this.deathRow.isEmpty()) {
			try {
				
				this.deathRow.remove().destroy();
			} catch (NoSuchElementException e) {
			}
		}
	}

}
