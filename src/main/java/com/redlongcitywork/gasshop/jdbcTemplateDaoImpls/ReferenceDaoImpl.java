package com.redlongcitywork.gasshop.jdbcTemplateDaoImpls;

import com.redlongcitywork.gasshop.dao.ReferenceDao;
import com.redlongcitywork.gasshop.jdbcTemplateUtils.FuelRowMapper;
import com.redlongcitywork.gasshop.jdbcTemplateUtils.ReferenceRowMapper;
import com.redlongcitywork.gasshop.jdbcTemplateUtils.GasStationRowMapper;
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
public class ReferenceDaoImpl implements ReferenceDao {

    private static final Logger LOG = Logger.getLogger(ReferenceDaoImpl.class.getName());

    @Autowired
    private JdbcTemplate template;

    private static final String SQL_SELECT_ALL_REFERENCES
            = "select * from references";

    private static final String SQL_SELECT_REFERENCE
            = "select * from references where reference_id = ?";

    private static final String SQL_INSERT_REFERENCE
            = "insert into references"
            + " (cost, gas_stations_gas_station_id, fuels_id)"
            + " values(?, ?, ?)";

    private static final String SQL_UPDATE_REFERENCE
            = "update references set cost = ?, gas_stations_gas_station_id = ?,"
            + "fuels_id = ?, where reference_id = ?";

    private static final String SQL_DELETE_REFERENCE
            = "delete from references wher reference_id = ?";

    private static final String SQL_SELECT_GAS_STATION_BY_REFERENCE_ID
            = " select gs.* from gas_stations gs "
            + " inner join references ref on ref.gas_stations_gas_station_id = "
            + "gs.gas_station_id where ref.reference_id = ?";

    private static final String SQL_SELECT_FUEL_BY_REFERENCE_ID
            = "select f.* from fuels f inner join references ref"
            + " on ref.fuels_id = f.fuel_id where ref.reference_id = ?";

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
    public Reference findById(Integer id) {
        Reference reference = template.queryForObject(SQL_SELECT_REFERENCE,
                new ReferenceRowMapper(), id);
        reference.setStation(findStationForReference(reference));
        reference.setFuel(findFuelForReference(reference));
        return reference;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void save(Reference reference) {
        template.update(SQL_INSERT_REFERENCE,
                reference.getCost(),
                reference.getStation().getId(),
                reference.getFuel().getId());

        reference.setId(template.
                queryForObject("select LAST_INSERT_ID()", Integer.class));
    }

    @Override
    public void delete(Reference reference) {
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
