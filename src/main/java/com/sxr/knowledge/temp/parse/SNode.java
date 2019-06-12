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

	public SNode parentNode;

	@Override
	public boolean isSatisfied() {
		return (sNodes.size() == 2 && plusNode != null) || (sNodes.size() == 2 && multiNode != null) || aNode != null;
	}

	protected boolean isEnd() {
		return (sNodes.size() == 2 && plusNode != null) || (sNodes.size() == 2 && multiNode != null);
	}

	public boolean isActive() {
		// 有孩子节点不满足，则不应被激活

		if (aNode != null) {
			return true;
		}

		return false;
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
	public GrammarNode create(char c) {
		if (!isEnd()) {
			if ('a' == c) {
				if (sNodes.size() < 2) {
					SNode sNode = new SNode();
					sNode.parentNode = this;
					sNodes.add(sNode);
					aNode = null;
					return sNode;
				}

			} else if ('+' == c) {
				if (sNodes.size() == 2) {
					PlusNode plusNode = new PlusNode();
					this.plusNode = plusNode;
					return plusNode;
				}

			} else if ('*' == c) {
				if (sNodes.size() == 2) {
					MultiNode multiNode = new MultiNode();
					this.multiNode = multiNode;
					return multiNode;
				}

			}
		}

		return null;
	}

	public SNode addSNode() { // 添加纯粹的S节点
		if (!isEnd()) {
			if (sNodes.size() < 2) {
				SNode sNode = new SNode();
				sNode.aNode = null;
				sNode.parentNode = this;
				sNodes.add(sNode);
				this.aNode = null;
				return sNode;
			}
		}
		return null;
	}

	public void transferToS() {
		this.aNode = null;
	}

	public boolean delete(GrammarNode subNode) {
		for (SNode sNode : sNodes) {
			if (sNode == subNode) {
				return sNodes.remove(sNode);
			}
		}

		if (subNode == multiNode) {
			multiNode = null;
			return true;
		}

		if (subNode == plusNode) {
			plusNode = null;
			return true;
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

	public List<SNode> getsNodes() {
		return sNodes;
	}

}
