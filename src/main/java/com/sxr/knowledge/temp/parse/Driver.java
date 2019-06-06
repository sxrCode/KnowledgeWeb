package com.sxr.knowledge.temp.parse;

public class Driver {

	public static void main(String[] args) {
		Driver driver = new Driver();
		driver.pos = 0;
		driver.rootNode = null;
		System.out.println(driver.run());
	}

	private SNode rootNode = null;
	private SNode backGrammarNode;

	private int pos = 0;

	public boolean run() {
		if (rootNode == null) {
			rootNode = new SNode();
		}

		backGrammarNode = new SNode();
		rootNode.copy(backGrammarNode);
		System.out.println("pos: " + pos + ";\n" + backGrammarNode.display());

		GrammarNode unsatisfiedNode = backGrammarNode.getUnsatisfiedNode();
		if (unsatisfiedNode == null)
			unsatisfiedNode = backGrammarNode;

		if (pos == DataStore.getLength()) {
			return rootNode.isSatisfied();
		}

		return deal(unsatisfiedNode, DataStore.getChar(pos));
	}

	private boolean deal(GrammarNode node, char c) {// 两种策略，对于更多策略应当使用循环，一种不行回退再试另一种
		System.out.println("deal new node --" + pos + "\n\n");
		rootNode.copy(backGrammarNode);
		if (node.create(c)) {

			// 发生修改，应当创建还原点,再交由子节点驱动
			Driver driver = new Driver();
			driver.pos = pos + 1;
			driver.rootNode = backGrammarNode;

			if (driver.run()) { // 解析成功
				return true;
			}
		}

		GrammarNode delegateNode = node.delegate(c);
		if (delegateNode != null) {
			return deal(delegateNode, c);
		}

		return false;
	}

}
