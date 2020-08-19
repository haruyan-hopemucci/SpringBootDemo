package com.example.demo;

import java.util.ArrayList;
import java.util.List;

/**
 * 1回ごとの売買を記録するオブジェクト
 * viewに受け渡して表にする。
 * @author haruyan
 *
 */
public class FukubikiOperationItem {
	/** 売買タイプ。"買"、"売"、"おまけ"のいずれか */
	private String operation;
	/** 売買するアイテムの名称 */
	private String itemName;
	/** 売買後の所持金 */
	private int gold;
	/** 売買後の所持アイテムリスト */
	private List<String> itemList = new ArrayList<String>();

	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public int getGold() {
		return gold;
	}
	public void setGold(int gold) {
		this.gold = gold;
	}
	public List<String> getItemList() {
		return itemList;
	}
	public void setItemList(List<String> itemList) {
		this.itemList = itemList;
	}

}
