import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

class sim_cache {
	public static void main(String[] args) {
		// System.out.println("Simulation start!");
		if (args.length != 8) {
			System.out.println("args format not match, please config 9 args!");
			return;
		}

		int blockSize = Integer.parseInt(args[0]);
		int l1Size = Integer.parseInt(args[1]);
		int l1Assoc = Integer.parseInt(args[2]);
		int l2Size = Integer.parseInt(args[3]);
		int l2Assoc = Integer.parseInt(args[4]);
		int replacementPolicy = Integer.parseInt(args[5]);
		int inclusionProperty = Integer.parseInt(args[6]);
		String trace = args[7];

		String[] lOfReplacementPolicy = {"LRU", "FIFO", "optimal"};
		String[] lOfinclusionProperty = {"non-inclusive", "inclusive"};

		//Print config state
		System.out.println("===== Simulator configuration =====");
		System.out.println("BLOCKSIZE: " + blockSize);
		System.out.println("L1_SIZE: " + l1Size);
		System.out.println("L1_ASSOC: " + l1Assoc);
		System.out.println("L2_SIZE: " + l2Size);
		System.out.println("L2_ASSOC: " + l2Assoc);
		System.out.println("REPLACEMENT POLICY: " + lOfReplacementPolicy[replacementPolicy]);
		System.out.println("INCLUSION PROPERTY: " + lOfinclusionProperty[inclusionProperty]);
		System.out.println("trace_file: " + trace);


		CacheSimulater myCache = new CacheSimulater(blockSize, l1Size, l1Assoc, l2Size, l2Assoc,
				replacementPolicy, inclusionProperty, trace);  

		// Read trace
		try {
			File f = new File(trace);
			BufferedReader b = new BufferedReader(new FileReader(f));
			String readLine = "";
			String[] operationArr;
			while ((readLine = b.readLine()) != null) {
				// System.out.println(readLine);
				// operationArr = readLine.split(" "); //["r", "FF2008CD"]
				myCache.runSimulationByStep(readLine);
			}
			System.out.println("===== L1 contents =====");
			myCache.l1Cache.printState();
			if (myCache.l2Cache != null) {
				System.out.println("===== L2 contents =====");
				myCache.l2Cache.printState();
			}
			myCache.printStates();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
