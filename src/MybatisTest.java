import com.xiexin.bean.Person;
import com.xiexin.bean.dto.PersonDTO;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MybatisTest {
    private SqlSession sqlSession;
    private SqlSessionFactory sqlSessionFactory;
    @Before //在@Test 注解之前，执行的方法，提取重复代码的
    public void before() throws IOException {
        //加载并读取xml
        String path="SqlMapConfig.xml";
        //import org.apache.ibatis.io.Resources;
        InputStream is = Resources.getResourceAsStream(path);
        //sql 连接的工厂类
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        sqlSession = sqlSessionFactory.openSession();
        System.out.println("sqlSession = " + sqlSession);  //sqlSession = org.apache.ibatis.session.defaults.DefaultSqlSession@71423665
    }

    //全查 select * from person
    @Test
    public void test01() {
        List<Person> personList = sqlSession.selectList("com.xiexin.dao.PersonDao.selectAll");
        for (Person person : personList) {
            System.out.println("person = " + person);
        }
        sqlSession.close();
    }

    @Test
    public void test02(){
        List<Person> personList = sqlSession.selectList("com.xiexin.dao.PersonDao.selectPersonBySex",2);
        for (Person person : personList) {
            System.out.println("person = " + person);
        }
        sqlSession.close();
    }

    //查总条数，这个主要学习的是返回数据类型，和上面的数据类型不一样
    @Test
    public void test03(){
        long o = sqlSession.selectOne("com.xiexin.dao.PersonDao.selectCount");
        System.out.println("o = " + o);
        sqlSession.close();
    }

    //带参查询 第一种方式：实体类传参-- 多见于 单表查询
    @Test
    public void test04(){
        Person person = new Person();
        person.setScore(100);
        person.setGender(2);
        Object o = sqlSession.selectOne("com.xiexin.dao.PersonDao.selectCountByParam01", person);
        System.out.println("o = " + o);
        sqlSession.close();
    }

    //带参查询 第二种方式：实体类传参-- 多见于 多表查询
    @Test
    public void test05() throws ParseException {
        String date = "2020-10-14";
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday = sf.parse(date);
        Map map = new HashMap();
        map.put("gender",2);
        map.put("birthday",birthday);
        List<Object> lists = sqlSession.selectList("com.xiexin.dao.PersonDao.selectCountByParam02", map);
        for (Object list : lists) {
            System.out.println("list = " + list);
        }
        sqlSession.close();
    }

    //子查询
    @Test
    public void test06(){
        List<Person> lists = sqlSession.selectList("com.xiexin.dao.PersonDao.selectPersonByZi");
        for (Object list : lists) {
            System.out.println("list = " + list);
        }
        sqlSession.close();
    }

    //分组查询
    @Test
    public void test07(){
        List<PersonDTO> personDTOS = sqlSession.selectList("com.xiexin.dao.PersonDao.selectAvgScore");
        for (PersonDTO personDTO : personDTOS) {
            System.out.println("personDTO = " + personDTO);
        }
        sqlSession.close();
    }

    //分组查询 + 参数
    @Test
    public void test08(){
        List<PersonDTO> personDTOS = sqlSession.selectList("com.xiexin.dao.PersonDao.selectAvgScoreParam",200);
        for (PersonDTO personDTO : personDTOS) {
            System.out.println("personDTO = " + personDTO);
        }
        sqlSession.close();
    }

    //分组查询 + 参数
    @Test
    public void test09(){
        List<Map> maps = sqlSession.selectList("com.xiexin.dao.PersonDao.selectAvgScoreParam02", 200);
        for (Map map : maps) {
            System.out.println("map = " + map);
        }
        sqlSession.close();
    }

    //查询姓 孙 的 所以你不要用 拼接的方式
    @Test
    public void test10(){
        Map map = new HashMap();
        map.put("name","孙");
        List<Person> personList = sqlSession.selectList("com.xiexin.dao.PersonDao.selectPersonByLike", "孙");
        //There is no getter for property named 'name' in 'class java.lang.String'错误 因为$ 是拼接的，没有getter 这个概念
        for (Person person : personList) {
            System.out.println("person = " + person);
        }
        sqlSession.close();
    }

    //查询名字中带有 孙 的
    @Test
    public void test11(){
        List<Person> personList = sqlSession.selectList("com.xiexin.dao.PersonDao.selectPersonByLike02", "孙");
        for (Person person : personList) {
            System.out.println("person = " + person);
        }
        sqlSession.close();
    }

    //查询名字中带有 孙 的  面试题： $ 和 # 的区别
    @Test
    public void test12(){
        List<Person> personList = sqlSession.selectList("com.xiexin.dao.PersonDao.selectPersonByLike03", "孙");
        for (Person person : personList) {
            System.out.println("person = " + person);
        }
        sqlSession.close();
    }

    //以上就是单表的 所有查询！！！ 看好这些例子，以后 模仿 去公司写
    //玩 增加 insert into ...
    @Test
    public void test13(){
        Person person = new Person();
        person.setName("吴");
        person.setGender(1);
        person.setBirthday(new Date());
        person.setAddress("加");
        person.setScore(666);
        int insert = sqlSession.insert("com.xiexin.dao.PersonDao.insertPerson", person);
        System.out.println("insert = " + insert);
        sqlSession.commit();
        sqlSession.close();
    }
}
