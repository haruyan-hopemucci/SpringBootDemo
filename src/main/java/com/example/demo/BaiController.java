package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BaiController {

	 /* @ModelAttribute アノテーションを付与すると、
	  * ページ初期表示時にフォーム用オブジェクトとして
	  * Modelに勝手に登録してくれる。
	  */
	@ModelAttribute
	BaiForm setupBaiform() {
		return new BaiForm();
	}

	@RequestMapping("/index")
	public String index(BaiForm form, Model m) {
		// 数値が0なら、x倍返しのxは空白にする
		// それ以外なら、数値を2倍にしてテキストセットする。
		if(form.getNum() == 0) {
			m.addAttribute("x", "");
		}else {
			m.addAttribute("x",Integer.toString(form.getNum()*2));
			// テキストボックスの整数値も2倍
			form.setNum(form.getNum()*2);
		}
		// テンプレート名を返す。indexとする。拡張子は省略。
		return "index";
	}

/*
	@RequestMapping("/index")
	public String index(Model m) {
		BaiForm form = new BaiForm();
		form.setNum(1);
		// ページを最初に開いたときは、"x倍返し"のxは空白にする。
		m.addAttribute("x", "xxx");
		// テキストボックスに最初に入る数字は1にする。
		m.addAttribute("baiForm", form);
		// テンプレート名を返す。indexとする。拡張子は省略。
		return "index";
	}
*/
}
