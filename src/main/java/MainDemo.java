import dao.OrderDao;
import entity.Order;

import java.util.ArrayList;
import java.util.List;

public class MainDemo {

    public static void main(String[] args) {
        //DBManager dbManager = DBManager.getInstance();

        OrderDao orderDao = new OrderDao();
        List<Order> orderList = orderDao.getAll();
        List<Order> orders = new ArrayList<>();
        for(Order order : orderList) {
            if(order.getBranch().getProfile().getProfileId() == 3) {
                orders.add(order);
                System.out.println(order);
            }
        }



    }

}
