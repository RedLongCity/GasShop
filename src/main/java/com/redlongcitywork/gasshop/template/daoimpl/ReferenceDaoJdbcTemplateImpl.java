package com.redlongcitywork.gasshop.template.daoimpl;

import static com.google.common.base.Preconditions.checkNotNull;
import com.redlongcitywork.gasshop.dao.ReferenceDao;
import com.redlongcitywork.gasshop.template.utils.FuelRowMapper;
import com.redlongcitywork.gasshop.template.utils.ReferenceRowMapper;
import com.redlongcitywork.gasshop.template.utils.GasStationRowMapper;
import com.redlongcitywork.gasshop.models.Fuel;
import com.redlongcitywork.gasshop.models.GasStation;
import com.redlongcitywork.gasshop.models.Reference;
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
@Repository("referenceDao")
public class ReferenceDaoJdbcTemplateImpl implements ReferenceDao {

    private static final Logger LOG = Logger.getLogger(ReferenceDaoJdbcTemplateImpl.class.getName());

    @Autowired
    private JdbcTemplate template;

    private static final String SQL_SELECT_ALL_REFERENCES
            = "select * from refers";

    private static final String SQL_SELECT_REFERENCE
            = "select * from refers where reference_id = ?";

    private static final String SQL_INSERT_REFERENCE
            = "insert into refers"
            + " (cost, gas_stations_gas_station_id, fuels_id)"
            + " values(?, ?, ?)";

    private static final String SQL_UPDATE_REFERENCE
            = "update refers set cost = ?, gas_stations_gas_station_id = ?,"
            + "fuels_id = ?, where reference_id = ?";

    private static final String SQL_DELETE_REFERENCE
            = "delete from refers wher reference_id = ?";

    private static final String SQL_SELECT_GAS_STATION_BY_REFERENCE_ID
            = " select gs.* from gas_stations gs "
            + " inner join refers ref on ref.gas_stations_gas_station_id = "
            + "gs.gas_station_id where ref.reference_id = ?";

    private static final String SQL_SELECT_FUEL_BY_REFERENCE_ID
            = "select f.* from fuels f inner join refers ref"
            + " on ref.fuels_id = f.fuel_id where ref.reference_id = ?";

    private static final String SQL_SELECT_COST_FOR_FUEL_AND_STATION
            = "select cost from refers where fuels_id = ?"
            + "and gas_stations_gas_station_id = ?";

    private static final String SQL_SELECT_AVERAGE_COST
            = "select AVG(cost) from refers where fuels_id = ?";

    private static final String SQL_SELECT_REFERENCES_BY_FUEL_ID
            = "select * from refers where fuels_id = ?";

    @Override
    public List<Reference> findAll() {
        List<Reference> list = new ArrayList();
        list = template.query(SQL_SELECT_ALL_REFERENCES,
                new ReferenceRowMapper());

        for (Reference reference : list) {
            reference.setStation(findStationForReference(reference));
            reference.setFuel(findFuelForReference(reference));
        }
        return list;
    }

    @Override
    public List<Reference> findByFuel(Fuel fuel) {
        checkNotNull(fuel);

        List<Reference> list = new ArrayList<Reference>();
        list = template.query(SQL_SELECT_REFERENCES_BY_FUEL_ID,
                new ReferenceRowMapper(),
                fuel.getId());

        for (Reference reference : list) {
            reference.setStation(findStationForReference(reference));
            reference.setFuel(findFuelForReference(reference));
        }
        return list;
    }

    @Override
    public Reference findById(Integer id) {
        checkNotNull(id);
        Reference reference = template.queryForObject(SQL_SELECT_REFERENCE,
                new ReferenceRowMapper(), id);
        reference.setStation(findStationForReference(reference));
        reference.setFuel(findFuelForReference(reference));
        return reference;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Reference save(Reference reference) {
        template.update(SQL_INSERT_REFERENCE,
                reference.getCost(),
                reference.getStation().getId(),
                reference.getFuel().getId());

        reference.setId(template.
                queryForObject("select LAST_INSERT_ID()", Integer.class));
        return reference;
    }

    @Override
    public void delete(Reference reference) {
        checkNotNull(reference);
        template.update(SQL_DELETE_REFERENCE, reference.getId());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void update(Reference reference) {
        template.update(SQL_UPDATE_REFERENCE,
                reference.getCost(),
                reference.getStation().getId(),
                reference.getFuel().getId(),
                reference.getId());
    }

    @Override
    public Float getCost(Fuel fuel, GasStation station) {
        checkNotNull(fuel);
        checkNotNull(station);
        return template.queryForObject(SQL_SELECT_COST_FOR_FUEL_AND_STATION,
                Float.class,
                fuel.getId(),
                station.getId());
    }

    @Override
    public Float getAverageCost(Fuel fuel) {
        checkNotNull(fuel);
        return template.queryForObject(SQL_SELECT_AVERAGE_COST,
                Float.class,
                fuel.getId());
    }

    private GasStation findStationForReference(Reference reference) {
        try {
            return template.queryForObject(SQL_SELECT_GAS_STATION_BY_REFERENCE_ID,
                    new GasStationRowMapper(), reference.getId());
        } catch (Exception e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
        return null;
    }

    private Fuel findFuelForReference(Reference reference) {
        try {
            return template.queryForObject(SQL_SELECT_FUEL_BY_REFERENCE_ID,
                    new FuelRowMapper(), reference.getId());
        } catch (Exception e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
        return null;
    }
}
