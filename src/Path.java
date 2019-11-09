class Path {
    // non-empty path of Position's
    private Position start; // one end position in path (other is (0,0))
    private Path next; // path following start node (may be null)
    private int pathLength; // length of path

    Path(Position start0, Path next0) {
        start = start0;
        next = next0;
        if (next == null) {
            pathLength = 1;
        } else {
            pathLength = 1 + next.pathLength;
        }
    }

    boolean contains(Position p) {
        // does p occur in this path?
        if (start.equals(p)) {
            return true;
        } else if (next == null) {
            return false;
        } else {
            return next.contains(p);
        }

    }

    Position[] pathMoves() {
        // new positions to extend path
        return start.knightMoves();
    }

    boolean pathComplete() {
        // does the path cover the entire board?
        int tourLength = Position.numRows * Position.numCols;
        return pathLength == tourLength;
    }

    boolean pathClosed() {
        // is the path (assumed complete) closed?
        return start.equals(Position.end1) || start.equals(Position.end2);
    }

    void putPath() {
        // print path
        start.putPosition();
        if (next != null) {
            System.out.print(":");

            next.putPath();
        }
    }
}