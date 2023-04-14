public class List<T extends Comparable<T>> {
    public Node<T> head;

    public List(){
        head=null;
    }

    public void insert(T key){
        if(head==null){
            head =new Node(key);
        }else{
            Node<T> ptr=head;
            while(ptr.next!=null){
                ptr=ptr.next;
            }
            ptr.next=new Node(key);
            ptr.next.prev=ptr;
        }
    }

    // public Node<T> access(T elem){
        
    // }

    public Node<T> access(T elem) {
        Node<T> ptr = head;
        if (ptr == null) {

            head = new Node<T>(elem);// Empty:[1/2mark]
            return head;

        }
        while (ptr.next != null) {// While not null:[1/2mark]
            if ((ptr.next.data).compareTo(elem) == 0)

                break;// [1/2mark]
            // (can be part of while condition)

            ptr = ptr.next;// [1/2mark]

        }
        if (ptr.next == null) {// Not in the list:[1/2mark]
            // Insert at the end of the list:
            ptr.next = new Node<T>(elem);// [1/2mark]
            ptr.next.prev = ptr;// [1/2mark]
            return ptr.next;

        } else if ((ptr.next.data).compareTo(elem) == 0)// [1/2mark]
        {

            if ( ptr.prev != null) {

                ptr.prev.next = ptr.next; // [ 1 / 2 mark ]
                
                }
                ptr.next.prev = ptr.prev; // [ 1 / 2 mark ]
                if(ptr.next.next!=null){
                    ptr.next.next.prev = ptr; // [ 1 / 2 mark ]
                }
           
                ptr.prev = ptr.next ; // [ 1 / 2 mark ]
              
                ptr.prev.next = ptr ; // [ 1 / 2 mark ]
                if ( ptr.next.prev == null) { // [ 1 / 2 mark ]
                    head = ptr.next ; // [ 1 / 2 mark ]
                }
                ptr.next = ptr.next.next ; // [ 1 / 2 mark ]
                ptr = ptr.next ;

        }
        return ptr;

    }
}
