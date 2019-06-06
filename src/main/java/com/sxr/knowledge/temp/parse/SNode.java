package com.sxr.knowledge.temp.parse;

import java.util.LinkedList;
import java.util.List;

/**
 * S -> SS+ | SS* | a
 * 
 * @author sunxiran
 *
 */
public class SNode extends GrammarNode {

	private List<SNode> sNodes = new LinkedList<>();
	private PlusNode plusNode;
	private MultiNode multiNode;
	private ANode aNode = new ANode();

	@Override
	public boolean isSatisfied() {
		return (sNodes.size() == 2 && plusNode != null) || (sNodes.size() == 2 && multiNode != null) || aNode != null;
	}

	protected boolean isEnd() {
		return (sNodes.size() == 2 && plusNode != null) || (sNodes.size() == 2 && multiNode != null);
	}

	@Override
	public boolean canDelegate(char c) {
		return !isSatisfied() && sNodes.size() > 0;
	}

	@Override
	public GrammarNode delegate(char c) {
		if (sNodes.size() > 0) {
			return sNodes.get(sNodes.size() - 1);
		}
		return null;
	}

	@Override
	public GrammarNode getUnsatisfiedNode() {
		for (SNode sNode : sNodes) {
			GrammarNode grammarNode = sNode.getUnsatisfiedNode();
			if (sNode.getUnsatisfiedNode() != null) {
				return grammarNode;
			}
		}

		if (!isSatisfied()) {
			return this;
		}

		return null;
	}

	@Override
	public boolean create(char c) {
		if (!isEnd()) {
			if ('a' == c) {
				aNode = null;
				if (sNodes.size() < 2) {
					SNode sNode = new SNode();
					sNodes.add(sNode);
					return true;
				}

			} else if ('+' == c) {
				if (sNodes.size() == 2) {
					PlusNode plusNode = new PlusNode();
					this.plusNode = plusNode;
					return true;
				}

			} else if ('*' == c) {
				if (sNodes.size() == 2) {
					MultiNode multiNode = new MultiNode();
					this.multiNode = multiNode;
					return true;
				}

			}
		}

		return false;
	}

	@Override
	public void copy(GrammarNode target) {
		SNode newNode = (SNode) target;

		if (this.aNode != null) {
			ANode newANode = new ANode();
			this.aNode.copy(newANode);
			newNode.aNode = newANode;
		} else {
			newNode.aNode = null;
		}

		if (this.multiNode != null) {
			MultiNode newMultiNode = new MultiNode();
			this.multiNode.copy(newMultiNode);
			newNode.multiNode = newMultiNode;
		}

		if (this.plusNode != null) {
			PlusNode newPlusNode = new PlusNode();
			this.plusNode.copy(newPlusNode);
			newNode.plusNode = newPlusNode;
		}

		for (SNode s : this.sNodes) {
			SNode newSNode = new SNode();
			s.copy(newSNode);
			newNode.sNodes.add(newSNode);
		}
	}

	// {s: {}, s: {}, a: 1}
	public String display() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("a: " + (aNode != null ? 1 : 0)).append(", ").append("m: " + (multiNode != null ? 1 : 0))
				.append(", ").append("p: " + (plusNode != null ? 1 : 0)).append(", ");
		for (SNode sNode : sNodes) {
			stringBuilder.append("s: {").append(sNode.display()).append("}").append(",");
		}

		return stringBuilder.toString();
	}

}
