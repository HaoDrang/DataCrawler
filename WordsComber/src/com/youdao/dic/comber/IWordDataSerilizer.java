package com.youdao.dic.comber;

import org.w3c.dom.Element;

public interface IWordDataSerilizer {
	void deSerialize(WordData data, Element ele);
	String serialize(WordData data);
}
