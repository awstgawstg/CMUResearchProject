import java.util.*;
import java.util.Map.Entry;

/* Node of DAG */
public class Node<NodeType> {

    /* Cost and value of computation of this node. */
    final int cost = 1;
    final int value = 1;

    private Set<Node<NodeType>> children;
    private Set<Node<NodeType>> parents;

    /* Parents direct and indirect from a source */
    private Map<Node<NodeType>, Set<Node<NodeType>>> parentsFromSource;
    private Set<Node<NodeType>> preceding;

    /* Node data */
    private NodeType data;

    Node(NodeType data) {
        children = new HashSet<Node<NodeType>>();
        parents = new HashSet<Node<NodeType>>();
        parentsFromSource = new HashMap<Node<NodeType>, Set<Node<NodeType>>>();
        preceding = new HashSet<Node<NodeType>>();
        this.data = data;
    }

    public void addChild(Node<NodeType> node) {
        /* Add links */
        children.add(node);
        node.parents.add(this);

		/* Update the child node */
        node.updateIndirectParents(this);
    }


    private void updateIndirectParents(Node<NodeType> parent) {
        /* Add direct parent */
        parentsFromSource.put(parent, new HashSet<Node<NodeType>>());
        preceding.add(parent);
        preceding.addAll(parent.preceding);

		/* Add indirect parent */
        for (Entry<Node<NodeType>, Set<Node<NodeType>>> nodeSetEntry : parent.parentsFromSource.entrySet()) {
            Set<Node<NodeType>> sources = parentsFromSource.get(nodeSetEntry.getKey());
            if (sources == null) {
                sources = new HashSet<Node<NodeType>>();
                parentsFromSource.put(nodeSetEntry.getKey(), sources);
            }
            sources.addAll(nodeSetEntry.getValue());
            sources.add(parent);
        }

        for (Node child : children)
            child.updateIndirectParents(this);
    }


    public String toString() {
        return data.toString();
    }

    private Collection<Node<NodeType>> getChildren() {
        return children;
    }

    public int getValue() {
        return value;
    }

    public Collection<Node<NodeType>> getPreceding() {
        return preceding;
    }

    public int getCost(Node sourceNode, Set<Node> alreadyCalculated) {
        int cost = 0;
        Set<Node> pending = new HashSet<Node>();
        pending.add(this);
        pending.addAll(parentsFromSource.get(sourceNode));
        pending.removeAll(alreadyCalculated);

        for (Node node : pending)
            cost += node.cost;
        return cost;
    }
}
