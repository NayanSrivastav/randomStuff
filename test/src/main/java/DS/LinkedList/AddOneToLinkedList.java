package DS.LinkedList;


public class AddOneToLinkedList {
    public static void main(String[] args) {
        LinkedList linkedList = new LinkedList();
        int i = 0;
        linkedList.head = new LinkedList.Node(9);
        linkedList.head.next = new LinkedList.Node(9);
        linkedList.head.next.next = new LinkedList.Node(9);

        process(linkedList);

        linkedList.printList();
    }

    private static void process(LinkedList list) {
        LinkedList.Node current = list.head;
        int nonNineIndex = -1;
        int i = 0;
        while (current != null) {
            if (current.data < 9) {
                nonNineIndex = i;
            }

            current = current.next;
            i += 1;
        }
        current = list.head;

        i = 0;
        if (nonNineIndex == -1) {
            LinkedList.Node temp=list.head;
            LinkedList.Node head=new LinkedList.Node(1);
            head.next=temp;
            list.head=head;
            makeDataZero(temp);
        } else {
            while (true) {
                if (i < nonNineIndex) {
                    i++;
                    current = current.next;
                } else {
                    current.data = current.data + 1;
                    current = current.next;
                    makeDataZero(current);
                    break;
                }
            }
        }
    }

    private static void makeDataZero(LinkedList.Node current) {


        while (current != null) {
            current.data = 0;
            current = current.next;
        }
    }
}
