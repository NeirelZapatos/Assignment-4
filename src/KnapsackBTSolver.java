import java.util.*;

// Backtracking solver
public class KnapsackBTSolver extends KnapsackBFSolver
{
	protected KnapsackInstance inst;
	protected KnapsackSolution crntSoln;
	protected KnapsackSolution bestSoln;
	private int currentLoad = 0;

	public KnapsackBTSolver()
	{

	}

	public void close()
	{

	}

	public void Solve(KnapsackInstance inst_, KnapsackSolution soln_)
	{
		inst = inst_;
		bestSoln = soln_;
		crntSoln = new KnapsackSolution(inst);
		FindSolns(1);
	}

	public void FindSolns(int itemNum)
	{
		int itemCnt = inst.GetItemCnt();

		if((currentLoad + inst.GetItemWeight(itemNum)) > inst.GetCapacity()) {
			return;
		}

		currentLoad += inst.GetItemWeight(itemNum);

		if(itemNum == itemCnt + 1)
		{
			CheckCrntSoln();
			return;
		}
		crntSoln.DontTakeItem(itemNum);
		FindSolns(itemNum + 1);
		crntSoln.TakeItem(itemNum);
		FindSolns(itemNum + 1);
	}

	public void CheckCrntSoln()
	{
		int crntVal = crntSoln.ComputeValue();
//		System.out.print("\nChecking solution ");
//		crntSoln.Print(" ");

		if (crntVal == DefineConstants.INVALID_VALUE)
		{
			return;
		}

		if (bestSoln.GetValue() == DefineConstants.INVALID_VALUE) //The first solution is initially the best solution
		{
			bestSoln.Copy(crntSoln);
		}
		else
		{
			if (crntVal > bestSoln.GetValue())
			{
				bestSoln.Copy(crntSoln);
			}
		}
	}
}