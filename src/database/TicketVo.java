package database;

public class TicketVo {
	private static int lastID;
	private String ticket_id;		//not null	number
	private String deppland_place;	//not null	varchar
	private String arrpland_place;	//not null	varchar
	private String train_name;		//			varchar
	private String car_number;		//			int
	private String seat;			//			varchar
	private String personnel;		//not null	int
	private String deppland_time; 	//not null	Date
	private String arrpland_time;	//not null	Date
	private String price;			//not null	int
	private String ticketing_day;	//not null	Date
	private String ticket_type;		//not null	int
	private String term;			//			int
	private String effective_date;	//			Date
	private String expiration_date;	//			Date
	
	public TicketVo() {
		
	}
	
	public TicketVo(TrainDAO dao) {
		if((lastID = dao.getLastId()) < 0) 
			lastID = 0;
		
		ticket_id = String.format("%d", ++lastID);
	}
	
	public TicketVo(String deppland_place, String arrpland_place, String train_name,
			String car_number, String seat, String deppland_time, String price, String ticketing_day,
			String ticket_type, String term, String effective_date, String expiration_date) {
		this();
		this.deppland_place = deppland_place;
		this.arrpland_place = arrpland_place;
		this.train_name = train_name;
		this.car_number = car_number;
		this.seat = seat;
		this.deppland_time = deppland_time;
		this.price = price;
		this.ticketing_day = ticketing_day;
		this.ticket_type = ticket_type;
		this.term = term;
		this.effective_date = effective_date;
		this.expiration_date = expiration_date;
	}
	
	public String getTicket_id() { return ticket_id; }
	public void setTicket_id(String ticket_id) { this.ticket_id = ticket_id; }
	
	public String getDeppland_place() { return deppland_place; }
	public void setDeppland_place(String deppland_place) { this.deppland_place = deppland_place; }
	
	public String getArrpland_place() {	return arrpland_place; }
	public void setArrpland_place(String arrpland_place) { this.arrpland_place = arrpland_place; }
	
	public String getTrain_name() { 
		return (train_name == null || train_name.length() == 0) ? 
				"null" : train_name; 
	}
	public void setTrain_name(String train_name) { this.train_name = train_name; }
	
	public String getCar_number() { 
		return (car_number == null || car_number.length() == 0) ? "null" : car_number; 
	}
	public void setCar_number(String car_number) { this.car_number = car_number; }
	
	public String getSeat() { 
		return (seat == null || seat.length() == 0) ? "null" : seat; 
	}
	public void setSeat(String seat) { this.seat = seat; }
	
	public String getDeppland_time() { 
		return deppland_time; 
	}
	public void setDeppland_time(String deppland_time) { this.deppland_time = deppland_time; }
	
	public String getArrpland_time() {
		return arrpland_time;
	}
	public void setArrpland_time(String arrpland_time) {
		this.arrpland_time = arrpland_time;
	}
	
	public String getPrice() { return price; }
	public void setPrice(String price) { this.price = price; }
	
	public String getTicketing_day() { 
		return ticketing_day; 
	}
	public void setTicketing_day(String ticketing_day) { this.ticketing_day = ticketing_day; }
	
	public String getTicket_type() { return ticket_type; }
	public void setTicket_type(String ticket_type) { this.ticket_type = ticket_type; }
	
	public String getTerm() { 
		return (term == null || term.length() == 0) ? "null" : term; 
	}
	public void setTerm(String term) { this.term = term; }
	
	public String getEffective_date() { 
		return (effective_date == null || effective_date.length() == 0) ? 
				"null" : effective_date;
	}
	public void setEffective_date(String effective_date) { this.effective_date = effective_date; }
	
	public String getExpiration_date() { 
		return (expiration_date == null || expiration_date.length() == 0) ?
				"null" : expiration_date;
	}
	public void setExpiration_date(String expiration_date) { this.expiration_date = expiration_date; }

	public String getPersonnel() { return personnel; }
	public void setPersonnel(String personnel) { this.personnel = personnel; }
	
	public String getDepTimeDB() {
		return (deppland_time == null || deppland_time.length() == 0) ? 
				"null" : "to_date('" + deppland_time + "', 'yyyyMMddHH24MI')";
	}
	public String getArrTimeDB() {
		return (arrpland_time == null || arrpland_time.length() == 0) ? 
				"null" : "to_date('" + arrpland_time + "', 'yyyyMMddHH24MI')";
	}
	public String getTicketingDayDB() {
		return (ticketing_day == null || ticketing_day.length() == 0) ? 
				"null" : "to_date('" + ticketing_day + "', 'yyyyMMddHH24MI')";
	}
	public String getEffDateDB() {
		return (effective_date == null || effective_date.length() == 0) ? 
				"null" : "to_date('" + effective_date + "', 'yyyyMMddHH24MI')";
	}
	public String getExpDateDB() {
		return (expiration_date == null || expiration_date.length() == 0) ? 
				"null" : "to_date('" + expiration_date + "', 'yyyyMMddHH24MI')";
	}
	
}
