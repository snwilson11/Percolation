import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
public class PercolationStats {
	private double [] results;
	private int T;
	   public PercolationStats(int n, int trials){
		   T = trials;
		   results = new double[trials];
		   for(int i = 0; i < trials; i++){
			  Percolation a = new Percolation(n);
			  double percolation_count = 0;
			  while(!a.percolates()){
				  percolation_count++;
				  // note that I add 1 to random numbers because the range is [0,n) since these are ints that means = > [0, n - 1] 
				  // adding one shifts that range to [1, n] thus avoiding any indexing issues
				  int row = StdRandom.uniform(n) + 1;
				  int col = StdRandom.uniform(n) + 1;
				  while(a.isOpen(row, col)){
					  row = StdRandom.uniform(n) + 1;
					  col = StdRandom.uniform(n) + 1;
				  }
				  a.open(row, col);
				  
			  }
			  results[i] = percolation_count/ (n * n);
		   }
	   }
	   public double mean()                          // samlep mean of percolation threshold
	   {
		   return StdStats.mean(results);
	   }
	   public double stddev()                        // sample standard deviation of percolation threshold
	   {
		   return StdStats.stddev(results);
	   }
	   public double confidenceLo()                  // low  endpoint of 95% confidence interval
	   {
		   return mean() - (1.96 * stddev()) / Math.sqrt(T);
	   }
	   public double confidenceHi()                  // high endpoint of 95% confidence interval
	   {
		   return mean() + (1.96 * stddev()) / Math.sqrt(T);

	   }
//
	   public static void main(String[] args){
		   /*
		    * reason program is not working is that you are connecting in a square around the element instead of 
		    * a cross around the element
		    */
		   int n = 0;
		   int Trials = 0;
		   if (args.length > 0) {
			   try{
				   n = Integer.parseInt(args[0]);
				   Trials = Integer.parseInt(args[1]);
				   System.out.println("n = " + n + " trials = " + Trials);
			   }catch(NumberFormatException e){
				   System.err.println("Arguments must be a number dumbass");				   
			   }
			   
		   }
		   PercolationStats a = new PercolationStats(n, Trials);
		   System.out.println("mean = " + a.mean() + "\n stdev = " + a.stddev() + "\n [ " + a.confidenceLo() + ", " + a.confidenceHi() + " ]");
		   
		   
	   }
}
