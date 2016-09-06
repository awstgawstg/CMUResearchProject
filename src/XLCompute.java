import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class XLCompute {

    public static void main(String[] args) {
        /* Test function */
        Node<String> a = new Node<String>("a");
        Node<String> b = new Node<String>("b");
        Node<String> c = new Node<String>("c");
        Node<String> d = new Node<String>("d");
        Node<String> e = new Node<String>("e");
        Node<String> f = new Node<String>("f");
        Node<String> g = new Node<String>("g");

        Node<String> z = new Node<String>("z");

        Node<String> x = new Node<String>("x");

        a.addChild(b);
        a.addChild(c);
        a.addChild(d);
        d.addChild(c);
        b.addChild(e);
        e.addChild(g);
        c.addChild(f);
        z.addChild(a);

        x.addChild(z);

        ArrayList<Node> starNodes = new ArrayList<Node>();
        starNodes.add(f);
        starNodes.add(g);
        starNodes.add(e);

        Set<Node> optNodes = findOptimumNodes(starNodes, 4, a);
        System.out.println("optNodes:" + optNodes);

    }

    /* Get a list of nodes that need computation when a node is updated */
    public static Set<Node> findOptimumNodes(ArrayList<Node> starNodes, int W, Node updatedNode) {
        Set<Node> out = new HashSet<Node>();
        int n = starNodes.size();
        int M[][] = new int[n + 1][W + 1];
        boolean K[][] = new boolean[n + 1][W + 1];
        ArrayList<ArrayList<Set<Node>>> computedNodes = new ArrayList<ArrayList<Set<Node>>>(n + 1);

        computedNodes.add(new ArrayList<Set<Node>>(W + 1));
        for (int w = 0; w <= W; w++)
            computedNodes.get(0).add(new HashSet<Node>());

        for (int i = 1; i <= n; i++) {
            computedNodes.add(new ArrayList<Set<Node>>(W + 1));
            int Vi = starNodes.get(i - 1).getValue();
            computedNodes.get(i).add(new HashSet<Node>());
            for (int w = 1; w <= W; w++) {
                int Wi = starNodes.get(i - 1).getCost(updatedNode, computedNodes.get(i - 1).get(w));

                if ((Wi > w) || (M[i - 1][w] > Vi + M[i - 1][w - Wi])) {
                    M[i][w] = M[i - 1][w];
                    computedNodes.get(i).add(computedNodes.get(i - 1).get(w));
                } else {
                    M[i][w] = Vi + M[i - 1][w - Wi];
                    K[i][w] = true;

                    /* Update computedNodes based on current Node */
                    computedNodes.get(i).add(new HashSet<Node>());
                    computedNodes.get(i).get(w).addAll(computedNodes.get(i - 1).get(w - Wi));
                    computedNodes.get(i).get(w).addAll(starNodes.get(i - 1).getPreceding());
                }
            }
        }

        // This is to output only star nodes
        /*
        int k = W;
        for (int i = n; i >= 1; i--) {
            System.out.println(i + " " + k + " " + K[i][k]);
            if (K[i][k]) {
                out.add(starNodes.get(i - 1));
                k = k - starNodes.get(i - 1).getCost(updatedNode, computedNodes.get(i - 1).get(k));
            }
        }*/

        /* Output all the nodes that need to be computed
           Can be used to filter the DFS from source node
         */
        for (int i = n; i >= 1; i--) {
            if (K[i][W]) {
                out.add(starNodes.get(i - 1));
                out.addAll(computedNodes.get(i).get(W));
            }
        }
        return out;
    }
}
