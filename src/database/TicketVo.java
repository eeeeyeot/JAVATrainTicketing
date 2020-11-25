package database;

public class TicketVo {
	private String ticket_id;
	private String deppland_place;
	private String arrpland_place;
	private String train_name;
	private String car_number;
	private String seat;
	private String deppland_time;
	private String price;
	private String ticketing_day;
	private String ticket_type;
	private String term;
	private String effective_date;
	private String expiration_date;
	
	public String getTicket_id() { return ticket_id; }
	public void setTicket_id(String ticket_id) { this.ticket_id = ticket_id; }
	
	public String getDeppland_place() { return deppland_place; }
	public void setDeppland_place(String deppland_place) { this.deppland_place = deppland_place; }
	
	public String getArrpland_place() {	return arrpland_place; }
	public void setArrpland_place(String arrpland_place) { this.arrpland_place = arrpland_place; }
	
	public String getTrain_name() { return train_name; }
	public void setTrain_name(String train_name) { this.train_name = train_name; }
	
	public String getCar_number() { return car_number; }
	public void setCar_number(String car_number) { this.car_number = car_number; }
	
	public String getSeat() { return seat; }
	public void setSeat(String seat) { this.seat = seat; }
	
	public String getDeppland_time() { return deppland_time; }
	public void setDeppland_time(String deppland_time) { this.deppland_time = deppland_time; }
	
	public String getPrice() { return price; }
	public void setPrice(String price) { this.price = price; }
	
	public String getTicketing_day() { return ticketing_day; }
	public void setTicketing_day(String ticketing_day) { this.ticketing_day = ticketing_day; }
	
	public String getTicket_type() { return ticket_type; }
	public void setTicket_type(String ticket_type) { this.ticket_type = ticket_type; }
	
	public String getTerm() { return term; }
	public void setTerm(String term) { this.term = term; }
	
	public String getEffective_date() { return effective_date; }
	public void setEffective_date(String effective_date) { this.effective_date = effective_date; }
	
	public String getExpiration_date() { return expiration_date; }
	public void setExpiration_date(String expiration_date) { this.expiration_date = expiration_date; }
}
