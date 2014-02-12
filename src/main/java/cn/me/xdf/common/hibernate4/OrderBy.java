package cn.me.xdf.common.hibernate4;

import org.hibernate.criterion.Order;

public class OrderBy {

	private OrderType orderType;

	protected String field;

	public String getField() {
		return field;
	}

	protected OrderBy(String field, OrderType orderType) {
		this.field = field;
		this.orderType = orderType;
	}

	public static OrderBy asc(String field) {
		return new OrderBy(field, OrderType.ASC);
	}

	public static OrderBy desc(String field) {
		return new OrderBy(field, OrderType.DESC);
	}

	public Order getOrder() {
		Order order = null;
		if (OrderType.ASC == orderType) {
			order = Order.asc(getField());
		} else if (OrderType.DESC == orderType) {
			order = Order.desc(getField());
		}
		return order;
	}

	public static OrderBy[] asOrders(OrderBy... orderBys) {
		if (orderBys != null) {
			OrderBy[] orders = new OrderBy[orderBys.length];
			for (int i = 0; i < orderBys.length; i++) {
				orders[i] = orderBys[i];
			}
			return orders;
		} else {
			return null;
		}
	}

	public static enum OrderType {
		ASC, DESC
	}

}
