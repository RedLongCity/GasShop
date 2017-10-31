package com.redlongcitywork.gasshop.jdbcTemplateDaoImpls;

import static com.google.common.base.Preconditions.checkNotNull;
import com.redlongcitywork.gasshop.dao.OrderDao;
import com.redlongcitywork.gasshop.jdbcTemplateUtils.GasPortionRowMapper;
import com.redlongcitywork.gasshop.jdbcTemplateUtils.OrderRowMapper;
import com.redlongcitywork.gasshop.jdbcTemplateUtils.UserRowMapper;
import com.redlongcitywork.gasshop.models.GasPortion;
import com.redlongcitywork.gasshop.models.Order;
import com.redlongcitywork.gasshop.models.User;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author redlongcity 31/10/2017
 */
@Repository("orderDao")
public class OrderDaoImpl implements OrderDao {

    private static final Logger LOG = Logger.getLogger(OrderDaoImpl.class.getName());

    @Autowired
    JdbcTemplate template;

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
        List<Order> list = new ArrayList<Order>();
        list = template.query(SQL_SELECT_ALL_ORDERS,
                new OrderRowMapper());
        for (Order order : list) {
            order.setGasPortionList(findPortionsForOrder(order));
            order.setUser(findUserForOrder(order));
        }
        return list;
    }

    @Override
    public Order findById(Integer id) {
        Order order = (Order) template.query(SQL_SELECT_ORDER,
                new OrderRowMapper(),
                id);

        order.setGasPortionList(findPortionsForOrder(order));
        order.setUser(findUserForOrder(order));

        return order;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Order save(Order order) {
        template.update(SQL_INSERT_ORDER,
                order.getDate(),
                order.getStatus(),
                order.getUser().getId());

        order.setId(template.queryForObject("select last_insert_id()",
                Integer.class));
        this.insertPortions(order);
        return order;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void delete(Order order) {
        template.update(SQL_DELETE_ORDER_PORTIONS,
                order.getId());
        template.update(SQL_DELETE_ORDER,
                order.getId());
    }

    @Override
    public void update(Order order) {
        template.update(SQL_UPDATE_ORDER,
                order.getDate(),
                order.getStatus(),
                order.getUser().getId(),
                order.getId());
        template.update(SQL_DELETE_ORDER_PORTIONS,
                order.getId());
        insertPortions(order);
    }

    private List<GasPortion> findPortionsForOrder(Order order) {
        return template.query(SQL_SELECT_PORTIONS_BY_ORDER_ID,
                new GasPortionRowMapper(),
                order.getId());
    }

    private User findUserForOrder(Order order) {
        return (User) template.query(SQL_SELECT_USER_BY_ORDER_ID,
                new UserRowMapper(),
                order.getId());
    }

    private void insertPortions(Order order) {
        checkNotNull(order);
        List<GasPortion> list = order.getGasPortionList();
        for (GasPortion portion : list) {
            template.update(SQL_INSERT_ORDER_GAS_PORTIONS,
                    order.getId(),
                    portion.getId());
        }
    }
    
}
