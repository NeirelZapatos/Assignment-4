// Dynamic programming solver
public class KnapsackDPSolver implements java.io.Closeable
{
	private KnapsackInstance inst;
	private KnapsackSolution soln;

	public KnapsackDPSolver()
	{
    
	}

	public void close()
	{
		if (soln != null)
		{
			soln = null;
		}
	}

	public void Solve(KnapsackInstance inst_, KnapsackSolution soln_)
	{
		inst = inst_;
		soln = soln_;

		int[][] table = new int[inst.GetItemCnt() + 1][inst.GetCapacity() + 1];

//		for(int j = 0; j <= inst.GetItemCnt(); j++) {
//			table[0][j] = 0;
//		}

		for(int i = 1; i <= inst.GetItemCnt(); i++) {
//			table[i][0] = 0;

			for(int j = 1; j <= inst.GetCapacity(); j++) {
				if(j < inst.GetItemWeight(i)) {
					table[i][j] = table[i - 1][j];
				}
				else {
					if((inst.GetItemValue(i) + table[i - 1][j - inst.GetItemWeight(i)]) < table[i - 1][j]) {
						table[i][j] = table[i - 1][j];
					}
					else {
						table[i][j] = inst.GetItemValue(i) + table[i - 1][j - inst.GetItemWeight(i)];
					}
				}
			}
		}

//		for(int i = 0; i < table.length; i++) {
//			for(int j = 0; j < table[i].length; j++) {
//				System.out.print(table[i][j] + "\t");  // Use tab for better alignment
//			}
//			System.out.println();  // Newline after each row
//		}

		int column = inst.GetCapacity();
		for(int row = inst.GetItemCnt(); row >= 1; row--) {
			if(table[row][column] > table[row - 1][column]) {
				soln.TakeItem(row);
				column -= inst.GetItemWeight(row);
			}
		}

		soln.ComputeValue();
	}
}