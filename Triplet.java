public class Triplet {
	private int buildingNum;
	private int executed_time;
	private int total_time;

	public Triplet(){}

	public Triplet(int buildingNum, int executed_time, int total_time) {
		this.setBuildingNum(buildingNum);
		this.setExecuted_time(executed_time);
		this.setTotal_time(total_time);
	}

	public int getBuildingNum() {return buildingNum;}
	public int getTotal_time() {return total_time;}
	public int getExecuted_time() {return executed_time;}

	public void setBuildingNum(int buildingNum) {this.buildingNum = buildingNum;}
	public void setTotal_time(int total_time) {this.total_time = total_time;}
	public void setExecuted_time(int executed_time) {this.executed_time = executed_time;}

}
