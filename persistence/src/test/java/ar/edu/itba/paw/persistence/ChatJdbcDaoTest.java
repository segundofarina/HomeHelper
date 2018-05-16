package ar.edu.itba.paw.persistence;


import ar.edu.itba.paw.interfaces.daos.ChatDao;
import ar.edu.itba.paw.model.Chat;
import ar.edu.itba.paw.model.Message;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Rollback

public class ChatJdbcDaoTest {

    @Autowired
    ChatDao chatDao;

    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void  GetChatBetweenTest(){
        Optional<Chat> response = chatDao.getChatBetween(Const.USER2_ID,Const.SPROVIDER2_ID);
        assertTrue(response.isPresent());
        Chat chat = response.get();

        assertEquals(Const.USER2_ID,chat.getGreen().getId());
        assertEquals(Const.SPROVIDER2_ID,chat.getGrey().getId());

        assertEquals(3,chat.getMessages().size());
    }


    @Test
    public void  GetChatsOfTest(){
        List<Chat> list = chatDao.getChatsOf(Const.USER2_ID);

        assertEquals(2,list.size());

        chatDao.writeMessage(6,Const.SERVICETYPE3_ID,"Hola");
        chatDao.writeMessage(6,Const.SERVICETYPE3_ID,"Hola");
        chatDao.writeMessage(6,Const.SERVICETYPE3_ID,"Hola");
        chatDao.writeMessage(Const.SERVICETYPE3_ID,6,"Hola");

        list = chatDao.getChatsOf(6);
        assertEquals(1,list.size());

    }

    @Test
    public void writeMessageTest(){
        int count =JdbcTestUtils.countRowsInTable(jdbcTemplate,"messages");
        final String message = "Hello";
        Optional<Message> response =chatDao.writeMessage(Const.USER_ID,Const.SPROVIDER_ID,message);
        assertTrue(response.isPresent());

        assertEquals(Const.USER_ID,response.get().getFrom());
        assertEquals(Const.SPROVIDER_ID,response.get().getTo());
        assertEquals(message,response.get().getMessage());
        assertEquals(count+1,JdbcTestUtils.countRowsInTable(jdbcTemplate,"messages"));
    }


}
