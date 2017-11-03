package com.redlongcitywork.gasshop.dao.jdbc.template.impl;

import static com.google.common.base.Preconditions.checkNotNull;
import com.redlongcitywork.gasshop.dao.GasStationDao;
import com.redlongcitywork.gasshop.jdbc.template.utils.GasStationRowMapper;
import com.redlongcitywork.gasshop.models.GasStation;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author redlongctiy 29/10/2017
 */
@Repository("gasStationDao")
public class GasStationDaoJdbcTemplateImpl implements GasStationDao {

    private static final Logger LOG = Logger.getLogger(GasStationDaoJdbcTemplateImpl.class.getName());

    @Autowired
    private JdbcTemplate template;

    private static final String SQL_SELECT_GAS_STATIONS
            = "select * from gas_stations";

    private static final String SQL_SELECT_GAS_STATION
            = "select * from gas_stations where gas_station_id = ?";

    private static final String SQL_INSERT_GAS_STATION
            = "insert into gas_stations (gas_station_name)"
            + " values(?)";

    private static final String SQL_DELETE_GAS_STATION
            = "delete from gas_stations where gas_station_id = ?";

    private static final String SQL_DELETE_GAS_STATION_GAS_PORTIONS
            = "delete from gas_portions where gas_stations_gas_station_id = ?";

    private static final String SQL_DELETE_GAS_STATION_REFERENCES
            = "delete from refers where gas_stations_gas_station_id = ?";

    private static final String SQL_UPDATE_GAS_STATION
            = "update gas_station set gas_station_name = ?"
            + " where gas_station_id = ?";

    @Override
    public List<GasStation> findAll() {
        List<GasStation> list = new ArrayList<>();
        list = template.query(SQL_SELECT_GAS_STATIONS, new GasStationRowMapper());
        checkNotNull(list);
        return list;
    }

    @Override
    public GasStation findById(Integer id) {
        checkNotNull(id);
        return template.queryForObject(SQL_SELECT_GAS_STATION,
                new GasStationRowMapper(),
                id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public GasStation save(GasStation station) {
        checkNotNull(station);
        template.update(SQL_INSERT_GAS_STATION,
                station.getName());
        station.setId(template.
                queryForObject("select LAST_INSERT_ID()", Integer.class));
        return station;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void delete(GasStation station) {
        checkNotNull(station);
        template.update(SQL_DELETE_GAS_STATION_REFERENCES,
                station.getId());
        template.update(SQL_DELETE_GAS_STATION_GAS_PORTIONS,
                station.getId());
        template.update(SQL_DELETE_GAS_STATION,
                station.getId());
    }

    @Override
    public void update(GasStation station) {
        checkNotNull(station);
        template.update(SQL_UPDATE_GAS_STATION,
                station.getName(),
                station.getId());
    }

}
