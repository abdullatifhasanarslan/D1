
package util;

import java.io.Serializable;

/**
 *
 * @author abdullatif
 */
public class Packet implements Serializable{
    public int id;
    public int posX,posY,posZ;
    public byte[] data;
}
