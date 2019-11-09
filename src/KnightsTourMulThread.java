import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

class KnightsTourMulThread {
    private static BlockingQueue<Path> workQ = new LinkedBlockingQueue<>();
    private static AtomicReference<Path> p = new AtomicReference<>();

    public static void main(String[] args) throws Exception {
        long startTime = System.nanoTime();

        knightsTours();

        long endTime = System.nanoTime();

        long time = (endTime - startTime) / 1000000;

        System.out.println("Running time = " + time);
    }

    private static void knightsTours() throws Exception {
        int numWorkers = Runtime.getRuntime().availableProcessors();

        ExecutorService executor = Executors.newFixedThreadPool(numWorkers);

        workQ.put(new Path(new Position(0, 0), null));

        IntStream.range(0, numWorkers).<Runnable>mapToObj(i -> () -> {
            try {
                knightsTour();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).forEach(executor::submit);

        executor.shutdown();
        System.out.println(workQ.size());
//		for(Iterator iter = workQ.iterator(); iter.hasNext();)
//		{
//			((Path)iter.next()).putPath();
//		}
    }

    private static void knightsTour() throws Exception {
        System.out.println(Thread.currentThread().getName());

        while (!workQ.isEmpty()) {
            Path path = workQ.take();

            Position[] moves = path.pathMoves();

            for (Position pos : moves) {
                if (!path.contains(pos)) {
                    Path newPath = new Path(pos, path);

                    if (newPath.pathComplete()) {
                        if (newPath.pathClosed()) {
                            p.set(newPath);
                            p.get().putPath();
                        }

                        System.out.println();

                        return;
                    } else {
                        workQ.put(newPath);
                    }

                }
            }
        }
    }
}