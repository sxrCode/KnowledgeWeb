package com.sxr.knowledge.temp.parse;

public abstract class GrammarNode {

	public abstract boolean canDelegate(char c);

	public abstract GrammarNode create(char c);

	protected abstract boolean isSatisfied();

	public abstract void copy(GrammarNode target);

	public abstract GrammarNode getUnsatisfiedNode();

	public abstract GrammarNode delegate(char c);
}
