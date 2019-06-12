package com.sxr.knowledge.temp.parse;

import java.util.LinkedList;
import java.util.List;

public class Driver {

	public static void main(String[] args) {
		if (DataStore.getLength() < 1) {
			char input = DataStore.getChar(0);
			System.out.println("" + (input == 'a'));
		} else if (DataStore.getLength() == 1) {
			System.out.println("false");
		} else {
			Driver driver = new Driver();
			driver.pos = 0;
			driver.rootNode = new SNode();
			driver.rootNode.transferToS();
			boolean result = driver.run();
			if (result) {
				System.out.println("result: " + driver.rootNode.display());
			} else {
				System.out.println("Can't parse!");
			}
		}

	}

	private SNode rootNode = null;
	private SNode beforeSNode;

	private int pos = 0;

	public boolean run() {
		if (beforeSNode == null) {
			beforeSNode = rootNode;
		}

		// System.out.println("\n\nrun node pos: " + pos + " -- " + rootNode.display());

		if (pos == DataStore.getLength()) {
			return rootNode != null && rootNode.isSatisfied();
		}

		return deal();
	}

	private boolean deal() {// 两种策略，对于更多策略应当使用循环，一种不行回退再试另一种
		List<SNode> activeNodes = new LinkedList<>();
		getActiveNodes(beforeSNode, activeNodes);
		char input = DataStore.getChar(pos);
		// System.out.println("activeNodes size: " + activeNodes.size() + " -- " +
		// DataStore.getChar(pos));

		if (activeNodes.size() > (DataStore.getLength() / 2)) {
			return false;
		}

		for (SNode sNode : activeNodes) {
			// System.out.println("loop " + " -- " + pos + " -- " + rootNode.display());

			// 当创建A节点时亦会有两种方案.
			if ('a' == input) {

				// 1-直接添加A节点
				GrammarNode newNode = sNode.create(input);
				if (newNode != null) {
					// System.out.println("create A node -- " + rootNode.display());
					Driver driver = new Driver();
					driver.pos = pos + 1;
					driver.rootNode = rootNode;
					if (newNode instanceof SNode) {
						driver.beforeSNode = (SNode) newNode;
					} else {
						driver.beforeSNode = sNode;
					}
					if (driver.run()) { // 解析成功
						return true;
					}

					sNode.delete(newNode);
					// System.out.println("delete A node -- " + rootNode.display());

				}

				// 2-添加S节点
				newNode = sNode.addSNode();
				if (newNode != null) {
					// System.out.println("create S node -- " + rootNode.display());
					Driver driver = new Driver();
					driver.pos = pos;
					driver.rootNode = rootNode;
					if (newNode instanceof SNode) {
						driver.beforeSNode = (SNode) newNode;
					} else {
						driver.beforeSNode = sNode;
					}

					if (driver.run()) { // 解析成功
						return true;
					}

					sNode.delete(newNode);
					// System.out.println("delete S node -- " + rootNode.display());
				}

			} else {
				GrammarNode newNode = sNode.create(input);
				if (newNode != null) {
					Driver driver = new Driver();
					driver.pos = pos + 1;
					driver.rootNode = rootNode;
					if (newNode instanceof SNode) {
						driver.beforeSNode = (SNode) newNode;
					} else {
						driver.beforeSNode = sNode;
					}

					// System.out.println("create * node -- " + rootNode.display());
					if (driver.run()) { // 解析成功
						return true;
					}

					sNode.delete(newNode);
					// System.out.println("delete * node -- " + rootNode.display());

				}
			}

		}

		return false;
	}

	private void getActiveNodes(SNode node, List<SNode> activeNodes) {

		if (!node.isSatisfied())
			activeNodes.add(node);
		if (node.parentNode != null)
			getActiveNodes(node.parentNode, activeNodes);

	}

}
