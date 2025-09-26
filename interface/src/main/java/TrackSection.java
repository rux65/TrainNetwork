public class TrackSection {
    private final String name;
    private boolean occupied = false;

    public TrackSection(String name) {
        this.name = name;
    }

    public synchronized void enter(String trainName) {
        while (occupied) {
            try {
                wait(); // wait until free
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        occupied = true;
        System.out.println(trainName + " entered section " + name);
    }

    public synchronized void leave() {
        occupied = false;
        notifyAll();
    }

    public String getName() {
        return name;
    }
}
