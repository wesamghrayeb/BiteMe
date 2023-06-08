package common;

/**
 * save W4C details for business user
 * @author asem
 *
 */
public class W4CBussiness extends W4CNormal {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int userid;
	/**
	 * available money
	 */
	private double money;
	/**
	 * how much to add each day/month
	 */
	private double value;
	private String type;

	public double getValue() {
		return value;
	}

	public W4CBussiness(int code, User user, int userid, double money, double value, String type) {
		super(code, user);
		this.userid = userid;
		this.money = money;
		this.value = value;
		this.type = type;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public W4CBussiness(int code, User user, double money) {
		super(code, user);
		this.money = money;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	@Override
	public String toString() {
		return super.toString() + " W4CBussiness [money=" + money + "]";
	}

}