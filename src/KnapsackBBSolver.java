
// Branch-and-Bound solver
public class KnapsackBBSolver extends KnapsackBFSolver
{
	protected UPPER_BOUND ub;
	private int ubType;

	public KnapsackBBSolver(UPPER_BOUND ub_)
	{
		ub = ub_;
		ubType = ub.getValue();
	}

	public void close()
	{
    
	}

	public void Solve(KnapsackInstance inst_, KnapsackSolution soln_)
	{
		inst = inst_;
		bestSoln = soln_;
		crntSoln = new KnapsackSolution(inst);
		int totalValue = 0;
		for(int i = 1; i <= inst.GetItemCnt(); i++) {
			totalValue += inst.GetItemValue(i);
		}

		if(ubType == 0) {
			FindSolnsUB1(1, totalValue);
		} else if (ubType == 1) {
			FindSolnsUB2(1, 0, 0);
		} else {
			FindSolnsUB3(1, 0, totalValue);
		}

	}

	public void FindSolnsUB1(int itemNum, int totalValue)
	{
		int itemCnt = inst.GetItemCnt();

		if (itemNum == itemCnt + 1)
		{
			CheckCrntSoln();
			return;
		}

		if(totalValue < bestSoln.GetValue()) {
			return;
		}

		crntSoln.DontTakeItem(itemNum);
		FindSolnsUB1(itemNum + 1, totalValue - inst.GetItemValue(itemNum));

		crntSoln.TakeItem(itemNum);
		FindSolnsUB1(itemNum + 1, totalValue);
	}

	public void FindSolnsUB2(int itemNum, int takenWeights, int takenValue)
	{
		int itemCnt = inst.GetItemCnt();

		if (itemNum == itemCnt + 1)
		{
			CheckCrntSoln();
			return;
		}

		int testValue = takenValue;
		int remainingCap = inst.GetCapacity() - takenWeights;
		for(int i = itemNum; i <= inst.GetItemCnt(); i++) {
			if(inst.GetItemWeight(itemNum) <= remainingCap) {
				testValue += inst.GetItemValue(itemNum);
			}
		}

		if(testValue < bestSoln.GetValue()) {
			return;
		}

		crntSoln.DontTakeItem(itemNum);
		FindSolnsUB2(itemNum + 1, takenWeights, takenValue);

		crntSoln.TakeItem(itemNum);
		FindSolnsUB2(itemNum + 1, takenWeights + inst.GetItemWeight(itemNum), takenValue + inst.GetItemValue(itemNum));
	}

	public void FindSolnsUB3(int itemNum, int takenWeights, int totalValue)
	{
		int itemCnt = inst.GetItemCnt();

		if (itemNum == itemCnt + 1)
		{
			CheckCrntSoln();
			return;
		}

		crntSoln.DontTakeItem(itemNum);
		FindSolnsUB3(itemNum + 1, takenWeights, totalValue);

		crntSoln.TakeItem(itemNum);
		FindSolnsUB3(itemNum + 1, takenWeights + inst.GetItemWeight(itemNum), totalValue);
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