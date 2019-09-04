package com.spring.discountservice;

import com.spring.discountservice.model.bill.Bill;
import com.spring.discountservice.model.item.Item;
import com.spring.discountservice.model.item.ItemCat;
import com.spring.discountservice.model.user.User;
import com.spring.discountservice.model.user.UserType;
import com.spring.discountservice.service.BillingService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DiscountserviceApplicationTests {

	private BillingService billCalculator = new BillingService();

	@Test
	public void affiliateDiscount() {
		User affiliateUser = new User("Affiliate Name", UserType.AFFILIATE, new Date());

		ArrayList<Item> items = new ArrayList<>();
		items.add(new Item("item0", ItemCat.ELECTRONICS, 77.8));
		items.add(new Item("item1", ItemCat.GROCERIES, 46.5));
		items.add(new Item("item2", ItemCat.CLOTHES, 29.0));
		items.add(new Item("item3", ItemCat.FURNITURE, 106.0));

		Bill affiliateBill = new Bill(items, affiliateUser);

		// Total for non groceries : 77.8 + 29.0 + 106.0 = 212.8
		// the user is an affiliate, he will get 10% discount, the above total will be 191.52
		// add the groceries total to it, 191.52+46.5 = 238.02
		// the whole bill is subject to 10$ discount so, 228.02$

		Assert.assertEquals(228.02, billCalculator.billCalculator(affiliateBill), 0);
	}


	@Test
	public void employeeDiscount() {
		User employeeUser = new User("Employee Name", UserType.EMPLOYEE, new Date());

		ArrayList<Item> items = new ArrayList<>();
		items.add(new Item("item0", ItemCat.ELECTRONICS, 96.0));
		items.add(new Item("item1", ItemCat.GROCERIES, 49.0));


		Bill employeeBill = new Bill(items, employeeUser);

		// Total for non groceries : 96.0
		// the user is an employee, he will get 30% discount, the above total will be 67.2
		// add the groceries total to it, 67.2+49 = 116.2
		// the whole bill is subject to 5$ discount so, 111.2$

		Assert.assertEquals(111.2, billCalculator.billCalculator(employeeBill), 0);
	}

	@Test
	public void oldCustomerDiscount() {
		Date date = new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime();
		User oldUser = new User("Old Customer", UserType.CUSTOMER, date);

		ArrayList<Item> items = new ArrayList<>();
		items.add(new Item("item0", ItemCat.ELECTRONICS, 96.0));
		items.add(new Item("item1", ItemCat.GROCERIES, 49.0));
		items.add(new Item("item2", ItemCat.FURNITURE, 86.0));


		Bill oldCustomerBill = new Bill(items, oldUser);

		// Total for non groceries : 96 + 86 = 182
		// the user is an old customer, he will get 5% discount, the above total will be 172.9
		// add the groceries total to it, 172.9 + 49 = 221.9
		// the whole bill is subject to 10$ discount so, 211.9$

		Assert.assertEquals(211.9, billCalculator.billCalculator(oldCustomerBill), 0);
	}

	@Test
	public void newCustomerDiscount() {
		Date date = new GregorianCalendar(2018, Calendar.FEBRUARY, 20).getTime();
		User newUser = new User("New Customer", UserType.CUSTOMER, date);

		ArrayList<Item> items = new ArrayList<>();
		items.add(new Item("item0", ItemCat.ELECTRONICS, 123.0));
		items.add(new Item("item1", ItemCat.GROCERIES, 105.0));
		items.add(new Item("item2", ItemCat.FURNITURE, 86.0));
		items.add(new Item("item3", ItemCat.CLOTHES, 456.0));

		Bill newUserBill = new Bill(items, newUser);

		// Total for non groceries : 123 + 86 + 456 = 665
		// the user is an new customer, he won't get any discount for non groceries
		// add the groceries total to it, 665 + 105 = 770
		// the whole bill is subject to 5$ * 7 discount so 35$, -> 770-35 = 735$

		Assert.assertEquals(735, billCalculator.billCalculator(newUserBill), 0);
	}

	@Test
	public void newCustomerNoDiscount() {
		Date date = new GregorianCalendar(2018, Calendar.FEBRUARY, 20).getTime();
		User newUser = new User("New Customer No discount", UserType.CUSTOMER, date);

		ArrayList<Item> items = new ArrayList<>();
		items.add(new Item("item0", ItemCat.ELECTRONICS, 3.0));
		items.add(new Item("item1", ItemCat.GROCERIES, 15.0));
		items.add(new Item("item2", ItemCat.FURNITURE, 66.0));

		Bill newUserBill = new Bill(items, newUser);

		// Total for non groceries : 3 + 66 = 69
		// the user is an new customer, he won't get any discount for non groceries
		// add the groceries total to it, 69 + 15 = 84
		// no discount on the bill, so 84$

		Assert.assertEquals(84, billCalculator.billCalculator(newUserBill), 0);
	}

	@Test
	public void oldCustomerDiscountPercentageOnly() {
		Date date = new GregorianCalendar(2016, Calendar.FEBRUARY, 22).getTime();
		User newUser = new User("New Customer No discount", UserType.CUSTOMER, date);

		ArrayList<Item> items = new ArrayList<>();
		items.add(new Item("item0", ItemCat.ELECTRONICS, 3.0));
		items.add(new Item("item1", ItemCat.GROCERIES, 20.0));
		items.add(new Item("item2", ItemCat.FURNITURE, 66.0));

		Bill newUserBill = new Bill(items, newUser);

		// Total for non groceries : 3 + 66 = 69
		// the user is an old customer, he will get 5% on non-groceries, so 69 *5 /100 = 3.45$
		// add the groceries total to it, 69 - 3.45 + 20 = 85.55
		// no discount on the overall bill, so 85.55$

		Assert.assertEquals(85.55, billCalculator.billCalculator(newUserBill), 0);
	}
}
