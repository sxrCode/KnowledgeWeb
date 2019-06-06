package com.sxr.knowledge.temp.parse;

public class ANode extends GrammarNode {

	@Override
	public boolean canDelegate(char c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean create(char c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean isSatisfied() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void copy(GrammarNode target) {
		// TODO Auto-generated method stub

	}

	@Override
	public GrammarNode getUnsatisfiedNode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GrammarNode delegate(char c) {
		// TODO Auto-generated method stub
		return null;
	}

}
