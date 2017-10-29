package com.redlongcitywork.gasshop.jdbcDaoImpls;

import static com.google.common.base.Preconditions.checkNotNull;
import com.redlongcitywork.gasshop.dao.OrderDao;
import com.redlongcitywork.gasshop.jdbcUtils.ConnectionProvider;
import com.redlongcitywork.gasshop.jdbcUtils.Transaction;
import com.redlongcitywork.gasshop.models.GasPortion;
import com.redlongcitywork.gasshop.models.Order;
import com.redlongcitywork.gasshop.models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author redlongcity 29/10/2017
 */
@Repository("orderDao")
public class OrderDaoImpl implements OrderDao {

    private static final Logger LOG = Logger.getLogger(OrderDaoImpl.class.getName());

    @Autowired
    Transaction tx;

    private static final String SQL_SELECT_ALL_ORDERS
            = "select * from orders";

    private static final String SQL_SELECT_ORDER
            = "select * from orders where order_id=";

    private static final String SQL_INSERT_ORDER
            = "insert into orders (date, status, user_id) "
            + "values(?, ?, ?)";

    private static final String SQL_UPDATE_ORDER
            = "update orders set date = ?, status = ?, user_id = ? "
            + "where order_id= ?";

    private static final String SQL_DELETE_ORDER
            = "delete from orders where order_id = ?";

    private static final String SQL_INSERT_ORDER_GAS_PORTIONS
            = "insert into orders_has_gas_portions "
            + "(orders_order_id, gas_portions_gas_portions_id) values (?, ?)";

    private static final String SQL_SELECT_PORTIONS_BY_ORDER_ID
            = "select por.* from gas_portions por "
            + "inner join orders_has_gas_portions ohgp on "
            + "ohgp.gas_portions_gas_portions_id = por.gas_portions_id "
            + "where ohgp.orders_order_id = ?";

    private static final String SQL_DELETE_ORDER_PORTIONS
            = "delete from orders_has_gas_portions where order_id = ?";

    private static final String SQL_SELECT_USER_BY_ORDER_ID
            = "select u.* from users u "
            + "inner join orders ord on ord.users_user_id = "
            + "u.user_id where ord.order_id = ?";

    @Override
    public List<Order> findAll() {
        List<Order> list = new LinkedList<Order>();
        try {
            PreparedStatement statement = ConnectionProvider.getInstance().
                    getConnection().prepareStatement(SQL_SELECT_ALL_ORDERS);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                list.add(convert(set));
            }

            for (Order order : list) {
                order.setGasPortionList(findPortionsForOrder(order));
                order.setUser(findUserForOrder(order));
            }
        } catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
        return list;
    }

    @Override
    public Order findById(Integer id) {
        checkNotNull(id);

        Order order = null;
        try {
            Connection connection = ConnectionProvider.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ORDER);
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                order = convert(set);
            }
            order.setGasPortionList(findPortionsForOrder(order));
            order.setUser(findUserForOrder(order));
        } catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
        return order;
    }

    @Override
    public void save(Order order) {
        Connection connection = tx.getConnection();

        try {
            tx.begin();

            PreparedStatement statement = connection.
                    prepareStatement(SQL_INSERT_ORDER);
            statement.setInt(1, order.getId());
            statement.setDate(2, order.getDate());
            statement.setInt(3, order.getUser().getId());

            statement.executeUpdate();
            tx.commit();

            order.setId(getOrderId());
            insertOrderPortions(order);
        } catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOG.log(Level.WARNING, e.getMessage());
            }
        }
    }

    @Override
    public void delete(Order order) {
        
    }

    @Override
    public void update(Order order) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void insertOrderPortions(Order order) {
        final int orderId = order.getId();
        final List<GasPortion> list = order.getGasPortionList();

        for (GasPortion portion : list) {
            Connection connection = tx.getConnection();
            try {
                tx.begin();

                PreparedStatement statement = connection.
                        prepareStatement(SQL_INSERT_ORDER_GAS_PORTIONS);
                statement.setInt(1, orderId);
                statement.setInt(2, portion.getId());
                statement.executeUpdate();
                tx.commit();

            } catch (SQLException e) {
                LOG.log(Level.WARNING, e.getMessage());
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    LOG.log(Level.WARNING, e.getMessage());
                }
            }
        }
    }

    private List<GasPortion> findPortionsForOrder(Order order) {
        List<GasPortion> list = new LinkedList<GasPortion>();
        try {
            Connection connection = ConnectionProvider.getInstance().getConnection();
            PreparedStatement statement = connection.
                    prepareStatement(SQL_SELECT_PORTIONS_BY_ORDER_ID);
            statement.setInt(1, order.getId());
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                list.add(convertPortion(set));
            }
        } catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
        return list;
    }

    private User findUserForOrder(Order order) {
        checkNotNull(order);

        User user = null;
        try {
            Connection connection = ConnectionProvider.getInstance().getConnection();
            PreparedStatement statement = connection.
                    prepareStatement(SQL_SELECT_USER_BY_ORDER_ID);
            statement.setInt(1, order.getId());
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                user = convertUser(set);
            }
        } catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
        return user;
    }

    private Integer getOrderId() {
        Integer id = null;
        try {
            Connection connection = ConnectionProvider.getInstance().getConnection();
            PreparedStatement statement = connection.
                    prepareStatement("select LAST_INSERT_ID()");
            ResultSet set = statement.executeQuery();
            id = set.getInt(1);
        } catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
        return id;
    }

    private Order convert(ResultSet resultSet) {
        Order order = new Order();

        try {
            order.setId(resultSet.getInt("order_id"));
            order.setDate(resultSet.getDate("date"));
            order.setStatus(resultSet.getString("status"));
        } catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
        return order;
    }

    private GasPortion convertPortion(ResultSet resultSet) {
        GasPortion portion = new GasPortion();

        try {
            portion.setId(resultSet.getInt("gas_portions_id"));
        } catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
        return portion;
    }

    private User convertUser(ResultSet resultSet) {
        User user = new User();

        try {
            user.setId(resultSet.getInt("user_id"));
        } catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
        return user;
    }

}
