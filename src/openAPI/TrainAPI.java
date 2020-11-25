package openAPI;

import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

//출발지 도착지 출발일
public class TrainAPI
{
	private static TrainAPI	_instance;
	private static final String SERVICE_KEY	= "wxxasIBZR7Fm76B44Ad9UeATwCDTMDPbD7KYi6y02h953AlV4ei%2FN6yo6%2FwZZ%2BKOyJFH01U47gc7mJ42czfR2A%3D%3D";
	private static String url = "http://openapi.tago.go.kr/openapi/service/TrainInfoService/";
	
	private String[] tmp = {"서울", "용산", "영등포", "광명", "수원", "평택", "천안아산", "천안", "오송",
			"조치원", "대전", "서대전", "김천", "구미", "김천구미", "대구", "동대구", "포항", "밀양", "구포",
			"부산", "신경주", "태화강", "울산(통도사)", "마산", "창원중앙", "경산", "논산", "익산", "정읍", "광주송정",
			"목포", "전주", "순천", "여수EXPO(구,여수역)", "대천", "청량리", "춘천", "제천", "동해", "강릉", "행신",
			"남춘천" , "부전", "신탄진", "영동", "왜관", "원주", "정동진", "홍성"};
	
	static DocumentBuilderFactory	dbFactory;
	static DocumentBuilder			dBuilder;
	static Document				doc;
	
	DecimalFormat df = new DecimalFormat("#,###");
	
	ArrayList<MyDictionary<String>> arrCityCode;
	ArrayList<MyDictionary<String>> arrTrain;
	ArrayList<MyDictionary<String>> arrStation;
	
	TrainAPI()
	{
		arrCityCode = new ArrayList<MyDictionary<String>>();
		arrTrain = new ArrayList<MyDictionary<String>>();
		arrStation = new ArrayList<MyDictionary<String>>();
	}
	
	//Singleton
	public static synchronized TrainAPI getInstance()
	{
		if(_instance == null)
			_instance = new TrainAPI();
		
		_instance.getCityCode();
		_instance.getTrainCode();
		
		return _instance;
	}
	
	public Vector<String> getStationTmp() {
		Vector<String> v = new Vector<String>();
		for(String s : tmp)
			v.add(s);
		
		return v;
	}
	
