import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * COMP9024 Assignment1
 * April 1v2 OK
 * @author Li Quan z5044754
 *
 */
public class MyDlist extends DList {

	Scanner scanner = null;
	String [] elementArray = new String[1024];
	
	/**
	 * Construct an empty DoublyLinked list
	 */
	public MyDlist() {
		super();
	}

	/**
	 * Construct DoublyLinked list based on terminal inputs or File. Assume that
	 * adjacent strings in the file f are separated by one or more white space
	 * characters. If f is “stdin”, MyDlist(“stdin”) creates a doubly linked
	 * list by reading all strings from the standard input.
	 * 
	 * @param f
	 */
	public MyDlist(String f) {
		super();
		if (f == "stdin") {
			readFromInput();
		} else {
			try {
				readFromFile(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// Construct a list
		for (String ele : elementArray) {
			if(ele==null){
				break;
			}
			DNode node = new DNode(ele, null, null);
			super.addLast(node);
		}
	
		// System.out.println("a");
	}

	/**
	 * Read data from input line
	 */
	public void readFromInput() {
		String element = "";
		int index = 0;
		while (true) {
			this.scanner = new Scanner(System.in);
			element = this.scanner.nextLine();
			if (!element.isEmpty()) {
				elementArray[index] = element;
				index++;
			} else {
				break;
			}
		}
	}

	/**
	 * Read Data From File
	 * 
	 * @param f
	 * @throws IOException
	 */
	private void readFromFile(String f) throws IOException {
		FileInputStream textFileIn = new FileInputStream(f);
		BufferedReader bfReader = new BufferedReader(new InputStreamReader(textFileIn));
		String line = "";
		int index = 0;
		while ((line = bfReader.readLine()) != null) {
			String[] elements = line.split("\\s+");// split one line by one or
													// more white spaces
			for (String e : elements) {
				elementArray[index] = e;
				index++;
			}
		}
		bfReader.close();

	}

	/**
	 * Print element of the list, one element per line
	 */
	public void printList() {
		DNode node = super.header.next;
		while (node.next != null) {
			System.out.println(node.element);
			node = node.next;
		}
	}

	/**
	 * Print element of the list, one element per line in reverse order
	 */
	public void printListRev() {
		DNode node = super.trailer.prev;
		while (node.prev != null) {
			System.out.println(node.element);
			node = node.prev;
		}
	}

	/**
	 * Returns a new MyDlist with same element as input MyDlist
	 * 
	 * @param u
	 * @return
	 */
	public static MyDlist cloneList(MyDlist u) {
		MyDlist mylist = new MyDlist();
		DNode node = u.header.next;
		while (node.next != null) {
			DNode copyNode = new DNode(node.element, null, null);
			mylist.addLast(copyNode);
			node = node.next;
		}
		return mylist;
	}

	/**
	 * Returns union of two sets
	 * Assume there are m elements in set u and n elements in set v. 
	 * The outter while loop iterates through m elements in MyDlist u, 
	 * and for each element in set u, the inner while loops will iterate n elements in set v in the worst case scenario. 
	 * Therefore, the time complexity should be O(m*n).
	 * @param u
	 * @param v
	 * @return
	 */
	public static MyDlist union(MyDlist u, MyDlist v) {

		DNode nodeA = u.header.next;
		DNode nodeB = v.header.next;
		while (nodeA.next != null) {

			String elementA = nodeA.element;
			boolean duplicate = false;
			nodeB = v.header.next;
			while (nodeB.next != null) {
				if (elementA.equals(nodeB.element)) {
					duplicate = true;
					break;
				}
				nodeB = nodeB.next;
			}

			if (duplicate) {
				DNode nodeToRemove = nodeA;
				nodeA = nodeA.next;
				u.remove(nodeToRemove);
			} else {
				nodeA = nodeA.next;
			}

		}

		// Concatenate two linked list
		u.trailer.prev.setNext(v.header.next);
		v.header.next.setPrev(u.trailer.prev);
		u.trailer.setNext(null);
		u.trailer.setPrev(null);
		v.header.setNext(null);
		v.header.setPrev(null);
		u.trailer = v.trailer;
		return u;
	}

	/**
	 * Returns the intersection of two set u,v
	 * Assume there are m elements in set u and n elements in set v. 
	 * The outter while loop iterates through m elements in MyDlist u, 
	 * and for each element in set u, the inner while loops will iterate n elements in set v in the worst case scenario. 
	 * Therefore, the time complexity should be O(m*n).
	 * @param u
	 * @param v
	 * @return
	 */
	public static MyDlist intersection(MyDlist u, MyDlist v) {
		DNode nodeA = u.header.next;
		DNode nodeB = v.header.next;
		MyDlist result = new MyDlist();
		while (nodeA.next != null) {

			String elementA = nodeA.element;
			boolean duplicate = false;
			nodeB = v.header.next;
			while (nodeB.next != null) {
				if (elementA.equals(nodeB.element)) {
					duplicate = true;
					break;
				}
				nodeB = nodeB.next;
			}

			if (duplicate) {
				DNode dupNode = new DNode(nodeA.element, null, null);
				result.addLast(dupNode);
			}

			nodeA = nodeA.next;

		}
		return result;//result returned @linus
	}
	
	
//	public static void main(String[] args) {
//		System.out.println("please type some strings, one string each line and an empty line for the end of input:");
//		MyDlist firstList = new MyDlist("stdin");
//		System.out.println("firstlist");
//		firstList.printList();
//		
//		// firstList.printListRev();
//		System.out.println("secondlist");
//		MyDlist secondList = new MyDlist(".//data//myfile.txt");
//		secondList.printList();
//		
//		// secondList.printListRev();
//		System.out.println("Clone first");
//		MyDlist thirdList = cloneList(firstList);
//		thirdList.printList();
//		
//		
//		System.out.println("Clone second");
//		MyDlist fourthList = cloneList(secondList);
//		fourthList.printList();
//
//		/** Compute the union of firstList and secondList */
//		MyDlist fifthList = union(firstList, secondList);
//		System.out.println("Union");
//		fifthList.printList();
//		
//		
//		System.out.println("Intersection:");
//
//		MyDlist sixthList = intersection(thirdList, fourthList);
//		sixthList.printList();
//
//	}

}
