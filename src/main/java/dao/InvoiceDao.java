package dao;

import appUtils.db.DBManager;
import entity.Invoice;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
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
        return null;
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
