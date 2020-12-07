package database;

public class TicketVo {
	
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
	
	public TicketVo() {
		
	}
	
	public TicketVo(TrainDAO dao) {
		int lastID;
		if((lastID = dao.getLastTicketId()) < 0) 
			lastID = 0;
		
		ticket_id = String.format("%d", ++lastID);
	}
	
	public TicketVo(String deppland_place, String arrpland_place, String train_name,
			String car_number, String seat, String deppland_time, String price, String ticketing_day) {
		this();
		this.deppland_place = deppland_place;
		this.arrpland_place = arrpland_place;
		this.train_name = train_name;
		this.car_number = car_number;
		this.seat = seat;
		this.deppland_time = deppland_time;
		this.price = price;
		this.ticketing_day = ticketing_day;
	}
	
	public String getTicket_id() { return ticket_id; }
	public TicketVo setTicket_id(String ticket_id) { this.ticket_id = ticket_id; return this;}
	
	public String getDeppland_place() { return deppland_place; }
	public TicketVo setDeppland_place(String deppland_place) { this.deppland_place = deppland_place; return this;}
	
	public String getArrpland_place() {	return arrpland_place; }
	public TicketVo setArrpland_place(String arrpland_place) { this.arrpland_place = arrpland_place; return this;}
	
	public String getTrain_name() { 
		return (train_name == null || train_name.length() == 0) ? 
				"null" : train_name; 
	}
	public TicketVo setTrain_name(String train_name) { this.train_name = train_name; return this;}
	
	public String getCar_number() { 
		return (car_number == null || car_number.length() == 0) ? "null" : car_number; 
	}
	public TicketVo setCar_number(String car_number) { this.car_number = car_number; return this;}
	
	public String getSeat() { 
		return (seat == null || seat.length() == 0) ? "null" : seat; 
	}
	public TicketVo setSeat(String seat) { this.seat = seat; return this;}
	
	public String getDeppland_time() { 
		return deppland_time; 
	}
	public TicketVo setDeppland_time(String deppland_time) { this.deppland_time = deppland_time; return this;}
	
	public String getArrpland_time() {
		return arrpland_time;
	}
	public TicketVo setArrpland_time(String arrpland_time) {
		this.arrpland_time = arrpland_time;
		return this;
	}
	
	public String getPrice() { return price; }
	public TicketVo setPrice(String price) { this.price = price; return this;}
	
	public String getTicketing_day() { 
		return ticketing_day; 
	}
	public TicketVo setTicketing_day(String ticketing_day) { this.ticketing_day = ticketing_day; return this;}
	
	public String getPersonnel() { return personnel; }
	public TicketVo setPersonnel(String personnel) { this.personnel = personnel; return this;}
	
	public String getDepTimeByFormat() {
		return (deppland_time == null || deppland_time.length() == 0) ? 
				"null" : "to_date('" + deppland_time + "', 'yyyyMMddHH24MI')";
	}
	public String getArrTimeByFormat() {
		return (arrpland_time == null || arrpland_time.length() == 0) ? 
				"null" : "to_date('" + arrpland_time + "', 'yyyyMMddHH24MI')";
	}
	public String getTicketingDayByFormat() {
		return (ticketing_day == null || ticketing_day.length() == 0) ? 
				"null" : "to_date('" + ticketing_day + "', 'yyyyMMddHH24MI')";
	}
}
