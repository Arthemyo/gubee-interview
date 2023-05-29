package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.model.request.HeroDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class HeroDTORowMapper implements RowMapper<HeroDTO> {
    @Override
    public HeroDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        HeroDTO heroDTO = new HeroDTO();
        heroDTO.setId(rs.getObject("id", UUID.class));
        heroDTO.setPower_stats_id(rs.getObject("power_stats_id", UUID.class));
        heroDTO.setName(rs.getString("name"));
        heroDTO.setAgility(rs.getInt("agility"));
        heroDTO.setDexterity(rs.getInt("dexterity"));
        heroDTO.setStrength(rs.getInt("strength"));
        heroDTO.setIntelligence(rs.getInt("intelligence"));
        heroDTO.setRace(rs.getString("race"));
        return heroDTO;
    }
}
