//import com.wileybros.flooringmastery.dao.Dao;
//import com.wileybros.flooringmastery.dao.DaoImpl;
//import com.wileybros.flooringmastery.dto.Order;
//import com.wileybros.flooringmastery.dto.Product;
//
//import com.wileybros.flooringmastery.service.ServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//public class ServiceImplTest {
//    private Dao dao;
//    private ServiceImpl service;
//
//    @BeforeEach
//    public void setUp() {
//        dao = new DaoImpl();
//        service = new ServiceImpl(dao);
//    }
//
//    @Test
//    public void testAddOrder() {
//        Order order = service.createOrder("TestCustomerAddOrderService", "TX", "Laminate", new BigDecimal(5000), LocalDate.now().plusDays(1));
//        assertTrue(service.addOrder(order));
//    }
//
//
//    @Test
//    public void testGetOrdersOnData(){
//        LocalDate date = LocalDate.now();
//        Set<Order> ordersOnDate = service.getOrdersOnData(date);
//
//        assertTrue(ordersOnDate.isEmpty());
//
//        Object[] args = new Object[]{"TestCustomerGetOrdersOnDataOrderService", "TX", "Laminate", new BigDecimal(5000)};
//        LocalDate futureDate = LocalDate.now().plusDays(1);
//        service.addOrder(args, futureDate);
//
//        Set<Order> ordersOnDatePlusOne = service.getOrdersOnData(futureDate);
//        assertFalse(ordersOnDatePlusOne.isEmpty());
//    }
//
//    @Test
//    public void testUpdateOrder(){
//        Object[] args = new Object[]{"TestCustomerUpdateOrderService", "CA", "Tile", new BigDecimal(3000)};
//        Integer id = 157539671;
//
//        assertTrue(service.addOrder(args, LocalDate.now()));
//
////        assertTrue(service.updateOrder(id, args)); TODO Fix updateOrder test in ServiceImplTest
//    }
//
//    @Test
//    public void testRemoveOrder(){
//        Object[] args = new Object[]{"TestCustomerRemoveOrderService", "CA", "Wood", new BigDecimal(200)};
//        LocalDate futureDate = LocalDate.now().plusDays(1);
//        boolean result = service.addOrder(args, futureDate);
//        assertTrue(result);
//
//        Integer id = dao.getNextID();
//
//        assertTrue(service.removeOrder(id));
//    }
//    @Test
//    public void testExportAllData() {
//        assertTrue(service.exportAllData());
//    }
//
//    @Test
//    public void testGetStateAbrs() {
//        Set<String> stateAbrs = service.getStateAbrs();
//        assertFalse(stateAbrs.isEmpty());
//    }
//
//    @Test
//    public void testGetProducts() {
//        Set<Product> products = service.getProducts();
//        assertFalse(products.isEmpty());
//    }
//}
