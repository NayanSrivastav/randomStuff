package DS.LinkedList;

public class LinkedList {


    Node head;

    static class Node {

        int data;
        Node next;

        Node(int d) {
            data = d;
            next = null;
        }
    }


    private Node reverseIterative(Node node) {
        Node current = node;
        Node prev = null;
        Node next;
        while (current != null) {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }

        return prev;
    }

    private Node reverseRecursive(Node node, Node prev) {
        if (node.next == null) {
            node.next = prev;
            return node;
        }

        Node next = node.next;
        node.next = prev;

        return reverseRecursive(next, node);
    }

    // prints content of double linked list
    void printList() {
        Node node = head;
        while (node != null) {
            System.out.print(node.data + " ");
            node = node.next;
        }
    }

    public static void main(String[] args) {

        System.out.println("\n======== Reverse iteratively =========");

        {
            LinkedList list = new LinkedList();
            list.head = new Node(85);
            list.head.next = new Node(15);
            list.head.next.next = new Node(4);
            list.head.next.next.next = new Node(20);

            System.out.println("Given Linked list");
            list.printList();
            list.head = list.reverseIterative(list.head);
            System.out.println();
            System.out.println("Reversed linked list ");
            list.printList();
        }

        System.out.println("\n======== Reverse recursively =========");

        {
            LinkedList list = new LinkedList();
            list.head = new Node(85);
            list.head.next = new Node(15);
            list.head.next.next = new Node(4);
            list.head.next.next.next = new Node(20);

            System.out.println("Given Linked list");
            list.printList();
            list.head = list.reverseRecursive(list.head, null);
            System.out.println();
            System.out.println("Reversed linked list ");
            list.printList();
        }
    }
}

