package com.example.demo;

/**
 * アイテムデータ
 * staticフィールドに今回使用するアイテムがあらかじめ定義されている。
 * @author haruyan
 *
 */
public class FukubikiItem {
	/** アイテム名 */
	private String itemName;
	/** 買値 */
	private int buyPrice;
	/** 売値 通常は買値の75%だがやくそうなどの道具はそうではない */
	private int sellPrice;

	/**
	 * コンストラクタ
	 * @param name 名前
	 * @param bp 買値
	 * @param sp 売値
	 */
	public FukubikiItem(String name, int bp, int sp) {
		this.setItemName(name);
		this.setBuyPrice(bp);
		this.setSellPrice(sp);
	}

	public int getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(int buyPrice) {
		this.buyPrice = buyPrice;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public int getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(int sellPrice) {
		this.sellPrice = sellPrice;
	}

	/** やくそう */
	public static FukubikiItem YAK = new FukubikiItem("やくそう",8,4);
	/** どくけしそう */
	public static FukubikiItem DOK = new FukubikiItem("どくけしそう",10,8);
	/** キメラのつばさ */
	public static FukubikiItem KIM = new FukubikiItem("キメラのつばさ",25,20);
	/** ふくびきけん
	 * 買値は0Gにしておく。
	 */
	public static FukubikiItem FUK = new FukubikiItem("ふくびきけん",0,53);

}
