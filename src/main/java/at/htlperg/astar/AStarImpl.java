package at.htlperg.astar;

import java.util.*;

public class AStarImpl {
    public void test() {
        //initialize the graph base on the Romania map
        AStarNode n1 = new AStarNode("Arad", 366);
        AStarNode n2 = new AStarNode("Zerind", 374);
        AStarNode n3 = new AStarNode("Oradea", 380);
        AStarNode n4 = new AStarNode("Sibiu", 253);
        AStarNode n5 = new AStarNode("Fagaras", 178);
        AStarNode n6 = new AStarNode("Rimnicu Vilcea", 193);
        AStarNode n7 = new AStarNode("Pitesti", 98);
        AStarNode n8 = new AStarNode("Timisoara", 329);
        AStarNode n9 = new AStarNode("Lugoj", 244);
        AStarNode n10 = new AStarNode("Mehadia", 241);
        AStarNode n11 = new AStarNode("Drobeta", 242);
        AStarNode n12 = new AStarNode("Craiova", 160);
        AStarNode n13 = new AStarNode("Bucharest", 0);
        AStarNode n14 = new AStarNode("Giurgiu", 77);

        //initialize the AStarAStarEdges

        //Arad
        n1.adjacencies = new AStarEdge[]{
                new AStarEdge(n2, 75),
                new AStarEdge(n4, 140),
                new AStarEdge(n8, 118)
        };

        //Zerind
        n2.adjacencies = new AStarEdge[]{
                new AStarEdge(n1, 75),
                new AStarEdge(n3, 71)
        };


        //Oradea
        n3.adjacencies = new AStarEdge[]{
                new AStarEdge(n2, 71),
                new AStarEdge(n4, 151)
        };

        //Sibiu
        n4.adjacencies = new AStarEdge[]{
                new AStarEdge(n1, 140),
                new AStarEdge(n5, 99),
                new AStarEdge(n3, 151),
                new AStarEdge(n6, 80),
        };


        //Fagaras
        n5.adjacencies = new AStarEdge[]{
                new AStarEdge(n4, 99),

                //178
                new AStarEdge(n13, 211)
        };

        //Rimnicu Vilcea
        n6.adjacencies = new AStarEdge[]{
                new AStarEdge(n4, 80),
                new AStarEdge(n7, 97),
                new AStarEdge(n12, 146)
        };

        //Pitesti
        n7.adjacencies = new AStarEdge[]{
                new AStarEdge(n6, 97),
                new AStarEdge(n13, 101),
                new AStarEdge(n12, 138)
        };

        //Timisoara
        n8.adjacencies = new AStarEdge[]{
                new AStarEdge(n1, 118),
                new AStarEdge(n9, 111)
        };

        //Lugoj
        n9.adjacencies = new AStarEdge[]{
                new AStarEdge(n8, 111),
                new AStarEdge(n10, 70)
        };

        //Mehadia
        n10.adjacencies = new AStarEdge[]{
                new AStarEdge(n9, 70),
                new AStarEdge(n11, 75)
        };

        //Drobeta
        n11.adjacencies = new AStarEdge[]{
                new AStarEdge(n10, 75),
                new AStarEdge(n12, 120)
        };

        //Craiova
        n12.adjacencies = new AStarEdge[]{
                new AStarEdge(n11, 120),
                new AStarEdge(n6, 146),
                new AStarEdge(n7, 138)
        };

        //Bucharest
        n13.adjacencies = new AStarEdge[]{
                new AStarEdge(n7, 101),
                new AStarEdge(n14, 90),
                new AStarEdge(n5, 211)
        };

        //Giurgiu
        n14.adjacencies = new AStarEdge[]{
                new AStarEdge(n13, 90)
        };

        search(n1, n13);

        List<AStarNode> path = printPath(n13);

        System.out.println("Path: " + path);
    }

    public void search(AStarNode source, AStarNode goal) {
        PriorityQueue<AStarNode> queue = new PriorityQueue<>(20, Comparator.comparingDouble(i -> i.getScoreF()));
        Set<AStarNode> explored = new HashSet<>();

        // cost from start
        source.setScoreG(0);

        queue.add(source);

        boolean found = false;
        while ((!queue.isEmpty()) && (!found)) {

            //the AStarNode in having the lowest f_score value
            AStarNode current = queue.poll();

            explored.add(current);

            //goal found
            if (current.value.equals(goal.value)) {
                found = true;
            }

            //check every child of current AStarNode
            for (AStarEdge e : current.adjacencies) {
                AStarNode child = e.target;
                double cost = e.cost;
                double temp_g_scores = current.getScoreG() + cost;
                double temp_f_scores = temp_g_scores + child.getScoreH();


                                /*if child AStarNode has been evaluated and
                                the newer f_score is higher, skip*/

                if ((explored.contains(child)) &&
                        (temp_f_scores >= child.getScoreF())) {
                    continue;
                }

                                /*else if child AStarNode is not in queue or
                                newer f_score is lower*/

                else if ((!queue.contains(child)) ||
                        (temp_f_scores < child.getScoreF())) {

                    child.setNext(current);
                    child.setScoreG(temp_g_scores);
                    child.setScoreF(temp_f_scores);

                    if (queue.contains(child)) {
                        queue.remove(child);
                    }

                    queue.add(child);

                }

            }

        }

    }

    public List<AStarNode> printPath(AStarNode target) {
        List<AStarNode> path = new ArrayList<>();

        for (AStarNode node = target; node != null; node = node.getNext()) {
            path.add(node);
        }

        Collections.reverse(path);

        return path;
    }
}

