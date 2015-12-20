package cn.edu.xmu.campushand.junit;

public class test {

	public static void main(String[] args) {
		String str = "绑定 110230076 ASQKAU ";
		str = str.substring(str.indexOf("定") + 1);
		char[] c = str.toCharArray();
		int startIndex = 0;
		while (c[startIndex] == ' ')
			startIndex++;
		System.out.println(startIndex);
		int endIndex = startIndex;
		while (c[endIndex] != ' ')
			endIndex++;
		System.out.println(endIndex);
		String zhanghao = str.substring(startIndex, endIndex);
		System.out.println(zhanghao);
		startIndex = endIndex;
		while (c[startIndex] == ' ')
			startIndex++;
		endIndex = startIndex;
		while (c[endIndex] != ' ')
			endIndex++;
		String mima = str.substring(startIndex, endIndex);
		System.out.println(mima);
	}

}
