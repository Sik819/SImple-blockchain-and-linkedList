
import java.security.MessageDigest;

class DNode {
	// Node for building a Doubly-linked list
	String contents;
	DNode next;
	DNode prev;	

	DNode (String k) {  // Constructor: builds a node which can be searched forwards and backwards
		next= null; prev= null;
		contents= k;		
	}

	DNode (String k, DNode prev, DNode next){ // Constructor: builds a node with given references
		this.prev = prev;
		this.next = next;
		this.contents = k;
	}

	DNode searchForwards(DNode curr, String key) { 
		// Post: DNode is an address of a doubly-linked list. 
		// returns the address of key if it occurs in the portion of the list
		// beginning at curr and ending at the last node. Returns null if key does not occur in that portion,
		// or if DNode is null.
		if(curr==null)
			return null;

		if(curr.contents.equals(key))
			return curr;
		else {
			DNode temp = curr.next;
			while(temp!=null) {
				if(temp.contents.equals(key))
					return temp;
				temp = temp.next;
			}
			return null;
		}
	}

	DNode searchBackwards(DNode curr, String key) { 
		// Post: DNode is an address of a doubly-linked list. 
		// returns the address of key if it occurs in the portion of the list
		// beginning at the head and ending at curr. Returns null if key does not occur in that portion,
		// or if DNode is null.
		if(curr==null)
			return null;
		if(curr.contents.equals(key))
			return curr;
		else {
			DNode temp = curr.prev;
			while(temp!=null) {
				if(temp.contents.equals(key))
					return temp;
				temp = temp.prev;
			}
			return null;
		}		
	}

	void insertAfter(DNode curr, DNode newNode) { 
		// Pre: curr and newNode are addresses for DNodes
		// Post: newNode is inserted between curr and its next neighbour, i.e.
		// let N be newNode's next neighbour, then: curr.next points to newNode, newNode.next points to N
		// N.prev points to newNode and newNode.prev points to curr.
		// If curr has no next neighbour, then newNode is inserted as the last node after curr.
		if(curr.next==null) {
			curr.next = newNode;
			newNode.prev=curr;
		}
		else {
			DNode N = curr.next;
			curr.next = newNode;
			newNode.prev=curr;
			newNode.next = N;
			N.prev = newNode;
		}
	}

	void insertBefore(DNode curr, DNode newNode) { 
		// Pre: curr and newNode are addresses for DNodes
		// Post: newNode is inserted between curr and its previous neighbour, i.e.
		// let P be newNode's previous neighbour, then: P.next points to newNode, newNode.next points to curr
		// curr.prev points to newNode and newNode.next points to curr.
		// If curr has no previous neighbour, then newNode is inserted as the first node before curr.
		if(curr.prev==null) {
			curr.prev = newNode;
			newNode.next=curr;
		}
		else {
			DNode P = curr.prev;
			curr.prev = newNode;
			newNode.next=curr;
			newNode.prev = P;
			P.next = newNode;
		}
	}

}

class DLSList {
	// Class invariant: The nodes in the list are sorted (ascending) according to the contents
	// AND numNodes == the number of nodes in the list
	// AND (lastVisited points to the node which was last valid access after method visit is called
	//     OR is set to head (in case removeNode demands it) or it is initialised)
	DNode head;  // The first node in the list
	DNode lastVisited; // The address of the node last visited
	int numNodes; // The number of nodes in the list

	DLSList (){
		head= null;
		lastVisited= null;
		numNodes= 0;
	}

	void addNewNode(DNode newNode) { 
		// Post: newNode is inserted into the current list in correct sorted order
		// numNodes is adjusted to be equal to the number of nodes in the list
		
		//list is empty
		if(head==null) {
			head = newNode;
		}
		else if(head.contents.compareTo(newNode.contents)>0) {
		head.insertBefore(head, newNode);
		head = newNode;
			
		}
		else {
			DNode cur = head;
			
			while(cur!=null) {
				//beginning of the list
				
				//end of the list
				if(cur.next==null) {
					cur.insertAfter(cur, newNode);
					break;
				}
				//in between two nodes
					else if(cur.contents.compareTo(newNode.contents)<=0 && cur.next.contents.compareTo(newNode.contents)>0) {
						cur.insertAfter(cur, newNode);				
					break;
				}

				cur = cur.next;
			}
			
		}
		numNodes++;
	}

	void removeNode(String key) { 
		// Post: All occurrences of nodes with contents field equal to key are removed.
		// If lastVisited points to one of the removed nodes, then lastVisited is set to head
		// numNodes is adjusted to be equal to the number of nodes in the list
		if(head==null)
			return;
		
		
		if(head.contents.equals(key)) {
			if(lastVisited == head)
				lastVisited=head.next;
			if(head.next==null)
				head=null;
			else {
			head=head.next;
			head.prev=null;
			}
			numNodes--;
		}
		DNode cur = head.next;
		while(cur!=null) {
			if(cur.contents.equals(key)) {
				if(lastVisited == cur)
					lastVisited = head;
				cur.prev.next=cur.next;
				if(cur.next!=null)
					cur.next.prev=cur.prev;
			numNodes--;
			}
			cur=cur.next;
		}
			
	}

	DNode visit(String key) { 
		// Post: Returns the address of the first node (in ascending order) whose contents equal key, and null if there is no such node;
		// lastVisited is set to the address returned if it is not null, otherwise lastVisited remains unchanged
		if(head==null)
			return null;
		 DNode result = head.searchForwards(head, key);
		if(result != null)
		lastVisited = result;
		return result;
	
	}


}

