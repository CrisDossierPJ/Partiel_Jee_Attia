package com.christian.attia.partiel;

import org.junit.jupiter.api.Test;

import com.christian.attia.partiel.database.entities.Order;
import com.christian.attia.partiel.database.entities.Product;
import com.christian.attia.partiel.database.entities.ProductOrder;
import com.christian.attia.partiel.database.entities.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserTest {
	@Test
	public void test1() {
		User user = mock(User.class);
		
		
		when(user.getId()).thenReturn((long)1);
		
		Order order = new Order();
		order.setUser(user);
		
		assertEquals(order.getUser().getId(), 1);
		
		
		
		
		
		
	}
	@Test
	public void test2() {
		Order order = mock(Order.class);
		Product product = mock(Product.class);
		
		when(order.getId()).thenReturn((long)1);
		when(product.getId()).thenReturn((long)1);
		
		ProductOrder po = new ProductOrder();
		po.setOrder(order);
		po.setProduct(product);
		
		assertEquals(po.getOrder().getId(), 1);
		assertEquals(po.getProduct().getId(), 1);
		
	}

}
