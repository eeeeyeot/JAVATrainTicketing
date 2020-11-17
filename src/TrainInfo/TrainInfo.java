package TrainInfo;

public class TrainInfo
{
	private String trainName;
	private String trainNo;
	private String depplandTime;
	private String arrplandTime;
	
	public String getName() { return trainName; }
	public void setName(String name) { this.trainName = name; }
	
	public String getTrainNo() { return trainNo; }
	public void setTrainNo(String trainNo) { this.trainNo = trainNo; }
	
	public String getDepplandTime() { return depplandTime; }
	public void setDepplandTime(String depplandTime) { this.depplandTime = depplandTime; }
	
	public String getArrplandTime() { return arrplandTime; }
	public void setArrplandTime(String arrplandTime) { this.arrplandTime = arrplandTime; }
	
	public String toString() {
		return "[기차 이름 : " + trainName + "][기차 번호 : " + trainNo + "][출발 시간 : " + depplandTime + "][도착 시간 : " + arrplandTime + "]";
	}
}
