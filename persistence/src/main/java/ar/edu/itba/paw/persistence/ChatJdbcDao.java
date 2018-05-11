package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.ChatDao;
import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.model.Chat;
import ar.edu.itba.paw.model.Message;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Repository
public class ChatJdbcDao implements ChatDao{
    private JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    private UserDao userDao;


    private final static RowMapper<Message> ROW_MAPPER = new RowMapper<Message>() {
        public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Message(rs.getInt("userFrom"), rs.getInt("userTo"), rs.getTimestamp("messageDate"), rs.getString("message"));
        }
    };

    @Autowired
    public ChatJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("messages").usingGeneratedKeyColumns("postId").usingColumns("userfrom", "userto", "message");

    }

    @Override
    public Optional<Chat> getChatBetween(int idFrom, int idTo) {
        Optional<User> userFrom=userDao.findById(idFrom);
        Optional<User> userTo=userDao.findById(idTo);
        if(!userFrom.isPresent()){
            return Optional.empty();
        }

        if(!userTo.isPresent()){
            return Optional.empty();
        }

        final List<Message> list = jdbcTemplate.query("SELECT * from messages  WHERE (userFrom = ? AND userTo=? ) OR (userFrom = ? AND userTo=? )ORDER BY messageDate ASC ;", ROW_MAPPER, idFrom,idTo,idTo,idFrom);

        Chat chat = new Chat(userFrom.get(),userTo.get(),list);
        return Optional.of(chat);
    }

    @Override
    public List<Chat> getChatsOf(int userId) {
        final  RowMapper<List<Integer>> ROW_MAPPER;
        ROW_MAPPER = new RowMapper<List<Integer>>() {
            public List<Integer> mapRow(ResultSet rs, int rowNum) throws SQLException {
                List<Integer> list = new ArrayList<Integer>();

                list.add(rs.getInt("userfrom"));
                list.add(rs.getInt("userfrom"));
                return list;
            }
        };

        List<List<Integer>> list =jdbcTemplate.query("select distinct userfrom,userto from messages where userfrom=? or userto=?;",ROW_MAPPER,userId,userId);

        Set<Integer> set = new HashSet<Integer>();

        for(List<Integer> l :list){
            set.add(l.get(0));
            set.add(l.get(1));
        }
        set.remove(userId);

        List<Chat> response = new ArrayList<Chat>();
        for(Integer id : set){
            response.add(getChatBetween(userId,id).get());
        }
        return response;
    }

    @Override
    public Optional<Message> writeMessage(int from, int to, String message) {
        Optional<User> userFrom=userDao.findById(from);
        Optional<User> userTo=userDao.findById(to);
        if(!userFrom.isPresent()){
            return Optional.empty();
        }

        if(!userTo.isPresent()){
            return Optional.empty();
        }

        final Map<String, Object> args = new HashMap<String, Object>();
        args.put("userFrom", from);
        args.put("userTo", to);
        args.put("message", message);

        jdbcInsert.execute(args);
        return Optional.of(new Message(from,to,Timestamp.from(Instant.now()),message));
    }
}
