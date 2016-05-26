/*
 * Linked list with the keys being Strings
 * Used to save Strings read from file, and input from user
 * Most useful characteristic is toCharArray() as it returns the contents of the List as one char[][]
 */
public class StringList
{
	// first Node in the list
	private Node head;
	
	// store length to prevent lookup
	private int length;
	
	/*
	 * If User does not provide a Node to be head, assign it to be null
	 */
	public StringList()
	{
		head = null;
		
		// length starts at 0
		length = 0;
	}
	
	/*
	 * User can provide the head to the List
	 */
	public StringList(Node head)
	{
		this.head = head;
		
		// length must take into account nodes linked to head
		updateLength();
	}
	
	/*
	 * Ensures that the length member variable accurately reflects the length of the list
	 * Will need to be called if Lists are made in an un-orthodox way (node-by-node, combining, etc.)
	 */
	public boolean updateLength()
	{
		// place holders for old and new lengths
		int oldLength = length;
		int newLength = 0;
		
		// used for traversal
		Node currentNode = head;
		
		// traverse list to end, incrementing newLength
		while (currentNode != null)
		{
			newLength++;
			currentNode = currentNode.nextNode;
		}
		
		// update the length accordingly
		length = newLength;
		
		// returns whether or not the length stayed the same
		return oldLength == newLength;
	}
	/*
	 * Returns a character array containing the strings passed
	 */
	public char[][] toCharArray()
	{
		// create empty char array
		char[][] theGrid = new char[length][0];
		
		// used for list traversal
		Node traversalNode = head;
		String currentNodeKey = "";
		int nodeKeyLength = 0;
		
		// traverse every Node in the List
		for (int i = 0; i < length; i++)
		{
			// stores the string at the current Node and the length of this string
			currentNodeKey = traversalNode.key;
			nodeKeyLength = currentNodeKey.length();
			
			// redefines grid char array at i
			theGrid[i] = new char[currentNodeKey.length()];
			
			// copies contents of the Node's string to the grid's row
			for (int j = 0; j < nodeKeyLength; j++)
			{
				theGrid[i][j] = currentNodeKey.charAt(j);
			}
			
			// moves on to the next Node
			traversalNode = traversalNode.nextNode;
		}
		
		// return completed grid
		return theGrid;
	}
	
	/*
	 * Returns the length of the list
	 */
	public int getLen()
	{
		return length;
	}
	
	/*
	 * Allow user to access head Node
	 */
	public Node getHead()
	{
		return head;
	}
	
	/*
	 * Returns whether or not the List is empty
	 */
	public boolean isEmpty()
	{
		return head == null;
	}
	
	/*
	 * Uses helpInsert to add the String passed to the list
	 */
	public void insert(String key)
	{
		// increment list length
		length++;
		
		// if the list is empty, assign head to the new Node and increment length
		if (isEmpty())
		{
			head = new Node(key, null);
			return;
		}
		
		// else use helpInsert to reach the end of the List
		helpInsert(key, head);
	}
	
	/*
	 * Adds the key to the string if the currentNode is the last in the list
	 * Else calls helpInsert on the next Node
	 */
	private void helpInsert(String key, Node currentNode)
	{
		// if this is the last Node in the list, add new Node to the end
		if (currentNode.nextNode == null)
		{
			currentNode.nextNode = new Node(key, null);
			return;
		}
		
		// recursively call helpInsert on the Node after the current
		helpInsert(key, currentNode.nextNode);
	}

	/*
	 * Prints all Strings in the list
	 */
	public void printAll()
	{
		// if the List is not empty
		if (!isEmpty())
		{
			helpPrintAll(head);
		}
	}
	
	/*
	 * Prints the key of the Node passed
	 * Recursively calls helpPrintAll on the next Node until the last Node is reached
	 */
	private void helpPrintAll(Node currentNode)
	{
		// print the current Node's info
		System.out.println(currentNode.key);
		
		// if the current Node is not the last in the list, go to the next
		if (currentNode.nextNode != null)
		{
			helpPrintAll(currentNode.nextNode);
		}
	}
	
	/*
	 * Returns the Node's key at the index passed
	 */
	public String getKeyAt(int index)
	{
		// if the list is empty
		if (isEmpty())
		{
			System.out.println("Empty List");
			return null;
		}
		
		// if user tries to access an index not in the list
		if (index >= getLen() || index < 0)
		{
			System.out.println("Index not in List");
			return null;
		}
		
		return helpGetKeyAt(index, 0, head);
	}
	
	/*
	 * Searches the through List starting at the Node passed until the desired index's String is found
	 */
	private String helpGetKeyAt(int index, int currentIndex, Node currentNode)
	{
		// if the current index
		if (index == currentIndex)
		{
			return currentNode.key;
		}
		
		return helpGetKeyAt(index, currentIndex + 1, currentNode.nextNode);
	}

	/*
	 * Removes the Node at the index passed
	 */
	public void deleteAt(int index)
	{
		// prevents attempted access of Node index not in the list
		if (index >= length || index < 0)
		{
			System.out.println("Index out of range");
			return;
		}
		
		// if deletion is possible, decrement list length
		length--;
		
		// handles deleting only Node in list
		if (length == 0)
		{
			head = null;
			return;
		}
		
		// if the list length > 1 and the user wishes to delete the first Node
		if (index == 0)
		{
			head = head.nextNode;
			return;
		}
		
		helpDeleteAt(index, 0, head);
	}
	
	/*
	 * Traverses the list until the Node behind the one to be deleted is found
	 * Adjusts pointer on Node so that the Node to be deleted is skipped
	 */
	private void helpDeleteAt(int index, int currentIndex, Node currentNode)
	{
		// if the Node to be deleted is the next Node, reassign pointer so that next Node is skipped
		if (currentIndex == (index - 1))
		{
			currentNode.nextNode = currentNode.nextNode.nextNode;
			return;
		}
		
		// traverse to the next Node
		helpDeleteAt(index, currentIndex + 1, currentNode.nextNode);
	}
}
/*
 * Node class contains a String (key), and the link to the next Node (nextNode)
 */
class Node
{
	public String key;
	public Node nextNode;
	
	public Node(String key, Node nextNode)
	{
		this.key = key;
		this.nextNode = nextNode;
	}
}
