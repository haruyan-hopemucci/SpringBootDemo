package com.example.demo;

/**
 * アイテム売買を記録するオブジェクト
 */
import java.util.ArrayList;
import java.util.List;

public class FukubikiOperation {
	/** 売り買いデータリスト */
	private List<FukubikiOperationItem> opeList = new ArrayList<FukubikiOperationItem>();
	/** 現在の所持金 */
	private int currentGold;
	/** 現在の所持アイテムリスト */
	private List<FukubikiItem> itemList = new ArrayList<FukubikiItem>();

	/** コンストラクタ */
	public FukubikiOperation() {
		getItemList().add(FukubikiItem.YAK);
	}

	/**
	 * 買いオペ
	 * アイテムを購入し、所持金を購入金額引く。
	 * アイテムリストに購入したアイテムをaddし、現在のアイテム状態を更新する。
	 * @param item
	 */
	public void buyOperation(FukubikiItem item) {
		FukubikiOperationItem o = new FukubikiOperationItem();
		o.setOperation("買");
		o.setItemName(item.getItemName());
		this.currentGold -= item.getBuyPrice();
		o.setGold(this.currentGold);
		getItemList().add(item);
		o.setItemList(makeItemListString(getItemList()));
		opeList.add(o);
		this.getFukubikiken();
	}

	/**
	 * 売りオペ
	 * アイテムを売却し、所持金を売却金額加算する。
	 * アイテムリストから売却したアイテムをremoveし、現在のアイテム状態を更新する。
	 * @param item
	 */
	public void sellOperation(FukubikiItem item) {
		FukubikiOperationItem o = new FukubikiOperationItem();
		o.setOperation("売");
		o.setItemName(item.getItemName());
		this.currentGold += item.getSellPrice();
		o.setGold(this.currentGold);
		getItemList().remove(item);
		o.setItemList(makeItemListString(getItemList()));
		opeList.add(o);
	}

	/**
	 * ふくびきけん取得処理
	 * ふくびきけんを取得できる条件がそろっていればふくびきけんゲット
	 */
	public void getFukubikiken() {
		// ふくびきけんがもらえる条件に満たない場合は何もせずreturnする
		if(!canGetFukubikiken()) {
			return;
		}
		FukubikiItem item = FukubikiItem.FUK;
		FukubikiOperationItem o = new FukubikiOperationItem();
		o.setOperation("おまけ");
		o.setItemName(item.getItemName());
		o.setGold(this.currentGold);
		getItemList().add(item);
		o.setItemList(makeItemListString(getItemList()));
		opeList.add(o);
	}

	/**
	 * 現状態でふくびきけんがおまけしてもらえるかの判定
	 * @return おまけしてもらえるならtrue
	 */
	public boolean canGetFukubikiken() {
		// 所持金の各桁の合計が5の倍数、かつ、アイテムの所持数が7以下
		return
				(calcGoldEachScale(this.currentGold) % 5 == 0)
				&& (this.getItemList().size() <= 7);
	}

	/**
	 * 所持金の各桁を合計して返す
	 * @param c 所持金
	 * @return
	 */
	public int calcGoldEachScale(int c) {
		int n = 0;
		while(c != 0) {
			n += c % 10;
			c = c / 10;
		}
		return n;
	}

	/**
	 * アイテム欄にあるすべての福引券を売却する
	 */
	public void sellAllFukubikiken() {
		while(getItemList().contains(FukubikiItem.FUK)) {
			sellOperation(FukubikiItem.FUK);
		}
	}

	public int getCurrentGold() {
		return currentGold;
	}
	public void setCurrentGold(int currentGold) {
		this.currentGold = currentGold;
	}

	public List<FukubikiOperationItem> getOpeList(){
		return this.opeList;
	}

	public List<String> makeItemListString(List<FukubikiItem>itemList){
		List<String> l = new ArrayList<String>();
		itemList.forEach((item) -> l.add(item.getItemName()));
		return l;
	}

	public List<FukubikiItem> getItemList() {
		return itemList;
	}
}
