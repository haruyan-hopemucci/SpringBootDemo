package com.example.demo;

import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ふくびきけん錬金サイトコントローラ
 * @author haruyan
 *
 */
@Controller
public class FukibukiController {

	static FukubikiItem YAK = FukubikiItem.YAK;
	static FukubikiItem DOK = FukubikiItem.DOK;
	static FukubikiItem KIM = FukubikiItem.KIM;
	static FukubikiItem FUK = FukubikiItem.FUK;

	@ModelAttribute
	FukubikiForm setupFukubikiform() {
		return new FukubikiForm();
	}

	@RequestMapping("/fukubiki")
	public String fukubiki(FukubikiForm form, Model m) {
		return "fukubiki";
	}

	@RequestMapping("/fukubiki_result")
	public String fukubikiResult(FukubikiForm form, Model m) {
		FukubikiOperation ope = new FukubikiOperation();
		int goldBase = form.getGold();
		ope.setCurrentGold(goldBase);

		FukubikiItem target = correctBuyItem(ope.getCurrentGold());
		// 次に何を買うとふくびきけんがおまけしてもらえるか探索する。
		while((target = correctBuyItem(ope.getCurrentGold())) == null) {
			// 該当なしの場合は、どくけしそうを売買して2ゴールド減らしてもう一度
			ope.buyOperation(DOK);
			ope.sellOperation(DOK);
		}
		//対象のアイテムを購入、おまけでふくびきけん取得
		ope.buyOperation(target);
		boolean loopflg = true;
		while(loopflg) {
			// 何かしらの条件に一致したフラグ
			boolean flg = false;
			// キメラのつばさがアイテムにある場合、売却してどくけしそう2つ購入
			if(ope.getItemList().contains(KIM)) {
				ope.sellOperation(KIM);
				ope.buyOperation(DOK);
				ope.buyOperation(DOK);
				flg = true;
			}
			// どくけしそうがアイテムにある場合、売却してやくそう１つ購入
			else if(ope.getItemList().contains(DOK)) {
				ope.sellOperation(DOK);
				ope.buyOperation(YAK);
				flg = true;
			}
			// やくそうがアイテムに２つ以上ある場合、２つとも売却してやくそう１つ購入
			else if(ope.getItemList()
					.stream()
					.filter(o -> o == YAK)
					.collect(Collectors.toList())
					.size() >= 2)
			{
				ope.sellOperation(YAK);
				ope.sellOperation(YAK);
				ope.buyOperation(YAK);
				flg = true;
			}
			// 終了条件
			// ふくびきけんの他にアイテムがやくそうだけ、すなわち上3つの条件に1つも当てはまらない場合
			if( flg == false) {
				//whileループを脱出
				loopflg = false;
			}
		}
		/*
		// 売買を全部自力で指定する方法
		while(goldBase >= ope.getCurrentGold()) {
			FukubikiItem item = correctBuyItem(ope.getCurrentGold());
			if(item == null) {
				// 該当なしの場合は、どくけしそうを売買して2ゴールド減らしてもう一度
				ope.buyOperation(DOK);
				ope.sellOperation(DOK);
			}else {
				if(item == KIM) {
					ope.buyOperation(KIM);
					ope.getFukubikiken();
					ope.sellOperation(KIM);
					ope.buyOperation(DOK);
					ope.buyOperation(DOK);
					ope.getFukubikiken();
					ope.sellOperation(DOK);
					ope.buyOperation(YAK);
					ope.getFukubikiken();
					ope.sellOperation(DOK);
					ope.buyOperation(YAK);
					ope.getFukubikiken();
					ope.sellOperation(YAK);
					ope.sellOperation(YAK);
					ope.buyOperation(YAK);
					ope.getFukubikiken();
					ope.sellOperation(YAK);
					ope.sellOperation(YAK);
					ope.buyOperation(YAK);
					ope.getFukubikiken();
				}else if(item == YAK) {
					ope.buyOperation(YAK);
					ope.getFukubikiken();
					ope.sellOperation(YAK);
					ope.sellOperation(YAK);
					ope.buyOperation(YAK);
					ope.getFukubikiken();
				}else if(item == DOK) {
					ope.buyOperation(DOK);
					ope.getFukubikiken();
					ope.sellOperation(DOK);
					ope.buyOperation(YAK);
					ope.getFukubikiken();
					ope.sellOperation(YAK);
					ope.sellOperation(YAK);
					ope.buyOperation(YAK);
					ope.getFukubikiken();
				}
				break;
			}
		}
		*/
		ope.sellAllFukubikiken();
		form.setGold(ope.getCurrentGold());
		m.addAttribute("operations", ope.getOpeList());
		m.addAttribute("result",true);
		return "fukubiki";
	}

	/**
	 * 現在所持金からどのアイテムを購入すればふくびきけんを購入できるか。
	 * @param current
	 * @return 購入すべきアイテム。該当なしの場合はnull。
	 */
	private FukubikiItem correctBuyItem(int current) {
		if(checkGold(current,FukubikiItem.KIM.getBuyPrice())) {
			return FukubikiItem.KIM;
		}
		if(checkGold(current,FukubikiItem.DOK.getBuyPrice())) {
			return FukubikiItem.DOK;
		}
		if(checkGold(current,FukubikiItem.YAK.getBuyPrice())) {
			return FukubikiItem.YAK;
		}
		return null;
	}

	/**
	 * 購入後所持金の5の倍数チェック
	 * @param current
	 * @param spent
	 * @return
	 */
	private boolean checkGold(int current, int spent) {
		int n = 0;
		current -= spent;
		while(current != 0) {
			n += current % 10;
			current = current / 10;
		}
		return (n % 5 == 0);
	}
}
