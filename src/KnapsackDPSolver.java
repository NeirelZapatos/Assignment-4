import java.util.*;

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
//					soln.DontTakeItem(i);
				}
				else {
					if((inst.GetItemValue(i) + table[i - 1][j - inst.GetItemWeight(i)]) < table[i - 1][j]) {
						table[i][j] = table[i - 1][j];
						soln.DontTakeItem(i);
					}
					else {
						table[i][j] = inst.GetItemValue(i) + table[i - 1][j - inst.GetItemWeight(i)];
						soln.TakeItem(i);
					}
				}
			}
		}

		soln.ComputeValue();
	}
}