	//for Station list
	public Vector<String> getStationNames() {
		Vector<String> list = new Vector<String>();
		for(MyDictionary<String> md : arrStation) {
			list.add(md.getKey());
		}
		
		list.sort(new Comparator<String>() {
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
		
		return list;
	}

	//get train list for ticketing
	public ArrayList<TrainVo> getTrainList(String depPlaceName, String arrPlaceName, String depPlandTime) {
		ArrayList<TrainVo> list =
				getTrainInfo(getValue(arrStation, depPlaceName), getValue(arrStation, arrPlaceName), depPlandTime);
		
		list.sort(new Comparator<TrainVo>() {
			public int compare(TrainVo o1, TrainVo o2) {
				return o1.getDepplandTime().compareTo(o2.getDepplandTime());
			}
		});
		
		return list;
	}
	

	//get train code from open API
	private void getTrainCode() {
		try
		{
			StringBuilder urlBuilder = new StringBuilder(url + "getVhcleKndList"); /*URL*/
	        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + SERVICE_KEY); /*Service Key*/
	        doc = getDocument(urlBuilder.toString());
	        
	        NodeList nList = doc.getElementsByTagName("item");
			
			for(int i = 0; i < nList.getLength(); i++) {
				Node nNode = nList.item(i);
				
				if (nNode.getNodeType() == Node.ELEMENT_NODE)
				{
					Element eElement = (Element) nNode;
					String vehicleName = getTagValue("vehiclekndnm", eElement);
					String vehicleId =  getTagValue("vehiclekndid", eElement);
					arrTrain.add(new MyDictionary<String>(vehicleName, vehicleId));
				}
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	//get city code from open API
	private void getCityCode()
	{
		try
		{
			StringBuilder urlBuilder = new StringBuilder(url + "getCtyCodeList"); /* URL */
			urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + SERVICE_KEY);
			doc = getDocument(urlBuilder.toString());
			
			NodeList nList = doc.getElementsByTagName("item");
			
			for(int i = 0; i < nList.getLength(); i++) {
				Node nNode = nList.item(i);
				
				if (nNode.getNodeType() == Node.ELEMENT_NODE)
				{
					Element eElement = (Element) nNode;
					String cityCode = getTagValue("citycode", eElement);
					String cityName = getTagValue("cityname", eElement);
					arrCityCode.add(new MyDictionary<String>(cityName, cityCode));
					
					StationThread st = new StationThread(cityCode);
					st.start();
					//getStationCode(cityCode);
				}
			}
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	//get train informations from open API
	private ArrayList<TrainVo> getTrainInfo(String depPlaceId, String arrPlacedId, String depPlandTime)
	{
		ArrayList<TrainVo> trainInfo = new ArrayList<TrainVo>();
		try
		{
			StringBuilder			urlBuilder	= new StringBuilder(url + "getStrtpntAlocFndTrainInfo");
			urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + SERVICE_KEY); /* Service Key */
			urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /* 한 페이지 결과 수 */
			urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /* 페이지 번호 */
			urlBuilder.append("&" + URLEncoder.encode("depPlaceId", "UTF-8") + "=" + URLEncoder.encode(depPlaceId, "UTF-8")); /* 출발기차역ID */
			urlBuilder.append("&" + URLEncoder.encode("arrPlaceId", "UTF-8") + "=" + URLEncoder.encode(arrPlacedId, "UTF-8")); /* 도착기차역ID */
			urlBuilder.append("&" + URLEncoder.encode("depPlandTime", "UTF-8") + "=" + URLEncoder.encode(depPlandTime, "UTF-8")); /* 출발일 */
			
			for(MyDictionary<String> md : arrTrain)
			{
				String str = "&" + URLEncoder.encode("trainGradeCode", "UTF-8") + "=" + URLEncoder.encode(md.getValue(), "UTF-8");
				doc = getDocument(urlBuilder.toString() + str);

				//파싱할 tag
				NodeList			nList	= doc.getElementsByTagName("item");
				//System.out.println("파싱할 리스트 수 : " + nList.getLength());
				SimpleDateFormat	fm		= new SimpleDateFormat("yyyyMMddHHmmss");
				for (int i = 0; i < nList.getLength(); i++)
				{
					Node nNode = nList.item(i);

					if (nNode.getNodeType() == Node.ELEMENT_NODE)
					{
						Element eElement = (Element) nNode;
						TrainVo ti = new TrainVo();
						Date date1 = fm.parse(getTagValue("arrplandtime", eElement));
						//도착시간
						ti.setArrplandTime(dateToString(date1));
						//출발시간
						date1 = fm.parse(getTagValue("depplandtime", eElement));
						ti.setDepplandTime(dateToString(date1));
						//기차 이름
						ti.setName(getTagValue("traingradename", eElement));
						//기차 번호
						ti.setTrainNo(getTagValue("trainno", eElement));
						
						trainInfo.add(ti);
					}
				}
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return trainInfo;
	}
	
	//initialize doc
	private synchronized Document getDocument(String url) {
		try {
			dbFactory	= DocumentBuilderFactory.newInstance();
			dBuilder	= dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(url);
			doc.getDocumentElement().normalize();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return doc;
	}
	
	//get tag value from open API
	private String getTagValue(String tag, Element eElement)
	{
		NodeList	nlList	= eElement.getElementsByTagName(tag).item(0).getChildNodes();
		Node		nValue	= (Node) nlList.item(0);

		if (nValue == null)
			return null;

		return nValue.getNodeValue();
	}
	
	//return date to string
	private String dateToString(Date date)
	{
		StringBuffer	sb	= new StringBuffer();
		Calendar		cal	= Calendar.getInstance();
		cal.setTime(date);
		sb.append(cal.get(Calendar.YEAR)).append("년 ").append(cal.get(Calendar.MONTH) + 1).append("월 ").append(cal.get(Calendar.DAY_OF_MONTH))
				.append("일 ").append(cal.get(Calendar.HOUR_OF_DAY)).append("시 ").append(cal.get(Calendar.MINUTE)).append("분 ");

		return sb.toString();
	}
	
	//find value with key
	private String getValue(ArrayList<MyDictionary<String>> list, String key) {
		String value = "";
		for(MyDictionary<String> md : list) {
			if(md.getKey().equals(key))
			{
				value = md.getValue();
				break;
			}
		}
		
		return value;
	}
	
	class StationThread extends Thread {
		String cityCode;
		
		StationThread(String cityCode)
		{
			this.cityCode = cityCode;
		}
		
		public void run() {
			getStationCode();
		}
		
		// get station code from open API
		// call from getCityCode()
		private void getStationCode() {
			try {
				StringBuilder urlBuilder = new StringBuilder(url + "getCtyAcctoTrainSttnList"); /* URL */
				urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + SERVICE_KEY); /* Service Key */
				urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "="
						+ URLEncoder.encode("10", "UTF-8")); /* 한 페이지 결과 수 */
				urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "="
						+ URLEncoder.encode("1", "UTF-8")); /* 페이지 번호 */
				urlBuilder.append("&" + URLEncoder.encode("cityCode", "UTF-8") + "="
						+ URLEncoder.encode(cityCode, "UTF-8")); /* 시/도ID */

				doc = getDocument(urlBuilder.toString());

				NodeList nList = doc.getElementsByTagName("item");

				for (int i = 0; i < nList.getLength(); i++) {
					Node nNode = nList.item(i);

					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						String nodeName = getTagValue("nodename", eElement);
						String nodeId = getTagValue("nodeid", eElement);
						arrStation.add(new MyDictionary<String>(nodeName, nodeId));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
