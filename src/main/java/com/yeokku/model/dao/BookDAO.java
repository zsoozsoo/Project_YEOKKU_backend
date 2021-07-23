//package com.yeokku.model.dao;
//
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.xml.parsers.SAXParser;
//import javax.xml.parsers.SAXParserFactory;
//
//import org.mybatis.spring.SqlSessionTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//import org.xml.sax.Attributes;
//import org.xml.sax.SAXException;
//import org.xml.sax.helpers.DefaultHandler;
//
//import com.yeokku.model.dto.Book;
//
//
//@Repository
//public class BookDAO {
//	@Autowired
//	private SqlSessionTemplate sqlSession;
//
//	List<Book> list = new ArrayList<Book>();
//
////	@Override
////	public List<Book> getAptList(String gucode, String url) {
////		list.clear();
////		connectNews(url);
////		return list;
////	}
////
////	@Override
////	public HouseDeal search(int index) { // 검색
////		return list.get(index);
////	}
////
////	public String getDesc(String title) {
////		for (HouseDeal n : list) {
////			if (n.getAptName().equals(title)) {
////				return n.getAptName();
////			}
////		}
////		return null;
////	}
//
//	private void connectNews(String url) {// sax 파싱 핵심
//
//		SAXParserFactory f = null;
//		try {
//			f = SAXParserFactory.newInstance(); // 팩토리패턴,싱글톤
//			SAXParser p = f.newSAXParser();
//			p.parse(new URL(url).openConnection().getInputStream(), new SAXHandler());
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//	}
//
//	class SAXHandler extends DefaultHandler { // 구현할 부분
//		StringBuilder b = new StringBuilder();
//		Book n = null;
//		String code = null;
//		@Override
//		public void startElement(String uri, String localName, String qName, Attributes attributes)
//				throws SAXException {// (이름상관없이)시작태그만나면 이 메서드 불림
//			
//			if (qName.equals("item")) { // item 만나면 시작 / qName: 태그네임 (네임스페이스로 관리하지 않으면 태그네임과 같음) / CDATA 모두 문자로 인식하라는 뜻(키워드같은것이 아니라
//				n = new Book();
//			}
//		}
//
//		@Override
//		public void characters(char[] ch, int start, int length) throws SAXException { // [CDATA] 만나면 이 메서드가 불림
//			b.append(ch, start, length);
//		}
//
//		@Override
//		public void endElement(String uri, String localName, String qName) throws SAXException {// (이름상관없이)종료태그만나면 이 메서드
//																								// 불림
//			if (n == null) return;
//			
//			if (qName.equals("아파트")) {
//				n.setAptName(b.toString());
//				n.setCode(gucode);
//			} else if (qName.equals("법정동")) {
//				n.setDong(b.toString());
//			} else if (qName.equals("거래금액")) {
//				String str = b.toString();
//				if(str.contains("00NORMAL SERVICE.")) {
//					str = str.replace("00NORMAL SERVICE.", "");
//				}
//				n.setDealAmount(str);
//			} else if (qName.equals("건축년도")) {
//				n.setBuildYear(b.toString());
//			} else if (qName.equals("년")) {
//				n.setDealYear(b.toString());
//			} else if (qName.equals("월")) {
//				n.setDealMonth(b.toString());
//			} else if (qName.equals("일")) {
//				n.setDealDay(b.toString());
//			} else if (qName.equals("층")) {
//				n.setFloor(b.toString());
//			} else if (qName.equals("전용면적")) {
//				n.setArea(b.toString());
//			} else if (qName.equals("item")) {
//				list.add(n);
//				n = null;
//			}
//			b.setLength(0);
//		}
//
//	}
//
//}
