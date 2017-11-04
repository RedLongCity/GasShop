package com.redlongcitywork.gasshop.template.daoimpl;

import static com.google.common.base.Preconditions.checkNotNull;
import com.redlongcitywork.gasshop.dao.FuelDao;
import com.redlongcitywork.gasshop.template.utils.FuelRowMapper;
import com.redlongcitywork.gasshop.models.Fuel;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author redlongcity 29/10/2017
 */
@Repository("fuelDao")
public class FuelDaoJdbcTemplateImpl implements FuelDao {

    private static final Logger LOG = Logger.getLogger(FuelDaoJdbcTemplateImpl.class.getName());

    @Autowired
    private JdbcTemplate template;

    private static final String SQL_SELECT_FUELS
            = "select * from fuels";

    private static final String SQL_SELECT_FUEL
            = "select * from fuels where fuel_id = ?";

    private static final String SQL_INSERT_FUEL
            = "insert into fuels (fuel_name) values(?)";

    private static final String SQL_DELETE_FUEL
            = "delete from fuels where fuel_id = ?";

    private static final String SQL_DELETE_FUEL_GAS_PORTIONS
            = "delete from gas_portions where fuels_id = ?";

    private static final String SQL_DELETE_FUEL_REFERENCES
            = "delete from refers where fuels_id = ?";

    private static final String SQL_UPDATE_FUEL
            = "update fuels set fuel_name = ?"
            + " where fuel_id = ?";

    @Override
    public List<Fuel> findAll() {
        List<Fuel> list;
        list = template.query(SQL_SELECT_FUELS, new FuelRowMapper());
        checkNotNull(list);
        return list;
    }

    @Override
    public Fuel findById(Integer id) {
        checkNotNull(id);
        return template.queryForObject(SQL_SELECT_FUEL,
                new FuelRowMapper(),
                id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Fuel save(Fuel fuel) {
        checkNotNull(fuel);
        template.update(SQL_INSERT_FUEL,
                fuel.getName());
        fuel.setId(template.queryForObject("select LAST_INSERT_ID()",
                Integer.class));
        return fuel;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void delete(Fuel fuel) {
        checkNotNull(fuel);
        template.update(SQL_DELETE_FUEL_GAS_PORTIONS,
                fuel.getId());
        template.update(SQL_DELETE_FUEL_REFERENCES,
                fuel.getId());
        template.update(SQL_DELETE_FUEL,
                fuel.getId());
    }

    @Override
    public void update(Fuel fuel) {
        checkNotNull(fuel);
        template.update(SQL_UPDATE_FUEL,
                fuel.getName(),
                fuel.getId());
    }

}
