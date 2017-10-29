package com.redlongcitywork.gasshop.jdbcTemplateDaoImpls;

import static com.google.common.base.Preconditions.checkNotNull;
import com.redlongcitywork.gasshop.dao.FuelDao;
import com.redlongcitywork.gasshop.jdbcTemplateUtils.FuelRowMapper;
import com.redlongcitywork.gasshop.models.Fuel;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author redlongcity 29/10/2017
 */
@Repository("fuelDao")
public class FuelDaoImpl implements FuelDao {

    @Autowired
    private JdbcTemplate template;

    @Override
    public List<Fuel> findAll() {
        List<Fuel> list;
        String query = "select * from fuels";
        list = template.query(query, new FuelRowMapper());
        checkNotNull(list);
        return list;
    }

    @Override
    public Fuel findById(Integer id) {
        checkNotNull(id);
        List<Fuel> list;
        String query = "select * from fuels where fuel_id=" + id;
        list = template.query(query, new FuelRowMapper());
        checkNotNull(list);
        return list.get(0);
    }

    @Override
    public void save(Fuel fuel) {
        checkNotNull(fuel);
        String query = "insert into fuels (fuel_id, fuel_name)"
                + " values(?, ?)";
        template.update(query, new Object[]{fuel.getId(), fuel.getName()});
    }

    @Override
    public void delete(Fuel fuel) {
        checkNotNull(fuel);
        String query = "delete from fuels where fuel_id=" + fuel.getId();
        template.update(query);
    }

    @Override
    public void update(Fuel fuel) {
        checkNotNull(fuel);
        String query = "update fuels set fuel_name = ?"+
                " where fuel_id=?";
        template.update(query, new Object[]{fuel.getName(),fuel.getId()});
    }

}
