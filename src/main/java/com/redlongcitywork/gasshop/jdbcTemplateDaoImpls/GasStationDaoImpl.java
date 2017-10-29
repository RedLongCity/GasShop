package com.redlongcitywork.gasshop.jdbcTemplateDaoImpls;

import static com.google.common.base.Preconditions.checkNotNull;
import com.redlongcitywork.gasshop.dao.GasStationDao;
import com.redlongcitywork.gasshop.jdbcTemplateUtils.GasStationRowMapper;
import com.redlongcitywork.gasshop.models.GasStation;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author redlongctiy 29/10/2017
 */
@Repository("gasStationDao")
public class GasStationDaoImpl implements GasStationDao {

    @Autowired
    private JdbcTemplate template;

    @Override
    public List<GasStation> findAll() {
        List<GasStation> list = new ArrayList<>();
        String query = "select * from gas_stations";
        list = template.query(query, new GasStationRowMapper());
        checkNotNull(list);
        return list;
    }

    @Override
    public GasStation findById(Integer id) {
        checkNotNull(id);
        List<GasStation> list = new ArrayList<>();
        String query = "select * from gas_stations where gas_station_id=" + id;
        list = template.query(query, new GasStationRowMapper());
        checkNotNull(list);
        return list.get(0);
    }

    @Override
    public void save(GasStation station) {
        checkNotNull(station);
        String query = "insert into gas_stations (gas_station_id, gas_station_name)"+
                " values(?, ?)";
        template.update(query,new Object[]{station.getId(),station.getName()});
    }

    @Override
    public void delete(GasStation station) {
        checkNotNull(station);
        String query = "delete from gas_stations where gas_station_id="+
                station.getId();
        template.update(query);
    }

    @Override
    public void update(GasStation station) {
        checkNotNull(station);
        String query = "update gas_station set gas_station_name = ?"+
                " where gas_station_id = ?";
        template.update(query,new Object[]{station.getName(),station.getId()});
    }

}
