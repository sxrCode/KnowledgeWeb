package com.sxr.knowledge.temp.parse;

import java.util.LinkedList;
import java.util.List;

public class Stm {

	private int pos;
	private List<Stm> stms = new LinkedList<>();
	private boolean end; // 终结标识

	private boolean satisfy = false; // 满足可接受，满足不可接收，不满足

	public boolean parse() {
		char input = DataStore.getChar(pos);

		DataStore backStore = new DataStore();
		if ('a' == input) {

			for (int i = 0; i < 5; i++) {
				DataStore newStore = backStore;
				switch (i) {
					case 0: // 创建新节点
						if (stms.size() < 2) {
							Stm stm = new Stm();
							stms.add(stm);
						}

						break;
					case 1: // 交由已有节点
						if (stms.size() > 0) {
							Stm stm = stms.get(stms.size() - 1);
						}

					default:
						break;
				}
			}

		} else if ('+' == input) {
			// 所有子节点均满足
			if (stms.size() == 2) {
				end = true;

			}

			for (int i = 0; i < 5; i++) {
				switch (i) {
					case 0: // 策略0 终结当前节点

						break;
					case 2: // 交由子节点处理
						if (stms.size() > 0) {
							Stm stm = stms.get(stms.size() - 1);
						}
					case 1:
						if (end) {
							Stm stm = stms.get(stms.size() - 1);
						}
					default: // 无子节点，解析方案失败
						return false;

				}

			}

		} else if ('*' == input) {
			if (end) {
				return false;
			}

		}

		return true;
	}
}

//此处有 策略节点 和 语法节点
//失败是哪步策略造成的？应当由哪个节点调整？  失败的标志是当前节点无计可施，这是父节点的错误！
//当出现新的不满足节点时再备份
