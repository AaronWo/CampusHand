package cn.edu.xmu.campushand.menu;

public class MenuManager {
	public static void main(String[] args) {
		// 第三方用户唯一凭证
		String appId = "wxbe580ae60f8d9afc";
		// 第三方用户唯一凭证密钥
		String appSecret = "7dd3dbb787155283e9090c4048c2c5b4";

		// 调用接口获取access_token
		AccessToken at = WeixinUtil.getAccessToken(appId, appSecret);

		if (null != at) {
			// 调用接口创建菜单
			int result = WeixinUtil.createMenu(getMenu(), at.getToken());

			// 判断菜单创建结果
			if (0 == result)
				System.out.println("菜单创建成功！");
			else
				System.out.print("菜单创建失败，错误码：" + result);
		}
	}

	/**
	 * 组装菜单数据
	 * 
	 * @return
	 */
	private static Menu getMenu() {
		CommonButton bandBtn = new CommonButton();
		bandBtn.setName("绑定账户");
		bandBtn.setType("click");
		bandBtn.setKey("11");

		CommonButton deBandBtn = new CommonButton();
		deBandBtn.setName("解除绑定");
		deBandBtn.setType("click");
		deBandBtn.setKey("12");

		CommonButton btn21 = new CommonButton();
		btn21.setName("查询成绩");
		btn21.setType("click");
		btn21.setKey("21");

		CommonButton btn22 = new CommonButton();
		btn22.setName("培养方案");
		btn22.setType("click");
		btn22.setKey("22");

		CommonButton btn23 = new CommonButton();
		btn23.setName("不及格成绩");
		btn23.setType("click");
		btn23.setKey("23");

		CommonButton btn24 = new CommonButton();
		btn24.setName("教学评估");
		btn24.setType("click");
		btn24.setKey("24");

		CommonButton btn25 = new CommonButton();
		btn25.setName("GPA计算");
		btn25.setType("click");
		btn25.setKey("25");

		ComplexButton mainBtn1 = new ComplexButton();
		mainBtn1.setName("账户操作");
		mainBtn1.setSub_button(new CommonButton[] { bandBtn, deBandBtn });

		ComplexButton mainBtn2 = new ComplexButton();
		mainBtn2.setName("成绩stuff");
		mainBtn2.setSub_button(new CommonButton[] { btn21, btn22, btn25 });
		/**
		 * 这是公众号xiaoqrobot目前的菜单结构，每个一级菜单都有二级菜单项<br>
		 * 
		 * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br>
		 * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br>
		 * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 });
		 */
		Menu menu = new Menu();
		menu.setButton(new Button[] { mainBtn1, mainBtn2 });

		return menu;
	}
}
