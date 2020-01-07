import java.util.ArrayList;

public class MinHeap {

	public ArrayList<Triplet> heap;

	public MinHeap() {
		this.heap = new ArrayList<Triplet>();
	}
	// ************************************************************************************************
	// Update
	// ************************************************************************************************
	public void update() {
		getmin().setExecuted_time(getmin().getExecuted_time() + 1);
	}
	// ************************************************************************************************
	// Insert
	// ************************************************************************************************
	public void insert(Triplet triplet) {
		heap.add(triplet);
	}
	// ************************************************************************************************
	// heapify
	// ************************************************************************************************
	public void heapify() {
		//fix up for insert
		int i = heap.size()-1;
		int parent = parentOf(i);
		while(parent != i && heap.get(parent).getExecuted_time() > heap.get(i).getExecuted_time()) {
			swap(i,parent);
			i = parent;
			parent = parentOf(i);
		}

		heapify(0);
	}
	private void heapify(int i) {
		int left = leftOf(i);
		int right = rightOf(i);
		int min_pos = -1;

		if(left <= heap.size()-1 && heap.get(left).getExecuted_time() <= heap.get(i).getExecuted_time()) {
			// if position i has a left child and its executed_time is less than i's executed_time
			// make i as min_pos(the position of min value)
			if(heap.get(left).getExecuted_time() == heap.get(i).getExecuted_time() &&
					heap.get(left).getBuildingNum() > heap.get(i).getBuildingNum()) {
				min_pos = i;
			}else {
				min_pos = left;
			}
		}else {
			min_pos = i;
		}

		if(right <= heap.size()-1 && heap.get(right).getExecuted_time() <= heap.get(min_pos).getExecuted_time()) {
			// if position i has a right child and its executed_time is less than i's executed_time
			// make i as min_pos(the position of min value)
			if(heap.get(right).getExecuted_time() == heap.get(i).getExecuted_time() &&
					heap.get(right).getBuildingNum() > heap.get(i).getBuildingNum()) {
				min_pos = i;
			}else {
				min_pos = right;
			}
		}
		// if the min_pos is not i
		// swap i and the min_pos and heapify again
		if(min_pos != i) {
			swap(i,min_pos);
			heapify(min_pos);
		}
	}

	// ************************************************************************************************
	// ExtractMin
	// ************************************************************************************************
	public Triplet extractMin() {
		// if heap is empty, throw exception
		// if heap has only one node, remove it and return
		if (heap.size() == 0) {
            throw new IllegalStateException("MinHeap is empty!");
        } else if (heap.size() == 1) {
            Triplet min = heap.remove(0);
            return min;
        }

        // Since the min is at the top of min heap,
        // store the min value and replace the min with the last node of the heap
        // Then, heapify the whole minheap and return the min value
		Triplet min = heap.get(0);
		Triplet last = heap.remove(heap.size()-1);
		heap.set(0, last);

        heapify(0);

        return min;
    }

	// ************************************************************************************************
	// Other Method
	// ************************************************************************************************

	// get the position of parent node
	private int parentOf(int pos) {
		if(pos % 2 == 1) {
			return(pos/2);
		}else {
			return((pos-1)/2);
		}
	}

	// get the position of left child node
	private int leftOf(int pos) {
		return 2 * pos + 1;
	}

	// get the position of right child node
	private int rightOf(int pos) {
		return 2 * pos + 2;
	}

	// swap the nodes of heap by their positions
	private void swap(int pos1, int pos2) {
		Triplet temp = heap.get(pos1);
		heap.set(pos1, heap.get(pos2));
		heap.set(pos2, temp);
	}

	// get the min of the heap
	public Triplet getmin() {
		return(heap.get(0));
	}

	// get the size of the heap
	public int heapSize() {
		return(heap.size());
	}

}
