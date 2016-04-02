/*
 * Singly Linked List
 */
class SList
{
	// first Node in the list
	Node head;
	
	/*
	 * Returns whether or not the list contains any Nodes
	 */
	public boolean isEmpty()
	{
		return head == null;
	}
	
	/*
	 * Returns the Node at the desired index
	 */
	public Node getNodeAt(int index)
	{
		Node current = head;
		int count = 0;
		
		// traverse through list
		while (count < index)
		{
			current = current.nextNode;
			count++;
		}
		
		return current;
	}
	
	/*
	 * Returns the length of the list through traversal
	 */
	public int getLen()
	{
		Node current = head;
		int count = 0;
		
		while (current != null)
		{
			current = current.nextNode;
			count++;
		}
		
		return count;
	}
	
	/*
	 * Deletes the Node at the desired index
	 * Returns said Node
	 */
	public Node delete(int index)
	{
		// if index is out of range, end method
		if (index >= getLen())
		{
			return null;
		}
		
		// if index is 0, replace the head of the list with its link
		if (index == 0)
		{
			Node oldHead = head;
			head = head.nextNode;
			
			return oldHead;
		}
		
		// traverse list
		Node current = head;
		for (int i = 0; i < index; i++)
		{
			current = head.nextNode;
		}
		
		// link to be deleted
		Node goodBye = current.nextNode;
		
		// reassign Node behind goodBye to link to the Node goodBye was linked to
		current.nextNode = goodBye.nextNode;
		
		return goodBye;
	}
	
	/*
	 * Adds a Node to the list with the passed value
	 * Node added to the end of the list (opposite side as head)
	 */
	public void addTo(int value)
	{
		// Node to be added
		Node newNode = new Node(value);
		
		// saved to prevent repeated traversal
		int size = getLen();
	
		if (isEmpty())
		{
			head = newNode;
			return;
		}
		
		// will be used for traversal
		Node current = head;
		
		// traverse through to the last Node
		for (int i = 0; i < size - 1; i++)
		{
			current = current.nextNode;
		}
		
		// assigns last Node's next link to newNode
		current.nextNode = newNode;	
	}

	/*
	 * Reverse the order of the nodes in the list
	 */
	public void reverse()
	{
		// prevent repeated traversal
		int size = getLen();
		
		// if size of list is 1 or 0, job is done
		if (size < 2)
		{
			return;
		}
		
		// used during traversal to swap pointers
		Node prev = null;
		Node current = head;
		Node next = head.nextNode;
		
		// traverse through list
		for (int i = 0; i < size; i++)
		{
			// assign next to the Node after the current one
			next = current.nextNode;
			
			// change current's pointer to the opposite direction
			current.nextNode = prev;
			
			// current Node will be used as the next Node's pointer
			prev = current;
			
			// move on to next Node
			current = next;
		}
		
		// reassign head Node in list to the last node visited (tail)
		head = prev;
	}
}
class Node
{
	// Node contains two types of data, an integer value and a pointer to the next Node
	int value;
	Node nextNode;
	
	public Node(int value, Node nextNode)
	{
		this.value = value;
		this.nextNode = nextNode;
	}
	
	// Overload, doesn't require user to supply Node's pointer
	public Node(int value)
	{
		this.value = value;
		this.nextNode = null;
	}
}
