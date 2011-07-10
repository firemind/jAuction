package jauctionclient.datamodel;

public class Stock extends DataObject {
	private Integer amount = 0;
	private Long resource_id = 0L;
	
	public Stock() {
	}

	public Stock(Long resource_id, int amount) {
		this.amount = amount;
		this.resource_id = resource_id;
	}

	
	public void setResourceId(Long resource_id) {
		this.resource_id = resource_id;
	}

	public Long getResourceId() {
		return resource_id;
	}

	
	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getAmount() {
		return amount;
	}
}
