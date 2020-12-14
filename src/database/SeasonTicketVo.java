package database;

public class SeasonTicketVo 
{
	private static SeasonTicketVo instance = null;
	
	private String season_id;
	private String depplandPlace;
	private String arrplandPlace;
	private int term;
	private String effDate;
	private String expDate;
	
	public static SeasonTicketVo getInstance() {
		if(instance == null) 
			instance = new SeasonTicketVo();
		
		return instance;
	}
	
	public SeasonTicketVo() {}
	
	public SeasonTicketVo(TrainDAO dao) {
		int lastID;
		if((lastID = dao.getLastSeasonId()) < 0) 
			lastID = 0;
		
		season_id = String.format("%d", ++lastID);
	}
	
	public String getSeason_id() {
		return season_id;
	}
	public SeasonTicketVo setSeason_id(String season_id) {
		this.season_id = season_id;
		return this;
	}
	public String getDepplandPlace() {
		return depplandPlace;
	}
	public SeasonTicketVo setDepplandPlace(String depplandPlace) {
		this.depplandPlace = depplandPlace;
		return this;
	}
	public String getArrplandPlace() {
		return arrplandPlace;
	}
	public SeasonTicketVo setArrplandPlace(String arrplandPlace) {
		this.arrplandPlace = arrplandPlace;
		return this;
	}
	public int getTerm() {
		return term;
	}
	public SeasonTicketVo setTerm(int term) {
		this.term = term;
		return this;
	}
	public String getEffDate() {
		return effDate;
	}
	public SeasonTicketVo setEffDate(String effDate) {
		this.effDate = effDate;
		return this;
	}
	public String getExpDate() {
		return expDate;
	}
	public SeasonTicketVo setExpDate(String expDate) {
		this.expDate = expDate;
		return this;
	}
	
	public String getEffDateByFormat() {
		return "to_date('" + effDate + "', 'yyyyMMddHH24MI')";
	}
	
	public String getExpDateByFormat() {
		return "to_date('" + expDate + "', 'yyyyMMddHH24MI')";
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(season_id).append(depplandPlace).append(arrplandPlace).append(term)
		.append(effDate).append(expDate);
		
		return sb.toString();
	}
}
