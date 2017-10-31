package com.redlongcitywork.gasshop.jdbcTemplateDaoImpls;

import com.redlongcitywork.gasshop.dao.GasPortionDao;
import com.redlongcitywork.gasshop.jdbcTemplateUtils.FuelRowMapper;
import com.redlongcitywork.gasshop.jdbcTemplateUtils.GasPortionRowMapper;
import com.redlongcitywork.gasshop.jdbcTemplateUtils.GasStationRowMapper;
import com.redlongcitywork.gasshop.models.Fuel;
import com.redlongcitywork.gasshop.models.GasPortion;
import com.redlongcitywork.gasshop.models.GasStation;
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
 * @author redlongcity 30/10/2017
 */
@Repository("gasPortionDao")
public class GasPortionDaoImpl implements GasPortionDao {

    private static final Logger LOG = Logger.getLogger(com.redlongcitywork.gasshop.jdbcDaoImpls.GasPortionDaoImpl.class.getName());

    @Autowired
    private JdbcTemplate template;

    private static final String SQL_SELECT_ALL_GAS_PORTIONS
            = "select * from gas_portions";

    private static final String SQL_SELECT_GAS_PORTION
            = "select * from gas_portions where gas_portion_id = ?";

    private static final String SQL_INSERT_GAS_PORTION
            = "insert into gas_portions"
            + " (amount, gas_stations_gas_station_id, fuels_id)"
            + " values(?, ?, ?)";

    private static final String SQL_UPDATE_GAS_PORTION
            = "update gas_portions set amount = ?, gas_stations_gas_station_id = ?,"
            + "fuels_id = ?, where gas_portion_id = ?";

    private static final String SQL_DELETE_GAS_PORTION
            = "delete from gas_portions wher gas_portion_id = ?";

    private static final String SQL_SELECT_GAS_STATION_BY_GAS_PORTION_ID
            = " select gs.* from gas_stations gs "
            + " inner join gas_portions gp on gp.gas_stations_gas_station_id = "
            + "gs.gas_station_id where gp.gas_portion_id = ?";

    private static final String SQL_SELECT_FUEL_BY_GAS_PORTION_ID
            = "select f.* from fuels f inner join gas_portions gp"
            + " on gp.fuels_id = f.fuel_id where gp.gas_portion_id = ?";

    @Override
    public List<GasPortion> findAll() {
        List<GasPortion> list = new ArrayList();
        list = template.query(SQL_SELECT_ALL_GAS_PORTIONS,
                new GasPortionRowMapper());

        for (GasPortion portion : list) {
            portion.setStation(findStationForGasPortion(portion));
            portion.setFuel(findFuelForGasPortion(portion));
        }
        return list;
    }

    @Override
    public GasPortion findById(Integer id) {
        GasPortion portion = template.queryForObject(SQL_SELECT_GAS_PORTION,
                new GasPortionRowMapper(), id);
        portion.setStation(findStationForGasPortion(portion));
        portion.setFuel(findFuelForGasPortion(portion));
        return portion;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public GasPortion save(GasPortion portion) {
        template.update(SQL_INSERT_GAS_PORTION, 
                portion.getAmount(),
                portion.getStation().getId(), 
                portion.getFuel().getId());

        portion.setId(template.
                queryForObject("select LAST_INSERT_ID()", Integer.class));
        return portion;
    }

    @Override
    public void delete(GasPortion portion) {
        template.update(SQL_DELETE_GAS_PORTION, portion.getId());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void update(GasPortion portion) {
        template.update(SQL_UPDATE_GAS_PORTION, 
                portion.getAmount(),
                portion.getStation().getId(),
                portion.getFuel().getId(),
                portion.getId());
    }

    private GasStation findStationForGasPortion(GasPortion portion) {
        try {
            return template.queryForObject(SQL_SELECT_GAS_STATION_BY_GAS_PORTION_ID,
                    new GasStationRowMapper(), portion.getId());
        } catch (Exception e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
        return null;
    }

    private Fuel findFuelForGasPortion(GasPortion portion) {
        try {
            return template.queryForObject(SQL_SELECT_FUEL_BY_GAS_PORTION_ID,
                    new FuelRowMapper(), portion.getId());
        } catch (Exception e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
        return null;
    }
}
