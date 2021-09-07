package com.xiexin.dao;

import com.xiexin.bean.Person;
import com.xiexin.bean.dto.PersonDTO;

import java.util.List;
import java.util.Map;

public interface PersonDao {
    //全查
    List<Person> selectAll();

    //根据 性别查询
    List<Person> selectPersonBySex(int sex);  //不支持多个参数

    //查询总条数
    long selectCount();

    //查询总台条数 + 多个参数  第一种方式 2 个参数都是个person类中的属性，所以直接可以把person作为参数
    //实体类做参数
    long selectCountByParam01(Person person);

    //查 性别和生日 同时满足条件， 当查出sql 语句 不确定是 唯一的一条的时候，一定要用list
    //当多表联查的时候，请求的参数一定要为 map 或者 是 自己写个实体类，应用场景，多表联查的多参数查询
    List<Person> selectCountByParam02(Map map);

    //1. 查询 分值最高的人是谁 ？
    List<Person> selectPersonByZi();

    //2. 男生和女生的平均分值各是多少 ？
    List<PersonDTO> selectAvgScore();

    //男生和女生的平均分值 > 200的是什么
    List<PersonDTO> selectAvgScoreParam(int score);

    //男生和女生的平均分值 > 200的是什么  有参数 使用map 做返回值
    List<Map> selectAvgScoreParam02(int score);

    //查询姓 孙 的 第一种方式
    List<Person> selectPersonByLike(String name);

    //查询姓 孙 的 第二种方式
    List<Person> selectPersonByLike02(String name);

    //查询姓 孙 的 第三种方式
    List<Person> selectPersonByLike03(String name);

    //增加的方法
    int insertPerson(Person person);

}
