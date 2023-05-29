package br.com.gubee.interview.core.features.powerstats;

import br.com.gubee.interview.model.PowerStats;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Repository
@RequiredArgsConstructor
public class PowerStatsRepository {

    private static final String GET_POWER_STATS_BY_ID = "SELECT * FROM power_stats WHERE power_stats.id = :id";

    private static final String CREATE_POWER_STATS_QUERY = "INSERT INTO power_stats" +
        " (strength, agility, dexterity, intelligence)" +
        " VALUES (:strength, :agility, :dexterity, :intelligence) RETURNING id";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    UUID create(PowerStats powerStats) {
        return namedParameterJdbcTemplate.queryForObject(
            CREATE_POWER_STATS_QUERY,
            new BeanPropertySqlParameterSource(powerStats),
            UUID.class);
    }

    public Optional<PowerStats> getPowerStatsByID(String id){
        try{
            SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", UUID.fromString(id));

            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(GET_POWER_STATS_BY_ID, namedParameters, new PowerStatsRowMapper()));
        }catch (Exception e){
            e.printStackTrace();
            return Optional.ofNullable(null);
        }
     }
}
