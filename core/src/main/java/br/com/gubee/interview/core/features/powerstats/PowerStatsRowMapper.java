package br.com.gubee.interview.core.features.powerstats;

import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.PowerStats;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class PowerStatsRowMapper implements RowMapper<PowerStats>{
    @Override
    public PowerStats mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new PowerStats(rs.getObject("id", UUID.class),
                rs.getInt("strength"),
                rs.getInt("agility"),
                rs.getInt("dexterity"),
                rs.getInt("intelligence"),
                rs.getTimestamp("created_at").toInstant(),
                rs.getTimestamp("updated_at").toInstant());
    }
}
