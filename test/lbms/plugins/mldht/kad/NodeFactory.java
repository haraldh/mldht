package lbms.plugins.mldht.kad;

import static the8472.utils.Functional.unchecked;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.concurrent.ThreadLocalRandom;

public class NodeFactory {

	static InetAddress generateIp(byte subnet) {
		// generate valid unicast IPs from 2001:20xx::/32
		byte[] addr = new byte[16];
		ThreadLocalRandom.current().nextBytes(addr);
		addr[0] = 0x20;
		addr[1] = 0x01;
		addr[2] = 0x20;
		addr[3] = subnet;
	
		return unchecked(() -> InetAddress.getByAddress(addr));
	}

	static void fillTable(Node node) {
		for(int i=0;i<1000;i++) {
			node.insertEntry(new KBucketEntry(new InetSocketAddress(generateIp((byte)0x00), 1024), Key.createRandomKey()), true);
		}
		node.rebuildAddressCache();
	}

}