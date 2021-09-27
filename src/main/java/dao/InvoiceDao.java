package dao;

import appUtils.db.DBManager;
import entity.*;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class InvoiceDao implements Dao<Invoice> {

    private static final Logger logger = LogManager.getLogger(InvoiceDao.class);
    private static final Lock CONNECTION_LOCK = new ReentrantLock();
    private static final DBManager dbManager = DBManager.getInstance();

    @Override
    public Optional<Invoice> get(long id) {
        return Optional.empty();
    }

    @Override
    public List<Invoice> getAll() {
        List<Invoice> invoiceList = new ArrayList<>();
        String getAllInvoiceQuery = "select  * from INVOICE;";
        try(Connection connection = dbManager.getConnection();
            Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(getAllInvoiceQuery);
            while(resultSet.next()) {
                Invoice invoice = new Invoice();
                invoice.setInvoiceId(resultSet.getLong("ID"));
                invoice.setInvoiceNumber(resultSet.getInt("INVOICE_NUM"));

                int carId = resultSet.getInt("CAR_ID");
                CarDao carDao = new CarDao();
                Optional<Car> optionalCar = carDao.get(carId);
                optionalCar.ifPresent(invoice::setCar);

                long profileId = resultSet.getLong("PROFILE_ID");
                ProfileDao profileDao = new ProfileDao();
                Optional<Profile> optionalProfile = profileDao.get(profileId);
                optionalProfile.ifPresent(invoice::setCustomer);

                invoice.setDescriptionOfDamage(resultSet.getString("DEMG_DESC"));
                invoice.setAmount(resultSet.getFloat("AMOUNT"));
                invoice.setAccount(resultSet.getString("IBAN_ACC"));

                long orderId = resultSet.getLong("ORDER_ID");
                OrderDao orderDao = new OrderDao();
                Optional<Order> optionalOrder = orderDao.get(orderId);
                optionalOrder.ifPresent(invoice::setOrder);

                invoiceList.add(invoice);
            }
        }catch(SQLException e) {
            logger.debug(ExceptionUtils.getStackTrace(e));
        }
        return invoiceList;
    }

    @Override
    public long create(Invoice invoice) {
        long invoiceId = -1;
        ResultSet generatedKeys;
        CONNECTION_LOCK.lock();
        String createInvoiceQuery = "insert into INVOICE values (?,?,?,?,?,?,?,?);";
        try(Connection connection = dbManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(createInvoiceQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setNull(1, Types.BIGINT);
            preparedStatement.setInt(2, invoice.getInvoiceNumber());
            preparedStatement.setLong(3, invoice.getCar().getCarId());
            preparedStatement.setLong(4, invoice.getCustomer().getProfileId());
            preparedStatement.setString(5, invoice.getDescriptionOfDamage());
            preparedStatement.setFloat(6, invoice.getAmount());
            preparedStatement.setString(7, invoice.getAccount());
            preparedStatement.setLong(8, invoice.getOrder().getOrderId());
            preparedStatement.executeUpdate();
            generatedKeys = preparedStatement.getGeneratedKeys();
            if(generatedKeys.next()) {
                invoiceId = generatedKeys.getLong(1);
            }
            CONNECTION_LOCK.unlock();
        }catch(SQLException e) {
            CONNECTION_LOCK.unlock();
            logger.debug(ExceptionUtils.getStackTrace(e));
        }
        return invoiceId;
    }

    @Override
    public void update(Invoice invoice, String[] params) {
    }

    @Override
    public void delete(long id) {
    }
}
