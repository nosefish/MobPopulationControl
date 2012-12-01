/**
 * Collection of helper functions.
 * 
 * @author nosefish, nosefish@gmx.net
 * 
 */
public final class MPCMath {

	/**
	 * 
	 * @param x1
	 *            X-value of the first point
	 * @param y1
	 *            Y-value of the first point
	 * @param z1
	 *            Z-value of the first point
	 * @param x2
	 *            X-value of the second point
	 * @param y2
	 *            Y-value of the second point
	 * @param z2
	 *            Z-value of the second point
	 * @return Square of the distance between the 3D points (x1, y1, z1), (x2,
	 *         y2, y3)
	 */
	public static double distanceSquared(double x1, double y1, double z1,
			double x2, double y2, double z2) {
		return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) + (z1 - z2)
				* (z1 - z2);
	}

	/**
	 * 
	 * @param e1
	 *            first BaseEntity
	 * @param e2
	 *            second BaseEntity
	 * @return Square of the distance between the two BaseEntities
	 */
	public static double distanceSquared(BaseEntity e1, BaseEntity e2) {
		return distanceSquared(e1.getX(), e1.getY(), e1.getZ(), e2.getX(),
				e2.getY(), e2.getZ());
	}
}
