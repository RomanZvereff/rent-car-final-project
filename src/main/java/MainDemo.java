import dao.CarDao;

public class MainDemo {

    public static void main(String[] args) {
        //DBManager dbManager = DBManager.getInstance();

        CarDao carDao = new CarDao();
        System.out.println(carDao.get(1));

    }

}
