package com.sxr.knowledge.temp.parse;

import java.util.LinkedList;
import java.util.List;

public class Driver {

	public static void main(String[] args) {
		Driver driver = new Driver();
		driver.pos = 0;
		driver.rootNode = new SNode();
		System.out.println(driver.run());
	}

	private SNode rootNode = null;
	private SNode beforeSNode;

	private int pos = 0;

	public boolean run() {
		if (beforeSNode == null) {
			beforeSNode = rootNode;
		}

		System.out.println("\n\nrun node pos: " + pos + " -- " + rootNode.display());

		if (pos == DataStore.getLength()) {
			return rootNode != null && rootNode.isSatisfied();
		}

		return deal();
	}

	private boolean deal() {// 两种策略，对于更多策略应当使用循环，一种不行回退再试另一种
		List<SNode> activeNodes = new LinkedList<>();
		getActiveNodes(beforeSNode, activeNodes);
		System.out.println("activeNodes size: " + activeNodes.size() + " -- " + DataStore.getChar(pos));

		for (SNode sNode : activeNodes) {
			System.out.println("loop  " + "  --  " + pos + "  --  " + rootNode.display());
			GrammarNode newNode = sNode.create(DataStore.getChar(pos));
			if (newNode != null) {

				Driver driver = new Driver();
				driver.pos = pos + 1;
				driver.rootNode = rootNode;
				if (newNode instanceof SNode) {
					driver.beforeSNode = (SNode) newNode;
				} else {
					driver.beforeSNode = sNode;
				}

				System.out.println("create new node -- " + rootNode.display());
				if (driver.run()) { // 解析成功
					return true;
				}

				sNode.delete(newNode);
			}
		}

		return false;
	}

	private void getActiveNodes(SNode node, List<SNode> activeNodes) {

		if (node.parentNode != null)
			getActiveNodes(node.parentNode, activeNodes);
		if (!node.isEnd())
			activeNodes.add(node);
	}

}
