package com.sxr.knowledge.temp.parse;

public abstract class GrammarNode {

	protected GrammarNode parentNode;

	public abstract boolean canDelegate(char c);

	public abstract boolean create(char c);

	protected abstract boolean isSatisfied();

	public abstract void copy(GrammarNode target);

	public abstract GrammarNode getUnsatisfiedNode();

	public abstract GrammarNode delegate(char c);
}
