package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.TemporaryImagesDao;
import ar.edu.itba.paw.model.TemporaryImage;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class TemporaryImagesJdbcDao implements TemporaryImagesDao {

    private JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private final static RowMapper<TemporaryImage> ROW_MAPPER = new RowMapper<TemporaryImage>() {

        public TemporaryImage mapRow(ResultSet rs, int rowNum) throws SQLException {

            return new TemporaryImage(rs.getInt("imageid"),rs.getBytes("image"));

        }
    };

    @Autowired
    public TemporaryImagesJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("temporaryImages").usingGeneratedKeyColumns("imageid");
    }


    @Override
    public TemporaryImage insertImage(final byte[] image){
        final Map<String, Object> args = new HashMap<String, Object>();
        args.put("image",image);
        final int imageId = jdbcInsert.executeAndReturnKey(args).intValue();
        return new TemporaryImage(imageId,image);
    }

    @Override
    public boolean deleteImage(int imageId) {
        return jdbcTemplate.update("DELETE FROM temporaryImages WHERE imageid = ?",imageId)>0;

    }

    @Override
    public Optional<TemporaryImage> getImage(int imageId){
        final List<TemporaryImage> list = jdbcTemplate.query("SELECT * FROM temporaryImages WHERE imageid = ?;", ROW_MAPPER, imageId);
        if (list.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(list.get(0));
    }
}
