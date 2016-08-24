package junit.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zyc.crm.bean.Authority;
import com.zyc.crm.bean.Contact;
import com.zyc.crm.bean.Customer;
import com.zyc.crm.bean.CustomerDrain;
import com.zyc.crm.bean.Order;
import com.zyc.crm.bean.Role;
import com.zyc.crm.bean.SalesChance;
import com.zyc.crm.bean.SalesPlan;
import com.zyc.crm.bean.User;
import com.zyc.crm.mapper.CustomerMapper;
import com.zyc.crm.mapper.DrainMapper;
import com.zyc.crm.mapper.OrderMapper;
import com.zyc.crm.mapper.ReportMapper;
import com.zyc.crm.mapper.RoleMapper;
import com.zyc.crm.mapper.SalesChanceMapper;
import com.zyc.crm.mapper.SalesPlanMapper;
import com.zyc.crm.mapper.UserMapper;
import com.zyc.crm.model.Page;
import com.zyc.crm.service.ReportService;

public class CRMTest {

	private ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");
	private DataSource dataSource = null;
	private UserMapper userMapper = null;
	private SalesChanceMapper salesChanceMapper = null;
	private SalesPlanMapper salesPlanMapper = null;
	private CustomerMapper customerMapper= null;
	private OrderMapper orderMapper=null;
	private DrainMapper drainMapper = null;
	private RoleMapper roleMapper= null;
	private ReportMapper reportMapper= null;
	private ReportService reportService =null;
	{
		
		dataSource = ioc.getBean(DataSource.class);
		userMapper = ioc.getBean(UserMapper.class);
		salesChanceMapper = ioc.getBean(SalesChanceMapper.class);
		salesPlanMapper = ioc.getBean(SalesPlanMapper.class);
		customerMapper = ioc.getBean(CustomerMapper.class);
		orderMapper= ioc.getBean(OrderMapper.class);
		drainMapper = ioc.getBean(DrainMapper.class);
		roleMapper = ioc.getBean(RoleMapper.class);
		reportMapper= ioc.getBean(ReportMapper.class);
		reportService = ioc.getBean(ReportService.class);
	}
	
	private DateFormat dateFormat = null;
	
	{
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	}
	
	@Test
	public void testUserName(){
		User userByName = userMapper.getUserByName("bcde");
		System.out.println(userByName);
	}
	
	@Test
	public void testPage() throws ParseException{
		Map<String, Object> params = new HashMap<>();
		params.put("LIKES_name", "");
		Date minOrderDate = dateFormat.parse("1900-1-1");
		params.put("GTD_mindate", minOrderDate);
		params.put("LTD_maxdate", new Date());
		
		Page<Object[]> page = reportService.getPage("1", params);
		
		System.out.println(page.getTotalElements());
		List<Object[]> content = page.getContent();
		for (Object[] objects : content) {
			System.out.println(objects);
		}
	}
	
	/*@Test
	public void testCustomerOrder(){
		List<Customer> customer = reportMapper.getCustomer();
		System.out.println(customer.size());
		for (Customer customer2 : customer) {
			Set<Order> orders = customer2.getOrders();
			for (Order order : orders) {
				System.out.println(order);
			}
			if(customer2.getOrders().size()!=0){
				System.out.println(customer2.getOrderMoney());
			}
		}
	}*/
	
	@Test
	public void testRole(){
		Role role = roleMapper.getRole(1);
		List<Authority> authorities = role.getAuthorities();
		System.out.println(authorities.size());
	}
	
	@Test
	public void testAuthor(){
		List<Authority> parentAuthorities = roleMapper.getParentAuthorities();
		for (Authority authority : parentAuthorities) {
			List<Authority> subAuthorities = authority.getSubAuthorities();
			System.out.println(authority.getId());
			System.out.println("************");
			for (Authority authority2 : subAuthorities) {
				System.out.println(authority2.getId());
			}
		}
	}
	
	
	@Test
	public void tesrDrain(){
		CustomerDrain customerDrainById = drainMapper.getCustomerDrainById(2505);
		System.out.println(customerDrainById.getDelay());
	}
	
	@Test
	public void testOrder(){
		Order order = orderMapper.getOrder(1);
		
		System.out.println(order.getItems());
	}
	
	@Test
	public void testgetCustomerById(){
		Customer customer = customerMapper.getCustomerById(4);
		System.out.println(customer.getManager());
		System.out.println(customer.getManager().getName());
		
		System.out.println(customer.getContacts().size());
	}
	
	@Test
	public void testCustomer(){
		Customer customer = customerMapper.getCustomerById(2);
		System.out.println(customer.getManager().getName());
		
		List<Contact> allContact = customerMapper.getAllContact(4);
		System.out.println(allContact);
		
	}
	
	@Test
	public void testSelectKey(){
		SalesPlan salesPlan = new SalesPlan();
		salesPlan.setTodo("asdasd");
		salesPlan.setDate(new Date());
		SalesChance chance = new SalesChance();
		chance.setId(20001L);
		salesPlan.setChance(chance);
		
		salesPlanMapper.save(salesPlan);
		System.out.println(salesPlan.getId());
	}
	
	@Test
	public void testUUID(){
		UUID uuid = UUID.randomUUID();
		String string = uuid.toString();
		System.out.println(string);
	}
	
	@Test
	public void testQueryList(){
		User createBy = new User();
		createBy.setId(21L);
		List<SalesChance> contentByQuery = salesChanceMapper.getContentByQuery(createBy, 1, 1, 4, null, null, "老三");
		System.out.println(contentByQuery.size());
	}
	
	@Test
	public void testQueryCount(){
		User careateBy = new User();
		careateBy.setId(24L);
		long totalElementsByQuery = salesChanceMapper.getTotalElementsByQuery(careateBy, 1, null, null, "黄");
		
		System.out.println(totalElementsByQuery);
	}
	
	@Test
	public void testGetContent(){
		
		User careateBy = new User();
		careateBy.setId(24L);
		List<SalesChance> content = salesChanceMapper.getContent(careateBy, 1, 1, 5);
		
		for (SalesChance salesChance : content) {
			System.out.println(salesChance.getCustName());
		}
		
	}
	
	@Test
	public void getTotalElements(){
		User careateBy = new User();
		careateBy.setId(24L);
		
		long totalElements = salesChanceMapper.getTotalElements(careateBy, 1);
		System.out.println(totalElements);
	}
	
	
	@Test
	public void testUserMapper(){
		String name = "admin";
		String password="admin1";
		
		User user = userMapper.getUserByNameAndPassword(name, password);
		
		System.out.println(user);
//		System.out.println(user.getName());
//		System.out.println(user.getRole().getName());
	
	}
	
	@Test
	public void testConn() throws SQLException{
		Connection connection = dataSource.getConnection();
		
		System.out.println(connection);
	}
}
