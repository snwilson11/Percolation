import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private WeightedQuickUnionUF grid_track;
	private boolean[][] grid;
	private int size;

	private int IndexCalc(int row, int col) {
		return row * size + col + 1;
	}

	public Percolation(int n) {
		if (n <= 0)
			throw new IllegalArgumentException();

		grid_track = new WeightedQuickUnionUF(n * n + 2);
		size = n;
		grid = new boolean[n][n];
		for (int i = 1; i <= n; i++) {
			grid_track.union(0, i);
			grid_track.union(size * size + 1, IndexCalc(size - 1, i - 1));

		}
	}

	public void open(int row, int col) // open site (row, col) if it is not open
										// // already
	{
		if (row > size || row <= 0 || col > size || col <= 0)
			throw new IndexOutOfBoundsException();
		// open in boolean grid
		row -= 1;
		col -= 1;
		grid[row][col] = true;
		// check all surrounding elements to check if open
		// if open connect - if not ignore

		int this_element = this.IndexCalc(row, col);
		// connect to adjacent cells if they exist and are open
		if (row - 1 >= 0 && (grid[row - 1][col]))
			grid_track.union(IndexCalc(row - 1, col), this_element); // connect
																		// to
																		// cell
																		// above
																		// element
		if (col - 1 >= 0 && (grid[row][col - 1]))
			grid_track.union(IndexCalc(row, col - 1), this_element); // connnect
																		// to
																		// cell
																		// to
																		// the
																		// left
																		// of
																		// element
		if (col + 1 < size && grid[row][col + 1])
			grid_track.union(IndexCalc(row, col + 1), this_element); // connect
																		// to
																		// cell
																		// to
																		// the
																		// right
																		// of
																		// element
		if (row + 1 < size && grid[row + 1][col])
			grid_track.union(IndexCalc(row + 1, col), this_element); // connect
																		// to
																		// cell
																		// below
																		// the
																		// element
	}

	public boolean isOpen(int row, int col) // is site (row, col) open?
	{
		if (row > size || row <= 0 || col > size || col <= 0)
			throw new IndexOutOfBoundsException();
		return grid[row - 1][col - 1];
	}

	public boolean isFull(int row, int col) {
		if (row > size || row <= 0 || col > size || col <= 0)
			throw new IndexOutOfBoundsException();
		int i = IndexCalc(row - 1, col - 1);
		// System.out.println("grid = " + grid[row][col] + " grid connected? = "
		// + grid_track.connected(i, 0));
		return isOpen(row, col) && grid_track.connected(i, 0);
	}

	public int numberOfOpenSites() {
		int count = 0;
		for (int i = 1; i <= size; i++) {
			for (int j = 1; j <= size; j++) {
				if (isOpen(i, j)) {
					count++;
				}
			}
		}
		return count++;
	}

	public boolean percolates() {
		return grid_track.connected(0, size * size + 1) && (numberOfOpenSites() >= 1);
	}

	public void print() { // function that prints out contents of grid
		for (int row_index = 0; row_index < size; row_index++) {
			for (int col_index = 0; col_index < size; col_index++) {
				if (grid[row_index][col_index]) {
					System.out.print("1 ");
				} else {
					System.out.print("0 ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}

	public static void main(String[] args) // test client (optional)
	{
		int n = 2;
		int row = 0;
		int col = 0;
		Percolation a = new Percolation(1);
		String FILENAME = "/Users/Wilson/Downloads/percolation/input20.txt";

		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {

			String sCurrentLine;
			boolean firstLine = true;
			while ((sCurrentLine = br.readLine()) != null) {
				if (firstLine) {
					sCurrentLine.trim();
					n = Integer.parseInt(sCurrentLine);
					System.out.println(n);
					a = new Percolation(n);
					firstLine = false;
				} else {
					sCurrentLine = sCurrentLine.trim();
					row = Integer.parseInt(sCurrentLine.split(" ")[0]);
					sCurrentLine = sCurrentLine.substring(sCurrentLine.split(" ")[0].length());
					sCurrentLine = sCurrentLine.trim();
					col = Integer.parseInt(sCurrentLine);
					a.open(row, col);
					System.out.println(row + " " + col);
					a.print();
					System.out.println(a.percolates());
					
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(a.isFull(18, 1));
	}

}
