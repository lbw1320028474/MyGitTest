package com.example.data;

import java.util.ArrayList;
import java.util.List;



public class GetImagesService {
	public static List<String> getImages(String str, String quality_code){
		int index1;
		int index2;
		int index = 0;
		List<String> images_lists = new ArrayList<String>();
		while(true){
			index1 = str.indexOf("].src = ", index);
			if (index1 == -1){		//如果没找到.src，结束
				//images_lists.add("没找到。src");
				break;
			}else{
				index = index1 + 71;
				index2 = str.indexOf("0.jpg",index);	//
				if (index2 == -1){		//如果没有找到。jpg。结束
					//images_lists.add("没找到。image");
					break;
				}else{
					index = index2;
					String sub_url = str.substring(index1 + 9, index2 + 5);		//提取字符串
					if (quality_code.equals("1")){
						sub_url = sub_url.substring(0, sub_url.length() - 8);
						sub_url = sub_url + ".jpg";
						images_lists.add(sub_url);
						index = index + 400;
					}else if(quality_code.equals("2")){
						images_lists.add(sub_url);
						index = index + 400;
					}

					continue;
				}
			}
			//System.out.print(index);
		}
		return images_lists;
	}
}
