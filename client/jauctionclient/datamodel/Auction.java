package jauctionclient.datamodel;


public class Auction extends DataObject {
	private Long user_id = 0L;
	private Integer amount = 0;
	private Long resource_id = 0L;
	private Integer price = 0;
	private Long timeleft_sec = 0L;
	
	public Auction() {
	}

	public Auction(Long id, Long user_id, Integer amount, Long resource_id, Integer price, Long timeleft_sec) {
		setId(id);
		this.user_id = user_id;
		this.amount = amount;
		this.resource_id = resource_id;
		this.price = price;
		this.timeleft_sec = timeleft_sec;
	}
	
	public Long getUserId() {
		return user_id; 
	}
	public void setUserId(Long user_id) {
		this.user_id = user_id;
	}
	
	public Integer getAmount() {
		return this.amount; 
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	public Long getResourceId() {
		return this.resource_id; 
	}
	public void setResourceId(Long resource_id) {
		this.resource_id = resource_id;
	}
	
	public Integer getPrice() {
		return this.price; 
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	
	public Long getTimeleftSec() {
		return this.timeleft_sec;	
	}
	public void setTimeleft_sec(Long timeleft_sec) {
		this.timeleft_sec = timeleft_sec;
	}
}
